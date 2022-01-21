package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter.AllQuizzesAdapter;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllQuizsFragment extends Fragment {
    ListView lvAllQuizzes;
    AllQuizzesAdapter allQuizzesAdapter;
    QuizDao quizDao;

    private static final String TAG = "AllQuizsFragmentLog";

    public AllQuizsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_quizs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls(view);
        init();
        addEvents();
    }



    private void init() {
        allQuizzesAdapter = new AllQuizzesAdapter(getActivity(), R.layout.item_all_quiz);
        lvAllQuizzes.setAdapter(allQuizzesAdapter);
        quizDao = new QuizDao(getActivity());
        allQuizzesAdapter.addAll(quizDao.getQuizOrderByNewestDateAddedToOldest());
    }

    private void addEvents() {
    }

    private void addControls(View view) {
        lvAllQuizzes = view.findViewById(R.id.lvAllQuizzes);
    }

    @Override
    public void onResume() {
        super.onResume();
        allQuizzesAdapter.clear();
        allQuizzesAdapter.addAll(quizDao.getQuizOrderByNewestDateAddedToOldest());
        allQuizzesAdapter.notifyDataSetChanged();
    }
}
