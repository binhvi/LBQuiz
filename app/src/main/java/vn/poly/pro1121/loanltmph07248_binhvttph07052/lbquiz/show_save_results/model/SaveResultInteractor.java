package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.SaveResultDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity.SaveResult;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USERNAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.SHARED_PREFERENCES_NAME;

public class SaveResultInteractor {
    private LoadResultListener listener;


    public SaveResultInteractor(LoadResultListener listener) {
        this.listener = listener;

    }

    //Xử lý tạo dữ liệu
    public void loadData(Context context) {
        String username = getUsernameLoggedIn(context);
        List<SaveResult> saveResultList = new ArrayList<>();

        //database
        SaveResultDao saveResultDao = new SaveResultDao(context);
        saveResultList.addAll(saveResultDao.getAllSaveResultsOrderBySaveTimeNewestToOldest(username));

        listener.onLoadResultSuccess(saveResultList);
    }

    private String getUsernameLoggedIn(Context context) {
        String username;
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(
                        SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                );
        //khoi phuc lai gia tri
        username = sharedPreferences.getString(KEY_USERNAME, "");
        return username;
    }
}
