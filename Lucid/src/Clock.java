import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//the clock class is used to track the flow of time in the game and all of the systems connected to that.
//the connected systems include any events that trigger off of the time of day, or different days.
//the medication timers are tied into the clock class as well.
//variables in the clock class include, hour, minutes, ampm, day, and the booleans for isMedicated or isSick.
public class Clock {
	private int minutes;
	private int hours;
	private String ampm;
	private int medtimer;
	private boolean isMedicated;
	private boolean isSick;
	private String day;
	private StringProperty clock = new SimpleStringProperty();
	
	//Constructor for the clock class.
	public Clock(int h, int m, String ampm, String day) {
		hours = h;
		minutes = m;
		this.ampm = ampm.toUpperCase();
		this.day = day.toUpperCase();
		isMedicated = true;
		isSick = false;
	}
//increases the minutes as well as the med timer for minutes.
	public void incrementMinutes(int k) {
		minutes = minutes + k;
		medtimer = medtimer + k;
		if (medtimer >= 480) 
			isMedicated = false;
		if (medtimer >= 960) 
			isSick = true;
		while (minutes >= 60) {
			minutes = minutes - 60;
			hoursCheck(1);
		}
	}
	//a method that calls other methods to keep track of hours and medication.
	public void incrementHours(int k) {
		hoursCheck(k);
		medCheck(k);
	}
	//increases the medication timer when hours is increased and checks to see if properly medicated.
	public void medCheck(int k) {
		medtimer = medtimer + (60*k);
		if (medtimer >= 480) 
			isMedicated = false;
		if (medtimer >= 960) 
			isSick = true;
	}
	//used for keeping the am pm and day of the week properly updated as well as returns ampm for any time of day specific events.
	public String hoursCheck(int k) {
		hours = hours + k;
		while (hours >= 12) {
			hours = hours - 12;
			if (ampm.equals("AM")) {
				ampm = "PM";
			}
			else if (ampm.equals("PM")) {
				ampm = "AM";
				if (day.equals("MON"))
					day = "TUE";
				else if (day.equals("TUE"))
					day = "WED";
				else if (day.equals("WED"))
					day = "THUR";
				else if (day.equals("THUR"))
					day = "FRI";
				else if (day.equals("FRI"))
					day = "SAT";
				else if (day.equals("SAT"))
					day = "SUN";
				else if (day.equals("SUN"))
					day = "MON";
			}
		}
		return ampm;
	}
	//used for triggering events when character isn't medicated
	public boolean getMedicated() {
		return isMedicated;
	}
	// used for triggering events that occur when character is sick
	public boolean getIsSick() {
		return isSick;
	}
	// used for triggering specific day events
	public String getDay() {
		return day;
	}
	
	// This is an auto updating Observer that holds a String value and listens for changes to keep my clock updated in game.
	public StringProperty getTime() {
		if (hours < 10 && minutes < 10) {
			clock.setValue("0" + hours + ":" + "0" + minutes + " " + ampm + " " + day);
		}
		else if (hours < 10 && minutes >= 10) {
			clock.setValue("0" + hours + ":" + minutes + " " + ampm + " " + day);  
		}
		else if (hours >= 10 && minutes < 10) {
			clock.setValue(hours + ":" + "0" + minutes + " " + ampm + " " + day);  
		}
		else if (hours >= 10 && minutes >= 10) {
			clock.setValue(hours + ":" + minutes + " " + ampm + " " + day); 
		}
		return clock;
	}
	//combines all the clock variables into a method I can call to advance the game and keeps the clock updated. 
	public void advanceGame(int h, int m) {
		incrementHours(h);
		incrementMinutes(m);
		getTime();
	}
	public int getCurrentMinutes() {
		return ((hours*60) + minutes);
	}
	
	public void takeMedication() {
		medtimer = 0;
		isMedicated = true;
		isSick = false;
	}
}


