package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter.AllQuizzesAdapter;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter.BookmarkedQuizAdapter;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.STATUS_BOOKMARKED;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao.COLUMN_QUIZZES_DATE_ADDED;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao.COLUMN_QUIZZES_SAVE_STATUS_ID;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao.TABLE_QUIZZES;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedQuizFragment extends Fragment {
    ListView lvSavedQuiz;
   BookmarkedQuizAdapter adapter;

    //database
    QuizDao quizDao;
    String selectBookmarkedQuizScript =
            "SELECT * FROM "+TABLE_QUIZZES+
            " WHERE "+COLUMN_QUIZZES_SAVE_STATUS_ID+"=?"
            +" ORDER BY "+COLUMN_QUIZZES_DATE_ADDED+" DESC";
    String[] arguments = new String[]{String.valueOf(STATUS_BOOKMARKED)};

    public SavedQuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls(view);
        init();
        getBookmarkedQuizzesFromDatabase();
        addEvents();
    }

    private void getBookmarkedQuizzesFromDatabase() {

        //get bookmarked quizzes from db and add to adapter
        adapter.addAll(quizDao.getQuizByCondition(selectBookmarkedQuizScript, arguments));
        adapter.notifyDataSetChanged();
    }

    private void addEvents() {

    }

    private void init() {
        adapter = new BookmarkedQuizAdapter(getActivity(), R.layout.item_saved_quiz);
        lvSavedQuiz.setAdapter(adapter);

        //database
        quizDao = new QuizDao(getActivity());
    }

    private void addControls(View view) {
        lvSavedQuiz = view.findViewById(R.id.lvSavedQuiz);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(quizDao.getQuizByCondition(selectBookmarkedQuizScript, arguments));
        adapter.notifyDataSetChanged();
    }
}
