package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model;

public class Question {
    private int id;
    private Quiz quiz;
    private String content;

    public Question(int id, Quiz quiz, String content) {
        this.id = id;
        this.quiz = quiz;
        this.content = content;
    }

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", quiz=" + quiz +
                ", content='" + content + '\'' +
                '}';
    }
}
