package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class IO {

	public static Scanner scan = new Scanner(System.in);
	public static Random rand = new Random();
	
	public static void cls() {
		for (int i = 0; i < 50; i++) System.out.println();
	}
	
	public static void enter() {
		System.out.println();
		System.out.print(Color.id(40) + "Press ENTER to continue...");
		scan.nextLine();
	}
	
	public static void writeFile(String source, ArrayList<String> lines) {
		try {
			FileWriter fw = new FileWriter(source);
			if (lines.isEmpty()) {
				fw.close();
				return;
			}
			for (String line : lines) {
				fw.write(line);
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> readFile(String source) {
		FileReader fr;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			fr = new FileReader(source);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) lines.add(line);
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines;
	}
	
	// (D-02) REMOVED: checkFile is not used at all by the program
}
