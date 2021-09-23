package etn.app.danghoc.demngaylenlich;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;

import etn.app.danghoc.demngaylenlich.Commonn.Commonn;
import etn.app.danghoc.demngaylenlich.Model.User;
import etn.app.danghoc.demngaylenlich.ui.dashboard.DashboardFragment;
import etn.app.danghoc.demngaylenlich.ui.home.HomeFragment;
import etn.app.danghoc.demngaylenlich.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity {

    DashboardFragment dashboardFragment;
    HomeFragment homeFragment;
    NotificationsFragment notificationsFragment;
    Fragment fragmentActive;
    FragmentManager fm=getSupportFragmentManager();

    int year,date,month;
    String daySuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        init();
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)navView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        /*
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
////        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

         */
    }

    private   BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    fm.beginTransaction().hide(fragmentActive).show(homeFragment).commit();
                    fragmentActive=homeFragment;
                    break;
                case R.id.navigation_create_new_note:
                    showLayoutAdd();
                    break;
                case R.id.navigation_dashboard:
                    fm.beginTransaction().hide(fragmentActive).show(dashboardFragment).commit();
                    fragmentActive=dashboardFragment;
                    break;
            }
            return true;
        }
    };

    private void showLayoutAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_write_note = inflater.inflate(R.layout.layout_add_note, null);
        builder.setView(layout_write_note);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnChooseDaySuccess = layout_write_note.findViewById(R.id.btnChooseDaySuccess);
        Button btnChoose = layout_write_note.findViewById(R.id.btnChoose);
        Button btnCancel = layout_write_note.findViewById(R.id.btnCancel);
        EditText edtWriteNote = layout_write_note.findViewById(R.id.edtWriteNote);
        ProgressBar progressBar = layout_write_note.findViewById(R.id.progressBar);

        btnChooseDaySuccess.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
            date = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    //to do ...
                    daySuccess = dayOfMonth + "/" + month + "/" + year;
                    btnChooseDaySuccess.setText(daySuccess);

                }
            }, year, month, date);

            datePickerDialog.show();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnChoose.setOnClickListener(v -> {
            if (daySuccess == null) {
                Toast.makeText(this, "Please choose date success", Toast.LENGTH_SHORT).show();
            } else if (edtWriteNote.getText().toString() == null || edtWriteNote.getText().toString().trim()
                    .equalsIgnoreCase("")) {
                Toast.makeText(this, "Please enter the note", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);

                String dayStart = date + "/" + month + "/" + year;
                Calendar calendar = Calendar.getInstance();
                String idNote = calendar.getTimeInMillis() + "";

                User user = new User(Commonn.checkLogin.getNameUser(), edtWriteNote.getText().toString(),
                        dayStart, daySuccess, idNote);

               HomeFragment.colRefNote = HomeFragment.firebaseFirestore.collection(Commonn.COLLECTION_REF);

                HomeFragment.colRefNote.document(Commonn.checkLogin.getNameUser()).collection(Commonn.checkLogin.getNameUser())

                        .document(idNote)
                        .set(user)
                        .addOnSuccessListener(documentReference -> {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Commonn.userList.add(0, user);
                            HomeFragment.adapterNote.notifyDataSetChanged();
                                    Log.d("sizeList", Commonn.userList.size() + "");
                                    dialog.dismiss();
                                }
                        )
                        .addOnFailureListener(e -> {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(this, "[error]" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });
            }


        });


    }

    private void init() {
        dashboardFragment=new DashboardFragment();
        homeFragment=new HomeFragment();
        notificationsFragment=new NotificationsFragment();
        fragmentActive=homeFragment;

        fm.beginTransaction().add(R.id.nav_host_fragment,homeFragment,"home").commit();
        fm.beginTransaction().add(R.id.nav_host_fragment,dashboardFragment,"dash").hide(dashboardFragment).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment,notificationsFragment,"notifi").hide(notificationsFragment).commit();

    }

}