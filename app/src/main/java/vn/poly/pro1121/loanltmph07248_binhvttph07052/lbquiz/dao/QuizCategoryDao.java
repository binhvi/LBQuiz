package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database.DatabaseHelper;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuizCategory;

public class QuizCategoryDao {
    public static final String TABLE_QUIZ_CATEGORIES = "QuizCategories";

    public static final String COLUMN_QUIZ_CATEGORIES_ID = "id";
    public static final String COLUMN_QUIZ_CATEGORIES_NAME = "name";

    DatabaseHelper databaseHelper;

    public QuizCategoryDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    //get QuizCategory object by id
    public QuizCategory getQuizCategoryById(int id) {
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectQuizCategoryById =
                "SELECT * FROM "+TABLE_QUIZ_CATEGORIES+" "+
                "WHERE "+COLUMN_QUIZ_CATEGORIES_ID+"=?";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(
                selectQuizCategoryById,
                new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()) {
            QuizCategory quizCategory = new QuizCategory();
            quizCategory.setId(id);
            quizCategory.setName(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZ_CATEGORIES_NAME)));

            cursor.close();
            database.close();
            return quizCategory;
        }

        cursor.close();
        database.close();
        return null;
    }
}
