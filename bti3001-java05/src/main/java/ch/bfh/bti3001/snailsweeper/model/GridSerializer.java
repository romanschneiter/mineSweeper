package ch.bfh.bti3001.snailsweeper.model;

import java.util.Scanner;

public class GridSerializer {
	private int width;
	private int height;
	private Grid grid;

	public GridSerializer(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public GridSerializer() {
	}

	/**
	 * Creates a empty game board as String, which will be the base for the nxn grid (first step of console app)
	 * @return a String as abstraction of the game field in any prefered size
	 */
	public String buildString() {
		StringBuilder finalString = new StringBuilder();
		StringBuilder solidLinesStart = new StringBuilder();
		StringBuilder solidLinesEnd = new StringBuilder();
		StringBuilder gamefieldLines = new StringBuilder();

		// Create SolidLines
		for (int i = width; i > 0; i--) {
			solidLinesStart.append("SV ");
			solidLinesEnd.append("SV ");
		}
		solidLinesStart.append("| ");
		solidLinesEnd.append("| ");

		// Append 1 SolidLine
		finalString.append(solidLinesStart);

		// Create Empty GameLine
		gamefieldLines.append("SV ");
		for (int i = width - 2; i > 0; i--) {
			gamefieldLines.append("EH ");
		}
		gamefieldLines.append("SV | ");

		// Append GameLine xHeight
		for (int j = height - 2; j > 0; j--) {
			finalString.append(gamefieldLines);
		}

		// Append 1 SolidLine
		finalString.append(solidLinesEnd);

		System.out.println(finalString);
		return finalString.toString();
	}

	/**
	 * translates the nxn matrix to a String
	 * @param grid - the nxn matrix as input for the the loop and prints every Celltype (Enum) to the console
	 * @return 
	 */
	public String serialize(Grid grid) {
		System.out.println("- - - - - - - - - - - - - - - -");

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				System.out.print(grid.gridString[i][j].label + "  ");
			}
			System.out.println();
		}
		return "";
	}

	/**
	 * parses the different String values and creates an game board of cell types Enum 
	 * @param str - takes a String as an abstraction of the game board, every field has a different Value (SV = Wall // EH = EmptyHidden // LH = LivingSnailHidden // EV = EmptyVisible // CV=CrushedSnailVisible)
	 * @return returns an nxn matrix
	 */
	public Grid deserialize(String str) {

		int counterForStringIndex = 0;
		String[] cleanedString = stringSlicer(str);

		// WENN SPIELFELDGROESSE NICHT BEKANNT ZÄHLE DIE FELDER DES STRINGS
		if (this.width == 0 && this.height == 0) {
			int counterWidth = 0;
			int counterHeigth = 0;
			for (String a : cleanedString) {
				if(a.equals("SV") && a != "|") {
					counterWidth++;
				}else {
					break;
				}
			}
			for (String b : cleanedString) {
				if (b.equals("|")) {
					counterHeigth++;
				}
			}
			this.width = counterWidth;
			this.height = counterHeigth;
		}
		int fields = (this.width - 2) * (this.height - 2);

		// Interpretiere String als ENUM UND SPEICHERE IN 2-D Array
		CellType[][] gridCellType = new CellType[this.height][this.width];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j <= this.width; j++) {
//		    	  STRINGVALUE AUSELSEN // Zeilenumbruch bei |
				if (cleanedString[counterForStringIndex].contentEquals("|")) {
					counterForStringIndex++;
					break;
				} else {
					String toLookUp = cleanedString[counterForStringIndex];
					CellType ct = CellType.fromString(toLookUp);
					gridCellType[i][j] = ct;
				}
				counterForStringIndex++;
			}
		}
		return new Grid(gridCellType);
	}

	public String[] stringSlicer(String str) {
		str = str.trim();
		String[] newStr = str.split("\\s+");
		for (int i = 0; i < newStr.length; i++) {
		}
		return newStr;
	}
}
