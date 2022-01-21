package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model;

import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity.SaveResult;

public interface LoadResultListener {
    void onLoadResultSuccess(List<SaveResult> resultList);
}
