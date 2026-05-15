package utilities;

import java.util.ArrayList;

public class Table {

    // CHANGED (B-02): header + size collapsed into a single ArrayList<TableColumn>
    public static void displayTable(String title, ArrayList<TableColumn> columns, ArrayList<ArrayList<String>> data) {
        int totalLength = 1;
        ArrayList<Integer> ends = new ArrayList<Integer>();
        for (TableColumn col : columns) {
            totalLength += col.getWidth();
            totalLength += 3;
            ends.add(totalLength);
        }

        int counter = 2;
        System.out.print("╔");
        if (title.isEmpty()) {
            for (int i = 0; i < totalLength - 2; i++) {
                if (ends.contains(counter)) System.out.print("╦");
                else System.out.print("═");
                counter++;
            }
        } else {
            int titleLength = title.length() + 2;
            for (int i = 0; i < (totalLength - 2 - titleLength) / 2; i++) {
                if (ends.contains(counter)) System.out.print("╦");
                else System.out.print("═");
                counter++;
            }
            System.out.print(" " + title + " ");
            counter += titleLength;
            for (int i = 0; i < (totalLength - 2 - titleLength + 1) / 2; i++) {
                if (ends.contains(counter)) System.out.print("╦");
                else System.out.print("═");
                counter++;
            }
        }
        System.out.println("╗");

        // CHANGED (B-02): use col.getLabel() and col.getWidth() instead of separate lists
        System.out.print("║");
        for (TableColumn col : columns) {
            String tmp = String.format(" %%-%ds ║", col.getWidth());
            System.out.format(tmp, col.getLabel());
        }
        System.out.println();

        if (data != null) {
            counter = 2;
            System.out.print("╠");
            for (int i = 0; i < totalLength - 2; i++) {
                if (ends.contains(counter)) System.out.print("╬");
                else System.out.print("═");
                counter++;
            }
            System.out.println("╣");

            for (ArrayList<String> line : data) {
                System.out.print("║");
                for (int i = 0; i < line.size(); i++) {
                    String tmp = String.format("%%s %%-%ds %%s║", columns.get(i).getWidth());
                    System.out.format(tmp, Color.id(250), line.get(i), Color.id(255));
                }
                System.out.println();
            }
        }

        counter = 2;
        System.out.print("╚");
        for (int i = 0; i < totalLength - 2; i++) {
            if (ends.contains(counter)) System.out.print("╩");
            else System.out.print("═");
            counter++;
        }
        System.out.println("╝");
    }

}