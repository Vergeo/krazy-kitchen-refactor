package utilities;

import java.util.ArrayList;

public class Table {

	public static void displayTable(String title, ArrayList<String> header, ArrayList<Integer> size, ArrayList<ArrayList<String>> data) {
		int totalLength = 1;
		ArrayList<Integer> ends = new ArrayList<Integer>();
		for (Integer i : size) {
			totalLength += i.intValue();
			totalLength += 3;
			ends.add(totalLength);
		}
		
		
		int counter = 2;
		System.out.print("╔");
		if (title.isEmpty())  {
			for (int i = 0; i < totalLength-2; i++) {
				if (ends.contains(counter)) System.out.print("╦");
				else System.out.print("═");
				counter++;
			}
		}
		else {
			int titleLength = title.length() + 2;
//			System.out.println((totalLength - 2 - titleLength)/2);
			for (int i = 0; i < (totalLength - 2 - titleLength)/2; i++) {
				if (ends.contains(counter)) System.out.print("╦");
				else System.out.print("═");
				counter++;
			}
			System.out.print(" " + title + " ");
			counter += titleLength;
			for (int i = 0; i < (totalLength - 2 - titleLength + 1)/2; i++) {
				if (ends.contains(counter)) System.out.print("╦");
				else System.out.print("═");
				counter++;
			}
		}
		System.out.println("╗");
		
		System.out.print("║");
		for (int i = 0; i < header.size(); i++) {
			String tmp = String.format(" %%-%ds ║", size.get(i).intValue());
			System.out.format(tmp, header.get(i));
		}
		
		System.out.println();
		
		
		
		if (data != null) {
			counter = 2;
			System.out.print("╠");
			for (int i = 0; i < totalLength-2; i++) {
				if (ends.contains(counter)) System.out.print("╬");
				else System.out.print("═");
				counter++;
			}
			System.out.println("╣");
			
			for (ArrayList<String> line : data) {
				System.out.print("║");
				for (int i = 0; i < line.size(); i++) {
					String tmp = String.format("%%s %%-%ds %%s║", size.get(i).intValue());
					System.out.format(tmp, Color.id(250), line.get(i), Color.id(255));
				}
				System.out.println();
			}
		}
		
		counter = 2;
		System.out.print("╚");
		for (int i = 0; i < totalLength-2; i++) {
			if (ends.contains(counter)) System.out.print("╩");
			else System.out.print("═");
			counter++;
		}
		System.out.println("╝");
	}

}
