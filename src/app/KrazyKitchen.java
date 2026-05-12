package app;

import java.util.ArrayList;
import java.util.Arrays;

import mode.Input;
import mode.Output;
import utilities.Color;
import utilities.IO;

public class KrazyKitchen {

	public KrazyKitchen() {
		displayMainMenu("");
	}
	
	public void resetGame() {
		IO.writeFile("restaurant.txt", new ArrayList<String>(Arrays.asList("")));
		IO.writeFile("inventory.txt", new ArrayList<String>(Arrays.asList("")));
		IO.writeFile("score.txt", new ArrayList<String>(Arrays.asList("0")));
	}
	
	private void displayMainMenu(String err) {
		IO.cls();
		System.out.println(Color.id(208) + "Welcome to VG's KrazyKitchen");
		System.out.println(Color.id(240) + "============================");
		System.out.println();
		System.out.println(Color.id(255) + "Main Menu:");
		System.out.println(Color.id(250) + "1. Output Mode (View orders and inventory");
		System.out.println("2. Input Mode (Process orders)");
		System.out.println("3. How to Play");
		System.out.println("4. Exit");
		if (!err.isEmpty()) System.out.println(Color.id(1) + "Error: " + err);
		System.out.print(Color.id(255) + ">> " + Color.id(226));
		
		String in;
		in = IO.scan.nextLine();
		if (in.equals("1")) {
			resetGame();
			new Output();
		}
		else if (in.equals("2")) new Input();
		else if (in.equals("3")) displayHowToPlay();
		else if (in.equals("4"));
		else displayMainMenu("Invalid Input!");
	}
	
	private void displayHowToPlay() {
		IO.cls();
		System.out.print(Color.id(255));
		System.out.println(" ╔══════════════════════════════════════════════════════════════════════════════╗\r\n"
				+ " ║                                HOW TO PLAY                                   ║\r\n"
				+ " ║                     "+Color.id(208)+"        VG's KrazyKitchen     "+Color.id(255)+"                           ║\r\n"
				+ " ╠══════════════════════════════════════════════════════════════════════════════╣\r\n"
				+ " ║ Welcome, Master Chef! Your mission: Manage a burger joint, complete orders   ║\r\n"
				+ " ║ accurately and quickly. You'll switch between Output and Input modes.        ║\r\n"
				+ " ╟──────────────────────────────────────────────────────────────────────────────╢\r\n"
				+ " ║ 1. Output Mode (View orders and inventory):                                  ║\r\n"
				+ " ║    ? View active orders, inventory, and current score                        ║\r\n"
				+ " ║    ? Monitor customer names, burger requests, timers, and potential rewards  ║\r\n"
				+ " ║    ? Watch out for VIP orders - higher rewards!                              ║\r\n"
				+ " ║    ? Keep an eye on your inventory                                           ║\r\n"
				+ " ╟──────────────────────────────────────────────────────────────────────────────╢\r\n"
				+ " ║ 2. Input Mode (Process orders):                                              ║\r\n"
				+ " ║    ? Assemble burgers based on customer requests                             ║\r\n"
				+ " ║    ? Follow recipes exactly (e.g., 'Cheeseburger' = Bun + Patty + Cheese)    ║\r\n"
				+ " ║    ? Correct assembly = Points and rewards                                   ║\r\n"
				+ " ╟──────────────────────────────────────────────────────────────────────────────╢\r\n"
				+ " ║ 3. Scoring and Gameplay Tips:                                                ║\r\n"
				+ " ║    ? Base reward for each completed order                                    ║\r\n"
				+ " ║    ? Bonus rewards for VIP orders                                            ║\r\n"
				+ " ║    ? Avoid expired orders                                                    ║\r\n"
				+ " ║    ? Manage your time and inventory wisely                                   ║\r\n"
				+ " ╟──────────────────────────────────────────────────────────────────────────────╢\r\n"
				+ " ║ 4. Exiting the Game:                                                         ║\r\n"
				+ " ║    ? Type 'exit' during Output Mode to leave                                 ║\r\n"
				+ " ║    ? Return to main menu or switch modes anytime                             ║\r\n"
				+ " ╚══════════════════════════════════════════════════════════════════════════════╝\r\n"
				+ "                 Good luck, Chef! Keep those burgers coming!");
		IO.enter();
		displayMainMenu("");
	}

}
