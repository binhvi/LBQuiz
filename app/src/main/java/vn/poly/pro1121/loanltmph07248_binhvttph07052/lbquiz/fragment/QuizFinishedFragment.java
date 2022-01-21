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

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_QUIZ;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USER_SCORE;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFinishedFragment extends Fragment {

    public static interface QuizFinishedFragmentListener {
        void buttonSubmitQuizClicked(Quiz quiz, double score);
    }

    private QuizFinishedFragmentListener listener;

    Button btnSubmit;

    Quiz quiz;
    double score;

    //debug
    private static final String TAG = "QuizFinishedFragmentLog";

    public QuizFinishedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_finished, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            quiz = (Quiz) bundle.getSerializable(KEY_QUIZ);
            score = bundle.getDouble(KEY_USER_SCORE);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.buttonSubmitQuizClicked(quiz, score);
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (QuizFinishedFragmentListener) context;
    }
}
