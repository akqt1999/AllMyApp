package etn.app.danghoc.likeuberrider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import etn.app.danghoc.likeuberrider.Common.Common;
import etn.app.danghoc.likeuberrider.Model.UserUtils;

public class ActivityHome extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavigationView navigationView;
    NavController navController;
    public static final int PICK_IMAGE_REQUEST = 123;

    private StorageReference storageReference;

    private Uri imageUri;
    ImageView imgAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        init();
    }

    private void init() {


        storageReference = FirebaseStorage.getInstance().getReference();

        //event click item nav
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_sign_out) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Sign out");
                builder.setMessage("Do you want really to sign out ?");
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ActivityHome.this, ActivitySplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }
                })
                        .setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(dialog1 -> {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(getResources().getColor(R.color.blue));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(getResources().getColor(R.color.red));

                });

                dialog.show();

            } else {
                drawer.close();

            }

            return true;
        });


        //set data for user
        View headerView = navigationView.getHeaderView(0);
        TextView txtName = headerView.findViewById(R.id.txt_name);
        TextView txtPhoneNumber = headerView.findViewById(R.id.txt_phone);
        imgAvatar = headerView.findViewById(R.id.img_avatar);

        txtName.setText(Common.buildWelcomeMessage());
        txtPhoneNumber.setText(Common.currentRider != null ? Common.currentRider.getPhoneNumber() : "");
        if (Common.currentRider != null && Common.currentRider.getImageAvatar() != null
                && !TextUtils.isEmpty(Common.currentRider.getImageAvatar())) {
            Glide.with(this).load(Common.currentRider.getImageAvatar())
                    .into(imgAvatar);
        }

        //open folder image
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                imageUri = data.getData();
                imgAvatar.setImageURI(imageUri);
                showDialogUpload();
            }
        }
    }

    private void showDialogUpload() {
        //waiting dialog
        AlertDialog.Builder waitingBuilder = new AlertDialog.Builder(this);
        waitingBuilder.setCancelable(false)
                .setMessage("Waiting...");
        AlertDialog waitingDialog = waitingBuilder.create();

        AlertDialog.Builder builderChange = new AlertDialog.Builder(this);
        builderChange.setTitle("Change avatar");
        builderChange.setMessage("Do you want really to change avatar");
        builderChange.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builderChange.setPositiveButton("Change", (dialog, which) -> {
            if (imageUri != null) {
                waitingDialog.show();
                String uniqueImage = FirebaseAuth.getInstance().getCurrentUser().getUid();
                StorageReference avatarFolder = storageReference.child("avatar/" + uniqueImage);
                avatarFolder.putFile(imageUri)
                        .addOnFailureListener(e -> {
                            waitingDialog.dismiss();
                            Snackbar.make(drawer, e.getMessage(), Snackbar.LENGTH_LONG).show();

                        })
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                waitingDialog.dismiss();
                                avatarFolder.getDownloadUrl().addOnSuccessListener(uri -> {
                                    Map<String, Object> updateData = new HashMap<>();
                                    updateData.put("avatar", uri.toString());
                                    UserUtils.updateData(drawer, updateData);
                                });
                            }
                        }).addOnProgressListener(taskSnapshot -> {
                    double process = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    waitingBuilder.setMessage(new StringBuilder("Uploading:").append(process).append("%"));
                });
            }
        });

        AlertDialog dialogChange = builderChange.create();
        dialogChange.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}