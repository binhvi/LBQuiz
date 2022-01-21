package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnregistedFragment extends Fragment {

    public static interface UnregistedFragmentListener {
        void buttonSignInSignUpClicked();
    }

    private UnregistedFragmentListener listener;

    Button btnSwitchToLoginScreen;

    public UnregistedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unregisted, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSwitchToLoginScreen = view.findViewById(R.id.btnSwitchToLoginScreen);

        btnSwitchToLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.buttonSignInSignUpClicked();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (UnregistedFragmentListener) context;
    }
}
