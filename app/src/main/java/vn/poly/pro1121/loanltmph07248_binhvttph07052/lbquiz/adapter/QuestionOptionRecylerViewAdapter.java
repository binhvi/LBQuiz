package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.activity.MainActivity;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuestionOption;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.DoQuizFragment.currentQuizToDo;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.DoQuizFragment.displayOptionsOfThisQuestion;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.DoQuizFragment.displayQuestion;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.DoQuizFragment.questionIndex;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.DoQuizFragment.questionList;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.DoQuizFragment.totalScore;

public class QuestionOptionRecylerViewAdapter extends RecyclerView.Adapter<QuestionOptionRecylerViewAdapter.QuestionOptionViewHolder> {
    Activity context;
    List<QuestionOption> optionList;

    //debug
    private static final String TAG = "OptionAdapterLog";

    public QuestionOptionRecylerViewAdapter(Activity context, List<QuestionOption> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public QuestionOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_quiz_option, parent, false);
        QuestionOptionViewHolder questionOptionViewHolder = new QuestionOptionViewHolder(view);
        return questionOptionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionOptionViewHolder holder, int position) {
        QuestionOption questionOption = optionList.get(position);

        holder.tvQuestionOption.setText(questionOption.getContent());
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public class QuestionOptionViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionOption;

        public QuestionOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionOption = itemView.findViewById(R.id.tvQuestionOption);

            //xu ly su kien chon mot dap an
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuestionOption questionOption = optionList.get(getAdapterPosition());
                    totalScore += questionOption.getPoints();
                    questionIndex++;

                    if (questionIndex < questionList.size()) {
                        displayQuestion(questionIndex);
                        displayOptionsOfThisQuestion(questionList.get(questionIndex));
                        notifyDataSetChanged();
                    } else {
                        ((MainActivity) context).napQuizFinishedFragment(currentQuizToDo, totalScore);
                    }

                }
            });
        }
    }
}
