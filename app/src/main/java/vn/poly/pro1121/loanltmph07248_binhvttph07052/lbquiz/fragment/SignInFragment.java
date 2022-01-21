package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.ValidateFunctionLibrary;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.activity.MainActivity;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.UserDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.databinding.FragmentSignInBinding;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.User;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_IS_LOGGIN;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USERNAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.LOGGED_IN;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.SHARED_PREFERENCES_NAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.isLoggedIn;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogIn;
    private TextInputLayout textInputLayoutUsername, textInputLayoutPassword;

    //info
    private String username, password;

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;
    UserDao userDao;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSignInBinding binding =
                DataBindingUtil.inflate(
                        inflater,
                        R.layout.fragment_sign_in,
                        container,
                        false
                );
        View view = binding.getRoot();
        User user = new User("", "", getActivity());
        binding.setUser(user);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        init();
        addEvents();
    }

    private void addEvents() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoFromForm();
                if (allValidate() == ValidateFunctionLibrary.FAIL) {
                    return;
                }
                handleLogIn();
            }
        });
    }

    private void handleLogIn() {
       saveLoginInfo();
       Toast.makeText(
               getActivity(),
               R.string.login_sucessfully,
               Toast.LENGTH_SHORT
       ).show();
        ((MainActivity) getActivity()).napRegistedFragment();
    }

    private void saveLoginInfo() {
        //tao doi tuong SharedPreferences
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences(
                        SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                );
        //tao doi tuong Editor de luu thay doi
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //save info
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_IS_LOGGIN, LOGGED_IN);
        //chap nhan luu xong file
        editor.commit();

        //bao voi cac man hinh khac la da dang nhap
        isLoggedIn = LOGGED_IN;
    }

    /**
     * Bắt lỗi tên đăng nhập trống, tên đăng nhập không tồn tại,
     * mật khẩu trống, mật khẩu không đúng
     * @return
     */
    private boolean allValidate() {
        //ten dang nhap trong
        if (validateFunctionLibrary.isTextEmpty(username)) {
            edtUsername.requestFocus();
            return false;
        }

        //ten dang nhap khong ton tai
        if (!userDao.isUsernameExist(username)) {
            textInputLayoutUsername.setError(
                            getContext().getResources().getString(R.string.username_does_not_exist)
            );
            edtUsername.requestFocus();
            return false;
        } else {
            textInputLayoutUsername.setError("");
        }


        //mat khau trong
        if (validateFunctionLibrary.isTextEmpty(password)) {
            edtPassword.requestFocus();
            return false;
        }

        //mat khau sai
        if (!userDao.isPasswordCorrect(username, password)) {
            textInputLayoutPassword.setError(
                    getContext().getResources().getString(R.string.password_is_incorrect)
            );
            edtPassword.requestFocus();
            return false;
        } else {
            textInputLayoutPassword.setError("");
        }


        return true;
    }

    private void getInfoFromForm() {
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
    }

    private void init() {
        validateFunctionLibrary = new ValidateFunctionLibrary(getActivity());
        userDao = new UserDao(getActivity());
    }

    private void addControls(View view) {
        edtUsername = (EditText) view.findViewById(R.id.edtUsername);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        btnLogIn = (Button) view.findViewById(R.id.btnLogIn);
        textInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUsername = view.findViewById(R.id.textInputLayoutUsername);
    }
}
