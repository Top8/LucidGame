//this is the main application for the game that launches when the game launches.
//it includes all of the ui elements that the game draws on to interact with the user.
// 3 array lists are in a HBox on the side to track useful information that the user will need in the case.
// the textfield at the bottom is the users input for interacting with the game.
// the text area in the middle of the screen is the main source of the game information for what is happening currently in the game.
// there is a menu bar at the top with a save game button for saving the game state and a load game button for loading a game state as well as a help menu with general command tips and an exit application button.
// a timer that automatically stays updated is located at the bottom left of the screen.

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Lucidmain extends Application{
	//Initializing my objects here.
	Button help, exit;
	ListView<Location> location;
	ListView<Suspect> suspect;
	ListView<Inventory> inventory;
	Label llocation, lsuspect, linventory, gameClock;
	static Label tSchedule, sSchedule;
	static Clock clock;
	static Suspect aaron, sarah, captain, dan, eric, tiffany, jackson;
	static Inventory gun, badge, medication, deskKey, bottle, sschedule, tschedule, photo;
	Game game;
	static Location johnHome, policeStation, tiffanyHome, school, aaronHome, aaronWork, woods, river, sarahHome;
	static Stage tScheduleStage = new Stage();
	static Stage sScheduleStage = new Stage();
	static VBox layout2 = new VBox();
	static VBox layout3 = new VBox();
	static Scene schedule1 = new Scene(layout2, 350, 300);
	static Scene schedule2 = new Scene(layout3, 350, 300);
	// the main which starts on game launch. 
	public static void main(String[] args) {
		launch(args);
	}
	@Override

	//Sets up the primary game area with all the GUI elements as well as there functions.
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Lucid");
		help = new Button("Help");
		exit = new Button("Exit");
		location = new ListView<Location>();
		suspect = new ListView<Suspect>();
		inventory = new ListView<Inventory>();
		llocation = new Label("Locations");
		lsuspect = new Label("Suspects");
		linventory = new Label("Inventory");
		tSchedule = new Label("			MON, WED, FRI \n"
				+ "7:20 - 7:50AM Homeroom RM 100  Mr. Jackson \n"
				+ "8:00 - 9:50AM Algebra RM 210  Mrs. Bell \n"
				+ "10:00 - 11:50AM Science RM 100  Mr. Jackson \n"
				+ "11:50 - 12:50PM Lunch CAFETERIA\n"
				+ "1:00 - 2:50PM PE GYMNASIUM  Mr. Tanner \n \n"
				+ "			TUE, THUR \n"
				+ "7:20 - 7:50AM Homeroom RM 100  Mr. Jackson \n"
				+ "8:00 - 9:50AM History RM 220  Mrs. Thompson \n"
				+ "10:00 - 11:50AM Drama RM 110  Ms. Li \n"
				+ "11:50 - 12:50PM Lunch CAFETERIA\n"
				+ "1:00 - 2:50PM English RM 230 Mr. Smith \n");
		sSchedule = new Label("			MON, WED, FRI \n"
				+ "7:20 - 7:50AM Homeroom RM 100  Mr. Jackson \n"
				+ "8:00 - 9:50AM Choir RM 300  Mrs. Peacock \n"
				+ "10:00 - 11:50AM Science RM 100  Mr. Jackson \n"
				+ "11:50 - 12:50PM Lunch CAFETERIA \n"
				+ "1:00 - 2:50PM Algebra RM 210  Mrs. Bell \n \n"
				+ "			TUE, THUR \n"
				+ "7:20 - 7:50AM Homeroom RM 100  Mr. Jackson \n"
				+ "8:00 - 9:50AM English RM 130  Ms. Kelly \n"
				+ "10:00 - 11:50AM Drama RM 110  Ms. Li \n"
				+ "11:50 - 12:50PM Lunch CAFETERIA\n"
				+ "1:00 - 2:50PM   Aquatics POOL Mr. Fields \n");
		//initialize the in game clock.
		clock = new Clock(7, 0, "am", "mon");
		gameClock = new Label();
		//bind the clock to the GUI
		gameClock.textProperty().bind(clock.getTime());

		//initializing locations
		johnHome = new Location("John's Home");
		policeStation = new Location("Police Station");
		tiffanyHome = new Location("Tiffany's Home");
		school = new Location("School");
		aaronHome = new Location("Aaron's Home");
		aaronWork = new Location("Aaron's Work");
		woods = new Location("Woods");
		river = new Location("River");
		sarahHome = new Location("Sarah's Home");

		//initializing inventory
		gun = new Inventory("Gun");
		badge = new Inventory("Police Badge");
		medication = new Inventory("Medication");
		deskKey = new Inventory("Key");
		bottle = new Inventory("Jack Daniels");
		tschedule = new Inventory("Tiffany's Schedule");
		sschedule = new Inventory("Sarah's Schedule");
		photo = new Inventory("Photo");

		//initializing the suspects
		aaron = new Suspect("Aaron", school);
		sarah = new Suspect("Sarah", school);
		captain = new Suspect("Captain:", policeStation);
		dan = new Suspect("Dan" , policeStation);
		eric = new Suspect("Eric", policeStation);
		tiffany = new Suspect("Tiffany", null);
		jackson = new Suspect("Mr. Jackson", school);

		//initializing the game state
		game = new Game(null, null, johnHome, null);
		if (Game.getCurrentLoc().equals(johnHome)){
			GText.initJohnHome();
		}

		//bind the lists to the GUI
		suspect.setItems(Suspect.getSuspectList());
		location.setItems(Location.getLocationList());
		inventory.setItems(Inventory.getItemList());

		//the different panels that display the UI
		HBox topMenuBar = new HBox();
		HBox bottomBar = new HBox();
		topMenuBar.setSpacing(10);
		VBox rightMenu = new VBox();
		VBox rightTopList = new VBox();
		VBox rightMiddleList = new VBox();
		VBox rightBottomList = new VBox();
		VBox main = new VBox();
		TextField textInput = new TextField();
		textInput.setMinWidth(550);
		Text textSceneOutput = new Text();
		textSceneOutput.setWrappingWidth(620);
		Text textGameOutput = new Text();
		textGameOutput.setWrappingWidth(620);
		ScrollPane mainSceneText = new ScrollPane();
		mainSceneText.setMinSize(600, 300);
		ScrollPane mainOutputText = new ScrollPane();
		mainOutputText.setMinSize(600, 200);
		mainSceneText.setContent(textSceneOutput);
		mainOutputText.setContent(textGameOutput);

		//binds text to the Text nodes.
		textSceneOutput.textProperty().bind(GText.sceneText());
		textGameOutput.textProperty().bind(GText.outputText());

		//scene text at the start of the game in Johns apartment

		textInput.setOnAction(e -> {GText.input(textInput.getText());
		textInput.setText("");
		});

		help.setOnAction(e -> GText.help());
		exit.setOnAction(e -> System.exit(0));

		//compiling the different UI elements into one GUI.
		topMenuBar.getChildren().addAll(help, exit);
		rightTopList.getChildren().addAll(llocation, location);
		rightMiddleList.getChildren().addAll(lsuspect, suspect);
		rightBottomList.getChildren().addAll(linventory, inventory);
		rightMenu.getChildren().addAll(rightTopList, rightMiddleList, rightBottomList);
		bottomBar.getChildren().addAll(gameClock, textInput);
		main.getChildren().addAll(mainSceneText, mainOutputText);

		//the parent GUI node that's a Borderpane
		BorderPane layout = new BorderPane();
		layout.setTop(topMenuBar);
		layout.setRight(rightMenu);
		layout.setCenter(main);
		layout.setBottom(bottomBar);


		// creates the scene
		Scene scene = new Scene(layout, 900, 600);
		
		tScheduleStage.setTitle("Tiffany's Schedule");
		tScheduleStage.setScene(schedule1);
		layout2.getChildren().add(tSchedule);
		sScheduleStage.setTitle("Sarah's Schedule");
		sScheduleStage.setScene(schedule2);
		layout3.getChildren().add(sSchedule);
		
		//sets the scene to the stage and shows it
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void schedule1() {
		tScheduleStage.show();
	}
	public static void schedule2() {
		sScheduleStage.show();
	}
}

