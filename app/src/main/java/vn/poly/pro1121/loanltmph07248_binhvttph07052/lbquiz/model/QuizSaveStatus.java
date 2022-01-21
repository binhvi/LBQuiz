package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model;

public class QuizSaveStatus {
    private int id;
    private String status;

    public QuizSaveStatus() {
    }

    public QuizSaveStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public QuizSaveStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "QuizSaveStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
