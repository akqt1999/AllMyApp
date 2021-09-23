package etn.app.danghoc.syntask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsynTaks extends AsyncTask<Void,Integer,Void> {
    Activity contextCha;
    public MyAsynTaks(Activity ctx){
        contextCha=ctx;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        Toast.makeText(contextCha, "onPreExecute!",Toast.LENGTH_LONG).show();

    }
    @Override
    protected Void doInBackground(Void... voids) {
        for(int i=0;i<=100;i++){
            SystemClock.sleep(100);
            publishProgress(i);
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
        ProgressBar paCha=(ProgressBar) contextCha.findViewById(R.id.progressBar);
        int giatri=values[0];
        paCha.setProgress(giatri);
        TextView txtmsg=(TextView)contextCha.findViewById(R.id.txtPhanTram);txtmsg.setText(giatri+"%");
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Toast.makeText(contextCha, "Update xong roi do!",Toast.LENGTH_LONG).show();
    }

}
