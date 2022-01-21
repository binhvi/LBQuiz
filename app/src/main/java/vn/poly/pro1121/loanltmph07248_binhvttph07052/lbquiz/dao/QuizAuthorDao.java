package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database.DatabaseHelper;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuizAuthor;

public class QuizAuthorDao {
    public static final String TABLE_QUIZ_AUTHOR = "QuizAuthors";

    //column
    public static final String COLUMN_QUIZ_AUTHOR_ID = "id";
    public static final String COLUMN_QUIZ_AUTHOR_NAME = "name";

    DatabaseHelper databaseHelper;

    public QuizAuthorDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public QuizAuthor getQuizAuthorById(int id) {
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectAuthorById =
                "SELECT * FROM "+TABLE_QUIZ_AUTHOR+" "
                +"WHERE "+COLUMN_QUIZ_AUTHOR_ID+"=?";
        //rawQuery
        Cursor cursor = database.rawQuery(
                selectAuthorById,
                new String[] {String.valueOf(id)}
        );
        if (cursor.moveToFirst()) {
            QuizAuthor quizAuthor = new QuizAuthor();
            quizAuthor.setId(id);
            quizAuthor.setName(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZ_AUTHOR_NAME)));

            cursor.close();
            database.close();
            return quizAuthor;
        }

        cursor.close();
        database.close();
        return null;
    }
}
