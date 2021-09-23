package etn.app.danghoc.testautotextview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtSelect;
    AutoCompleteTextView singleComplete;
    MultiAutoCompleteTextView multiAutoCompleteTextView;
    String arr[]={"quang nam","ha noi","da nang","vinh long","qunag binh","quang ninh"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        singleComplete.setAdapter(adapter);
        multiAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arr
        ));
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        singleComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtSelect.setText(singleComplete.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void init() {
        txtSelect=findViewById(R.id.txtSelect);
        singleComplete=findViewById(R.id.edtAuto);
        multiAutoCompleteTextView=findViewById(R.id.edtMutiAuto);
    }
}