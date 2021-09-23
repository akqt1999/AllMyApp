package etn.app.danghoc.pragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.pragment.model.ItemListViewA;

public class MainActivity extends AppCompatActivity {
    public static String TAG_FRAGMENT_A = null;
    public static String TAG_FRAGMENT_B = null;
    public static List<ItemListViewA> listA=new ArrayList<>();

    EditText editTextName, editTextNumber;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        Fragment fragment = null;
        fragment = new FragmentA();
        anhXa();
       android.app.FragmentManager fragmentManager=getFragmentManager();

        TAG_FRAGMENT_A = "fragmentA";
    }

    // m
    public void addFragment(View view) {
        //FragmentManager fragmentManager=getSupportFragmentManager();
        //  FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment fragment = null;
        fragment = new FragmentA();
        switch (view.getId()) {
            case R.id.button_a:
                fragment = new FragmentA();
                TAG_FRAGMENT_A = "fragmentA";
        //        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, fragment, TAG_FRAGMENT_A).addToBackStack("aaa").commit();
           FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_content, fragment);
                fragmentTransaction.addToBackStack("aaa");
                fragmentTransaction.commit();
                break;
            case R.id.button_b:
                fragment = new FragmentB();
                TAG_FRAGMENT_B = "fragmentB";
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, fragment, TAG_FRAGMENT_B).addToBackStack("bbb").commit();
                break;
        }

    }

    public void removeAll(View view) {
        FragmentA fragmentA = (FragmentA) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_A);
        FragmentB fragmentB = (FragmentB) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_B);
        if (fragmentA != null) {
            getSupportFragmentManager().beginTransaction().remove(fragmentA).commit();
        } else if (fragmentB != null) {
            getSupportFragmentManager().beginTransaction().remove(fragmentB).commit();
        } else {
            Toast.makeText(this, "Fragment is not exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void anhXa() {
        editTextName = (EditText) findViewById(R.id.edittext_name);
        editTextNumber = (EditText) findViewById(R.id.edittext_number);
        buttonAdd = (Button) findViewById(R.id.button_add);

    }

    public void buttonAdd(View view) {
//        Bundle bundle=new Bundle();// bundle thì không tính
//        bundle.putString("NAME",editTextName.getText().toString());
//        bundle.putString("NUMBER",editTextNumber.getText().toString());
//
//        ItemListViewA itemListViewA=new ItemListViewA(editTextName.getText().toString(),editTextNumber.getText().toString());
//        listA.add(itemListViewA);
//
//        FragmentA fragmentA=new FragmentA();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content,fragmentA,TAG_FRAGMENT_A).commit();
//        fragmentA.setArguments(bundle);
    }

    public void popA(View view) {
        //getFragmentManager().popBackStack("aaa",0);
        Toast.makeText(this, "con ket", Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().popBackStack("aaa",0);
//        getSupportFragmentManager().popBackStack("aaa",
//                FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public void popB(View view) {
        getSupportFragmentManager().popBackStack("bbb",0);
    }


    // dấu ngoặt cuối cùng
}



/*
vãi lồn f
đoi k như là mơ
 */
