package etn.app.danghoc.fragment_send_data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterViewPager extends FragmentStatePagerAdapter {
//
    private String []listTab ={"fragment send","fragment A","fagment B"};
    private FragmetSend mFragmentSend;
    protected FragmentA mFragmentA;
    protected FragmentB mFragmentB;

    public AdapterViewPager(@NonNull FragmentManager fm) {
        super(fm);
    mFragmentA=new FragmentA();
    mFragmentB=new FragmentB();
    mFragmentSend=new FragmetSend();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return mFragmentSend;
        }else if(position==1){
            return mFragmentA;
        }else if(position==2){
            return mFragmentB;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
