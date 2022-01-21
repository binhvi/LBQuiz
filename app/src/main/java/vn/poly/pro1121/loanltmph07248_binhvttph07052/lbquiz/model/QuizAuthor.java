package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model;

public class QuizAuthor {
    private int id;
    private String name;

    public QuizAuthor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public QuizAuthor(String name) {
        this.name = name;
    }

    public QuizAuthor() {
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
        return "QuizAuthor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
