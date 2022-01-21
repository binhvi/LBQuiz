package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity;

import java.util.Date;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.Quiz;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.User;

public class SaveResult {
    private Quiz quiz;
    private User user;
    private Date saveTime;
    private double score;

    public SaveResult(Quiz quiz, User user, Date saveTime, double score) {
        this.quiz = quiz;
        this.user = user;
        this.saveTime = saveTime;
        this.score = score;
    }

    public SaveResult() {
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SaveResult{" +
                "quiz=" + quiz +
                ", user=" + user +
                ", saveTime=" + saveTime +
                ", score=" + score +
                '}';
    }
}
