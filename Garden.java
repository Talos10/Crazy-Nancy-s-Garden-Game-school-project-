/**
 * Purpose: a class which is the blueprint of the class garden where players will be able to plant
 * trees and flowers in it in order to win the game.
 */
public class Garden {

    private char [][] garden;

    //Constructor
    public Garden (){
        garden = new char[3][3];
        initializeGarden();
    }

    //Constructor with a specified size for the garden
    public Garden(int size){
        garden = new char [size][size];
        initializeGarden();
    }

    //A method which fills up all the garden array with the empty character '-'
    private void initializeGarden(){
        for(int r =0; r<garden.length; r++)
            for(int c=0; c<garden[r].length; c++)
                garden[r][c] = '-';
    }

    //A method which returns the character of the inputted coordinates (where 't' is a part of a tree, 'f' is a flower, and '-' is an empty place)
    public char getInLocation(int r, int c){
        return garden[r][c];
    }

    //A method which replaces the current character at the square located at the inputted coordinates with the character that represents a flower
    public void plantFlower(int r, int c){
        garden[r][c] = 'f';
    }

    //A method which replaces the current character at the square located at the inputted coordinates (and adjacent squares that are below, to the right, and diagonally
    //across of the initial square) with the character that represents a tree
    public void plantTree(int r, int c){
        garden[r][c] = 't';
        garden[r+1][c] = 't';
        garden[r][c+1] = 't';
        garden[r+1][c+1] = 't';
    }

    //A method which, despite its name, is used to replace any character at the inputted location by the empty character '-' (used to remove planted stuff at a specific point)
    public void removeFlower(int r, int c){
        garden[r][c] = '-';
    }

    //Method which counts the amount of possible places where a tree could be planted at
    public int countPossibleTrees(){
        int count = 0;

        for(int r =0; r<garden.length-1; r++)
            for (int c = 0; c < garden[r].length-1; c++)
                if(garden[r][c] == '-' && garden[r+1][c] == '-' && garden[r][c+1] == '-' && garden[r+1][c+1] == '-')
                    count++;

        return count;
    }

    //Method which counts the amount of possible places where a flower could be planted at
    public int countPossibleFlowers(){
        int count = 0;

        for(int r =0; r<garden.length; r++)
            for (int c = 0; c < garden[r].length; c++)
                if(garden[r][c] == '-')
                    count++;

        return count;
    }

    //Method which checks to see if the garden is full i.e. no empty characters '-' present
    public boolean gardenFull(){
        for(int r =0; r<garden.length; r++)
            for (int c = 0; c < garden[r].length; c++)
                if(garden[r][c] == '-')
                    return false;

        return true;
    }

    //Method which checks to see if the garden is empty i.e. all spots in the garden are empty characters '-'
    public boolean gardenEmpty(){
        for(int r =0; r<garden.length; r++)
            for (int c = 0; c < garden[r].length; c++)
                if(garden[r][c] > '-')
                    return false;

        return true;
    }

    //toString method which displays the array of the player
    public String toString(){

        String string = "";

        string += "  " + "|";

        for(int p = 0; p<garden.length; p++)
            string += " " + (p+1) + "  ";

        string += "\n";

        for(int r = 0; r<garden.length; r++) {

            string += (r+1) + " |\t";

            for (int c = 0; c < garden[r].length; c++) {
                string += garden[r][c];
                string += "\t";
            }

            string += "\n";
        }

        return string;
    }

}
