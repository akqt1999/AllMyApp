package etn.app.danghoc.dialogg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Dialog dialog;
    Button buttonShowDialog,buttonAlretDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       buttonAlretDialog =(Button) findViewById(R.id.button_show_altert_dialog);
        buttonShowDialog=(Button) findViewById(R.id.button_show_dialog);
        ////=========

        buttonShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showDialog();
            }
        });
// như trò đùa

        buttonAlretDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   showAlertDialog();
            }
        });
    }

    private void showDialog(){
         dialog=new Dialog (MainActivity.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.show();

        Button buttonYes=(Button) dialog.findViewById(R.id.button_yes);
        Button buttonNo=(Button) dialog.findViewById(R.id.button_no);
        //
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "bạn dã thoaet", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }




    /// dialog
    private void showAlertDialog(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        builder.setTitle("thong bao ");
        builder.setMessage("do you want to exit");

        builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "ban da thoaet", Toast.LENGTH_SHORT).show();
              dialog.dismiss();
            }
        });

        builder.setPositiveButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
       AlertDialog alertDialog= builder.create();
       alertDialog.show();

    }
}
