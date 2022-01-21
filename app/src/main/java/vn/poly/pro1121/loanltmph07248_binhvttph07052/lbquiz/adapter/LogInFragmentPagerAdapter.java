package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.SignInFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.SignUpFragment;

public class LogInFragmentPagerAdapter extends FragmentPagerAdapter {
    public LogInFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Đăng nhập";
            case 1:
                return "Đăng ký";
        }
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SignInFragment();
            case 1:
                return new SignUpFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
