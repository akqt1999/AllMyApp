package etn.app.danghoc.app_random;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
// khai bao
    EditText edtMin;
    EditText edtMax;
    Button btCreate;
    TextView tvNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
     // ánh xạ
        //full screen


        edtMax=(EditText) findViewById(R.id.max);
        edtMin=(EditText) findViewById(R.id.min);
        btCreate=(Button)  findViewById(R.id.btCreate);
        tvNumber=(TextView) findViewById(R.id.tvNumber);
   // code

btCreate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String numberMin = edtMin.getText().toString().trim();
        String numberMax = edtMax.getText().toString().trim();

        if (numberMin.length() == 0) {
            Toast.makeText(MainActivity.this, "phai nhap min", Toast.LENGTH_LONG).show();
        } else if (numberMax.length() == 0) {
            Toast.makeText(MainActivity.this, "phai nhap max", Toast.LENGTH_LONG).show();
        } else {
            int min = Integer.parseInt(numberMin);
            int max = Integer.parseInt(numberMax);

       if (min >= max) {
                Toast.makeText(MainActivity.this, "nhập max phải lớn hơn min", Toast.LENGTH_LONG).show();
            } else {

                Random random = new Random();
                int number = random.nextInt(max - min + 1) + min;
                String numberString = String.valueOf(number);
                tvNumber.setText(numberString);
            }
        }
    }
});


    }
}
