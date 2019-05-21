import java.io.OutputStream;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
// Gtext Class contains all the game logic for the game when dealing with the inputs and outputs of the text fields.
// the main methods look at the last input to the text field and then depending on the characters current location redirects that input to the current list of possible inputs for that location

public class GText {
	private static StringProperty sceneText = new SimpleStringProperty();
	private static StringProperty outputText = new SimpleStringProperty();
	private static boolean talkCaptain = false;
	private static boolean papersDone = false;
	private static boolean phoneAnswered = false;
	private static boolean quickResponse = false;
	private static int turnLimit = 0;
	private static boolean sarahFound = false;
	public static StringProperty sceneText() {
		return sceneText;
	}

	public static StringProperty outputText() {
		return outputText;
	}
	// Initializes the scene text when going to johns home.
	public static void initJohnHome() {
		if (Location.getLocationList().contains(Lucidmain.johnHome)){
		}
		else Location.addLocation(Lucidmain.johnHome);
		if (Location.getLocationList().contains(Lucidmain.policeStation)) {
		}
		else Location.addLocation(Lucidmain.policeStation);
		Game.setCurrentLoc(Lucidmain.johnHome);
		if (Lucidmain.clock.getCurrentMinutes() == 420 && Lucidmain.clock.getDay().equals("MON")){
			sceneText.setValue("You wake up to the sound of an alarm buzzing in your ears. The clock reads 7:00AM.  You hit the button same as every morning."
					+ " You have a splitting headache and your groggy from too much drinking the night before."
					+ " Your vision is adjusting to the morning light filtering through the dusty blinds of your never cleaned apartment."
					+ " As you fumble around to get up your legs knock over empty beer bottles that litter the floor."
					+ " You go about your daily routine to get ready for the morning. You take a shower and get dressed for work."
					+ " You can't be late again otherwise the Captain is going to ride you around the office.");
		}
		else sceneText.setValue("You wake up extremely groggy. Your body feels exhausted like you've been up all night.  You are still wearing your clothes from the night before.  They appear to be pretty dirty and your boots are covered in mud.  What the hell happened to you last night?"
				+ " You can't remember anything that happened after you passed out. If you try to your head begins to spin and you think you might pass out again. You grab your medication and take one quickly.  I guess it's best to try not to think about it.");
		if (Lucidmain.tiffany.getAlive() == true) {
			Lucidmain.tiffany.killSuspect();
		}
	}

	// Initializes the scene text when going to the police station.
	public static void initPoliceStation() {
		Game.setCurrentLoc(Lucidmain.policeStation);
		if (Lucidmain.clock.getDay().equals("MON")) {
			if (Lucidmain.clock.getCurrentMinutes() > 480) {
				outputText.setValue("The Captain sticks his head out of his office as you walk into the station and yells to you. 'Your late again John! I'll have you handing out parking tickets for the next month if you pull this shit one more time." );
			}
			sceneText.setValue("The police station is pretty calm this morning.  A couple of the more rookie detectives are tossing a ball back and forth to pass the time. A pile of crumpled up"
					+ " paper lays near a waste basket telling you that whoever was trying to make those baskets needs some serious practice. Officers mill about here and there going "
					+ "through the morning routine and shuffling around paperwork.");
		}
	}

	public static void initSchool() {
		Game.setCurrentLoc(Lucidmain.school);
		if (Lucidmain.clock.getDay().equals("MON")) {
			if (Lucidmain.clock.getCurrentMinutes() < 900) {
				sceneText.setValue("You arrive at the school during the school day.  Students are running around everywhere going too and from classes.  Everything appears to be normal."
						+ " Nobody seems to notice that one of the students isn't accounted for. You head to the principal's office first to ask about Tiffany.  The principal isn't aware of any issues with Tiffany and infact she is a model student. "
						+ "He mentions having talked to Ms. Swanson earlier this morning and checked in with Tiffany's teachers and infact she hasn't been attending classes today but that's all he knows. Great grades, popular, everyone seems to like her."
						+ " No enemies to speak of.  No one who would want to harm her. Never so much as a detention. You get Tiffany's SCHEDULE from the principal as well as Sarahs so you can track her down more easily.");
				Inventory.addItem(Lucidmain.sschedule);
				Inventory.addItem(Lucidmain.tschedule);
				Lucidmain.sschedule.pickItem();
				Lucidmain.tschedule.pickItem();
			}
		}
	}
	//determines what inputs to allow based on the location of the character. Redirects the input to the current method. Also contains any over arcing commands that you can do independent of location.  
	public static void input(String k) {
		k = k.toLowerCase();
		if (quickResponse == true) {
			turnLimit++;
			if (turnLimit > 2) {
				Game.gameOver();
			}
		}
		if (Game.gameEnd == false) {
			if (Lucidmain.clock.getCurrentMinutes() > 900) {
				Lucidmain.aaron.moveLocation(Lucidmain.aaronWork);
				Lucidmain.sarah.moveLocation(Lucidmain.sarahHome);
			}
			if (Inventory.getItemList().contains(Lucidmain.gun) && (k.contains("shoot john") || k.contains("kill john"))) {
				Game.killJohn();
				Game.gameOver();
			}
			else if (Inventory.getItemList().contains(Lucidmain.medication) && k.contains("use medication")) {
				outputText.setValue("You take some of your medication. Your headache will start to go away soon."); 
				Lucidmain.clock.takeMedication();
				Lucidmain.clock.advanceGame(0, 5);
			}
			else if (Lucidmain.clock.getIsSick() == true) {
				outputText.setValue("It's been too long since you've last taken your medication. Your head is getting very heavy and you pass out.");
				Lucidmain.clock.advanceGame(12, 0);
				Lucidmain.clock.takeMedication();
				initJohnHome();
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.johnHome)) {
				johnHomeInputs(k);
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.aaronHome)) {
				aaronHomeInputs(k);
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.aaronWork)) {
				aaronWorkInputs(k);
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.sarahHome)) {
				sarahHomeInputs(k);
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.policeStation)) {
				policeStationInputs(k);
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.river)) {
				riverInputs(k);
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.school)){
				schoolInputs(k);
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.tiffanyHome)) {
				tiffanyHomeInputs(k);
			}
			else if (Game.getCurrentLoc().equals(Lucidmain.woods)) {
				woodsInputs(k);
			}
		}
	}

	public static void policeStationInputs(String k) {
		//outputs for Monday on the first day of the investigation. 
		if (Lucidmain.clock.getDay().equals("MON")) {
			if (Lucidmain.clock.getCurrentMinutes() > 480 && phoneAnswered == false) {
				sceneText.setValue("Your PHONE starts ringing on your desk. You should answer that.");
			}
			switch (k) {
			case "look around":
				outputText.setValue("You only wish to get to your DESK.  Going to talk to the CAPTAIN would be a bad idea you think. You're still in hot water"
						+ " from the events of last week.");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "look desk":
				if (papersDone == true) {
					if (Inventory.getItemList().contains(Lucidmain.deskKey) || Lucidmain.deskKey.getUsed() == true) {
						outputText.setValue("Your desk is finally looking organized. Your desk has a TOP DRAWER and a BOTTOM DRAWER.");
						Lucidmain.clock.advanceGame(0, 5);
					}
					else {
						outputText.setValue("Your desk is finally looking organized. A KEY is laying on your desk. Your desk has a TOP DRAWER and a BOTTOM DRAWER.");
						Lucidmain.clock.advanceGame(0, 5);
					}
				}
				else outputText.setValue("Your desk is a complete mess. PAPERS are scattered everywhere. Your desk has a TOP DRAWER and a BOTTOM DRAWER.");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "look papers":
				outputText.setValue("The papers are everwhere. They don't look particularly interesting, mostly just paperwork that needs to be FILED but you've been to lazy to do that.");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "take papers":
				if (papersDone == false) {
					outputText.setValue("You try to straigten up the papers a bit to make the desk more presentable, putting them all into a pile on the corner of your desk.  You notice a KEY was under the papers.");
					Lucidmain.clock.advanceGame(0, 10);
					papersDone = true;
				}
				else outputText.setValue("You already did that.");
				break;

			case "file papers":
				if (papersDone == false) {
					outputText.setValue("You never do this but you decide to take the paperwork down to the fileroom to file them away where they belong. After awhile it's all done and you actually feel kind of accomplished. What a productive morning your having today.  After you get back to your"
							+ " desk you notice there was a KEY where the papers use to be.");
					papersDone = true;
					Lucidmain.clock.advanceGame(0, 30);
				}
				else outputText.setValue("You already took care of those.");
				break;

			case "look key":
				if (papersDone == true) {
					outputText.setValue("This is the key to your desk. You've been looking for that.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "take key":
				if (Inventory.getItemList().contains(Lucidmain.deskKey) || Lucidmain.deskKey.getUsed() == true) {
					outputText.setValue("You already have that.");
				}
				else if (papersDone == true){
					outputText.setValue("You pick up the key.");
					Lucidmain.deskKey.pickItem();
					Inventory.addItem(Lucidmain.deskKey);
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "open top drawer":
				outputText.setValue("Nothing interesting seems to be in here. Just some loose change, old receipts and extra ketchup packets.");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "go school":
				if (Location.getLocationList().contains(Lucidmain.school)) {
					initSchool();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go john's home":
				if (Location.getLocationList().contains(Lucidmain.johnHome)) {
					Game.setCurrentLoc(Lucidmain.johnHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go john home":
				if (Location.getLocationList().contains(Lucidmain.johnHome)) {
					Game.setCurrentLoc(Lucidmain.johnHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go johns home":
				if (Location.getLocationList().contains(Lucidmain.johnHome)) {
					Game.setCurrentLoc(Lucidmain.johnHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go tiffany's home":
				if (Location.getLocationList().contains(Lucidmain.tiffanyHome)) {
					Game.setCurrentLoc(Lucidmain.tiffanyHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go tiffany home":
				if (Location.getLocationList().contains(Lucidmain.tiffanyHome)) {
					Game.setCurrentLoc(Lucidmain.tiffanyHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go tiffanys home":
				if (Location.getLocationList().contains(Lucidmain.tiffanyHome)) {
					Game.setCurrentLoc(Lucidmain.tiffanyHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go sarah's home":
				if (Location.getLocationList().contains(Lucidmain.sarahHome)) {
					Game.setCurrentLoc(Lucidmain.sarahHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go sarah home":
				if (Location.getLocationList().contains(Lucidmain.sarahHome)) {
					Game.setCurrentLoc(Lucidmain.sarahHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go sarahs home":
				if (Location.getLocationList().contains(Lucidmain.sarahHome)) {
					Game.setCurrentLoc(Lucidmain.sarahHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "open bottom drawer":
				if (Lucidmain.deskKey.getUsed() == true) {
					if (Inventory.getItemList().contains(Lucidmain.bottle) || Lucidmain.bottle.getUsed() == true) {
						outputText.setValue("You open the bottom drawer and find a PICTURE.");
						Lucidmain.clock.advanceGame(0, 5);
					}
					else {
						outputText.setValue("You open the bottom drawer and find a BOTTLE of Jack Daniels and a PICTURE.");
						Lucidmain.clock.advanceGame(0, 5);
					}
				}
				else outputText.setValue("The bottom drawer is locked.");
				break;

			case "look photo":
				if (Lucidmain.deskKey.getUsed() == true) {
					outputText.setValue("It's a picture of your daughter when she was 7. It's been 2 years since she passed away and you still haven't forgiven yourself.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "drink jack daniels":
				if (Inventory.getItemList().contains(Lucidmain.bottle)) {
					if (Lucidmain.captain.getAlive() == false) {
						outputText.setValue("I guess it's okay to have one last drink now. Not like the Captain is going to mind anymore. You take a long swig and poor some out of the Captain too.");
						Lucidmain.clock.advanceGame(0, 5);
						Lucidmain.bottle.useItem();
						Inventory.removeItem(Lucidmain.bottle);
					}
					else outputText.setValue("You can't drink on the job.");
				}
				else outputText.setValue("You don't know how to do that");
				break;

			case "look bottle":
				if (Lucidmain.deskKey.getUsed() == true) {
					outputText.setValue("It's a half empty bottle of Jack Daniels, your prefered drink of choice.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "take bottle":
				if (Inventory.getItemList().contains(Lucidmain.bottle) || Lucidmain.bottle.getUsed() == true) {
					outputText.setValue("You already have that.");
				}
				else  { 
					if (Lucidmain.deskKey.getUsed() == true) {
						outputText.setValue("You pick up the bottle.");
						Lucidmain.bottle.pickItem();
						Inventory.addItem(Lucidmain.bottle);
						Lucidmain.clock.advanceGame(0, 5);
					}
					else outputText.setValue("You don't know how to do that.");
				}
				break;

			case "use key bottom drawer":
				if (Inventory.getItemList().contains(Lucidmain.deskKey) && Lucidmain.deskKey.getUsed() == false) {
					outputText.setValue("You unlock the bottom drawer with the key.");
					Lucidmain.deskKey.useItem();
					Inventory.removeItem(Lucidmain.deskKey);
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You can't do that.");
				break;

			case "use key on bottom drawer":
				if (Inventory.getItemList().contains(Lucidmain.deskKey) && Lucidmain.deskKey.getUsed() == false) {
					outputText.setValue("You unlock the bottom drawer with the key.");
					Lucidmain.deskKey.useItem();
					Inventory.removeItem(Lucidmain.deskKey);
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You can't do that.");
				break;
				
			case "unlock bottom drawer with key":
				if (Inventory.getItemList().contains(Lucidmain.deskKey) && Lucidmain.deskKey.getUsed() == false) {
					outputText.setValue("You unlock the bottom drawer with the key.");
					Lucidmain.deskKey.useItem();
					Inventory.removeItem(Lucidmain.deskKey);
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You can't do that.");
				break;
				
			case "look captain":
				outputText.setValue("The Captain looks like he's on the phone having a heated argument with someone.  It's probably best not to disturb him." );
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "talk captain":
				if (talkCaptain == false) {
					outputText.setValue(" You knock on the Captain's door. He looks up from his phone call and yells to you \"John get out of here you idiot. I don't have time for your **** today. Unless you want to be on scanner duty for the next week.\" "
							+ "You decide it's probably best to come back another time.  You don't have anything to talk to him about anyway." );
					talkCaptain = true;
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("Why would I want to do that again?");	
				break;

			case "shoot captain":
				if (Inventory.getItemList().contains(Lucidmain.gun) && Lucidmain.captain.getAlive() == true) {
					Lucidmain.captain.killSuspect();
					outputText.setValue("You shoot the Captain.  He goes down easily enough as he is caught unaware. You can hear screaming in the distance. Most people scatter for the nearest exits.  DAN and ERIC who were playing nearby reach for their own weapons.");
					Lucidmain.clock.advanceGame(0, 10);
					quickResponse = true;
					sceneText.setValue("The police station is empty now.  Chairs are knocked over everywhere as people fleed from the scene of the shooting.  You can hear sounds of sirens outside as backup has arrived and they await for your surrender.");
				}
				else outputText.setValue("You can't do that right now.");
				break;

			case "shoot dan":
				if (Lucidmain.captain.getAlive() == false && Lucidmain.dan.getAlive() == true) {
					outputText.setValue("You quickly turn around and take out Dan before he can pull his gun out of his holster.");
					Lucidmain.clock.advanceGame(0, 1);
					Lucidmain.dan.killSuspect();
					if (Lucidmain.eric.getAlive() == false) {
						quickResponse = false;
						turnLimit = 0;
					}
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "shoot eric":
				if (Lucidmain.captain.getAlive() == false && Lucidmain.eric.getAlive() == true) {
					outputText.setValue("You quickly turn around and take out Eric before he can pull his gun out of his holster.");
					Lucidmain.clock.advanceGame(0, 1);
					Lucidmain.eric.killSuspect();
					if (Lucidmain.dan.getAlive() == false) {
						quickResponse = false;
						turnLimit = 0;
					}
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "answer phone":
				if (Lucidmain.clock.getCurrentMinutes() > 480 && phoneAnswered == false) {
					phoneAnswered = true;
					sceneText.setValue("The police station is pretty calm this morning.  A couple of the more rookie detectives are tossing a ball back and forth to pass the time. A pile of crumpled up"
							+ "paper lays near a waste basket telling you that whoever was trying to make those baskets needs some serious practice. Officers mill about here and there going "
							+ "through the morning routine and shuffling around paperwork.");

					outputText.setValue("You answer your phone. \n John: Oakville Police Department, this is detective Mallard. How can I help you? \n"
							+ " Woman: Hello, my name is Ms. Swanson. I'm calling because my daughter Tiffany has gone missing. She never returned last night after studying at her friends house and she hasn't been answering her phone. I called the school and she didn't show up to first period either. \n"
							+ "John: Okay mam stay calm. When was the last time you had contact with your daughter. \n"
							+ "Ms. Swanson: She called me last night at 9:15 to let me know she was on her way home. That was the last I heard from her. \n"
							+ "John: Did you try to contact her friend to see if she stayed the night there? What was the friend's name? \n"
							+ "Ms. Swanson: Of course I did! Her name is SARAH and she hasn't heard from her since Tiffany left her house after studying at 9.\n"
							+ "John: Does Tiffany have a boyfriend she might have stayed with? A lot of times thats where kids are found hanging out when they skip school. \n"
							+ "Ms. Swanson: Tiffany isn't that kind of girl. She is a good kid. She doesn't have a boyfriend and she would never spend the night at a boys house.\n"
							+ "John: Ok, well I would give it a little bit of time. Usually kids will show up. Sounds like she is just skipping school and having some fun. If you still haven't heard from her tonight then give me a callback.\n"
							+ "Ms. Swanson: No please, you don't understand. Tiffany would never lose contact for this long. She has never done anything like this before. I don't know if your a parent or not but when something is wrong with your child you just know it. \n"
							+ "You think about your daughter. \n"
							+ "John: Okay I can tell you are really shaken up. Give me the address of her friend's house and your address and what school she goes to and I'll go check it out. \n"
							+ "Ms. Swanson: Thank you so much! Please find my daughter.");
					Location.addLocation(Lucidmain.tiffanyHome);
					Location.addLocation(Lucidmain.sarahHome);
					Location.addLocation(Lucidmain.school);
					Suspect.addSuspect(Lucidmain.sarah);

				}
				else outputText.setValue("You don't know how to do that.");
				break;

			default: outputText.setValue("You don't know how to do that.");
			}
		}
	}

	public static void tiffanyHomeInputs(String k) {
		switch (k) {

		}
	}
	public static void sarahHomeInputs(String k) {
		switch (k) {

		}
	}
	public static void schoolInputs(String k) {
		if (Lucidmain.clock.getDay().equals("MON") && Lucidmain.clock.getCurrentMinutes() < 900) {
			switch (k) {
			case "look around":
				outputText.setValue("It's what you would expect a school to be. You'll never find anything interesting just wandering around.  You should probably figure out where Sarah is using SARAH'S SCHEDULE.");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "look tiffany's schedule":
				if (Inventory.getItemList().contains(Lucidmain.tschedule)){
					Lucidmain.schedule1();
					outputText.setValue("You look at Tiffany's Schedule.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "look tiffanys schedule":
				if (Inventory.getItemList().contains(Lucidmain.tschedule)){
					Lucidmain.schedule1();
					outputText.setValue("You look at Tiffany's Schedule.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "look tiffany schedule":
				if (Inventory.getItemList().contains(Lucidmain.tschedule)){
					Lucidmain.schedule1();
					outputText.setValue("You look at Tiffany's Schedule.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "look sarah's schedule":
				if (Inventory.getItemList().contains(Lucidmain.sschedule)){
					Lucidmain.schedule2();
					outputText.setValue("You look at Sarah's Schedule.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "look sarahs schedule":
				if (Inventory.getItemList().contains(Lucidmain.sschedule)){
					Lucidmain.schedule2();
					outputText.setValue("You look at Sarah's Schedule.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "look sarah schedule":
				if (Inventory.getItemList().contains(Lucidmain.sschedule)){
					Lucidmain.schedule2();
					outputText.setValue("You look at Sarah's Schedule.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go rm 100":
				Lucidmain.clock.advanceGame(0, 10);
				if (Lucidmain.clock.getCurrentMinutes() >= 600 && Lucidmain.clock.getCurrentMinutes() < 710){
					outputText.setValue("You look into RM 100 and see MR. JACKSON teaching Science class.  Tiffany's friend SARAH should be here as well according to her schedule.");
					sarahFound = true;
				}
				else outputText.setValue("You look into RM 100 and see MR. JACKSON teaching Science class.");
				break;

			case "go gymnasium":
				 outputText.setValue("You look into the Gymnasium and see Mr. Tanner coaching as the kids play basketball.");
				Lucidmain.clock.advanceGame(0, 10);
				break;
				
			case "go rm 220":
				 outputText.setValue("You look into RM 220 and see Mrs. Thompson teaching History class.");
				Lucidmain.clock.advanceGame(0, 10);
				break;
				
			case "go rm 110":
				 outputText.setValue("You look into RM 110 and see Ms. Li organizing auditions for a school play.");
				Lucidmain.clock.advanceGame(0, 10);
				break;
				
			case "go rm 230":
				 outputText.setValue("You look into RM 230 and see Mr. Smith teaching English class.");
				Lucidmain.clock.advanceGame(0, 10);
				break;
			
			case "go rm 130":
				 outputText.setValue("You look into RM 130 and see Ms. Kelly teaching English class.");
				Lucidmain.clock.advanceGame(0, 10);
				break;
				
			case "go pool":
				 outputText.setValue("You go to the pool and see Mr. Fields checking his phone while the kids splash around in the pool.");
				Lucidmain.clock.advanceGame(0, 10);
				break;
				
			case "go rm 300":
				Lucidmain.clock.advanceGame(0, 10);
				if (Lucidmain.clock.getCurrentMinutes() >= 480 && Lucidmain.clock.getCurrentMinutes() <= 590){
					outputText.setValue("You look into RM 300 and see Mrs. Peacock coaching choir.  Tiffany's friend SARAH should be here as well according to her schedule.");
					sarahFound = true;
				}
				else outputText.setValue("You look into RM 300 and see Mrs. Peacock coaching choir class.");
				break;

			case "go cafeteria":
				Lucidmain.clock.advanceGame(0, 10);
				if (Lucidmain.clock.getCurrentMinutes() >= 710 && Lucidmain.clock.getCurrentMinutes() <= 770){
					outputText.setValue("Kids are eating lunch in the cafeteria.  Tiffany's friend SARAH should be here as well according to her schedule.");
					sarahFound = true;
				}
				else outputText.setValue("The cafeteria is pretty empty other then the staff cleaning around. It isn't time for the kids to be eating lunch.");
				break;

			case "go rm 210":
				Lucidmain.clock.advanceGame(0, 10);
				if (Lucidmain.clock.getCurrentMinutes() >= 780 && Lucidmain.clock.getCurrentMinutes() <= 890){
					outputText.setValue("You look into RM 210 and see Mrs. Bell teaching Algebra class.  Tiffany's friend SARAH should be here as well according to her schedule.");
					sarahFound = true;
				}
				else outputText.setValue("You look into RM 210 and see Mrs. Bell teaching Algebra class.");
				break;

			case "talk mrs. bell":
				outputText.setValue("Talking to Mrs. Bell doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mrs bell":
				outputText.setValue("Talking to Mrs. Bell doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mr. tanner":
				outputText.setValue("Talking to Mr. Tanner doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mr tanner":
				outputText.setValue("Talking to Mr. Tanner doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mrs. peacock":
				outputText.setValue("Talking to Mrs. Peacock doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mrs peacock":
				outputText.setValue("Talking to Mrs. Peacock doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk ms kelly":
				outputText.setValue("Talking to Ms. Kelly doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk ms. kelly":
				outputText.setValue("Talking to Ms. Kelly doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mrs. thompson":
				outputText.setValue("Talking to Mrs. Thompson doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mrs thompson":
				outputText.setValue("Talking to Mrs. Thompson doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk ms. li":
				outputText.setValue("Talking to Ms. Li doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk ms li":
				outputText.setValue("Talking to Ms. Li doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mr. fields":
				outputText.setValue("Talking to Mr. Fields doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mr fields":
				outputText.setValue("Talking to Mr. Fields doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mr. smith":
				outputText.setValue("Talking to Mr. Smith doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk mr smith":
				outputText.setValue("Talking to Mr. Smith doesn't provide you with any useful information.");
				Lucidmain.clock.advanceGame(0, 5);
				break;
				
			case "talk sarah":
				if (sarahFound == true) {
					outputText.setValue("You are able to track down Sarah, Tiffany's best friend.  \n"
							+ "John: Hello Sarah, my name is Detective Mallard, I'm here to ask you a few questions about your friend Tiffany. When was the last time you had contact with her? \n"
							+ "Sarah: Is everything all right? I got worried about her when her mom called and said she hasn't seen her and then she didn't show up to class this morning. \n"
							+ "John: There's no reason to worry yet, I have no reason to believe something bad has happened to her.  I'm just trying to collect some information. So when was the last time you heard from her? \n"
							+ "Sarah:  Last night when she left my house.  She came over to study for the Science Test. She left around 8:30. \n"
							+ "John: 8:30? are you sure about that. Tiffany's mom said she called her at 9:15 saying she was coming home now. \n"
							+ "Sarah: Yeah I'm sure, we were watching an episode of Friends and she left right after it ended and that ends at 8:30. \n"
							+ "John: Do you know anywhere she might be? Any other friends she might be staying at? \n"
							+ "Sarah: Well Sarah is a popular girl but if she was staying somewhere she would of let me know, I'm her best friend we tell each other everything. \n"
							+ "John: Do you have a photo of Sarah I could have? You know for the investigation.\n"
							+ "Sarah: Of course.  I have a photo a bunch of photos of us.  Here you can have this one of us on the Spring Field Trip, we went camping at a park nearby. \n"
							+ "John: Okay thanks, If I have any more questions I'll let you know.  Here's my number.  Call me if you can think of anything else that might be able to help me.\n"
							+ "Ms. Swanson said Tiffany was on her way home at 9:15, Sarah says she left her house at 8:30. I don't have any reason to think either of them is lying just yet so what was Tiffany doing for those 45 minutes? This is starting to seem a little weird.");
					Lucidmain.clock.advanceGame(0, 10);
					Inventory.addItem(Lucidmain.photo);
					Lucidmain.photo.pickItem();
					sarahFound = false;
				}
				else outputText.setValue("You haven't found her yet.");
				break;

			case "talk mr. jackson":
				if (Suspect.getSuspectList().contains(Lucidmain.jackson)) {
					outputText.setValue("You already did that.");
				}
				else 	outputText.setValue("The teacher steps out of his classroom to talk to you. \n"
						+ "Mr. Jackson: Is their anything I can help you with? \n"
						+ "John: I'm just trying to figure out where Tiffany Swanson is. My names Detective Mallard. She seems to be missing from school today. \n"
						+ "Mr. Jackson: Yes I haven't seen her today. And she missed the test I'm giving.  Is this offical police business? I wasn't aware tardiness was the concern of the police. \n"
						+ "John: No nothing so offical. I'm just helping out a friend.  What happened to your hand? (You notice a bandage on his left middle finger) \n"
						+ "Mr. Jackson: I slammed my hand in my car door. If you don't mind I have a test to get back to before too many of my students cheat.");
				Lucidmain.clock.advanceGame(0, 10);
				Suspect.addSuspect(Lucidmain.jackson);
				break;

			case "talk mr jackson":
				if (Suspect.getSuspectList().contains(Lucidmain.jackson)) {
					outputText.setValue("You already did that.");
				}
				else 	outputText.setValue("The teacher steps out of his classroom to talk to you. \n"
						+ "Mr. Jackson: Is their anything I can help you with? \n"
						+ "John: I'm just trying to figure out where Tiffany Swanson is. My names Detective Mallard. She seems to be missing from school today. \n"
						+ "Mr. Jackson: Yes I haven't seen her today. And she missed the test I'm giving.  Is this offical police business? I wasn't aware tardiness was the concern of the police. \n"
						+ "John: No nothing so offical. I'm just helping out a friend.  What happened to your hand? (You notice a bandage on his left middle finger) \n"
						+ "Mr. Jackson: I slammed my hand in my car door. If you don't mind I have a test to get back to before too many of my students cheat.");
				Lucidmain.clock.advanceGame(0, 10);
				Suspect.addSuspect(Lucidmain.jackson);
				break;

			case "talk jackson":
				if (Suspect.getSuspectList().contains(Lucidmain.jackson)) {
					outputText.setValue("You already did that.");
				}
				else 	outputText.setValue("The teacher steps out of his classroom to talk to you. \n"
						+ "Mr. Jackson: Is their anything I can help you with? \n"
						+ "John: I'm just trying to figure out where Tiffany Swanson is. My names Detective Mallard. She seems to be missing from school today. \n"
						+ "Mr. Jackson: Yes I haven't seen her today. And she missed the test I'm giving.  Is this offical police business? I wasn't aware tardiness was the concern of the police. \n"
						+ "John: No nothing so offical. I'm just helping out a friend.  What happened to your hand? (You notice a bandage on his left middle finger) \n"
						+ "Mr. Jackson: I slammed my hand in my car door. If you don't mind I have a test to get back to before too many of my students cheat.");
				Lucidmain.clock.advanceGame(0, 10);
				Suspect.addSuspect(Lucidmain.jackson);
				break;

			case "go john's home":
				if (Location.getLocationList().contains(Lucidmain.johnHome)) {
					Game.setCurrentLoc(Lucidmain.johnHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go john home":
				if (Location.getLocationList().contains(Lucidmain.johnHome)) {
					Game.setCurrentLoc(Lucidmain.johnHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go johns home":
				if (Location.getLocationList().contains(Lucidmain.johnHome)) {
					Game.setCurrentLoc(Lucidmain.johnHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go tiffany's home":
				if (Location.getLocationList().contains(Lucidmain.tiffanyHome)) {
					Game.setCurrentLoc(Lucidmain.tiffanyHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go tiffany home":
				if (Location.getLocationList().contains(Lucidmain.tiffanyHome)) {
					Game.setCurrentLoc(Lucidmain.tiffanyHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go tiffanys home":
				if (Location.getLocationList().contains(Lucidmain.tiffanyHome)) {
					Game.setCurrentLoc(Lucidmain.tiffanyHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go sarah's home":
				if (Location.getLocationList().contains(Lucidmain.sarahHome)) {
					Game.setCurrentLoc(Lucidmain.sarahHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go sarah home":
				if (Location.getLocationList().contains(Lucidmain.sarahHome)) {
					Game.setCurrentLoc(Lucidmain.sarahHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go sarahs home":
				if (Location.getLocationList().contains(Lucidmain.sarahHome)) {
					Game.setCurrentLoc(Lucidmain.sarahHome);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "go police station":
				if (Location.getLocationList().contains(Lucidmain.policeStation)) {
					Game.setCurrentLoc(Lucidmain.policeStation);
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "arrest sarah":
				if (sarahFound == true || Inventory.getItemList().contains(Lucidmain.photo)) {
					Lucidmain.sarah.arrestSuspect();
					Game.gameOver();
				}
				else outputText.setValue("You don't know where she is.");
				break;

			case "arrest mr. jackson":
				if (Suspect.getSuspectList().contains(Lucidmain.jackson)) {
					Lucidmain.jackson.arrestSuspect();
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "arrest mr jackson":
				if (Suspect.getSuspectList().contains(Lucidmain.jackson)) {
					Lucidmain.jackson.arrestSuspect();
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "arrest jackson":
				if (Suspect.getSuspectList().contains(Lucidmain.jackson)) {
					Lucidmain.jackson.arrestSuspect();
					Game.gameOver();
				}
				else outputText.setValue("You don't know how to do that.");
				break;

			case "look photo":
				if (Inventory.getItemList().contains(Lucidmain.photo)) {
					outputText.setValue("It's a photo of Tiffany and Sarah.  They are standing in the woods next to a tent. They look happy.  You can't help but see a resemblence between Tiffany and your daughter.  They could of been sisters. Where is Tiffany at?");	
					Lucidmain.clock.advanceGame(0, 5);
				}
				break;

			default: outputText.setValue("You don't know how to do that.");
			}
		}
	}

	public static void aaronHomeInputs(String k) {
		switch (k) {

		}
	}

	public static void aaronWorkInputs(String k) {
		switch (k) {

		}
	}

	public static void woodsInputs(String k) {
		switch (k) {

		}
	}

	public static void riverInputs(String k) {
		switch (k) {

		}
	}

	public static void johnHomeInputs(String k) {
		// These inputs are for the first time you are at home on Monday of the week.		
		if (Lucidmain.clock.getDay().equals("MON")){
			if (Lucidmain.clock.getCurrentMinutes() > 440) {
				sceneText.set("It is getting late. You should think about getting to WORK soon.");
			}
			switch (k){

			case "look around": 
				if (Inventory.getItemList().contains(Lucidmain.gun) && Inventory.getItemList().contains(Lucidmain.badge) && Inventory.getItemList().contains(Lucidmain.medication)) {
					outputText.setValue("Knocked over on your dresser lays a PHOTO.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else if (Inventory.getItemList().contains(Lucidmain.gun) && Inventory.getItemList().contains(Lucidmain.badge)) {
					outputText.setValue("You look around your place. Everything looks like it did the day before. Other then trash the only things of any importance" + 
							" to you is your MEDICATION on the nightstand. Knocked over on your dresser lays a PHOTO.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else if (Inventory.getItemList().contains(Lucidmain.gun) && Inventory.getItemList().contains(Lucidmain.medication)) {
					outputText.setValue("You look around your place. Everything looks like it did the day before. Other then trash the only things of any importance" + 
							" to you is your BADGE on the nightstand. Knocked over on your dresser lays a PHOTO.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else if (Inventory.getItemList().contains(Lucidmain.medication) && Inventory.getItemList().contains(Lucidmain.badge)) {
					outputText.setValue("You look around your place. Everything looks like it did the day before. Other then trash the only things of any importance" + 
							" to you is your GUN on the nightstand. Knocked over on your dresser lays a PHOTO.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else if (Inventory.getItemList().contains(Lucidmain.badge)) {
					outputText.setValue("You look around your place. Everything looks like it did the day before. Other then trash the only things of any importance" + 
							" to you are your GUN resting on the nightstand next to your MEDICATION. Knocked over on your dresser lays a PHOTO.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else if (Inventory.getItemList().contains(Lucidmain.gun)) {
					outputText.setValue("You look around your place. Everything looks like it did the day before. Other then trash the only things of any importance" + 
							" to you are your BADGE resting on the nightstand next to your MEDICATION. Knocked over on your dresser lays a PHOTO.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else if (Inventory.getItemList().contains(Lucidmain.medication)) {
					outputText.setValue("You look around your place. Everything looks like it did the day before. Other then trash the only things of any importance" + 
							" to you are your BADGE and GUN resting on the nightstand. Knocked over on your dresser lays a PHOTO.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				else {
					outputText.setValue("You look around your place. Everything looks like it did the day before. Other then trash the only things of any importance"
							+ " to you are your BADGE and GUN resting on the nightstand next to your MEDICATION. Knocked over on your dresser lays a PHOTO.");
					Lucidmain.clock.advanceGame(0, 5);
				}
				break;

			case "pick up gun": 
				if (Inventory.getItemList().contains(Lucidmain.gun)) {
					outputText.setValue("You already have that.");
				}
				else {
					outputText.setValue("You pick up your gun.");
					Lucidmain.gun.pickItem();
					Inventory.addItem(Lucidmain.gun);
					Lucidmain.clock.advanceGame(0, 5);
				}
				break;
			case "look gun":
				outputText.setValue("It's a standard police issue Glock 22 handgun.");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "take gun": 
				if (Inventory.getItemList().contains(Lucidmain.gun)) {
					outputText.setValue("You already have that.");
				}
				else {
					outputText.setValue("You pick up your gun.");
					Lucidmain.gun.pickItem();
					Inventory.addItem(Lucidmain.gun);
					Lucidmain.clock.advanceGame(0, 5);
				}
				break;

			case "look badge":
				outputText.setValue("It's your police badge.");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "pick up badge":
				if (Inventory.getItemList().contains(Lucidmain.badge)) {
					outputText.setValue("You already have that.");
				}
				else {
					outputText.setValue("You pick up your badge.");
					Lucidmain.badge.pickItem();
					Inventory.addItem(Lucidmain.badge);
					Lucidmain.clock.advanceGame(0, 5);
				}
				break;

			case "take badge":
				if (Inventory.getItemList().contains(Lucidmain.badge)) {
					outputText.setValue("You already have that.");
				}
				else {
					outputText.setValue("You pick up your badge.");
					Lucidmain.badge.pickItem();
					Inventory.addItem(Lucidmain.badge);
					Lucidmain.clock.advanceGame(0, 5);
				}
				break;

			case "look medication":
				outputText.setValue("It's medication you've been taking to help you sleep better. You've been prone to waking up exhausted over the last couple years.");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "pick up medication":
				if (Inventory.getItemList().contains(Lucidmain.medication)) {
					outputText.setValue("You already have that.");
				}
				else {
					outputText.setValue("You pick up your medication.");
					Lucidmain.medication.pickItem();
					Inventory.addItem(Lucidmain.medication);
					Lucidmain.clock.advanceGame(0, 5);
				}
				break;

			case "take medication":
				if (Inventory.getItemList().contains(Lucidmain.medication)) {
					outputText.setValue("You already have that.");
				}
				else {
					outputText.setValue("You pick up your medication.");
					Lucidmain.medication.pickItem();
					Inventory.addItem(Lucidmain.medication);
					Lucidmain.clock.advanceGame(0, 5);
				}
				break;

			case "look photo":
				outputText.setValue("You look at the photo on the dresser. It shows you on your graduation day. Your dad is standing next to you.  He was so proud of you for graduating from the Police Academy. If only he could see you now, what would he think?");
				Lucidmain.clock.advanceGame(0, 5);
				break;

			case "go police station":
				if (Inventory.getItemList().contains(Lucidmain.gun) && Inventory.getItemList().contains(Lucidmain.badge)) {
					initPoliceStation();
					Lucidmain.clock.advanceGame(0, 30);
				}
				else outputText.setValue("You can't leave without your gun and badge, the Chief would have your head.");
				break;

			case "go work":
				if (Inventory.getItemList().contains(Lucidmain.gun) && Inventory.getItemList().contains(Lucidmain.badge)) {
					initPoliceStation();
					Lucidmain.clock.advanceGame(0, 30);
				}
				else outputText.setValue("You can't leave without your gun and badge, the Chief would have your head.");
				break;
			default: outputText.setValue("You don't know how to do that.");
			}
		}
	}

	// endings for the game.
	public static void ending() {
		if (Game.isAlive == false && Lucidmain.captain.getAlive() == true) {
			outputText.setValue("John decides he can't take it anymore. The world would be better off without him.  He has failed too many people and the thought of failing again is too much for him to handle. He thinks that finally he can be reuinted with his daughter. \n \n"
					+ "                     					THE END");
		}
		else if (Lucidmain.sarah.getArrested() == true) {
			outputText.setValue("Something just doesn't seem right about Sarah.  Her story just doesn't match up.  I think she was jealous of Tiffany and things got out of hand and she killed her.  Even though Sarah is a minor she's going to get put away for a long time. \n \n"
					+ "									THE END");
		}
		else if (Lucidmain.jackson.getArrested() == true) {
			outputText.setValue("Seems to me like Mr. Jackson was getting a little to close to his student.  I think he secretely loved Tiffany but didn't know how to handle his emotions so using his knowledge of chemisty he was able to drug Tiffany but sustained an injury in the struggle as she fought back. I'll take him down to the station and break him to tell us where he put Tiffany's body and then he's going away for good. \n"
					+ "							THE END");
		}
		else if (Lucidmain.captain.getAlive() == false) {
			if (Game.getCurrentLoc().equals(Lucidmain.school) || Game.getCurrentLoc().equals(Lucidmain.sarahHome) || Game.getCurrentLoc().equals(Lucidmain.tiffanyHome) || Game.getCurrentLoc().equals(Lucidmain.johnHome)) {
				outputText.setValue("As John is leaving the police station he is confronted by his former colleagues now yelling at him to surrender is weapon. John doesn't feel like complying with their demands and is promptly gunned down. Why did John do that? Did he finally crack after everything that has happened to him? I guess we will never know.  His secrets died with him. \n \n \n" + 
						"										THE END");
			}
			else outputText.setValue("John was gunned down after shooting the Captain.  Why did John do that? Did he finally crack after everything that has happened to him? I guess we will never know.  His secrets died with him. \n \n"
					+ "								THE END");
		}
		else outputText.setValue("Thank you for playing my demo.  What will happen to John next as he investigates the crime and you dig deeper into his past. Check back later for the next episode of Lucid. \n \n"
				+ "							THE END");
	}
// the help menu.
	public static void help(){
		outputText.setValue("Enter commands into the text field. When in doubt use 'look around' to start.\n"
				+ "The items in all caps are the items you can interact with. Trying using commands like 'look' or 'take' then the item name. Avoid using filler words like at or on. \n"
				+ "If you want to go to a location try 'go' followed by the name of the location. \n"
				+ "If you want to use an item try 'use' followed by the item then 'on' and the name of what you want to use the item on."
				+ " And try to experiement and see what works.");
	}
}





















