package etn.app.danghoc.firebaselai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView mTxtValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtValues= findViewById(R.id.textview_values);

     //   FirebaseDatabase.getInstance().getReference().child("message").child("android").setValue("nguyen xuan tri");
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("bai hoc khoa pham");
//        mDatabase.child("ho ten ").setValue("nguyen xuan tri");
//
//        // save object
//        SinhVien sv=new SinhVien("nguyen xuan tri","da nang ",9);
//        mDatabase.child("sinh vien").setValue(sv);
//
//        // save map
//        Map<String,Integer>mMap=new HashMap<String, Integer>();
//        mMap.put("tri",5);
//        mMap.put("ha",4);
//        mMap.put("ngu",6);
//        mDatabase.child("score").setValue(mMap);
//
//        // push
//        SinhVien sv1=new SinhVien("nguyen xuan tri","da nang ",9);
//        SinhVien sv2=new SinhVien("nguyen xuan tri","da nang ",9);
//        SinhVien sv3=new SinhVien("nguyen van luu","sai gon ",6);
//        //mDatabase.child("student").push().setValue(sv1);
        //mDatabase.child("student").push().setValue(sv2);
//        mDatabase.child("student").push().setValue(sv3, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                    if(databaseError==null){
//                        Toast.makeText(MainActivity.this, "save success", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(MainActivity.this, "save not success", Toast.LENGTH_SHORT).show();
//                    }
//            }
//        });

        // nhận dữ liệu từ firebase
            mDatabase.child("ho ten ").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mTxtValues.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
}
