// -------------------------------------------------------
// Assignment 4 
// Written by: Hao Yi Liu (40174210)
// Date: 19/04/2021
// For COMP 248 Section W 2204 - Winter 2021
// -------------------------------------------------------
// this program creates a battle game of creatures. The code is divided in two parts, the first part is to store information in the class Creature 
// as well as methods in that class that are useful for the game, and the second one is to
// run and display all the possibilities of the game depending on different options. 
import java.util.Date; //allows the use of the Date variable type
import java.util.Scanner; //allows the user's input

public class Creature { //class containing the list of private data members
	private static final int FOOD2HEALTH= 6; //declare the necessary variables 
    private static final int HEALTH2POWER= 4;
    private String name;
    private int foodUnits;
    private int healthUnits;
    private int firePowerUnits;
    private Date dateCreated;
    private Date dateDied;
    private static int numStillAlive= 0;
    public Creature() { //default constructor 
    	name= "none"; //initializing variables 
        numStillAlive= numStillAlive+1; //incrementing the numStillAlive counter by 1
        foodUnits= (int) (Math.random()*12) + 1; 
        healthUnits= (int) (Math.random()*7) + 1; //generating random foodUnits, healthUnits, firePowerUnits in the right ranges
        firePowerUnits= (int) (Math.random()*11);
        dateCreated= new Date(); //generates the creation date
        dateDied= null; //initializing death date
        normalizeUnits(); //call the normalizeUnits method defined later
    }
    public void setName(String creatureName) { //public mutator method to set the creature's name
        name= creatureName;
    }
    public String getName() { //public method that returns name
        return name;
    } 
    public void setHealthUnits(int creatureHealthUnits) { //public mutator method to set the health units
        healthUnits= creatureHealthUnits;
    }
    public void setFoodUnits(int creatureFoodUnits) { //public mutator method to set the food units
        foodUnits= creatureFoodUnits;
    }
    public int getFoodUnits() { //public method that returns food units
        return foodUnits;
    }
    public void reduceFirePowerUnits(int amount) { //public method that reduces the fire power units by the amount in the parameter
        firePowerUnits= firePowerUnits-amount;
    }
    public int getHealthUnits() { //public method that returns health units
        return healthUnits;
    }
    public int getFirePowerUnits() { //public method that returns fire power units
        return firePowerUnits;
    }
    public Date getDateCreated() { //public method to return the creation date
        return dateCreated;
    }
    public Date getDateDied() { //public method to return the death date
        return dateDied;
    }
    public int getNumStillAlive() { //public method to return the number of creatures sill alive
        return numStillAlive;
    }
    public boolean isAlive() { //public method that returns true if the creature is still alive
    	if (dateDied== null) 
    		return true;
    	else
    		return false;
    }
    public int earnFood() { //public method that adds a random number in the range in food units
        int newFood = (int) (Math.random()*16); //generates a random number between 0 and 15
        setFoodUnits(foodUnits+newFood); //setting the new food units value
        normalizeUnits();
        return newFood;
    }
    public void attacking(Creature player) { //public method that applies the necessary changes when a creature attacks 
        int stolenFoodUnits = (player.foodUnits+1)/2; //round up the stolen/gained food units 
        int stolenHealthUnits = (player.healthUnits+1)/2; //round up the stolen/gained health units
        setFoodUnits(foodUnits+stolenFoodUnits);  
        setHealthUnits(healthUnits+stolenHealthUnits);  
        player.setFoodUnits(player.foodUnits-stolenFoodUnits);  
        player.setHealthUnits(player.healthUnits-stolenHealthUnits); //setting the new food units and the new health units of the attacking and the attacked players (lines 81-84)
        reduceFirePowerUnits(2); //reduces the fire power units of 2
        normalizeUnits();
        player.healthFoodUnitsZero(); //checks if the player is dead and execute the appropriate method
    }
    public boolean healthFoodUnitsZero() { //public method that returns true if the creature is dead
        if (healthUnits == 0 && foodUnits == 0) {
            if (dateDied == null) {
                died();
            }
            return true;
        }
        else return false;
    }
    private void died() { //private method that sets the death date to the current date
        dateDied = new Date();
        numStillAlive=numStillAlive-1; //decrements the number of creatures alive when the method is called
        System.out.println("---->"+name+" is dead"); //printing which creature has died
    }
    public String toString() { //returns a string of all the non-static attributes
        return "The creature's name is " + name + ", it has " + foodUnits + " food units, " + healthUnits +" health units, "
        		+ firePowerUnits + " fire power units. It was created on " + dateCreated + ". Date died: " + dateDied ;
    }
    public String showStatus() { //returns in the string format the food units, the health units and the fire power units
        return String.format("%d food units, %d health units, %d fire power units", foodUnits, healthUnits, firePowerUnits);
    }
    public void displayStatus() { //prints the status of a creature when it is called
    	System.out.printf("\n%-15s %-15s %-25s %-15s","Food units","Health units", "Fire power units", "Name");
        System.out.printf("\n%-15s %-15s %-25s %-15s","------------","--------------","------------------","-----");  
        System.out.printf("\n%-15s %-15s %-25s %-15s",getFoodUnits(), getHealthUnits(), getFirePowerUnits(), getName());
      	System.out.print("\nDate created: "+getDateCreated());
        if (getHealthUnits() > 0) {
      		System.out.println ("\nDate died: is still alive");
        }
      	else{
      		System.out.print("\nDate died: "+getDateDied());
      	}
    }
    private void normalizeUnits() { //private method that converts food into health and health into fire power according to the specified rules
        healthUnits= healthUnits+(foodUnits/FOOD2HEALTH);
        foodUnits= foodUnits % FOOD2HEALTH;
        if (healthUnits > 7) {
            firePowerUnits= firePowerUnits+((healthUnits-4)/HEALTH2POWER);
            healthUnits= (healthUnits % HEALTH2POWER)+4;
        }
    }
    public static void main(String args[]){ //main method containing all the arguments
    	Scanner keyboard= new Scanner (System.in); //allows the user to input data 
    	System.out.println("\n[-----------------------------------------]");
    	System.out.println("\n[      WELCOME TO THE BATTLE GAME         ]"); //displaying welcome message
    	System.out.println("\n[-----------------------------------------]");
    	int creaturesNumber=0; //declaring and initializing creature number to 0
    	int playerChosen; //declaring variables
    	boolean invalidNumber=true; 
    	Creature[] creatureList= new Creature [8]; //declaring and initializing the array list
    	while(invalidNumber){ //loops while the number entered is invalid
    		try { 
    			System.out.println("\nHow many creatures would you like to have (minimum 2, maximum 8)?"); //printing the message of the range of creatures
    			invalidNumber=false;
    			creaturesNumber = Integer.parseInt(keyboard.nextLine()); 
    		} 
    		catch (NumberFormatException exception) {
    			System.out.print("Your input is invalid. Please try again.");
    			invalidNumber=true;
    		}		
    		if(creaturesNumber >= 2 && creaturesNumber <= 8){ //checking if the entered number is valid
	    		invalidNumber=false;
	    	    for (int i=0; i<creaturesNumber; i++){ //loops until the user has entered the name of all present creatures
		    	    System.out.println("\nWhat is the name of creature " + (i+1) + "?");     
		    	    String name=keyboard.nextLine();
		    	    creatureList[i]= new Creature();   	   
		    	    creatureList[i].setName(name);
		    	    System.out.printf("\n%-15s %-15s %-25s %-15s","Food units","Health units", "Fire power units", "Name");
		    	    System.out.printf("\n%-15s %-15s %-25s %-15s","------------","--------------","------------------","-----");  
		    	    System.out.printf("\n%-15s %-15s %-25s %-15s",creatureList[i].getFoodUnits(), creatureList[i].getHealthUnits(), creatureList[i].getFirePowerUnits(), creatureList[i].getName());
		    	    System.out.print("\nDate created: "+creatureList[i].getDateCreated());
		    	    System.out.print("\nDate died: "+creatureList[i].getDateDied());	                  
	    	    }
	    	}
	    	else{
	    		invalidNumber=true;
	    		System.out.println("\n*** Illegal number of creatures requested ***"); //requesting to change the invalid number to a valid one
	    	}
    	}
    	playerChosen = (int) (Math.random()*creaturesNumber)+1; //between 0 and creatureNumbers since that's how they are indexed in the array
    	boolean invalidChoice=true;
    	while (invalidChoice){ //loops while the choice entered is invalid
    		int i=playerChosen; //initializing the variable i to player chosen 
    		while (numStillAlive>1){ //loops while there are more than 1 creature alive
    			boolean turnSwitch=false;
    			while (turnSwitch==false) { //loops while the creature doesn't select a switching turn option (option 5 or 6) or if they are dead
    				int option=0; //initializing the option
	    			int j=i % creaturesNumber; //using the remainder operator to cycle through the creatures list (j is the index of the current player)
	    			if (creatureList[j].isAlive()) { //check if the current player is alive if so, asks to select an option
	    				try { //execute commands which might fire an error
	    					System.out.println ("\nCreature #"+(j+1)+": "+creatureList[j].getName()+", what do you want to do?");
			    			System.out.println ("\n\t1. How many creatures are alive?");
			    			System.out.println ("\n\t2. See my Status");
			    			System.out.println ("\n\t3. See Status of all players");
			    			System.out.println ("\n\t4. Change My Name");
			    			System.out.println ("\n\t5. Work for some food");
			    			System.out.println("\n\t6. Attack another creature (Warning! may turn against you)");
			    			System.out.println("\nYour Choice please > ");
			    			invalidChoice=false;
	    	    			option = Integer.parseInt(keyboard.nextLine()); 
	    	    		} 
	    	    		catch (NumberFormatException exception) { //catch the error if the user doesn't input an integer
	    	    			invalidChoice=true;
	    	    		}
		    			if (option >= 1 && option <= 6){ //checks if the option entered is between 1 and 6
		    				invalidChoice=false;	
		    			}
		    			else System.out.print("\nYour input is invalid. Please try again."); //tells the user to print a valid input 
		    			switch(option){ //selects a case depending on the option input
		    				case 1:
		    					System.out.print("\nNumber of creatures alive: "+creatureList[j].getNumStillAlive()); //gets the number of creatures alive from the Creature class method
    			
		    				break;	  
		    				case 2:
		    					creatureList[j].displayStatus();	//display status from the Creature class method of the current creature 
		    				break;	  
				    		case 3:
				    			for (int k=0; k<creaturesNumber; k++){ //display status of all creatures  
				    				creatureList[k].displayStatus();
				    			}
				    		break; 
				    		case 4:
				    			System.out.println("\nYour name is currently " +creatureList[j].getName()); //changes the name of the current creature from the Creature class method
				    			System.out.println("What is the new name: ");
				    			String newName=keyboard.nextLine();
				    			creatureList[j].setName(newName);
				    		break;
				    		case 5:
				    			System.out.println("Your status before working for food: " +creatureList[j].showStatus()); //prints the status of the current creature before earning food
				    			int gainedFood=creatureList[j].earnFood(); //adds the foods earned 
				    			System.out.println("\n... You earned "+ gainedFood +" food units.");
				    			System.out.println("\nYour status after working for food: " +creatureList[j].showStatus()); //prints the status of the current creature before earning food
				    			turnSwitch=true; 
				    		break;  
				    		case 6:	
				    			turnSwitch=true; 
				    			boolean invalidTarget=true; 
				    			int targetInt= 10; //initializing the integer targetInt to an invalid index for creatureList
				    			Creature target=null; //initializing the Creature target
				    			while (invalidTarget) { //loops while the target is invalid
				    				try { //execute commands which might fire an error
						    			System.out.println("\nWho do you want to attack? (enter a number from 1 to "+creaturesNumber+" other than yourself("+playerChosen+")): ");
						    			invalidTarget=false;
						    			targetInt = Integer.parseInt(keyboard.nextLine());
						    			target=creatureList[targetInt-1];
				    	    		} 
				    	    		catch (NumberFormatException exception) { //catch the error if the user doesn't input an integer
				    	    			invalidTarget=true;
				    	    		}
				    				catch (ArrayIndexOutOfBoundsException exception) { //catch the error if the user doesn't input an integer
				    	    			invalidTarget=true;
				    	    		}
				    				if(target==null){ //checks if the target exists 
				    					System.out.println("\nThe input is invalid or that player doesn't exist! Try again ...");
				    				}
				    				else if (target.isAlive()==false) { //checks if target is alive
				    					System.out.println("\nThat player is already dead. Choose again...");
				    				}
				    				else if(creatureList[j] == target){ //checks if the target is not itself
				    					System.out.println("\nCan't attack yourself silly! Try again ...");
				    				}
				    				else { //the target is valid
				    					invalidTarget=false;
				    					int attackRNG= (int) (Math.random()*3); //sets a random probability chance of being attacked out of 3
				    					if (creatureList[j].getFirePowerUnits()< 2) { //checks if the player has enough fire power units
				    						attackRNG=0; 
				    						System.out.println("That was not a good idea ... you only had "+creatureList[j].getFirePowerUnits()+" Fire Power units!!!");
				    					}
					    				if (attackRNG==0) {  //checks the odds the player is being attacked
					    					if (target.getFirePowerUnits()<2) { //checks if the opponent has enough fire power units
					    						System.out.println("Lucky you, the odds were that the other player attacks you, but "+target.getName()+ " doesn't have enough fire power to attack you! So it is status quo!!");
					    					}
					    					else { //prints the status of the current player when the attack is not successful
					    						System.out.println("\n....... Oh No!!! You are being attacked by "+target.getName()+"!");
					    						System.out.println("Your status before being attacked: "+creatureList[j].showStatus());
					    						target.attacking(creatureList[j]);
					    						System.out.println("Your status after being attacked: "+creatureList[j].showStatus());
					    					}
					    				}
					    				else { //prints the status of the current player when the attack is successful
					    					System.out.println("\n..... You are attacking "+target.getName()+"!");
					    					System.out.println("Your status before attacking: "+creatureList[j].showStatus());
					    					creatureList[j].attacking(target);
					    					System.out.println("Your status after attacking: "+creatureList[j].showStatus());		
					    				}
				    				}
				    			}
				    		break;
		    			}
	    			}
	    			else turnSwitch=true; //switch turns if the player is dead
    			}
    			i++; //increments to move to the next player 
    		}
    		System.out.println("\nGAME OVER!!!!!"); //game over message once the loop is done 
    		for (int k=0; k<creaturesNumber; k++){ //displays status of all players
    			creatureList[k].displayStatus();
    		}
    	}
    	System.out.println("\n[-----------------------------------------]");
    	System.out.println("\n[    Thank you for playing Battle Game!   ]"); //ending message
    	System.out.println("\n[-----------------------------------------]");
    }
}