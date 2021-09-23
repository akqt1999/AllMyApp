package etn.app.danghoc.socdia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText edt_sochan, edt_sole;

    TextView txt_result, txt_tienthoi, txt_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edt_sochan=findViewById(R.id.edt_sochan);
        edt_sole=findViewById(R.id.edt_sole);

        txt_result=findViewById(R.id.txt_result);
        txt_number=findViewById(R.id.txt_number);
        txt_tienthoi=findViewById(R.id.txt_tien_thoi);
    }

    public void btnChay(View view) {

        txt_number.setText(createNumber() + "");


    }

    private String createNumber() {
        if (!edt_sochan.getText().toString().isEmpty() && !edt_sole.getText().toString().isEmpty()) {
            Random random = new Random();
            txt_number.setText("waiting....");
            int sole = Integer.parseInt(edt_sole.getText().toString());
            int sochan = Integer.parseInt(edt_sochan.getText().toString());

            int number=0;

            try {

                        number = random.nextInt(100 - 0 + 1) + 0;// random cac so tu 0->>100

                        Thread.sleep(3000);
                        txt_number.setText(number+"");



            } catch (InterruptedException e) {

            }


            if (number % 2 == 0) {
                txt_result.setText("số chẵn");
                if (sochan > sole) {
                    int tienthoi = sochan - sole;
                    txt_tienthoi.setText("cái thua " + tienthoi);
                } else if (sochan < sole) {
                    int tienthoi = sole - sochan;
                    txt_tienthoi.setText("cái an " + tienthoi);
                } else {
                    txt_tienthoi.setText("đụ bò");
                }
            } else {
                txt_result.setText("số lẻ");

                if (sochan > sole) {
                    int tienthoi = sochan - sole;
                    txt_tienthoi.setText("cái an " + tienthoi);
                } else if (sochan < sole) {
                    int tienthoi = sole - sochan;
                    txt_tienthoi.setText("cái thua " + tienthoi);
                } else {
                    txt_tienthoi.setText("đụ bò");
                }
            }

            String numbers = String.valueOf(number);
            return numbers;
        }else
            return "chua nhap";

        }

    public void btnRefrest(View view) {
        txt_number.setText("ket qua");
        txt_tienthoi.setText("con ket");
        txt_result.setText("con ket");
    }
}