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
import android.widget.ImageView;
import android.widget.TextView;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_QUIZ;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizDetailFragment extends Fragment {

    public static interface QuizDetailFragmentListener {
        void buttonDoQuizClicked(Quiz quiz);
    }

    private QuizDetailFragmentListener listener;

    private ImageView imgQuiz;
    private TextView tvQuizTitle;
    private TextView tvQuizDescription;
    private Button btnToDoQuizFragment;

    //quiz from all quizzes and saved quiz
    Quiz quiz;

    //debug
    private static final String TAG = "QuizDetailFragmentLog";

    public QuizDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            quiz = (Quiz) bundle.getSerializable(KEY_QUIZ);
            displayQuizDetail(quiz);
        }

        addEvents();
    }

    private void addEvents() {
        btnToDoQuizFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.buttonDoQuizClicked(quiz);
                }
            }
        });
    }

    private void addControls(View view) {
        imgQuiz = (ImageView) view.findViewById(R.id.imgQuiz);
        tvQuizTitle = (TextView) view.findViewById(R.id.tvQuizTitle);
        tvQuizDescription = (TextView) view.findViewById(R.id.tvQuizDescription);
        btnToDoQuizFragment = (Button) view.findViewById(R.id.btnToDoQuizFragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (QuizDetailFragmentListener) context;
    }

    public void displayQuizDetail(Quiz quiz) {
        imgQuiz.setImageBitmap(quiz.getImage());
        tvQuizTitle.setText(quiz.getName());
        tvQuizDescription.setText(quiz.getDescription());
    }
}
