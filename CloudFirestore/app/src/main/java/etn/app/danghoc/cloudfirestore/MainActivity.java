package etn.app.danghoc.cloudfirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import etn.app.danghoc.cloudfirestore.Model.Note;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";

    EditText edtTitle, edtDescription;
    TextView txtData;
    ProgressBar progressBar;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference =firebaseFirestore.document("Notebook/My first note");
   // DocumentReference noteRef2=firebaseFirestore.collection("ss").document("dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtDescription = findViewById(R.id.edtDescription);
        edtTitle = findViewById(R.id.edtTitle);
        progressBar=findViewById(R.id.progressBar);
        txtData=findViewById(R.id.txtData);
        progressBar.setVisibility(View.INVISIBLE);
    }

        @Override
        protected void onStart() {
            super.onStart();
            documentReference.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null){
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG,error.getMessage());
                        progressBar.setVisibility(View.INVISIBLE);
                        return;
                    }
                    if (value.exists()){
//                            String title=value.getString(KEY_TITLE);
//                            String description=value.getString(KEY_DESCRIPTION);
                        Note note=value.toObject(Note.class);
                        String title=note.getTitle();
                        String description=note.getDescription();

                        txtData.setText(title +" : "+description );
                        
                        progressBar.setVisibility(View.INVISIBLE);
                    }else{
                        txtData.setText( "");
                    }
                }
            });
        }



    public void saveNote(View view) {
        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
      //  Map<String, Object> note = new HashMap<>();
//        note.put(KEY_TITLE, title);
//        note.put(KEY_DESCRIPTION, description);

        Note note=new Note(title,description);

        documentReference.set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,e.getMessage());
            }
        });

    }

    public void loadData(View view) {
        progressBar.setVisibility(View.VISIBLE);
            documentReference.get().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG,e.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if(documentSnapshot.exists()){
//                        String title=documentSnapshot.getString(KEY_TITLE);
//                        String description=documentSnapshot.getString(KEY_DESCRIPTION);
                      //      Map<String,Object> note=documentSnapshot.getData();

                        Note note=documentSnapshot.toObject(Note.class);
                        String title=note.getTitle();
                        String description=note.getDescription();

                        txtData.setText(title +" : "+description );
                    }else{
                        Toast.makeText(MainActivity.this, "data is not exits", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public void updateData(View view) {
            String description=edtDescription.getText().toString();
            documentReference.update(KEY_DESCRIPTION,description);
    }


    public void deleteData(View view) {
        documentReference.delete();

    }

    public void deleteDescription(View view) {
        documentReference.update(KEY_DESCRIPTION, FieldValue.delete());
    }
}