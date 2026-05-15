package utilities;

public class TableColumn {

    private String label;
    private int width;

    public TableColumn(String label, int width) {
        this.label = label;
        this.width = width;
    }

    public String getLabel() {
        return label;
    }

    public int getWidth() {
        return width;
    }

}