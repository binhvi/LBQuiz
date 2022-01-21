package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database.DatabaseHelper;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Question;

public class QuestionDao {
    public static final String TABLE_QUESTIONS = "Questions";

    public static final String COLUMN_QUESTIONS_ID = "id";
    public static final String COLUMN_QUESTIONS_QUIZ_ID = "quizId";
    public static final String COLUMN_QUESTIONS_CONTENT = "content";

    DatabaseHelper databaseHelper;
    QuizDao quizDao;

    public QuestionDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
        quizDao = new QuizDao(context);
    }

    public List<Question> getQuestionByQuizId(int quizId){
        List<Question> questionList = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectQuestionsByQuizId =
                "SELECT * FROM "+TABLE_QUESTIONS+" "
                +"WHERE "+COLUMN_QUESTIONS_QUIZ_ID+"=? " +
                "ORDER BY "+COLUMN_QUESTIONS_ID+" ASC";
        //raw query
        Cursor cursor = database.rawQuery(
                selectQuestionsByQuizId,
                new String[] {String.valueOf(quizId)});
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_QUESTIONS_ID)));
                question.setQuiz(
                        quizDao.getQuizById(
                                cursor.getInt(
                                        cursor.getColumnIndex(
                                                COLUMN_QUESTIONS_QUIZ_ID
                                        )
                                )
                        )
                );
                question.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTIONS_CONTENT)));

                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return questionList;
    }

    public Question getQuestionById(int id) {
        Question question = new Question();
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectQuestionById =
                "SELECT * FROM "+TABLE_QUESTIONS+" "
                +"WHERE "+COLUMN_QUESTIONS_ID+"=?";
        //raw query
        Cursor cursor = database.rawQuery(
                selectQuestionById,
                new String[] {String.valueOf(id)}
        );
        if (cursor.moveToFirst()) {
            question.setId(id);
            question.setQuiz(
                    quizDao.getQuizById(
                            cursor.getInt(
                                    cursor.getColumnIndex(COLUMN_QUESTIONS_QUIZ_ID)
                            )
                    )
            );
            question.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTIONS_CONTENT)));
        }
        cursor.close();
        database.close();
        return question;
    }
}
