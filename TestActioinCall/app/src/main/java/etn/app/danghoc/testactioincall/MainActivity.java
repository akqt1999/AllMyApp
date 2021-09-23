package etn.app.danghoc.testactioincall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText edtA,edtB;
    Button btnKetQua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnKetQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ChildActivity.class);
                Bundle bundle=new Bundle();
                double a=Double.parseDouble(edtA.getText().toString());
                double b=Double.parseDouble(edtB.getText().toString());
                bundle.putDouble("a",a);
                bundle.putDouble("b",b);
                intent.putExtra("pt",bundle);
                startActivity(intent);
            }
        });
    }

    private void init() {
        edtA=findViewById(R.id.edt_a);
        edtB=findViewById(R.id.edt_b);
        btnKetQua=findViewById(R.id.btn_ketqua);
    }
}