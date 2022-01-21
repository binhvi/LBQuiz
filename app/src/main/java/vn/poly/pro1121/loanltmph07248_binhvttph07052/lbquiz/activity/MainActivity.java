package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.SaveResultDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.UserDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.database.DatabaseHelper;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.DoQuizFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.QuizDetailFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.QuizFinishedFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.QuizFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.QuizResultFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.QuizScoreFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.RegistedFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.SignInSignUpFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.UnregistedFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.fragment.UserDetailChangePasswordFragment;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity.SaveResult;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_IS_LOGGIN;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_QUIZ;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USERNAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USER_SCORE;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.LOGGED_IN;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.SHARED_PREFERENCES_NAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.isLoggedIn;

public class MainActivity extends AppCompatActivity
    implements QuizFinishedFragment.QuizFinishedFragmentListener,
        UnregistedFragment.UnregistedFragmentListener,
        RegistedFragment.RegistedFragmentListener,
        QuizDetailFragment.QuizDetailFragmentListener,
        QuizScoreFragment.QuizScoreFragmentListener {
    BottomNavigationView navigationView;

    //xem da dang nhap hay chua

    //database
    DatabaseHelper databaseHelper;
    QuizDao quizDao;
    UserDao userDao;
    SaveResultDao saveResultDao;

    //login info
    String username;

    //debug
    private static final String TAG = "MainActivityLog";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        init();

        napQuizFragmnent();

        //tao database tu file db cua asset
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.createDatabase();

        addEvents();
    }

    private void init() {
        quizDao = new QuizDao(this);
        userDao = new UserDao(this);
        saveResultDao = new SaveResultDao(this);
    }

    private void addEvents() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        napQuizFragmnent();
                        return true;
                    case R.id.navigation_result:
                        napQuizResultFragment();
                        return true;
                    case R.id.navigation_user_account:
                        napLoginFragment();
                        return true;
                }
                return false;
            }
        });
    }

    public void napLoginFragment() {
        if (isLoggedIn == LOGGED_IN) {
            napRegistedFragment();
        } else {
            napUnRegistedFragment();
        }
    }

    public void napRegistedFragment() {
        //chuyen man hinh RegistedFragment

        //khoi tao fragment
        RegistedFragment registedFragment = new RegistedFragment();

        //khai bao doi tuong FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Them fragment vao layout co id = container
        fragmentManager.beginTransaction().replace(R.id.frame_layout_container, registedFragment).commit();
    }

    public void napUnRegistedFragment() {
        //chuyen man hinh UnregistedFragment
        //khoi tao fragment
        UnregistedFragment unregistedFragment = new UnregistedFragment();
        //khai bao doi tuong FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Them fragment vao layout co id = container
        fragmentManager.beginTransaction().replace(R.id.frame_layout_container, unregistedFragment).commit();
    }

    public void napQuizFragmnent() {
        //khoi tao fragment
        QuizFragment quizFragment = new QuizFragment();

        //khai bao doi tuong FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Them fragment vao layout co id = container
        fragmentManager.beginTransaction().replace(R.id.frame_layout_container, quizFragment).commit();
    }

   public void napDoQuizFragment(Quiz quiz) {
        //khoi tao fragment
       DoQuizFragment doQuizFragment = new DoQuizFragment();

       //khai bao doi tuong FragmentManager
       FragmentManager fragmentManager = getSupportFragmentManager();

       //Them fragment vao layout co id = container
       fragmentManager.beginTransaction().replace(R.id.frame_layout_container, doQuizFragment).commit();

       //gui thong tin quiz vao bundle de gui sang DoQuizFragment
       Bundle bundle = new Bundle();
       bundle.putSerializable(KEY_QUIZ, quiz);
       doQuizFragment.setArguments(bundle);
   }

   public void napQuizFinishedFragment(Quiz quiz, double score) {
        //khoi tao fragment
       QuizFinishedFragment quizFinishedFragment = new QuizFinishedFragment();

       //khai bao doi tuong FragmentManager
       FragmentManager fragmentManager = getSupportFragmentManager();

       //them fragment vao layout co id = container
       fragmentManager.beginTransaction().replace(R.id.frame_layout_container, quizFinishedFragment).commit();

       //gui thong tin quiz va score de gui sang QuizFinishedFragment
       Bundle bundle = new Bundle();
       bundle.putSerializable(KEY_QUIZ, quiz);
       bundle.putDouble(KEY_USER_SCORE, score);
       quizFinishedFragment.setArguments(bundle);
   }

   void napQuizScoreFragment(Quiz quiz, double score) {
        //khoi tao fragment
       QuizScoreFragment quizScoreFragment = new QuizScoreFragment();

       //khai bao doi tuong FragmentManager
       FragmentManager fragmentManager = getSupportFragmentManager();

       //them fragment vao layout co id = container
       fragmentManager.beginTransaction().replace(R.id.frame_layout_container, quizScoreFragment).commit();

       //gui thong tin quiz va score de gui sang QuizScoreFragment
       Bundle bundle = new Bundle();
       bundle.putSerializable(KEY_QUIZ, quiz);
       bundle.putDouble(KEY_USER_SCORE, score);
       quizScoreFragment.setArguments(bundle);
   }

   void napQuizResultFragment() {
        //khoi tao fragment
       QuizResultFragment quizResultFragment = new QuizResultFragment();

       //khai bao doi tuong FragmentManager
       FragmentManager fragmentManager = getSupportFragmentManager();

       //them fragment vao layout co id = container
       fragmentManager.beginTransaction().replace(R.id.frame_layout_container, quizResultFragment).commit();
   }

   public void napQuizDetailFragment(Quiz quiz) {
        //khoi tao fragment
       QuizDetailFragment quizDetailFragment = new QuizDetailFragment();
       //khai bao doi tuong FragmentManager
       FragmentManager fragmentManager = getSupportFragmentManager();
       //them fragment vao layout
       fragmentManager.beginTransaction().replace(R.id.frame_layout_container, quizDetailFragment).commit();

       Bundle bundle = new Bundle();
       bundle.putSerializable(KEY_QUIZ, quiz);
       quizDetailFragment.setArguments(bundle);
   }


    private void addControls() {
        navigationView = findViewById(R.id.navigationView);
    }

    @Override
    public void buttonSubmitQuizClicked(Quiz quiz, double score) {
        napQuizScoreFragment(quiz, score);
    }


    @Override
    public void buttonSignInSignUpClicked() {
        napSignInSignUpFragment();
    }

    private void napSignInSignUpFragment() {
        //khoi tao fragment
        SignInSignUpFragment signInSignUpFragment = new SignInSignUpFragment();

        //khai bao doi tuong Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //them fragment
        fragmentManager.beginTransaction().replace(R.id.frame_layout_container, signInSignUpFragment).commit();
    }

    @Override
    public void viewGroupRegistedUserClicked() {
        napUserDetailChangePasswordFragment();
    }

    private void napUserDetailChangePasswordFragment() {
        //khoi tao fragment
        UserDetailChangePasswordFragment userDetailChangePasswordFragment = new UserDetailChangePasswordFragment();
        //khai bao doi tuong FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //them fragment
        fragmentManager.beginTransaction().replace(R.id.frame_layout_container, userDetailChangePasswordFragment).commit();
    }

    @Override
    public void buttonDoQuizClicked(Quiz quiz) {
        napDoQuizFragment(quiz);
    }

    @Override
    public void buttonSaveScoreClicked(int quizId, double score, View view) {
        if (isLoggedIn == LOGGED_IN) {
            getUsername();
            saveScore(quizId, score, view);
        } else {
            Toast.makeText(
                    MainActivity.this,
                    R.string.you_have_to_log_in_to_save_result,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                MODE_PRIVATE
        );
        username = sharedPreferences.getString(KEY_USERNAME, "");
    }

    private void saveScore(int quizId, double score, View view) {
        Button buttonSaveResult = view.findViewById(R.id.buttonSaveResult);

        SaveResult saveResult = new SaveResult();
        saveResult.setQuiz(quizDao.getQuizById(quizId));
        saveResult.setUser(userDao.getUserByUsername(username));
        saveResult.setSaveTime(new Date(System.currentTimeMillis()));
        saveResult.setScore(score);

        long result = saveResultDao.insertSaveResult(saveResult);
        if (result > 0) {
            //thong bao, disable button de chi luu mot ket qua mot lan
            Toast.makeText(
                    MainActivity.this,
                    R.string.save_successfully,
                    Toast.LENGTH_SHORT
            ).show();
            buttonSaveResult.setEnabled(false);
        } else {
            Toast.makeText(
                    MainActivity.this,
                    R.string.save_failed,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreLoginInfo();
    }

    private void restoreLoginInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                MODE_PRIVATE
        );
        //khoi phuc lai trang thai dang nhap
        isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGIN, false);
        username = sharedPreferences.getString(KEY_USERNAME, "");
    }
}
