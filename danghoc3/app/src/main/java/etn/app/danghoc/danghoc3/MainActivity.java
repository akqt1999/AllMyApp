package etn.app.danghoc.danghoc3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtNoiDung;// toàn cục
        Button btClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ
        //txtNoiDung=(TextView) findViewById(R.id.textViewJoin);
        edtNoiDung=(EditText) findViewById(R.id.editTextJoin);

        btClick=(Button) findViewById(R.id.buttonJoin);
        // viết code

        btClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  // txtNoiDung.setText("hello xin chao");
                String noiDung=edtNoiDung.getText().toString();
                Toast.makeText(MainActivity.this,noiDung,Toast.LENGTH_LONG).show();
            }
        });

    }
}
