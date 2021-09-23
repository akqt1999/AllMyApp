package etn.app.danghoc.likeuber.Mode;


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

import etn.app.danghoc.likeuber.Common;
import etn.app.danghoc.likeuber.servies.MyFirebaseMessagingService;

public class UserUtils {
    public static void updateUser(View view, Map<String, Object> updateData) {
        FirebaseDatabase.getInstance()
                .getReference(Common.DRIVER_INFO_REF)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(updateData)
                .addOnFailureListener(e -> Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show())
                .addOnSuccessListener(aVoid -> Snackbar.make(view, "Update information successfully", Snackbar.LENGTH_LONG).show());
    }

    public static void updateToken(Context context, String token) { // gui thong bao
        TokenModel tokenModel = new TokenModel(token);

        FirebaseDatabase.getInstance()
                .getReference(Common.TOKEN_REFERENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(tokenModel)
                .addOnFailureListener(e ->{

                    Log.d("aaa",e.getMessage()+"___5"); //error here
                    Toast.makeText(context, "[ERROR] "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }
}
