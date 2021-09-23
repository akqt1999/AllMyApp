package etn.app.danghoc.fragment_send_data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    private MutableLiveData<CharSequence> mTextA=new MutableLiveData<>();
   private MutableLiveData<CharSequence>mTextB=new MutableLiveData<>();

    public void setTextA(CharSequence input ){
        mTextA.setValue(input);
    }
   public void setTextB(CharSequence input){
        mTextB.setValue(input);
   }

    public LiveData<CharSequence> getTextA(){
        return mTextA;
    }

    public LiveData<CharSequence> getTextB(){
        return mTextB;
    }
}

/*

web code : https://codinginflow.com/tutorials/android/fragment-to-fragment-communication-with-shared-viewmodel
send data : https://www.youtube.com/watch?v=ACK67xU1Y3s
pop : https://www.youtube.com/watch?v=CUhcPuxMy7Y
 */