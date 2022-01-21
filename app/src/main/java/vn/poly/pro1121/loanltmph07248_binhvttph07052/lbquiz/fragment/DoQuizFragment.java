package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter.QuestionOptionRecylerViewAdapter;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.OptionDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuestionDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Question;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuestionOption;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_QUIZ;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoQuizFragment extends Fragment {
    public static TextView tvQuizQuestionIndex;
    public static TextView tvQuestionContent;
    private RecyclerView rvQuizQuestionOptions;
    QuestionOptionRecylerViewAdapter questionOptionRecylerViewAdapter;
    public static List<QuestionOption> questionOptionList;


    //score
    public static double totalScore;
    public static int questionIndex;

    //quiz from previous screen
    public static Quiz currentQuizToDo;

    //debug
    private static final String TAG = "DoQuizFragmentLog";

    //database
    QuestionDao questionDao;
    public static OptionDao optionDao;
    public static List<Question> questionList;

    public DoQuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_do_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls(view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentQuizToDo = (Quiz) bundle.getSerializable(KEY_QUIZ);
        }
        init();

        displayFirstQuestion();
    }

    private void displayFirstQuestion() {
            //hien thi cau hoi o vi tri questionIndex
            displayQuestion(0);

            //hien thi dap an cua cau hoi o vi tri questionIndex
            displayOptionsOfThisQuestion(questionList.get(0));
    }

    public static void displayQuestion(int indexOfQuestion) {
        tvQuizQuestionIndex.setText(String.format("%d/%d", indexOfQuestion + 1, questionList.size()));
        tvQuestionContent.setText(questionList.get(indexOfQuestion).getContent());
    }

    public static void displayOptionsOfThisQuestion(Question question) {
        //truy van option
        questionOptionList.clear();
        questionOptionList.addAll(optionDao.getOptionsByQuestionId(question.getId()));
    }

    private void init() {
        totalScore = 0.0;
        questionIndex = 0;
        //config recycler view question option
        questionOptionList = new ArrayList<>();
        questionOptionRecylerViewAdapter = new QuestionOptionRecylerViewAdapter(
                getActivity(), questionOptionList
        );
        rvQuizQuestionOptions.setAdapter(questionOptionRecylerViewAdapter);
        LinearLayoutManager vertical = new LinearLayoutManager(getActivity());
        rvQuizQuestionOptions.setLayoutManager(vertical);

        //database
        questionDao = new QuestionDao(getActivity());
        optionDao = new OptionDao(getActivity());
        questionList = questionDao.getQuestionByQuizId(currentQuizToDo.getId());
    }

    private void addControls(View view) {
        tvQuizQuestionIndex = (TextView) view.findViewById(R.id.tvQuizQuestionIndex);
        tvQuestionContent = (TextView) view.findViewById(R.id.tvQuestionContent);
        rvQuizQuestionOptions = (RecyclerView) view.findViewById(R.id.rvQuizQuestionOptions);
    }

}
