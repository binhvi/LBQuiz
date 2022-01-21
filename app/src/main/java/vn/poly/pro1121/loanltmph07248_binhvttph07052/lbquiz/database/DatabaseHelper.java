package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelperLog";
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private static String DB_NAME = "LBQuiz.db"; //Database name
    private SQLiteDatabase mDatabase;
    private final Context mContext;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        //lay duong dan theo version
        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    /**
     *Kiểm tra xem file db đã tồn tại trong bộ nhớ của app chưa.
     * Nếu chưa thì copy file từ assets sang.
     */
    public void createDatabase() {
        //If the database does not exist, copy it from the assets.

        boolean mDatabaseExist = checkDatabase();
        if (!mDatabaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assets
                copyDatabase();
                Log.i(TAG, "createDatabase: database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopytingDatabase");
            }
        }
    }

    /**
     * Copy dữ liệu từ file db của assets sang file db của app.
     * @throws IOException
     */
    private void copyDatabase() throws IOException{
        InputStream mInput = mContext.getAssets().open(DB_NAME); //database from asset
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //open database, so we can query it
    public boolean openDatabase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDatabase != null;
    }


    /**
     * Tạo tham chiếu file trong package của app.
     * (không tự tạo file nếu file không tồn tại)
     * Trả về giá trị của lời gọi hàm kiểm tra xem là file này đã tồn tại chưa.
     * @return Nếu file đã tồn tại rồi thì trả về true, file chưa tồn tại thì trả về false
     */
    private boolean checkDatabase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
