package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model;

public class QuizResult {
    private int quizId;
    private double upperLimit;
    private double lowerLimit;
    private String title;
    private String description;

    public QuizResult() {
    }

    public QuizResult(int quizId, double upperLimit, double lowerLimit, String title, String description) {
        this.quizId = quizId;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.title = title;
        this.description = description;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "quizId=" + quizId +
                ", upperLimit=" + upperLimit +
                ", lowerLimit=" + lowerLimit +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
