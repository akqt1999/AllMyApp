package etn.app.danghoc.luuhinhanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemDoVat extends AppCompatActivity {
 ImageView imvHinh;
 EditText edtTitle;
 DatabseImage databseImage;
 static final int REQUEST_CODE_CAMERA=123;
 static final int REQUEST_CODE_FOLDER=456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_do_vat);
        imvHinh=(ImageView)findViewById(R.id.image_hinh);
        edtTitle=(EditText)findViewById(R.id.edit_text_title);
        databseImage=new DatabseImage(this);
    }

       public void openCamera(View view){
//        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,123);
           ActivityCompat.requestPermissions(ThemDoVat.this,
                   new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
  }

    public void uploadImage(View view) {
//        Intent intent =new Intent( Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent,456);
            ActivityCompat.requestPermissions(ThemDoVat.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_FOLDER);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     switch (requestCode){
         case REQUEST_CODE_CAMERA :
             if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                 Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(intent,REQUEST_CODE_CAMERA);
             }else{
                 Toast.makeText(this, "ban khong cho quyen ", Toast.LENGTH_SHORT).show();
             }
             break;
         case REQUEST_CODE_FOLDER :
             if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                 Intent intent=new Intent(Intent.ACTION_PICK);
                 intent.setType("image/*");
                 startActivityForResult(intent,REQUEST_CODE_FOLDER);
             }else{
                 Toast.makeText(this, "ban khong cho quyen ", Toast.LENGTH_SHORT).show();
             }


     }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==123&&resultCode==RESULT_OK&&data!=null){ // camera
            Bitmap bitmap= (Bitmap)data.getExtras().get("data");
            imvHinh.setImageBitmap(bitmap);
        }
        if(requestCode==456&&resultCode==RESULT_OK&&data!=null){// folder
            // cai nay thi khac
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            imvHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
     public void buttonOk(View v) {
         ItemImage itemImage = createItem();
         if (itemImage != null) {
             databseImage.addData(itemImage);
             MainActivity.itemImageList.add(itemImage);
             MainActivity.adapter.notifyDataSetChanged();
             Toast.makeText(this, "them thanh cong", Toast.LENGTH_SHORT).show();
         }
     }

        private ItemImage createItem(){
          String title=edtTitle.getText().toString();
          //chuyen imageView -->> byte[]
            BitmapDrawable bitmapDrawable=(BitmapDrawable) imvHinh.getDrawable();
            Bitmap bitmap=bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
          byte []image=byteArray.toByteArray();
            ItemImage itemImage=new ItemImage(title,image);
            return itemImage;
        }

}
/*

 */