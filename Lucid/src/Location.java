import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Location {
	private String name;
	private static ObservableList<Location> locations = FXCollections.observableArrayList();
	
	//Constructor
	public Location(String n) {
		name = n;
	}
	
	//observer and listener for the list of locations. binded to the listview in the UI
	public static ObservableList<Location> getLocationList(){
		return locations;
	}
	
	//adds a location to the list of locations
	public static void addLocation(Location x) {
		if (locations.contains(x)) {
		}
		else locations.add(x);
}
	//returns the location
	public Location getLocation() {
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
