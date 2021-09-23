package etn.app.danghoc.cloudfirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etn.app.danghoc.cloudfirestore.Model.Note;

public class AddAndLoad extends AppCompatActivity {

    static final String TAG = "MainActivity";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";

    EditText edtTitle, edtDescription, edtPriority,edtId;
    TextView txtData;
    ProgressBar progressBar;


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    //DocumentReference noteRef = firebaseFirestore.document("Notebook/My first note");
    CollectionReference collectionReference = firebaseFirestore.collection("Notebook");
  //  DocumentReference documentReference = firebaseFirestore.document("Notebook/My first Note");

    // DocumentReference noteRef2=firebaseFirestore.collection("ss").document("dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_load);
        edtDescription = findViewById(R.id.edtDescription);
        edtTitle = findViewById(R.id.edtTitle);
        progressBar = findViewById(R.id.progressBar);
        txtData = findViewById(R.id.txtData);
        edtPriority = findViewById(R.id.edtPriority);
        progressBar.setVisibility(View.INVISIBLE);

        excuteTransaction();
    }

    private void excuteTransaction() { // cap nhap theo kieu??
        //firebaseFirestore.runTransaction()
    }

    @Override
    protected void onStart() {



        super.onStart();
        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(AddAndLoad.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("loi", error.getMessage());
                } else {
                    String data = "";
                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        Note note = documentSnapshot.toObject(Note.class);
                        note.setDocumentId(documentSnapshot.getId());

                        data += note.getDocumentId() + " _--_ " + note.getTitle() + "  _  " +
                                note.getDescription() + "  _   " + note.getPriority() + "\n";
                    }
                    txtData.setText(data);
                }

            }
        });
    }


    public void addNote(View view) {


        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        //  Map<String, Object> note = new HashMap<>();
//        note.put(KEY_TITLE, title);
//        note.put(KEY_DESCRIPTION, description);

        if (edtPriority.length() == 0) {
            edtPriority.setText("0");
        }
        int priority = Integer.parseInt(edtPriority.getText().toString());

        Note note = new Note(title, description, priority);

        collectionReference.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddAndLoad.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("loi", e.getMessage());
            }
        });

    }

    public void loadData(View view) {
        progressBar.setVisibility(View.VISIBLE);
        collectionReference.whereGreaterThanOrEqualTo("priority", 3)
                .whereEqualTo("title", "titi4")
                .orderBy("priority", Query.Direction.DESCENDING)
                .limit(3)//gioi han chi 3 cai
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddAndLoad.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("loi", e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressBar.setVisibility(View.INVISIBLE);
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Note note = documentSnapshot.toObject(Note.class);

                    note.setDocumentId(documentSnapshot.getId());
                    String title = note.getTitle();
                    String des = note.getDescription();
                    String documentId = note.getDocumentId();
                    data += documentId + " ____ " + title + " _ " + des + " _  " + note.getPriority() + "\n";

                    txtData.setText(data);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("loi", e.getMessage());

            }
        });

    }

    public void updateData(View view) {
        progressBar.setVisibility(View.VISIBLE);
       // String id=edtId.getText().toString();//id : 8sB1foFXsnhfgPrOFyMu
        WriteBatch batch=firebaseFirestore.batch();
        DocumentReference docRefUpdate=collectionReference.document("8sB1foFXsnhfgPrOFyMu");
        batch.update(docRefUpdate,"title","titleUpdatee");
        batch.update(docRefUpdate,"description","descriptionUpdate");
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddAndLoad.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void deleteData(View view) {
        progressBar.setVisibility(View.VISIBLE);
       /// String id=edtId.getText().toString() ;
        WriteBatch batch =firebaseFirestore.batch();
        DocumentReference docRefDelete=collectionReference.document("yUSbZ8x4p2lenPoBzWmf");
        batch.delete(docRefDelete);
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddAndLoad.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void deleteDescription(View view) {
//        noteRef.update(KEY_DESCRIPTION, FieldValue.delete());
//    }

    public void loadOr(View view) { //or
        Task task1 = collectionReference.whereGreaterThan("priority", 2).get();
        Task task2 = collectionReference.whereLessThan("priority", 2).get();
        Task<List<QuerySnapshot>> allTask = Tasks.whenAllSuccess(task1, task2);
        allTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                String data = "";
                for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Note note = documentSnapshot.toObject(Note.class);

                        note.setDocumentId(documentSnapshot.getId());
                        String title = note.getTitle();
                        String des = note.getDescription();
                        String documentId = note.getDocumentId();
                        data += documentId + " ____ " + title + " _ " + des + " _  " + note.getPriority() + "\n";

                        txtData.setText(data);
                    }


                }
            }
        });
    }

    public void addNew(View view) {
        progressBar.setVisibility(View.VISIBLE);
        WriteBatch batch=firebaseFirestore.batch();
        DocumentReference docRefUpdate=collectionReference.document("newID_hello");
        batch.set(docRefUpdate,new Note("new Note title","new des",3));
//        DocumentReference docRefUpdate2=collectionReference.document();
//        batch.set(docRefUpdate2,new Note("title new 2","des new 2",2));

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddAndLoad.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void child(View view) {
        String childNew=edtTitle.getText().toString();
        collectionReference.document("newID_hello").collection(childNew)
                .add(new Note("title child","des child",3));
    }

    public void loadChild(View view) {
            collectionReference.document("newID_hello")
                    .collection("ccc")
                    .get()
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddAndLoad.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        Note note=documentSnapshot.toObject(Note.class);
                        txtData.setText(note.getTitle()+"_____"+note.getDescription()+"\n");
                    }
                }
            });

    }


    public void loadNoCol(View view) {
        collectionReference.document("newID_hello")
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddAndLoad.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }
        });
    }
}