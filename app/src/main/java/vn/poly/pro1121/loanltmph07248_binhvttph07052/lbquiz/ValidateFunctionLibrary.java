package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz;

import android.content.Context;
import android.widget.Toast;

public class ValidateFunctionLibrary {
    public static final boolean FAIL = false;

    Context context;

    public ValidateFunctionLibrary(Context context) {
        this.context = context;
    }

    public boolean isTextEmpty(String text) {
        if (text.isEmpty()) {
            return true;
        }
        return false;
    }

}
