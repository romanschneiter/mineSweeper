package ch.bfh.bti3001.snailsweeper.model;

public class Grid {

	public CellType[][] gridString;

	public Grid(CellType[][] stringMatrix) {
		this.gridString = stringMatrix;
	}
	
	/**
	 * a recursive uncover mechanism of a specific coordinate which checks different base cases first.
	 * if a field is a snake the game is lost.
	 * As long as all neighbor's all empty cells uncover them, if its a bomb place the number of adjacent bombs in to the field
	 * @param x - x coordinate of the grid
	 * @param y - y coordinate of the grid
	 */
	public void uncover(int x, int y) {

		// Check game field borders
		if (x < 0 || y < 0 || y >= gridString.length || x >= gridString[0].length) {
			return;
		}
		if (gridString[y][x] == CellType.LivingSnailHidden) {
			gridString[y][x] = CellType.CrushedSnailVisible;
			return;
		}
		if (gridString[y][x] == CellType.LivingSnailFlagged) {
			return;
		}
		if (gridString[y][x] == CellType.EmptyVisible) {
			return;
		}
		if (gridString[y][x] == CellType.SolidVisible) {
			return;
		}
		
		gridString[y][x] = CellType.EmptyVisible;

		//counts number of bombs
		int numberOfBombsOfField = numberOfBombs(x, y);

		//Places distance to Bomb
		if (numberOfBombsOfField > 0 ) {
			if (gridString[y][x] != CellType.ShowDistanceToBomb || gridString[y][x] != CellType.ShowDistanceToBomb2|| gridString[y][x] != CellType.ShowDistanceToBomb3 || gridString[y][x] != CellType.ShowDistanceToBomb4|| gridString[y][x] != CellType.ShowDistanceToBomb5    ) {

				if (numberOfBombsOfField == 5 ) {
					CellType ct = CellType.ShowDistanceToBomb4;
					gridString[y][x] = ct;
				}
				if (numberOfBombsOfField == 4 ) {
					CellType ct = CellType.ShowDistanceToBomb4;
					gridString[y][x] = ct;
				}
				if (numberOfBombsOfField == 3 ) {
					CellType ct = CellType.ShowDistanceToBomb3;
					gridString[y][x] = ct;
				}
				if (numberOfBombsOfField == 2 ) {

					CellType ct = CellType.ShowDistanceToBomb2;
					gridString[y][x] = ct;
				}
				if(numberOfBombsOfField == 1){
				
					CellType ct = CellType.ShowDistanceToBomb;
					ct.setLabel("1 ");
					gridString[y][x] = ct;
				}
			}
			return;
		}

		uncover(x + 1, y); // Right
//		uncover(i+1, j + 1); // RightDown
		uncover(x, y + 1); // Down
//		uncover(i + 1, j-1); // DownLeft
		uncover(x - 1, y); // Left
//		uncover(i - 1, j-1); // Topleft
		uncover(x, y - 1); // Top
//		uncover(i - 1, j+1); //TopRight

	}

	/**
	 * Counts number of bombs
	 * @param x coordinate of the grid
	 * @param y coordinate of the grid
	 * @return number of bombs
	 */
	public int numberOfBombs(int x, int y) {
		int bombCounter = 0;
		// TOP
		if (isInGrid(x - 1, y - 1)) {
			if (this.gridString[y - 1][x - 1] == CellType.LivingSnailHidden) {
				bombCounter++;
			}
		}
		if (isInGrid(x - 1, y)) {
			if (this.gridString[y - 1][x] == CellType.LivingSnailHidden) {
				bombCounter++;
			}
		}
		if (isInGrid(x - 1, y + 1)) {
			if (this.gridString[y - 1][x + 1] == CellType.LivingSnailHidden) {
				bombCounter++;
			}
		}
		// RIGHT
		if (isInGrid(x, y + 1)) {
			if (this.gridString[y][x + 1] == CellType.LivingSnailHidden) {
				bombCounter++;
			}
		}
		// BOTTOM
		if (isInGrid(x + 1, y + 1)) {
			if (this.gridString[y + 1][x + 1] == CellType.LivingSnailHidden) {
				bombCounter++;
			}
		}
		if (isInGrid(x + 1, y)) {
			if (this.gridString[y + 1][x] == CellType.LivingSnailHidden) {
				bombCounter++;
			}
		}
		if (isInGrid(x + 1, y - 1)) {
			if (this.gridString[y + 1][x - 1] == CellType.LivingSnailHidden) {
				bombCounter++;
			}
		}
		// LEFT
		if (isInGrid(x, y - 1)) {
			if (this.gridString[y][x - 1] == CellType.LivingSnailHidden) {
				bombCounter++;
			}
		}
		return bombCounter;
	}

	/**
	 * checks if the actual position is in grid
	 * @param i - x coordinate 
	 * @param j - y coordinate
	 * @return true if a value is in grid, false if outside
	 */
	public boolean isInGrid(int i, int j) {
		if (i < 0 || j < 0 || j >= gridString.length || i >= gridString[0].length) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * places a flag to a specific coordinate
	 * @param i - x coordinate
	 * @param j - y coordinate
	 */
	public void toggleFlag(int i, int j) {
		
		if((gridString[j][i] == CellType.EmptyFlagged)|| (gridString[j][i] == CellType.LivingSnailFlagged)  ) {
			// Reverse Flagged
			if (gridString[j][i] == CellType.EmptyFlagged) {
				gridString[j][i] = CellType.EmptyHidden;
				gridString[j][i].label = "EH";
				System.out.println(gridString[j][i].label);
			}
			if (gridString[j][i] == CellType.LivingSnailFlagged) {
				gridString[j][i] = CellType.LivingSnailHidden;
				gridString[j][i].label = "LH";
				System.out.println(gridString[j][i].label);
			}
		}else {
			// Flag
			if (gridString[j][i] == CellType.EmptyHidden) {
				gridString[j][i] = CellType.EmptyFlagged;
				gridString[j][i].label = "EF";
				System.out.println(gridString[j][i].label);
			}
			if (gridString[j][i] == CellType.LivingSnailHidden) {
				gridString[j][i] = CellType.LivingSnailFlagged;
				gridString[j][i].label = "LF";
				System.out.println(gridString[j][i].label);
			}	
		}	
	}

	/**
	 * checks if in the grid is a cell type of CrushedSnailVisible
	 * @return if the cell type CrushedSnailVisible is visible in the Grid the function returns true and the game is lost, if false the game continues
	 */
	public boolean gameOver() {	
		if(hasWon()) {
			System.out.println("WON!");
			System.out.println("");
			return false;
		}else {	
		int height = getNbOfLines();
		int width = getNbOfColumns();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (gridString[i][j] == CellType.CrushedSnailVisible) {
					return true;
				}
			}
		}
		return false;
		}
	}

	public boolean hasLost() {
		if(gameOver()) {
			return true;
		}
		return false;
	}

	/**
	 * the function check's if the living snail fields are flagged, if so the game is won
	 * @return true if the game is won and all snails are flagged, false if not all snails are flagged and the game continues
	 */
	public boolean hasWon() {
		int countingLivingSnailsHidden = 0;
		int countingCrushedSnailVisible = 0;
		int countingFlaggedSnails = 0;
		int countingEmptyFlagged = 0;
		int countingHiddenElements = 0;
		
		int height = getNbOfLines();
		int width = getNbOfColumns();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(gridString[i][j] == CellType.LivingSnailHidden) {
					countingLivingSnailsHidden++;
				}
				if(gridString[i][j] == CellType.CrushedSnailVisible) {
					countingCrushedSnailVisible++;
				}
				if(gridString[i][j] == CellType.EmptyHidden) {
					countingHiddenElements++;
				}
				if(gridString[i][j] == CellType.LivingSnailFlagged) {
					countingFlaggedSnails++;
				}
				if(gridString[i][j] == CellType.EmptyFlagged) {
					countingEmptyFlagged++;
				}
			}
		}
		
		if(countingHiddenElements == 0 && countingLivingSnailsHidden == 0 && countingEmptyFlagged ==0){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * reports the current game status
	 */
	public void getGameStatus() {
		int countingLivingSnailsHidden = 0;
		int countingCrushedSnailVisible = 0;
		int countingFlaggedElements = 0;
		int countingHiddenElements = 0;
		
		int height = getNbOfLines();
		int width = getNbOfColumns();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(gridString[i][j] == CellType.LivingSnailHidden) {
					countingLivingSnailsHidden++;
				}
				if(gridString[i][j] == CellType.CrushedSnailVisible) {
					countingCrushedSnailVisible++;
				}
				if(gridString[i][j] == CellType.EmptyHidden) {
					countingHiddenElements++;
				}
				if(gridString[i][j] == CellType.LivingSnailFlagged) {
					countingFlaggedElements++;
				}
			}
		}
		System.out.println("LivingSnails: " +countingLivingSnailsHidden);
		System.out.println("HiddenElements: " +countingHiddenElements);
		System.out.println("FlaggedElements: " +countingFlaggedElements);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Grid otherMatrix = ((Grid) obj);

		if (this.getNbOfColumns() != otherMatrix.getNbOfColumns()
				|| this.getNbOfLines() != otherMatrix.getNbOfLines()) {
			return false;
		}

		for (int k = 0; k < this.gridString.length; k++) {
			for (int l = 0; l < this.gridString[k].length; l++) {
				if (this.gridString[k][l] == otherMatrix.gridString[k][l]
						|| this.gridString[k][l] == CellType.ShowDistanceToBomb) {
					return true;
				}
			}
		}
		return false;
	}

	//width
	public int getNbOfLines() {
		int numbersOfLines = gridString.length;
		if (numbersOfLines == 0) {
			throw new UnsupportedOperationException();
		}
		return numbersOfLines;
	}

	//height
	public int getNbOfColumns() {
		int numberOfColums = gridString[0].length;
		if (numberOfColums == 0) {
			throw new UnsupportedOperationException();
		}
		return numberOfColums;
	}

}
