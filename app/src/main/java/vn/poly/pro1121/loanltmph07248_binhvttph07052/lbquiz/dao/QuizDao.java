package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database.DatabaseHelper;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuizSaveStatus;

public class QuizDao {
    public static final String TABLE_QUIZZES = "Quizzes";

    public static final String COLUMN_QUIZZES_ID = "id";
    public static final String COLUMN_QUIZZES_NAME = "name";
    public static final String COLUMN_QUIZZES_AUTHOR_ID = "authorId";
    public static final String COLUMN_QUIZZES_DESCRIPTION = "description";
    public static final String COLUMN_QUIZZES_IMAGE = "image";
    public static final String COLUMN_QUIZZES_CATEGORY_ID = "categoryId";
    public static final String COLUMN_QUIZZES_DATE_ADDED = "dateAdded";
    public static final String COLUMN_QUIZZES_SAVE_STATUS_ID = "saveStatusId";

    DatabaseHelper databaseHelper;
    QuizCategoryDao quizCategoryDao;
    QuizSaveStatusDao quizSaveStatusDao;
    QuizAuthorDao quizAuthorDao;

    public QuizDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
        quizCategoryDao = new QuizCategoryDao(context);
        quizSaveStatusDao = new QuizSaveStatusDao(context);
        quizAuthorDao = new QuizAuthorDao(context);
    }

    //get All, get Quizzes object by id
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery = "SELECT * FROM "+TABLE_QUIZZES;
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Quiz quiz = new Quiz();
                quiz.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_QUIZZES_ID)));
                quiz.setName(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZZES_NAME)));

                quiz.setQuizAuthor(
                        quizAuthorDao.getQuizAuthorById(
                                cursor.getInt(
                                        cursor.getColumnIndex(
                                                COLUMN_QUIZZES_AUTHOR_ID)
                                )
                        )
                );

                quiz.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZZES_DESCRIPTION)));

                //blob from db --> bitmap
                byte[] byteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_QUIZZES_IMAGE));
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                quiz.setImage(bm);

                quiz.setQuizCategory(
                        quizCategoryDao.getQuizCategoryById(
                                cursor.getInt(
                                        cursor.getColumnIndex(COLUMN_QUIZZES_CATEGORY_ID)
                                )
                        )
                );

                quiz.setDateAdded(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_QUIZZES_DATE_ADDED))));

                quiz.setSaveStatus(
                        quizSaveStatusDao.getQuizSaveStatusById(
                                cursor.getInt(
                                        cursor.getColumnIndex(COLUMN_QUIZZES_SAVE_STATUS_ID)
                                )
                        )
                );

                quizzes.add(quiz);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return quizzes;
    }

    public Quiz getQuizById(int id) {
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectQuizById =
                "SELECT * FROM "+TABLE_QUIZZES+" "
                +"WHERE "+COLUMN_QUIZZES_ID+"=?";
        //rawQuery
        Cursor cursor = database.rawQuery(
                selectQuizById,
                new String[] {String.valueOf(id)}
        );

        if (cursor.moveToFirst()) {
            Quiz quiz = new Quiz();
            quiz.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_QUIZZES_ID)));
            quiz.setName(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZZES_NAME)));

            quiz.setQuizAuthor(
                    quizAuthorDao.getQuizAuthorById(
                            cursor.getInt(
                                    cursor.getColumnIndex(
                                            COLUMN_QUIZZES_AUTHOR_ID)
                            )
                    )
            );

            quiz.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZZES_DESCRIPTION)));

            //blob from db --> bitmap
            byte[] byteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_QUIZZES_IMAGE));
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            quiz.setImage(bm);

            quiz.setQuizCategory(
                    quizCategoryDao.getQuizCategoryById(
                            cursor.getInt(
                                    cursor.getColumnIndex(COLUMN_QUIZZES_CATEGORY_ID)
                            )
                    )
            );

            quiz.setDateAdded(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_QUIZZES_DATE_ADDED))));

            quiz.setSaveStatus(
                    quizSaveStatusDao.getQuizSaveStatusById(
                            cursor.getInt(
                                    cursor.getColumnIndex(COLUMN_QUIZZES_SAVE_STATUS_ID)
                            )
                    )
            );

            cursor.close();
            database.close();
            return quiz;
        }

        //khong tim duoc quiz co id tuong ung
        cursor.close();
        database.close();
        return null;
    }

    public List<Quiz> getQuizOrderByNewestDateAddedToOldest() {
        List<Quiz> quizzes = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery = "SELECT * FROM "+TABLE_QUIZZES +
                " ORDER BY dateAdded DESC;";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Quiz quiz = new Quiz();
                quiz.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_QUIZZES_ID)));
                quiz.setName(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZZES_NAME)));

                quiz.setQuizAuthor(
                        quizAuthorDao.getQuizAuthorById(
                                cursor.getInt(
                                        cursor.getColumnIndex(
                                                COLUMN_QUIZZES_AUTHOR_ID)
                                )
                        )
                );

                quiz.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZZES_DESCRIPTION)));

                //blob from db --> bitmap
                byte[] byteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_QUIZZES_IMAGE));
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                quiz.setImage(bm);

                quiz.setQuizCategory(
                        quizCategoryDao.getQuizCategoryById(
                                cursor.getInt(
                                        cursor.getColumnIndex(COLUMN_QUIZZES_CATEGORY_ID)
                                )
                        )
                );

                quiz.setDateAdded(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_QUIZZES_DATE_ADDED))));

                quiz.setSaveStatus(
                        quizSaveStatusDao.getQuizSaveStatusById(
                                cursor.getInt(
                                        cursor.getColumnIndex(COLUMN_QUIZZES_SAVE_STATUS_ID)
                                )
                        )
                );

                quizzes.add(quiz);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return quizzes;
    }

    /**
     * update trạng thái bookmark/chưa bookmark cho quiz
     * @param quiz
     * @param bookmarkStatusId
     * @return
     */
    public int updateQuizBookmarkStatus(Quiz quiz, int bookmarkStatusId) {
        //xin quyen
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUIZZES_SAVE_STATUS_ID, bookmarkStatusId);
        //update
        int result = database.update(
                TABLE_QUIZZES,
                values,
                COLUMN_QUIZZES_ID+"=?",
                new String[] {String.valueOf(quiz.getId())}
        );
        database.close();
        return result;
    }

    public List<Quiz> getQuizByCondition(String conditionSql, String[] argumentArr) {
        List<Quiz> quizzes = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(conditionSql, argumentArr);
        if (cursor.moveToFirst()) {
            do {
                Quiz quiz = new Quiz();
                quiz.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_QUIZZES_ID)));
                quiz.setName(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZZES_NAME)));

                quiz.setQuizAuthor(
                        quizAuthorDao.getQuizAuthorById(
                                cursor.getInt(
                                        cursor.getColumnIndex(
                                                COLUMN_QUIZZES_AUTHOR_ID)
                                )
                        )
                );

                quiz.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_QUIZZES_DESCRIPTION)));

                //blob from db --> bitmap
                byte[] byteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_QUIZZES_IMAGE));
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                quiz.setImage(bm);

                quiz.setQuizCategory(
                        quizCategoryDao.getQuizCategoryById(
                                cursor.getInt(
                                        cursor.getColumnIndex(COLUMN_QUIZZES_CATEGORY_ID)
                                )
                        )
                );

                quiz.setDateAdded(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_QUIZZES_DATE_ADDED))));

                quiz.setSaveStatus(
                        quizSaveStatusDao.getQuizSaveStatusById(
                                cursor.getInt(
                                        cursor.getColumnIndex(COLUMN_QUIZZES_SAVE_STATUS_ID)
                                )
                        )
                );

                quizzes.add(quiz);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return quizzes;
    }

}
