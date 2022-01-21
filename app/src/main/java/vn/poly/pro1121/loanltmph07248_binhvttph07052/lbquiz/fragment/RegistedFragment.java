package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.activity.MainActivity;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.UserDao;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USERNAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.NOT_LOGGED_IN;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.SHARED_PREFERENCES_NAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.isLoggedIn;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistedFragment extends Fragment {
    private CardView cardViewLoggedInUser;
    private TextView tvUserFullname;
    private TextView tvUsername;
    private Button btnLogOut;

    //info
    private String username, fullname;

    //database
    UserDao userDao;

    public static interface RegistedFragmentListener {
        void viewGroupRegistedUserClicked();
    }

    private RegistedFragmentListener listener;

    public RegistedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls(view);
        init();

        cardViewLoggedInUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.viewGroupRegistedUserClicked();
                }
            }
        });
        addEvents();
    }

    private void addEvents() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogOut();
            }
        });
    }

    private void handleLogOut() {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences(
                        SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //xoa moi luu tru truoc do
        editor.clear();
        editor.commit();

        //thong bao cho cac man hinh khac la da log out
        isLoggedIn = NOT_LOGGED_IN;

        ((MainActivity) getActivity()).napUnRegistedFragment();
    }

    private void init() {
        userDao = new UserDao(getActivity());
    }

    private void addControls(View view) {
        cardViewLoggedInUser = (CardView) view.findViewById(R.id.cardViewLoggedInUser);
        tvUserFullname = (TextView) view.findViewById(R.id.tvUserFullname);
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (RegistedFragmentListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreLoginInfo();
    }

    private void restoreLoginInfo() {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences(
                        SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                );
        //khoi phuc lai gia tri
        username = sharedPreferences.getString(KEY_USERNAME, "");
        fullname = userDao.getFullnameByUsername(username);

        tvUsername.setText(username);
        tvUserFullname.setText(fullname);
    }
}
