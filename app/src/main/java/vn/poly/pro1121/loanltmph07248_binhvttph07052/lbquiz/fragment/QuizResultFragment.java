package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter.QuizResultRecyclerViewAdapter;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity.SaveResult;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.presenter.SaveResultPresenter;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.view.SaveResultView;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizResultFragment extends Fragment implements SaveResultView {
    //view
    SearchView searchViewQuizName;
    RecyclerView rvQuizResult;

    public static QuizResultRecyclerViewAdapter quizResultRecyclerViewAdapter;

    //debug
    private static final String TAG = "QuizResultFragmentLog";

    //presenter
    SaveResultPresenter resultPresenter;


    public QuizResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls(view);

        init();
        addEvents();
    }

    private void addEvents() {
        searchViewQuizName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                giveFilterText(newText);
                return false;
            }
        });
    }

    private void addControls(View view) {
        rvQuizResult = view.findViewById(R.id.rvQuizResult);
        searchViewQuizName = view.findViewById(R.id.searchViewQuizName);
    }

    private void init() {
        //set up recycler view
        LinearLayoutManager vertical = new LinearLayoutManager(getActivity());
        rvQuizResult.setLayoutManager(vertical);

        //load data
        resultPresenter = new SaveResultPresenter(this);
        resultPresenter.loadData(getActivity());
    }

    @Override
    public void displaySaveResult(List<SaveResult> resultList) {
        quizResultRecyclerViewAdapter = new QuizResultRecyclerViewAdapter(getActivity(), resultList);
        rvQuizResult.setAdapter(quizResultRecyclerViewAdapter);
    }

    @Override
    public void giveFilterText(String text) {
        resultPresenter.giveTextToFilterResult(text);
    }
}
