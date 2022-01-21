package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.ValidateFunctionLibrary;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.activity.MainActivity;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.UserDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.User;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USERNAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.REGEX_PASSWORD;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.SHARED_PREFERENCES_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailChangePasswordFragment extends Fragment {
    private EditText edtFullname;
    private CheckBox chkIsChangePassword;
    private LinearLayout llChangePassword;
    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtRetypeNewPassword;
    private Button btnSaveChanges;

    //info from shared preferences file
    private String username; //must't change

    //info
    private String fullname;
    private String oldPassword;
    private String newPassword, retypeNewPassword;

    //database
    UserDao userDao;

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;

    public UserDetailChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail_change_password, container, false);
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
    }

    private void addEvents() {
        chkIsChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkIsChangePassword.isChecked()) {
                    llChangePassword.setVisibility(View.VISIBLE);
                } else {
                    llChangePassword.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoFromForm();
                if (allValidate() == ValidateFunctionLibrary.FAIL) {
                    return;
                }
                applyChanges();
            }
        });
    }

    /**
     *
     */
    private void applyChanges() {
        int result;
        //save changes to database
        User user = new User(username, newPassword, fullname, getActivity());
        if (chkIsChangePassword.isChecked()) {
            result = userDao.updateUserAllDetails(user);
        } else {
            result = userDao.updateUserFullname(user);
        }
        if (result > 0) {
            Toast.makeText(
                    getActivity(),
                    R.string.save_successfully,
                    Toast.LENGTH_SHORT
            ).show();
            //return to RegistedFragment
            ((MainActivity) getActivity()).napRegistedFragment();
        } else {
            Toast.makeText(
                    getActivity(),
                    R.string.save_failed,
                    Toast.LENGTH_SHORT
            ).show();
        }

    }

    private boolean allValidate() {
        //fullname can not be empty
        if (fullname.isEmpty()) {
            edtFullname.setError(
                    getResources().getString(R.string.fullname)+" "+
                    getResources().getString(R.string.can_not_be_empty)
            );
            edtFullname.requestFocus();
            return false;
        } else {
            edtFullname.setError(null);
        }

        if (chkIsChangePassword.isChecked()) {
            //old password empty
            if (oldPassword.isEmpty()) {
                edtOldPassword.setError(
                        getResources().getString(R.string.old_password)+" "
                        + getResources().getString(R.string.can_not_be_empty)
                );
                edtOldPassword.requestFocus();
                return false;
            } else {
                edtOldPassword.setError(null);
            }

            //old password is incorrect
            if (!userDao.isPasswordCorrect(username, oldPassword)) {
                edtOldPassword.setError(
                        getContext().getResources().getString(R.string.old_password_is_incorrect)
                );
                edtOldPassword.requestFocus();
                return false;
            } else {
                edtOldPassword.setError(null);
            }

            //new password is empty
            if (newPassword.isEmpty()) {
                edtNewPassword.setError(
                        getResources().getString(R.string.new_password)+" "
                        + getResources().getString(R.string.can_not_be_empty)
                );
                edtNewPassword.requestFocus();
                return false;
            } else {
                edtNewPassword.setError(null);
            }

            //new password mustn't contains white space character
            if (!newPassword.matches(REGEX_PASSWORD)) {
                edtNewPassword.setError(
                        getContext().getString(R.string.new_password)+" "+
                                getContext().getResources().getString(R.string.not_contain_white_space_character)
                );
                edtNewPassword.requestFocus();
                return false;
            } else {
                edtNewPassword.setError(null);
            }

            //mat khau phai co it nhat 6 ky tu
            if (newPassword.length() < 6) {
                edtNewPassword.setError(
                        getContext().getResources().getString(R.string.password_must_have_at_least_six_characters)
                );
                edtNewPassword.requestFocus();
                return false;
            } else {
                edtNewPassword.setError(null);
            }

            //retypeNewPassword must be same as newPassword
            if (!retypeNewPassword.equals(newPassword)) {
                edtRetypeNewPassword.setError(
                        getContext().getResources().getString(R.string.repass_must_equal_password)
                );
                edtRetypeNewPassword.requestFocus();
                return false;
            } else {
                edtRetypeNewPassword.setError(null);
            }
        }

        return true;
    }

    private void getInfoFromForm() {
        fullname = edtFullname.getText().toString().trim();
        oldPassword = edtOldPassword.getText().toString().trim();
        newPassword = edtNewPassword.getText().toString().trim();
        retypeNewPassword = edtRetypeNewPassword.getText().toString().trim();
    }

    private void addControls(View view) {
        edtFullname = (EditText) view.findViewById(R.id.edtFullname);
        chkIsChangePassword = (CheckBox) view.findViewById(R.id.chkIsChangePassword);
        llChangePassword = (LinearLayout) view.findViewById(R.id.llChangePassword);
        edtOldPassword = (EditText) view.findViewById(R.id.edtOldPassword);
        edtNewPassword = (EditText) view.findViewById(R.id.edtNewPassword);
        edtRetypeNewPassword = (EditText) view.findViewById(R.id.edtRetypeNewPassword);
        btnSaveChanges = (Button) view.findViewById(R.id.btnSaveChanges);
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
        //khoi phuc lai username
        username = sharedPreferences.getString(KEY_USERNAME, "");
        fullname = userDao.getFullnameByUsername(username);
        edtFullname.setText(fullname);
    }
}
