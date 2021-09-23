package etn.app.danghoc.fixgetcurrentlydate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_date=findViewById(R.id.txt_date);

        long estimatedServerTimeMs = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date resultDate = new Date(estimatedServerTimeMs);
        String date = sdf.format(resultDate);


        String dateTest="5/31/000";


        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateTest));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dateTest = sdf.format(c.getTime());
        txt_date.setText(dateTest);

    }
}