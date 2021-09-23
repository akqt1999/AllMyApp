package etn.app.danghoc.appshopping_demo.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import etn.app.danghoc.appshopping_demo.R;

public class BottomSheetSelectPay extends BottomSheetDialogFragment implements View.OnClickListener {
    Button mBtnCashPay, mBtnCredit, mBtnCancel;
    private  onCallback mListener;

    public BottomSheetSelectPay(onCallback mListener) {
        this.mListener=mListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_select_pay, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mBtnCancel = view.findViewById(R.id.button_cancel_sheet);
        mBtnCancel.setOnClickListener(this);

        mBtnCashPay = view.findViewById(R.id.button_cash_pay_sheet);
        mBtnCashPay.setOnClickListener(this);

        mBtnCredit = view.findViewById(R.id.button_pay_credit_sheet);
        mBtnCredit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_cancel_sheet:
                dismiss();
                break;

            case R.id.button_cash_pay_sheet:
                mListener.clickSelectPay("thanh toán khi nhận hàng");
                dismiss();
                break;

            case R.id.button_pay_credit_sheet:
                mListener.clickSelectPay("thanh toán thẻ tín dụng");
                dismiss();
                break;

        }
    }

    public interface onCallback {
         void clickSelectPay(String pay);
    }

    ;
}
