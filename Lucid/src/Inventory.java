import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
	private String item;
	private boolean isHeld;
	private boolean isUsed;
	private static ObservableList<Inventory> inventory = FXCollections.observableArrayList();

	public Inventory(String n) {
		item = n;
		isHeld = false;
		isUsed = false;
	}
	@Override
	public String toString() {
		return item;
	}

	public static void addItem(Inventory x) {
		if (inventory.contains(x)){
		}
		else inventory.add(x);
	}
	public static void removeItem(Inventory x) {
		if (inventory.contains(x)){
			inventory.remove(x);
		}
	}

	public static ObservableList<Inventory> getItemList(){
		return inventory;
	}

	public void pickItem() {
		isHeld = true;
	}
	public void useItem() {
		isUsed = true;
	}
	public boolean getUsed() {
		return isUsed;
	}
}
