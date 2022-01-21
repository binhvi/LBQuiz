package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.AllQuizsFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.SavedQuizFragment;

public class QuizFragmentAdapter extends FragmentPagerAdapter {
    public QuizFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tất cả";
            case 1:
                return "Đã lưu";
        }
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllQuizsFragment();
            case 1:
                return new SavedQuizFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
