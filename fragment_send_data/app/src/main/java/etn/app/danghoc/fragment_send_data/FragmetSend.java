package etn.app.danghoc.fragment_send_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class FragmetSend extends Fragment {
    private EditText editTextSend;
    private Button buttonSendA, buttonSendB;
    private ShareViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        anhXa(view);
        viewModel= ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        buttonSendA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), editTextSend.getText()+"", Toast.LENGTH_SHORT).show();
                viewModel.setTextA(editTextSend.getText());
            }
        });

        buttonSendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setTextB(editTextSend.getText());
            }
        });

        return view;
    }

    public void anhXa(View view) {
        editTextSend = view.findViewById(R.id.edittext_send);
        buttonSendA = view.findViewById(R.id.button_send_a);
        buttonSendB = view.findViewById(R.id.button_send_b);
    }

}
