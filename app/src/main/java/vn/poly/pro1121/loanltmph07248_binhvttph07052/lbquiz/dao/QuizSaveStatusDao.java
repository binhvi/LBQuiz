package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database.DatabaseHelper;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuizSaveStatus;

public class QuizSaveStatusDao {
    public static final String TABLE_QUIZ_SAVE_STATUS = "QuizSaveStatus";

    public static final String COLUMN_QUIZ_SAVE_STATUS_ID = "id";
    public static final String COLUMN_QUIZ_SAVE_STATUS_NAME = "status";

    DatabaseHelper databaseHelper;

    public QuizSaveStatusDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public QuizSaveStatus getQuizSaveStatusById(int id) {
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectStatusById =
                "SELECT * FROM "+TABLE_QUIZ_SAVE_STATUS+" "+
                "WHERE "+COLUMN_QUIZ_SAVE_STATUS_ID+"=?";
        //cau lenh raw query
        Cursor cursor = database.rawQuery(selectStatusById, new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()) {
            QuizSaveStatus quizSaveStatus = new QuizSaveStatus();
            quizSaveStatus.setId(id);
            quizSaveStatus.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZ_SAVE_STATUS_NAME)));

            cursor.close();
            database.close();
            return quizSaveStatus;
        }

        cursor.close();
        database.close();
        return null;
    }
}
