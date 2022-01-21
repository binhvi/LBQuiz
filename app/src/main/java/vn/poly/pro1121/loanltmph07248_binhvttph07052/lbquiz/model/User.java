package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.REGEX_PASSWORD;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.REGEX_USERNAME;

public class User extends BaseObservable {
    private String username;
    private String password;
    private String fullname;


    private Context context;

    public User() {
    }

    public User(String username, String password, String fullname, Context context) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.context = context;
    }

    public User(String username, String password, Context context) {
        this.username = username;
        this.password = password;
        this.context = context;
    }

    public User(Context context) {
        this.context = context;
    }

    //username
    @Bindable
    public String getUsername() {
        if (username == null) {
            return "";
        }
        return username;
    }

    @Bindable
    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable({"username"})
    public String getUsernameError() {
        if (getUsername().trim().isEmpty()) {
            return context.getString(R.string.username)+" "+
                    context.getResources().getString(R.string.can_not_be_empty);
        }

        //username must not contain any special characters
        if (!getUsername().trim().matches(REGEX_USERNAME)) {
            return context.
                    getResources().
                    getString(
                            R.string.username_only_contains_letters_numbers_and_understroke_character);
        }

        return "";
    }

    //password
    @Bindable
    public String getPassword() {
        if (password == null) {
            return "";
        }
        return password;
    }

    @Bindable
    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable({"password"})
    public String getPasswordError() {
        //password empty
        if (getPassword().trim().isEmpty()) {
            return context.getString(R.string.password)+" "+
                    context.getResources().getString(R.string.can_not_be_empty);
        }

        //mat khau khong chua ky tu trang
        if (!getPassword().trim().matches(REGEX_PASSWORD)) {
            return context.getString(R.string.password)+" "+
                    context.getResources().getString(
                            R.string.not_contain_white_space_character
                    );
        }

        //mat khau phai co it nhat 6 ky tu
        if (getPassword().trim().length() < 6) {
            return context
                    .getResources()
                    .getString(
                            R.string.password_must_have_at_least_six_characters
                    );
        }

        return "";
    }

    //fullname
    @Bindable
    public String getFullname() {
        if (fullname == null) {
            return "";
        }
        return fullname;
    }

    @Bindable
    public void setFullname(String fullname) {
        this.fullname = fullname;
        notifyPropertyChanged(BR.fullname);
    }

    @Bindable({"fullname"})
    public String getFullnameError() {
        if (getFullname().isEmpty()) {
            return context.getResources().getString(R.string.fullname) +" "+
                    context.getResources().getString(R.string.can_not_be_empty);
        }
        return "";
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
