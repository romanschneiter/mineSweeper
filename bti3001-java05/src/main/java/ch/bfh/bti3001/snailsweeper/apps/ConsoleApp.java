package ch.bfh.bti3001.snailsweeper.apps;

import java.util.Random;
import java.util.Scanner;

import ch.bfh.bti3001.snailsweeper.model.CellType;
import ch.bfh.bti3001.snailsweeper.model.Grid;
import ch.bfh.bti3001.snailsweeper.model.GridSerializer;

public class ConsoleApp {

//	Grid initalGame;
	public static Grid createInitalGame() {
		Scanner scanWidth = new Scanner(System.in);
		System.out.print("Enter width of Game: ");
		int width = scanWidth.nextInt();
		scanWidth.close();

		Scanner scanHeight = new Scanner(System.in);
		System.out.print("Enter height of Game: ");
		int height = scanHeight.nextInt();

		scanHeight.close();
		System.out.println("width: " + width + " | heigth: " + height);

		GridSerializer gs = new GridSerializer(width, height);
		String str = gs.buildString();
		Grid initalGrid = gs.deserialize(str);

		return initalGrid;
	}

	private static Grid placeSnails(Grid initalGrid, int width, int height) {

		int maxFieldsToPlaceSnails = (width - 2) * (height - 2);
		int snakesToPlace = (int)(Math.floor(maxFieldsToPlaceSnails) *0.1);
		System.out.println(maxFieldsToPlaceSnails + " Fields to Place " + maxFieldsToPlaceSnails * 0.1 + " Snakes");

		for(int i=0; i<=snakesToPlace; i++) {
			Random random = new Random();
		    int x=random.nextInt(width - 1) + 1;
		    int y=random.nextInt(height - 1) + 1;
			initalGrid.gridString[x][y] = CellType.LivingSnailHidden;
		}
		return initalGrid;
	}

	public static void updateView(Grid currentState) {
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
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		if (currentState.gameOver()) {
			System.out.print(" You Are GAME OVER");
		}
	}

	public static void main(String[] args) {
		//Controller & Serializer
		ControllerConsoleApp controllerConsoleApp = new ControllerConsoleApp();
		GridSerializer gs = new GridSerializer();

		//IntroGame with Description
		System.out.println("***************************");
		System.out.println("WELCOME TO SNAILSWEEPER");
		System.out.println(" ");
		System.out.println("u=Uncover, f=Flag/Unflag");
		System.out.println(" ");

		//Create InitalGame 
		Grid initalGrid = controllerConsoleApp.createInitalGame();
		System.out.println(" ");

		//Place Snakes
		Grid initalGridWithSnails = controllerConsoleApp.placeSnails(initalGrid);
		initalGrid = initalGridWithSnails;
		System.out.println(" ");
		controllerConsoleApp.updateView(initalGrid);
		
		while(!initalGrid.hasWon() || !initalGrid.gameOver()) {
			String move = controllerConsoleApp.move();
			String action = controllerConsoleApp.parseAction(move);
			int x = controllerConsoleApp.parseX(move);
			int y = controllerConsoleApp.parseY(move);
			System.out.println("action: " + action);
			System.out.println("x: " +x);
			System.out.println("y: " + y);			
			if(action != null) {
				if(action.equals("u")) {
					initalGrid.uncover(x,y);
				}else if (action.equals("f")){
					initalGrid.toggleFlag(x, y);
				}
			}
			controllerConsoleApp.updateView(initalGrid);
			if(initalGrid.hasWon() || initalGrid.hasLost()) {
				break;
			}
			initalGrid.getGameStatus();
		}	
	}
}
