package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


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
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.UserDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.databinding.FragmentSignUpBinding;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.User;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.REGEX_PASSWORD;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.REGEX_USERNAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    //view
    private EditText edtFullname;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogUp;
    private EditText edtRePassword;
    private TextInputLayout textInputLayoutRepassword;
    private TextInputLayout textInputLayoutUsername;

    //database
    UserDao userDao;

    //info
    private String username, password, rePassword, fullname;

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;
    
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSignUpBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sign_up,
                container,
                false
        );
        View view = binding.getRoot();
        User user = new User("", "", "", getActivity());
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

    private void init() {
        userDao = new UserDao(getActivity());
        validateFunctionLibrary = new ValidateFunctionLibrary(getActivity());
    }

    private void addEvents() {
        btnLogUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay thong tin tu form
                getInfoFromForm();
                //validate
                if (allValidate() == ValidateFunctionLibrary.FAIL) {
                    return;
                }
                //tao doi tuong User
                User user = new User(username, password, fullname, getActivity());
                //them doi tuong vao db
                long result = userDao.insertUser(user);
                //thong bao thanh cong hay that bai
                if (result > 0) {
                    Toast.makeText(
                            getActivity(),
                            R.string.save_successfully,
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            getActivity(),
                            R.string.save_failed,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private boolean allValidate() {
        //ho ten
        if (validateFunctionLibrary.isTextEmpty(fullname)) {
            edtFullname.requestFocus();
            return false;
        }

        //ten dang nhap trong
        if (validateFunctionLibrary.isTextEmpty(username)) {
            edtUsername.requestFocus();
            return false;
        }

        //The username only contains letters, numbers and _
        if (!username.matches(REGEX_USERNAME)) {
            edtUsername.requestFocus();
            return false;
        }

        //trung ten dang nhap
        if (userDao.isUsernameExist(username)) {
            textInputLayoutUsername.setError(
                    getContext().getResources().getString(R.string.username_duplicate)
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

        //password must not contain white space character
        if (!password.matches(REGEX_PASSWORD)) {
            edtPassword.requestFocus();
            return false;
        }

        //mat khau phai co it nhat 6 ky tu
        if (password.length() < 6) {
            edtPassword.requestFocus();
            return false;
        }

        //go lai mat khau
        if (!rePassword.equals(password)) {
            textInputLayoutRepassword.setError(
                    getContext().getResources().getString(R.string.repass_must_equal_password)
            );
            edtRePassword.requestFocus();
            return false;
        } else {
            textInputLayoutRepassword.setError("");
        }
        return true;
    }

    private void getInfoFromForm() {
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        rePassword = edtRePassword.getText().toString().trim();
        fullname = edtFullname.getText().toString().trim();
    }

    private void addControls(View view) {
        edtFullname = (EditText) view.findViewById(R.id.edtFullname);
        edtUsername = (EditText) view.findViewById(R.id.edtUsername);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        btnLogUp = (Button) view.findViewById(R.id.btnLogUp);
        edtRePassword = view.findViewById(R.id.edtRePassword);
        textInputLayoutRepassword = view.findViewById(R.id.textInputLayoutRepassword);
        textInputLayoutUsername = view.findViewById(R.id.textInputLayoutUsername);
    }
}
