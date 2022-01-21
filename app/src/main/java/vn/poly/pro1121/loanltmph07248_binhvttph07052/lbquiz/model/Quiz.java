package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class Quiz implements Serializable {
    private int id;
    private String name;
    private QuizAuthor quizAuthor;
    private String description;
    private Bitmap image;
    private QuizCategory quizCategory;
    private Date dateAdded;
    private QuizSaveStatus saveStatus;

    public Quiz(int id, String name, QuizAuthor quizAuthor, String description, Bitmap image, QuizCategory quizCategory, Date dateAdded, QuizSaveStatus saveStatus) {
        this.id = id;
        this.name = name;
        this.quizAuthor = quizAuthor;
        this.description = description;
        this.image = image;
        this.quizCategory = quizCategory;
        this.dateAdded = dateAdded;
        this.saveStatus = saveStatus;
    }

    public Quiz(String name, String description, Bitmap image, QuizSaveStatus saveStatus) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.saveStatus = saveStatus;
    }

    public Quiz(String name, String description, QuizSaveStatus saveStatus) {
        this.name = name;
        this.description = description;
        this.saveStatus = saveStatus;
    }

    public Quiz(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Quiz(String name) {
        this.name = name;
    }

    public Quiz() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuizAuthor getQuizAuthor() {
        return quizAuthor;
    }

    public void setQuizAuthor(QuizAuthor quizAuthor) {
        this.quizAuthor = quizAuthor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public QuizCategory getQuizCategory() {
        return quizCategory;
    }

    public void setQuizCategory(QuizCategory quizCategory) {
        this.quizCategory = quizCategory;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public QuizSaveStatus getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(QuizSaveStatus saveStatus) {
        this.saveStatus = saveStatus;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quizAuthor=" + quizAuthor +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", quizCategory=" + quizCategory +
                ", dateAdded=" + dateAdded +
                ", saveStatus=" + saveStatus +
                '}';
    }
}
