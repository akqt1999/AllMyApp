package etn.app.danghoc.fragment_send_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class FragmentA extends Fragment {
private ShareViewModel viewModel ;
TextView textViewA;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragmet_a,container,false);
        textViewA=view.findViewById(R.id.textview_a);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      viewModel= ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
      viewModel.getTextA().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
          @Override
          public void onChanged(CharSequence charSequence) {
              textViewA.setText(charSequence);
          }
      });
    }

    // anh xa

}
