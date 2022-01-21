package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz;

public class PublicVariableLibrary {
    public static final boolean LOGGED_IN = true;
    public static final boolean NOT_LOGGED_IN = false;

    public static boolean isLoggedIn;

    //quiz
    public static final String KEY_QUIZ = "Quiz";

    //score
    public static final String KEY_USER_SCORE = "QuizScoreOfUser";

    //bookmark status
    public static final int STATUS_UNBOOKMARKED = 0;
    public static final int STATUS_BOOKMARKED = 1;

    //regex
    public static final String REGEX_USERNAME = "\\w+"; //a-zA-Z0-9_
    public static final String REGEX_PASSWORD = "[^\\s]+"; //khong chua ky tu trang

    //login info
    public static final String SHARED_PREFERENCES_NAME = "login_info";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_IS_LOGGIN = "is_login";
}
