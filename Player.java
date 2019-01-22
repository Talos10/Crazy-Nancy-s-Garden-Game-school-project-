/**
 * Purpose: a class which is the blueprint of the player object which is used to do many actions in order to change a garden. Each real life player has a player object that belongs to them
 * which means they all have different gardens since every player object has their own garden object.
 */
public class Player {

    private String name;
    private Garden garden;

    //Constructor which takes the name of the player and the desired garden size
    public Player(String name, int size ){
        this.name = name;
        garden = new Garden(size);
    }

    public String getName(){
        return name;
    }

    //Below all are the corresponding garden methods but made so that the player object can use them.

    public int howManyFlowersPossible(){
        return garden.countPossibleFlowers();
    }

    public int howManyTreesPossible(){
        return garden.countPossibleTrees();
    }

    public int whatIsPlanted(int r, int c){
        return garden.getInLocation(r,c);
    }

    public void plantTreeInGarden(int r, int c){
        garden.plantTree(r,c);
    }

    public void plantFlowerInGarden(int r, int c){
        garden.plantFlower(r,c);
    }

    public void eatHere(int r, int c){
        garden.removeFlower(r,c);
    }

    public boolean isGardenFull(){
        return garden.gardenFull();
    }

    public boolean isGardenEmpty(){
        return garden.gardenEmpty();
    }

    public String showGarden(){
        return garden.toString();
    }

}
