package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model;

public class QuestionOption {
    private Question question;
    private String content;
    private double points;

    public QuestionOption(Question question, String content, double points) {
        this.question = question;
        this.content = content;
        this.points = points;
    }

    public QuestionOption() {
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "QuestionOption{" +
                "question=" + question +
                ", content='" + content + '\'' +
                ", points=" + points +
                '}';
    }
}
