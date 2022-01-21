package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model;

public class QuizCategory {
    private int id;
    private String name;

    public QuizCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public QuizCategory() {
    }

    public QuizCategory(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "QuizCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
