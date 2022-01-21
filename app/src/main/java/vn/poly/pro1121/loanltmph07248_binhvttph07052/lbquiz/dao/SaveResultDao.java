package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database.DatabaseHelper;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity.SaveResult;

public class SaveResultDao {
    public static final String TABLE_SAVE_RESULTS = "SaveResults";

    public static final String COLUMN_SAVE_RESULT_QUIZ_ID = "quizId";
    public static final String COLUMN_SAVE_RESULT_USERNAME = "username";
    public static final String COLUMN_SAVE_RESULT_SAVE_TIME = "saveTime";
    public static final String COLUMN_SAVE_RESULT_SCORE = "score";

    DatabaseHelper databaseHelper;
    QuizDao quizDao;
    UserDao userDao;

    public SaveResultDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
        quizDao = new QuizDao(context);
        userDao = new UserDao(context);
    }

//    them sua xoa, getAll
    public long insertSaveResult(SaveResult saveResult) {
        //xin quyen
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        values.put(COLUMN_SAVE_RESULT_QUIZ_ID, saveResult.getQuiz().getId());
        values.put(COLUMN_SAVE_RESULT_USERNAME, saveResult.getUser().getUsername());
        //getTime: Date to milisecond
        values.put(COLUMN_SAVE_RESULT_SAVE_TIME, saveResult.getSaveTime().getTime());
        values.put(COLUMN_SAVE_RESULT_SCORE, saveResult.getScore());

        //insert
        long result = database.insert(
                TABLE_SAVE_RESULTS,
                null,
                values
        );
        database.close();
        return result;
    }

    public List<SaveResult> getAllSaveResultsByUsername(String username) {
        List<SaveResult> saveResults = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery =
                "SELECT * FROM "+TABLE_SAVE_RESULTS+" " +
                "WHERE "+COLUMN_SAVE_RESULT_USERNAME+"=?";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, new String[] {username});
        if (cursor.moveToFirst()) {
            do {
                SaveResult saveResult = new SaveResult();

                saveResult.setQuiz(
                    quizDao.getQuizById(
                            cursor.getInt(cursor.getColumnIndex(COLUMN_SAVE_RESULT_QUIZ_ID))
                    )
                );

                saveResult.setUser(
                    userDao.getUserByUsername(
                            cursor.getString(cursor.getColumnIndex(COLUMN_SAVE_RESULT_USERNAME))
                    )
                );

                saveResult.setSaveTime(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_SAVE_RESULT_SAVE_TIME))));
                saveResult.setScore(cursor.getDouble(cursor.getColumnIndex(COLUMN_SAVE_RESULT_SCORE)));

                saveResults.add(saveResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return saveResults;
    }

    public int deleteSaveResult(SaveResult saveResult) {
        //xin quyen
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        //xoa
        int result = database.delete(
                TABLE_SAVE_RESULTS,
                COLUMN_SAVE_RESULT_QUIZ_ID+"=? AND "+
                        COLUMN_SAVE_RESULT_USERNAME+"=? AND "+
                        COLUMN_SAVE_RESULT_SAVE_TIME+"= ?",
                new String[] {
                        String.valueOf(saveResult.getQuiz().getId()),
                        saveResult.getUser().getUsername(),
                        String.valueOf(saveResult.getSaveTime().getTime())
                }
        );
        //dong ket noi
        database.close();
        return result;
    }

    public List<SaveResult> getAllSaveResultsOrderBySaveTimeNewestToOldest(String username) {
        List<SaveResult> saveResults = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery =
                "SELECT * FROM " +TABLE_SAVE_RESULTS+" "+
                "WHERE "+COLUMN_SAVE_RESULT_USERNAME+"=? "+
                "ORDER BY "+COLUMN_SAVE_RESULT_SAVE_TIME +" DESC";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, new String[] {username});
        if (cursor.moveToFirst()) {
            do {
                SaveResult saveResult = new SaveResult();

                saveResult.setQuiz(
                        quizDao.getQuizById(
                                cursor.getInt(cursor.getColumnIndex(COLUMN_SAVE_RESULT_QUIZ_ID))
                        )
                );

                saveResult.setUser(
                        userDao.getUserByUsername(
                                cursor.getString(cursor.getColumnIndex(COLUMN_SAVE_RESULT_USERNAME))
                        )
                );

                saveResult.setSaveTime(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_SAVE_RESULT_SAVE_TIME))));
                saveResult.setScore(cursor.getDouble(cursor.getColumnIndex(COLUMN_SAVE_RESULT_SCORE)));

                saveResults.add(saveResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return saveResults;
    }

}
