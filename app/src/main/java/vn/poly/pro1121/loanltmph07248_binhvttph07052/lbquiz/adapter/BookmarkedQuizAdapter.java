package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.FunctionLibrary;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.activity.MainActivity;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.STATUS_BOOKMARKED;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.STATUS_UNBOOKMARKED;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao.COLUMN_QUIZZES_DATE_ADDED;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao.COLUMN_QUIZZES_SAVE_STATUS_ID;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao.TABLE_QUIZZES;

public class BookmarkedQuizAdapter extends ArrayAdapter<Quiz> {
    FunctionLibrary functionLibrary;
    Activity context;
    int resource;

    //database
    QuizDao quizDao;
    String selectBookmarkedQuizScript =
            "SELECT * FROM "+TABLE_QUIZZES+
                    " WHERE "+COLUMN_QUIZZES_SAVE_STATUS_ID+"=?"
                    +" ORDER BY "+COLUMN_QUIZZES_DATE_ADDED+" DESC";
    String[] arguments = new String[]{String.valueOf(STATUS_BOOKMARKED)};

    public BookmarkedQuizAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        functionLibrary = new FunctionLibrary();
        quizDao = new QuizDao(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final int pos = position; //de su dung trong ham xu ly su kien
        int numberOfWordsWantRetrieve; //number of words of description displays

        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(resource, null);

        //lay view
        ImageView imgQuiz;
        TextView tvQuizTitle;
        TextView tvQuizDescription;
        ImageButton imageButtonUnbookmarkQuiz;

        imgQuiz = (ImageView) customView.findViewById(R.id.imgQuiz);
        tvQuizTitle = (TextView) customView.findViewById(R.id.tvQuizTitle);
        tvQuizDescription = (TextView) customView.findViewById(R.id.tvQuizDescription);
        imageButtonUnbookmarkQuiz = (ImageButton) customView.findViewById(R.id.imageButtonUnbookmarkQuiz);

        //set thong tin tu doi tuong len view
        Quiz quiz = getItem(position);
        imgQuiz.setImageBitmap(quiz.getImage());
        tvQuizTitle.setText(quiz.getName());

        String description = quiz.getDescription();
        numberOfWordsWantRetrieve = 20;
        //Nếu mô tả > 25 từ thì chỉ hiển thị 25 từ đầu tiên, ngược lại hiển thị như bình thường
        if (functionLibrary.countWords(description) > numberOfWordsWantRetrieve) {
            //thì cắt chuỗi rồi thêm ...vào
            String subStringOfDescription = functionLibrary.getFirstXWordsOfString(
                    description,
                    numberOfWordsWantRetrieve
            );

            tvQuizDescription.setText(
                    String.format("%s...", subStringOfDescription)
            );
        } else {
            tvQuizDescription.setText(description);
        }

        imageButtonUnbookmarkQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quiz currentQuiz = getItem(pos);
                //set trang thai thanh unbookmarked
                int result = quizDao.updateQuizBookmarkStatus(currentQuiz, STATUS_UNBOOKMARKED);
                if (result > 0) {
                    refreshAdapter();
                }
            }
        });


        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).napQuizDetailFragment(getItem(pos));
            }
        });

        return customView;
    }

    private void refreshAdapter() {
        clear();
        addAll(quizDao.getQuizByCondition(selectBookmarkedQuizScript, arguments));
        notifyDataSetChanged();
    }
}
