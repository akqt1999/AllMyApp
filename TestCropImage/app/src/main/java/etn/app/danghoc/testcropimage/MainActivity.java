package etn.app.danghoc.testcropimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.soundcloud.android.crop.Crop;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =123 ;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=findViewById(R.id.img);
    }

    public void open(View view) {
        Crop.pickImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==Crop.REQUEST_PICK){
                Uri source_uri=data.getData();
                Uri destination_uri=Uri.fromFile(new File(getCacheDir(),"cropped"));
                Crop.of(source_uri,destination_uri).asSquare().start(this);
                img.setImageURI(Crop.getOutput(data));
            }else if(requestCode==Crop.REQUEST_CROP){
                handleCrop(resultCode,data);
            }
        }

    }

    private void handleCrop(int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            img.setImageURI(Crop.getOutput(data));
        }
    }
}