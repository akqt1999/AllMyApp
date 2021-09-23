package etn.app.danghoc.likeuberrider.Model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import etn.app.danghoc.likeuberrider.Common.Common;
import etn.app.danghoc.likeuberrider.Services.MyFirebaseMessagingService;

public class UserUtils {

    public static void updateData(View view, Map<String, Object> updateData) {
        FirebaseDatabase.getInstance()
                .getReference(Common.RIDER_INFO_REF)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(updateData)
                .addOnSuccessListener(aVoid -> Snackbar.make(view,"update success",Snackbar.LENGTH_LONG).show())
                .addOnFailureListener(e -> Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show());
    }

    public static void updateToken(Context context, String token) {
        TokenModel tokenModel=new TokenModel(token);
        FirebaseDatabase.getInstance()
                .getReference(Common.TOKEN_REF)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(tokenModel)
                .addOnFailureListener(e ->{
                            Log.d("aaa",e.getMessage()+"___token");
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                        })
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "success", Toast.LENGTH_SHORT).show());

    }
}
