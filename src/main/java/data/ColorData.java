package data;

public enum ColorData {

    BLACK("Черный"),
    WHITE("Белый"),
    BROWN("Коричневый");



    private String name;

    ColorData(String name) {
       this.name  = name;
    }

    public String getName() {
        return name;

    }

}
