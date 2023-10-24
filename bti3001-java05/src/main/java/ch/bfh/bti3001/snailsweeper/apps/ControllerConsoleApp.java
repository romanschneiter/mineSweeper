package ch.bfh.bti3001.snailsweeper.apps;

import java.util.Random;
import java.util.Scanner;

import ch.bfh.bti3001.snailsweeper.model.CellType;
import ch.bfh.bti3001.snailsweeper.model.Grid;
import ch.bfh.bti3001.snailsweeper.model.GridSerializer;

public class ControllerConsoleApp {

	private int width = 0;
	private int gameHeight = 0;

	public Grid createInitalGame() {
		Scanner scanGameSize = new Scanner(System.in);
		System.out.print("Enter a game size: ");
		int gameSize = scanGameSize.nextInt();
		this.width = gameSize;
		this.gameHeight = gameSize;

		System.out.println("width: " + width + " | heigth: " + gameHeight);

		GridSerializer gs = new GridSerializer(width, gameHeight);
		String str = gs.buildString();
		Grid initalGrid = gs.deserialize(str);

		return initalGrid;
	}

	/**
	 * places randomly the snails in relation to the size of the grid
	 * @param initalGrid - gameboard where the game is played
	 * @return Grid with random snails
	 */
	public Grid placeSnails(Grid initalGrid) {
		int maxFieldsToPlaceSnails = (this.width - 2) * (this.gameHeight - 2);
		if(this.width == 3 && this.gameHeight ==3) {
			return initalGrid;
		}else {
		int snakesToPlace = (int)(Math.floor(maxFieldsToPlaceSnails) *0.1);
		System.out.println(maxFieldsToPlaceSnails + " Fields to Place " + maxFieldsToPlaceSnails * 0.1 + " Snakes");

		for(int i=0; i<=snakesToPlace; i++) {
			Random random = new Random();
		    int x=random.nextInt(width - 2) + 1;
		    int y=random.nextInt(gameHeight - 2) + 1;
			initalGrid.gridString[y][x] = CellType.LivingSnailHidden;
		}
		return initalGrid;
		}
	}

	public static String move() {
		System.out.println(" ");
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		System.out.println("Enter your move: ");

		Scanner input = new Scanner(System.in);
		String value = input.next();
		System.out.println("Current Move: " + value);
		return value;
	}

	/**
	 * takes in the current move from the console as String. ex. ud4 (uncover + x value + y value) or. fd4 (flag + x value + y value)
	 * @param move - the move as string
	 * @return returns the character "u" or "f" which is needed to initiate the function uncover oder toogleflag
	 */
	public String parseAction(String move) {
		return move.substring(0, 1);
	}

	/**
	 * parsing the x-Value in relation to the length of the string
	 * @param move - the coordinates of the current move ("u" and "f" are already cut off)
	 * @return return a int value in relation to the ASCII-value to use as x-coordinate in the grid
	 */
	public int parseX(String move) {
		int x = 0;
		if (width >= 10 && move.length()>3) {
			try {
				String sub = move.substring(1, 3);
				byte[] bytes = sub.getBytes("US-ASCII");
				x = bytes[0] - 97;
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			try {
				String sub = move.substring(1, 2);
				byte[] bytes = sub.getBytes("US-ASCII");
				x= bytes[0] - 97;
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return x;
	}

	/**
	 * parsing the y-Value in relation to the length of the string
	 * @param move - the coordinates of the current move ("u" and "f" and the x-coordinate are already cut off)
	 * @return return a int value to use as y-coordinate in the grid
	 */
	public int parseY(String move) {
		if (gameHeight >= 10 && width >= 10 && move.length()>3) {
			return (Integer.parseInt(move.substring(2, 4))-1);
		} else if (gameHeight >= 10 && width < 10 && move.length()>3) {
			return (Integer.parseInt(move.substring(2, 5))-1);
		}
		return (Integer.parseInt(move.substring(2, 3))-1);
	}

	/**
	 * updates the view after every move
	 * @param currentState - is the gameboard where the game is played
	 */
	public void updateView(Grid currentState) {
		int row = 0;
		int columns = 0;
		int width = currentState.getNbOfColumns();
		System.out.println();

		for (int i = 97; i < 97 + width; i++) {
			System.out.print((char) i + " ");
		}
		System.out.println("");
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		for (int i = 0; i < currentState.getNbOfLines(); i++) {
			for (int j = 0; j < currentState.getNbOfColumns(); j++) {
				if (currentState.gridString[i][j].label != "EV") {
					if (currentState.gridString[i][j].label == "SV") {
						System.out.print("# ");
					}
					if (currentState.gridString[i][j].label == "EH" || currentState.gridString[i][j].label == "LH") {
						System.out.print("X ");
					}

					if (currentState.gridString[i][j] == CellType.EmptyFlagged
							|| currentState.gridString[i][j] == CellType.LivingSnailFlagged) {
						System.out.print("F ");
					}
					if (currentState.gridString[i][j].label == "CV") {
						System.out.print(". ");
					}

					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb2) {
						System.out.print("2 ");
					} 
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb3) {
						System.out.print("3 ");
					} 
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb4) {
						System.out.print("4 ");
					} 
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb5) {
						System.out.print("5 ");
					} 
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb) {
						System.out.print(currentState.gridString[i][j].label);
					}
				} else {
					System.out.print(" " + " ");
				}
			}
			++row;
			System.out.print(" " + row);
			System.out.println();
		}
		gameStatus(currentState);
	}

	public static void gameStatus(Grid currentState) {
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		if (currentState.gameOver()) {
			System.out.print("You are GAME OVER!");
			System.out.println("");

		}
	}
}
