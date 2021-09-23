package etn.app.danghoc.likeuber;

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

import etn.app.danghoc.likeuber.Mode.UserUtils;

public class ActivityHome extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =823 ;
    private DrawerLayout drawer;
    NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;

    private AlertDialog waitingDialog;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode== Activity.RESULT_OK){
            if(data!=null&&data.getData()!=null){
                imageUri=data.getData();
                imgAvatar.setImageURI(imageUri);
                showDialogUpload();
            }
        }
    }

    private void showDialogUpload() {

        waitingDialog=new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Waisting...")
                .create();

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHome.this);
        builder.setTitle("Change avatar")
                .setMessage("Do you want really to change avatar?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Upload", (dialog, which) -> {
                    if(imageUri!=null){
                        waitingDialog.show();
                        String uniqueName=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        StorageReference avatarFolder=storageReference.child("avatar/"+uniqueName);
                        avatarFolder.putFile(imageUri)
                                .addOnFailureListener(e -> {
                                    waitingDialog.dismiss();
                                    Snackbar.make(drawer,e.getMessage(),Snackbar.LENGTH_LONG).show();
                                }).addOnCompleteListener(task -> { // neu upload thanh cong thi lay link de len avatar
                                    if(task.isSuccessful()){
                                        waitingDialog.dismiss();
                                        avatarFolder.getDownloadUrl().addOnSuccessListener(uri -> {
                                            Map<String,Object>updateData=new HashMap<>();
                                                updateData.put("avatar",uri.toString());
                                            UserUtils.updateUser(drawer,updateData);
                                        });
                                    }
                                })
                        .addOnProgressListener(taskSnapshot -> {
                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            waitingDialog.setMessage(new StringBuilder("Uploading:").append(progress).append("%"));
                        });
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialog1 -> {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(getResources().getColor(R.color.colorAccent));
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(getResources().getColor(android.R.color.holo_red_dark));

        });
        dialog.show();
    }

    private void init() {



        storageReference= FirebaseStorage.getInstance().getReference();


        //event click item nav
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_sign_out) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHome.this);
                builder.setTitle("Sign out")
                        .setMessage("Do you want really to sign out?")
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("Sign out", (dialog, which) -> {
                            Common.geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(ActivityHome.this, ActivitySignIn.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        })
                .setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(dialog1 -> {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(getResources().getColor(R.color.colorAccent));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(getResources().getColor(android.R.color.holo_red_dark));

                });
                dialog.show();
                }

            return true;
        });

        //set data for user
        View headerView = navigationView.getHeaderView(0);
        TextView txtName = headerView.findViewById(R.id.txt_name);
        TextView txtPhone = headerView.findViewById(R.id.txt_phone);
        TextView txtStar = headerView.findViewById(R.id.txt_star);
         imgAvatar=headerView.findViewById(R.id.img_avatar);

        txtName.setText(Common.buildWelcomeMessage());
        txtPhone.setText(Common.currentUser != null ? Common.currentUser.getPhoneNumber() : "");
        txtStar.setText(Common.currentUser != null ? Common.currentUser.getRating() + "" : "0");
        if(Common.currentUser!=null&&Common.currentUser.getAvatar()!=null
        &&!TextUtils.isEmpty(Common.currentUser.getAvatar())) {
            Glide.with(this)
                    .load(Common.currentUser.getAvatar())
                    .into(imgAvatar);
        }

        //upLoad user
        imgAvatar.setOnClickListener(v -> { // cai dat  modul
                   Intent intent=new Intent();
                   intent.setType("image/*");
                   intent.setAction(Intent.ACTION_GET_CONTENT);
                   startActivityForResult(intent,PICK_IMAGE_REQUEST);
        });



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