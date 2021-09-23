package etn.app.danghoc.guithongbao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import etn.app.danghoc.guithongbao.model.FCMSendData;
import etn.app.danghoc.guithongbao.remote.IFCMService;
import etn.app.danghoc.guithongbao.remote.RetrofitFCMClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    IFCMService ifcmService;
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ifcmService= RetrofitFCMClient.getInstance().create(IFCMService.class);

    }

    public void sendNoti(View view) {
        Map<String, String> notiData = new HashMap<>();
        notiData.put(etn.app.danghoc.guithongbao.common.Common.NOTI_TITILE, "New Order");
        notiData.put(etn.app.danghoc.guithongbao.common.Common.NOTI_CONTENT, "You have new Order from nguyen xuan tri ");

        FCMSendData sendData = new FCMSendData(etn.app.danghoc.guithongbao.common.Common.createTopicOrder(), notiData);

        compositeDisposable.add(ifcmService.sendNotification(sendData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fcmResponse -> {
                    Toast.makeText(this , "send notification success", Toast.LENGTH_SHORT).show();

                }, throwable -> {
                    Toast.makeText(this, "failure to send notification", Toast.LENGTH_SHORT).show();
                })
        );


    }
}