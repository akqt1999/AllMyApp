package etn.app.danghoc.note_appp1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import etn.app.danghoc.note_appp1.data.DatabaseNote;
import etn.app.danghoc.note_appp1.model.ItemTitle;

public class activity_text extends AppCompatActivity {
    EditText editTextPut;
    ImageButton imageButtonBack;
    ImageButton imageButtonDelete;
    TextView tvTitle;
    int POSITION = -1;

    DatabaseNote data = new DatabaseNote(this);
    int id;
    androidx.appcompat.widget.Toolbar toolbarContent;

    // mình dưới là main
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        anhXa();
        getDataByBundle();
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true); tạo nút back
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        eventButton();
         if (POSITION != -1) {
            ItemTitle itemTitle = ActivityHome.list.get(POSITION);
            editTextPut.setText(itemTitle.getmContent() + "");
            id = itemTitle.getId();
            editTextPut.setText(itemTitle.getmContent());
        }

        eventChangeText();
    }


    public void eventChangeText() {
        editTextPut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ItemTitle itemTitle = ActivityHome.list.get(POSITION);
                itemTitle.setmContent(s + "");
                data.updateContent(id, s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void eventButton() {
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPut.setText("");
            }
        });
    }

    public void anhXa() {
        imageButtonBack = (ImageButton) findViewById(R.id.image_button_back);
        imageButtonDelete = (ImageButton) findViewById(R.id.imagebutton_delete);
        editTextPut = (EditText) findViewById(R.id.edittext_put);
        toolbarContent = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_content);
        tvTitle=findViewById(R.id.tv_title_tool_bar);
        setSupportActionBar(toolbarContent);
    }

    public void getDataByBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        POSITION = bundle.getInt("noteId");
        tvTitle.setText(bundle.getString("noteTitle"));
    }
}
