package etn.app.danghoc.likeuberrider.Services;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import etn.app.danghoc.likeuberrider.Common.Common;
import etn.app.danghoc.likeuberrider.Model.UserUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            UserUtils.updateToken(this, s);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> dataRev = remoteMessage.getData();
        if (dataRev != null) {
            Common.showNotification(this, new Random().nextInt(),
                    dataRev.get(Common.NOTI_TITLE)
                    , dataRev.get(Common.NOTI_CONTENT)
                    , null);
        }
    }
}
