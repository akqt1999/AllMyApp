package etn.app.danghoc.testsavefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {
    EditText edtTest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_a,container,false);
        edtTest=view.findViewById(R.id.edt_test);
        if (MainActivity.save!=null){
            //edtTest.setText(MainActivity.save.getString("test"));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            edtTest.setText(savedInstanceState.getString("test2"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.save.putString("test",edtTest.getText().toString());
    }


}
