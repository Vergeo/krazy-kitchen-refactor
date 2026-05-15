package utilities;

import java.util.ArrayList;

public class Table {

    // CHANGED (B-03): displayTable is now a manger only delegates to 4 focused methods
    public static void displayTable(String title, ArrayList<TableColumn> columns, ArrayList<ArrayList<String>> data) {
        int[] layout = calculateLayout(columns);
        int totalLength = layout[0];
        ArrayList<Integer> ends = buildEnds(columns);

        printTopBorder(title, totalLength, ends);
        printHeaderRow(columns);
        printDataRows(data, totalLength, ends, columns);
        printBottomBorder(totalLength, ends);
    }

    // (B-03) EXTRACTED: computes total table width from column widths
    private static int[] calculateLayout(ArrayList<TableColumn> columns) {
        int totalLength = 1;
        for (TableColumn col : columns) {
            totalLength += col.getWidth() + 3;
        }
        return new int[]{totalLength};
    }

    // (B-03) EXTRACTED: builds the list of column end positions for border junction detection
    private static ArrayList<Integer> buildEnds(ArrayList<TableColumn> columns) {
        ArrayList<Integer> ends = new ArrayList<Integer>();
        int pos = 1;
        for (TableColumn col : columns) {
            pos += col.getWidth() + 3;
            ends.add(pos);
        }
        return ends;
    }

    // (B-03) EXTRACTED: prints the top border row, with optional centered title
    private static void printTopBorder(String title, int totalLength, ArrayList<Integer> ends) {
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
    }

    // (B-03) EXTRACTED: prints the header row using column labels and widths
    private static void printHeaderRow(ArrayList<TableColumn> columns) {
        System.out.print("║");
        for (TableColumn col : columns) {
            String tmp = String.format(" %%-%ds ║", col.getWidth());
            System.out.format(tmp, col.getLabel());
        }
        System.out.println();
    }

    // (B-03) EXTRACTED: prints the separator + data rows if data is present
    private static void printDataRows(ArrayList<ArrayList<String>> data, int totalLength, ArrayList<Integer> ends, ArrayList<TableColumn> columns) {
        if (data == null) return;

        int counter = 2;
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
                String tmp = String.format("%s %%-%ds %s║", Color.id(250), columns.get(i).getWidth(), Color.id(255));
                System.out.format(tmp, line.get(i));
            }
            System.out.println();
        }
    }

    // (B-03) EXTRACTED: prints the bottom border row
    private static void printBottomBorder(int totalLength, ArrayList<Integer> ends) {
        int counter = 2;
        System.out.print("╚");
        for (int i = 0; i < totalLength - 2; i++) {
            if (ends.contains(counter)) System.out.print("╩");
            else System.out.print("═");
            counter++;
        }
        System.out.println("╝");
    }

}