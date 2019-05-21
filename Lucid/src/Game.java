import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class Game {
private ObservableList<Inventory> inventory;
private ObservableList<Location> locations;
private static Location currentLoc;
private ObservableList<Suspect> suspects;
static boolean gameEnd = false;
static boolean isAlive = true;

public Game(ObservableList<Inventory> i, ObservableList<Location> l, Location cl, ObservableList<Suspect> s) {
	inventory = i;
	locations = l;
	currentLoc = cl;
	suspects = s;
}

public static Location getCurrentLoc() {
	return currentLoc;
}
public static void setCurrentLoc(Location l) {
	currentLoc = l;
}

public static void gameOver() {
	gameEnd = true;
	GText.ending();
}
public static void killJohn() {
	isAlive = false;
}
public void saveGame(ObservableList<Inventory> observableList, ObservableList<Suspect> observableList2, ObservableList<Location> observableList3) {
	inventory = observableList;
	suspects = observableList2;
	locations = observableList3;
}
}
