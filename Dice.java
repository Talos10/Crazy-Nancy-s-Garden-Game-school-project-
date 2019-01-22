/**
 * Purpose: A class which is the blueprint of the object Dice which can be rolled (gives two random number,
 * one for die1 and one for die2).
 */
public class Dice {

    private int die1;
    private int die2;

    //Constructor of the dice object
    public Dice (){
        die1 = 0;
        die2 = 0;
    }

    //Getter for die1
    public int getDie1 (){
        return die1;
    }

    //Getter for die2
    public int getDie2 (){
        return die2;
    }

    //A method which returns the sum of the dice
    public int getDiceSum(){
        return (die1 + die2);
    }

    //A method which assigns a random number between 1 and 6 to both die1 and die2
    public int rollDice(){
        die1 = (int) (Math.random() *6 +1);
        die2 = (int) (Math.random() *6 +1);
        return (die1 + die2);
    }

    //A toString method which displays the contents of both die1 and die2
    public String toString(){
        return "(Die 1: " + die1 + " ; Die 2: " + die2 + ")";
    }

}
