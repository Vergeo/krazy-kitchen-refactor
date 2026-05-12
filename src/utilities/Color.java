package utilities;

public class Color {

	public static String id(int id) {
		return String.format("\u001B[38;5;%dm", id);
	}
}
