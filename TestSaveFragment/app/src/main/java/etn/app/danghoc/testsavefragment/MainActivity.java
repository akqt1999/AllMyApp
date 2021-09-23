package etn.app.danghoc.testsavefragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnA,btnB;
    Fragment fragment=null;
    public static Bundle save=new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment=new FragmentA();
        getSupportFragmentManager().beginTransaction().add(R.id.contain_fragment,fragment).commit();
        init();
        if (savedInstanceState!=null){
            fragment=getSupportFragmentManager().getFragment(savedInstanceState,"fragmenttes");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"fragmenttes",fragment);
    }

    private void init() {
        btnA=findViewById(R.id.btn_a);
        btnA.setOnClickListener(this);
        btnB=findViewById(R.id.btn_b);
        btnB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_a:
            fragment=new FragmentA();
            getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,fragment).commit();
                break;
            case R.id.btn_b:
             fragment=new FragmentB();
             getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,fragment).commit();
             break;
        }
    }
}