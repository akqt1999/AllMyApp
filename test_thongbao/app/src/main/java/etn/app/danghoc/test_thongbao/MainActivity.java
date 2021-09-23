package etn.app.danghoc.test_thongbao;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtContent;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtContent=findViewById(R.id.txt_name);
        text="nguyen xuan tri muon mua san pham bo rau mon cua ban";
        SpannableString ss=new SpannableString(text);
        StyleSpan boldSpand1=new StyleSpan(Typeface.BOLD);
        StyleSpan boldSpand2=new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpand1,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(boldSpand2,34,44, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtContent.setText(ss);
        
    }
}