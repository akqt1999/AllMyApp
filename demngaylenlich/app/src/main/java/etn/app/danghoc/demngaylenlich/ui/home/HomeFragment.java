package etn.app.danghoc.demngaylenlich.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import etn.app.danghoc.demngaylenlich.Adapter.AdapterNote;
import etn.app.danghoc.demngaylenlich.Commonn.Commonn;
import etn.app.danghoc.demngaylenlich.Model.User;
import etn.app.danghoc.demngaylenlich.R;

public class HomeFragment extends Fragment implements AdapterNote.IOnCallBack {

  public static   AdapterNote adapterNote;

    //to day
    int year, month, date;
    String daySuccess;

    //cloud firebase
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static CollectionReference colRefNote;

    //    @BindView(R.id.btnAdd)
//    ImageButton btnAdd;
    ImageButton btnAdd;
    //@BindView(R.id.recyclerViewNote)
    //RecyclerView recyclerView;
    ProgressBar progressBar;
    public static ImageButton btnDelete, btnCancel, btnEdit;

    RecyclerView recyclerView;

    //delete multi items
    public static boolean isChooseItem = false;
    public static List<User> userListChoose;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init(root);
        Toast.makeText(getContext(), Commonn.checkLogin.getNameUser(), Toast.LENGTH_SHORT).show();

        btnAdd = root.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            //something
            showLayoutAdd();
        });

        btnCancel.setOnClickListener(v -> {
            btnEdit.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);

            for (int i = 0; i < Commonn.userList.size(); i++) {
                Commonn.userList.get(i).setSelect(false);
            }


            isChooseItem = false;
            adapterNote.setMultiCheckMode(false);


        });
        btnDelete.setOnClickListener(v -> {
            removeItem(userListChoose);
            btnEdit.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);

            for (int i = 0; i < Commonn.userList.size(); i++) {
                Commonn.userList.get(i).setSelect(false);
            }
            adapterNote.setMultiCheckMode(false);
            isChooseItem = false;
            deleteDataFirebase(userListChoose);

        });


        loadData();
        showHideWhenScroll();
        return root;
    }

    private void deleteDataFirebase(List<User>userListChoose) {
        WriteBatch batch=firebaseFirestore.batch();
        CollectionReference collectionReference=firebaseFirestore.collection(Commonn.COLLECTION_REF);
        for(int i=0;i<userListChoose.size();i++){
            DocumentReference docRfDelete=collectionReference.document(Commonn.checkLogin.getNameUser())
                    .collection(Commonn.checkLogin.getNameUser())
                    .document(userListChoose.get(i).getIdNote());
            Toast.makeText(getContext(),userListChoose.get(i).getIdNote()+"" , Toast.LENGTH_SHORT).show();
            batch.delete(docRfDelete);
        }
        progressBar.setVisibility(View.VISIBLE);
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showHideWhenScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //dy > 0: scroll up; dy < 0: scroll down
                if (dy > 0) btnAdd.setVisibility(View.INVISIBLE);
                else btnAdd.setVisibility(View.VISIBLE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void removeItem(List<User> userListChoose) {
        for (int i = 0; i < userListChoose.size(); i++) {
            Commonn.userList.remove(userListChoose.get(i));
            adapterNote.notifyDataSetChanged();
        }
    }

    private void loadData() {

        progressBar.setVisibility(View.VISIBLE);

        colRefNote = firebaseFirestore.collection(Commonn.COLLECTION_REF)
                .document(Commonn.checkLogin.getNameUser())
                .collection(Commonn.checkLogin.getNameUser())

        ;
        colRefNote
                .orderBy("dayStart", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (queryDocumentSnapshots != null) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Commonn.userList.add(documentSnapshot.toObject(User.class));
                            adapterNote.notifyDataSetChanged();
                            Log.d("sizeList", Commonn.userList.size() + "");
                        }
                    }

                }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("loi", e.getMessage());
        });
    }

    private void showLayoutAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme);
        LayoutInflater inflater = LayoutInflater.from(getContext());
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                Toast.makeText(getContext(), "Please choose date success", Toast.LENGTH_SHORT).show();
            } else if (edtWriteNote.getText().toString() == null || edtWriteNote.getText().toString().trim()
                    .equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Please enter the note", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);

                String dayStart = date + "/" + month + "/" + year;
                Calendar calendar = Calendar.getInstance();
                String idNote = calendar.getTimeInMillis() + "";

                User user = new User(Commonn.checkLogin.getNameUser(), edtWriteNote.getText().toString(),
                        dayStart, daySuccess, idNote);

                colRefNote = firebaseFirestore.collection(Commonn.COLLECTION_REF);

                colRefNote.document(Commonn.checkLogin.getNameUser()).collection(Commonn.checkLogin.getNameUser())

                        .document(idNote)
                        .set(user)
                        .addOnSuccessListener(documentReference -> {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Commonn.userList.add(0, user);
                                    adapterNote.notifyDataSetChanged();
                                    Log.d("sizeList", Commonn.userList.size() + "");
                                    dialog.dismiss();
                                }
                        )
                        .addOnFailureListener(e -> {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "[error]" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });
            }


        });


    }


    private void init(View root) {

        userListChoose = new ArrayList<>();

        Commonn.userList = new ArrayList<>();

        ButterKnife.bind(root);

        recyclerView = root.findViewById(R.id.recyclerViewNote);
        progressBar = root.findViewById(R.id.progressBar);


        adapterNote = new AdapterNote(Commonn.userList, this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterNote);

        btnDelete = root.findViewById(R.id.btnDelete);
        btnCancel = root.findViewById(R.id.btnCancel);
        btnEdit = root.findViewById(R.id.btnEdit);


    }

    @Override
    public void itemOnClick(int position) {

    }

    @Override
    public void itemOnLongClick(int position) {

    }


}