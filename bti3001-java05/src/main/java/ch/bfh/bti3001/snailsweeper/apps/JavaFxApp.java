package ch.bfh.bti3001.snailsweeper.apps;

import ch.bfh.bti3001.snailsweeper.model.CellType;
import ch.bfh.bti3001.snailsweeper.model.Grid;
import ch.bfh.bti3001.snailsweeper.model.GridSerializer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

	private Grid initalGrid;
	private ControllerConsoleApp ccA;
	private GridPane gp;
	private Label labelsection;

	
	@Override
	public void start(Stage stage) throws Exception {
		ControllerConsoleApp controllerConsoleApp = new ControllerConsoleApp();
		this.ccA = controllerConsoleApp;

		// IntroGame with Description
		System.out.println("***************************");
		System.out.println("WELCOME TO SNAILSWEEPER");
		System.out.println(" ");
		System.out.println("u=Uncover, f=Flag/Unflag");
		System.out.println(" ");

		// Create InitalGame
		Grid initalGrid = ccA.createInitalGame();
		System.out.println(" ");

		Grid initalGridWithSnails = controllerConsoleApp.placeSnails(initalGrid);
		this.initalGrid = initalGridWithSnails;
		System.out.println(" ");
		controllerConsoleApp.updateView(this.initalGrid);

		int width = this.initalGrid.getNbOfColumns();
		int height = this.initalGrid.getNbOfLines();

		System.out.println("width: " + width + " height: " + height);
		
		// creates Grid with the inital board size with a button element 
		// event handler is placed on every button for left and right click from the mouse
		GridPane gridPane = new GridPane();
		this.gp = gridPane;
		
		VBox root = new VBox();
		this.labelsection = new Label();
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Button button = new Button();
				button.setPrefSize(40, 40);
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {
							System.out.println("Left button was clicked");
							int x = GridPane.getColumnIndex(button);
							int y = GridPane.getRowIndex(button);
							System.out.println(x +" " + y);
							updateBackground(x,y);
						}  
						if (event.getButton() == MouseButton.SECONDARY) {
							System.out.println("Right button was clicked");
							int x = GridPane.getColumnIndex(button);
					        int y = GridPane.getRowIndex(button);
							System.out.println(x +" " + y);
							toggleFlag(x,y);
						}
					}

				});
				this.gp.add(button, i, j);
			}
		}
		
		StackPane rootgame = new StackPane(gridPane);
		root.getChildren().addAll(rootgame, labelsection);
		root.setSpacing(10);
		
		Scene scene = new Scene(root, 400, 400);
		stage.setScene(scene);
		stage.show();

	}
	/**
	 * the function uncovers the field in the initalGrid with the given coordinates, updates the view and copy the view to the JAVAFX GridPane
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void updateBackground(int x, int y) {
		this.initalGrid.uncover(x, y);
		this.ccA.updateView(this.initalGrid);
		
		updateFullGridJAVAFX(this.initalGrid, this.gp);
	}
	
	/**
	 * the function places the flag on the initalGrid with the given coordinates, updates the view and copy the view to the JAVAFX GridPane
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void toggleFlag(int x, int y) {
		this.initalGrid.toggleFlag(x, y);
		this.ccA.updateView(this.initalGrid);
	
		updateFullGridJAVAFX(this.initalGrid, this.gp);
	}

	/**
	 * helper function to return every button at a specific index
	 * @param i - x coordinate
	 * @param j - y coordinate
	 * @return - returns button at a specific index
	 */
	private Button getButtonatthisindex(int i, int j) {
		Node target = null;
		for (Node node : gp.getChildren()) {
		    if (GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) {
		        target = node;
		        break;
		    }
		}
		return (Button)target;
	}
	
	/**
	 * the function removes the event listeners on the buttons. When the game is finished there can't be further moves
	 * @param gp - the GridPane where the buttons are placed
	 * @param currentState - loops through the current Grid, to make no issues with the index
	 */
	private void clearBoard(GridPane gp, Grid currentState) {
		for (int i = 0; i < currentState.getNbOfLines(); i++) {
			for (int j = 0; j < currentState.getNbOfColumns(); j++) {
				Button bt = getButtonatthisindex(i,j);
				bt.setGraphic(null);
				bt.setStyle("");
			    bt.setOnMouseClicked(null);
			}
		}
	}
	
	/**
	 * Updates the view for every step from the initalGrid and the JAVAFX Gridpane in the same loop.
	 * With the same loop there will not be an issue with problems of different coordinates
	 * @param currentState - initalGrid (backend)
	 * @param gp - the JAVAFX GridPane (frontend)
	 */
	public void updateFullGridJAVAFX(Grid currentState, GridPane gp) {
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
						Button bt = getButtonatthisindex(i, j);
						bt.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
						System.out.print("# ");
					}
					if (currentState.gridString[i][j].label == "EH" || currentState.gridString[i][j].label == "LH") {
						Button bt = getButtonatthisindex(i, j);
						bt.setText("");
						System.out.print("X ");
					}

					if (currentState.gridString[i][j] == CellType.EmptyFlagged
							|| currentState.gridString[i][j] == CellType.LivingSnailFlagged) {
						Button bt = getButtonatthisindex(i, j);
						char flag = 232;
						bt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
						bt.setText("(!)");
						bt.setTextFill(Color.ORANGE);
						bt.setStyle("-fx-font-weight: bold");

						System.out.print("F ");
					}
					if (currentState.gridString[i][j].label == "CV") {
						Button bt = getButtonatthisindex(i, j);
						bt.setText(".");
						bt.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
						System.out.print(". ");
					}
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb2) {
						Button bt = getButtonatthisindex(i, j);
						bt.setText("2");
						System.out.print("2 ");
					} 
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb3) {
						Button bt = getButtonatthisindex(i, j);
						bt.setText("3");
						System.out.print("3 ");
					} 
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb4) {
						Button bt = getButtonatthisindex(i, j);
						bt.setText("4");
						System.out.print("4 ");
					} 
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb5) {
						Button bt = getButtonatthisindex(i, j);
						bt.setText("5");
						System.out.print("5 ");
					} 
					if (currentState.gridString[i][j] == CellType.ShowDistanceToBomb) {
						Button bt = getButtonatthisindex(i, j);
						bt.setText("1");
						System.out.print(currentState.gridString[i][j].label);
					}
				} else {
					Button bt = getButtonatthisindex(i, j);
					bt.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
					bt.setText("");
					System.out.print(" " + " ");
				}
			}
			++row;
			System.out.print(" " + row);
			System.out.println();
		}
		gameStatus(currentState);
	}

	/**
	 * checks if game over or won
	 * @param currentState - gives the current grid to the function to check state
	 */
	public void gameStatus(Grid currentState) {
		System.out.println("- - - - - - - - - - - - - - - -");
		if (currentState.gameOver()) {
			labelsection.setText("GAME OVER");
			labelsection.setAlignment(Pos.CENTER);
			labelsection.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
			labelsection.setTextFill(Color.LIGHTPINK);
			clearBoard(gp, currentState);
		}
		if (currentState.hasWon()) {
			labelsection.setText("You won!");
			labelsection.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
			labelsection.setTextFill(Color.GREEN);
			labelsection.setAlignment(Pos.CENTER);
			clearBoard(gp, currentState);
		}
	}
	
	public static void main(String[] args) {
		launch();
	}

}
