package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database.DatabaseHelper;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuestionOption;

public class OptionDao {
    public static final String TABLE_OPTIONS = "Options";

    public static final String COLUMN_OPTIONS_QUESTION_ID = "questionId";
    public static final String COLUMN_OPTIONS_QUESTION_CONTENT = "content";
    public static final String COLUMN_OPTIONS_QUESTION_POINTS = "points";

    DatabaseHelper databaseHelper;
    QuestionDao questionDao;

    //debug
    private static final String TAG = "OptionDaoLog";

    public OptionDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
        questionDao = new QuestionDao(context);
    }

    public List<QuestionOption> getOptionsByQuestionId(int questionId) {
        List<QuestionOption> optionList = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectOptionsByQuestionId =
                "SELECT * FROM "+TABLE_OPTIONS+" "
                +"WHERE "+COLUMN_OPTIONS_QUESTION_ID+"=? " +
                "ORDER BY rowid ASC";
        //rawQuery
        Cursor cursor = database.rawQuery(
                selectOptionsByQuestionId,
                new String[] {String.valueOf(questionId)}
        );
        if (cursor.moveToFirst()) {
            do {
                QuestionOption questionOption = new QuestionOption();
                questionOption.setQuestion(questionDao.getQuestionById(questionId));
                questionOption.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_OPTIONS_QUESTION_CONTENT)));
                questionOption.setPoints(cursor.getDouble(cursor.getColumnIndex(COLUMN_OPTIONS_QUESTION_POINTS)));

                optionList.add(questionOption);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return optionList;
    }
}
