package etn.app.danghoc.broadcastreceiverdoctromtinnhan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mp;
        processReceive(context,intent);
    }

    private void processReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String message="";
        String body ="";
        String address="";
        if(extras!=null){
            Object[]smsEtra=(Object[])extras.get("pdus");
            for (int i =0;i<smsEtra.length;i++)             {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsEtra[i]);
                body = sms.getMessageBody();
                address = sms.getOriginatingAddress();
                message +="SMS From"+address+"\n"+body+"\n";
            }
            Toast.makeText(context,message,Toast.LENGTH_LONG).show(); if (address.equalsIgnoreCase("6505551212"))             {
                SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("0932588649", null,
                    message, null, null);             }
        }
    }
}
