import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Suspect {
	private String name;
	private boolean isAlive;
	private Location presentLoc;
	private boolean isArrested;
	private static ObservableList<Suspect> suspects = FXCollections.observableArrayList();

	//constructor 
	public Suspect(String n, Location k) {
		name = n;
		presentLoc = k;
		isAlive = true;
		isArrested = false;
	}

	//kills the suspect
	public void killSuspect() {
		isAlive = false;
	}

	//moves the suspect to a new location
	public void moveLocation(Location k) {
		presentLoc = k;
	}

	@Override
	public String toString() {
		return name;
	}

	//adds the suspect to the list of suspects
	public static void addSuspect(Suspect x) {
		if (suspects.contains(x)) {

		}
		else suspects.add(x);
	}

	public void arrestSuspect() {
		isArrested = true;
	}
	
	public boolean getArrested() {
		return isArrested;
	}
	public boolean getAlive() {
		return isAlive;
	}
	//removes the suspect from the list of suspects
	public void removeSuspect(Suspect x) {
		suspects.remove(x);
	}

	//observer and listener for binding the list of suspects to the GUI
	public static ObservableList<Suspect> getSuspectList(){
		return suspects;
	}

	//gets the current location of a suspect
	public Location currentLocation() {
		return presentLoc;
	}
}
