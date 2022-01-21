package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.presenter;

import android.content.Context;

import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter.QuizResultRecyclerViewAdapter;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.LoadResultListener;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.SaveResultInteractor;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity.SaveResult;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.view.SaveResultView;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.QuizResultFragment.quizResultRecyclerViewAdapter;

public class SaveResultPresenter implements LoadResultListener {
    private SaveResultInteractor resultInteractor;
    private SaveResultView resultView;

    public SaveResultPresenter(SaveResultView resultView) {
        this.resultView = resultView;
        resultInteractor = new SaveResultInteractor(this);
    }

    public void loadData(Context context) {
        resultInteractor.loadData(context);
    }

    @Override
    public void onLoadResultSuccess(List<SaveResult> resultList) {
        resultView.displaySaveResult(resultList);
    }

    public void giveTextToFilterResult(String textToFilter) {
        quizResultRecyclerViewAdapter.getFilter().filter(textToFilter);
    }
}
