package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizResultDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuizResult;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_QUIZ;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USER_SCORE;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizScoreFragment extends Fragment {
    public static interface QuizScoreFragmentListener {
        void buttonSaveScoreClicked(int quizId, double score, View view);
    }

    private QuizScoreFragmentListener listener;

    private TextView tvResultScore;
    private TextView tvResultTitle;
    private TextView tvResultDescription;
    private Button buttonSaveResult;

    //info
    Quiz quiz;
    double score;

    //database
    QuizResultDao quizResultDao;

    //result
    QuizResult quizResult;

    //debug
    private static final String TAG = "QuizScoreFragmentLog";

    public QuizScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);

        init();

        //query result
        quizResult = quizResultDao.getResultByQuizIdAndScore(quiz.getId(), score);
        displayQuizResult();

        addEvents(view);
    }

    private void init() {
        //get info from previous screen
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            quiz = (Quiz) bundle.getSerializable(KEY_QUIZ);
            score = bundle.getDouble(KEY_USER_SCORE);
        }

        quizResultDao = new QuizResultDao(getActivity());
    }

    private void displayQuizResult() {
        tvResultScore.setText(
                String.format("%s %d %s",
                        getContext().getResources().getString(R.string.you_scored),
                        (int) score,
                        getContext().getResources().getString(R.string.points)));
        tvResultTitle.setText(quizResult.getTitle());
        tvResultDescription.setText(quizResult.getDescription());
    }

    private void addEvents(final View view) {
        buttonSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.buttonSaveScoreClicked(quiz.getId(), score, view);
                }
            }
        });
    }

    private void addControls(View view) {
        tvResultScore = (TextView) view.findViewById(R.id.tvResultScore);
        tvResultTitle = (TextView) view.findViewById(R.id.tvResultTitle);
        tvResultDescription = (TextView) view.findViewById(R.id.tvResultDescription);
        buttonSaveResult = (Button) view.findViewById(R.id.buttonSaveResult);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (QuizScoreFragmentListener) context;
    }
}
