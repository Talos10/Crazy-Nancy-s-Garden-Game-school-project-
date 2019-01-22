import java.util.Scanner;

public class LetsPlay {

    public static void main(String[] args) {
        int size;//Integer variable used to store the size of the garden the user wants to play with.
        int playerCount;//Integer variable used to store the number of players that will be playing.
        Player [] players;//A 1-D array of player objects where each player object is one player that is playing.
        Dice [] dice;//A 1-D array of dice objects where each dice object belongs to a player that is playing. By "belongs", it is meant that each dice object stores the current roll of a certain player and that dice object is only "rolled" by that same player. Throughout the program, the index at which a certain player is found in the players array is also the index at which the dice of that same player is found.
        String playerName;//A string object which is used to temporarily store the name of each player. This string object is used to initialize the array with player objects.
        boolean check = true;//A boolean variable which stays true until the user enters a correct garden size.
        String empty;//A string variable that is never used anywhere and where its sole use is to store the "enter" character from when the user is asked the number of players that will play so that this specific "enter" character doesn't get picked up when the program asks the names of the players.

        Scanner keyboard = new Scanner(System.in);//Scanner object used to register input from the different users.

        //Prints out a welcome message to the users.
        System.out.println("*********************************************************************");
        System.out.println("Welcome to Crazy Nancy's Garden Game!");
        System.out.println("*********************************************************************");

        //Prints out the rules of the game to the users.
        System.out.println("\nTo win this game you need some luck with the dice and a bit of strategy.\n" +
                "Your garden is an NxN lot. You can plant flowers or trees. A flower takes one spot. A tree takes 4 spots (2x2).\n" +
                "You roll the dice and based on the outcome you get to plant a pre-set number of trees and flowers.");

        System.out.println("\nRolls and their outcome:\n" +
                "---------------------\n" +
                "3: plan a tree (2x2) and a flower (1x1)\n" +
                "6: plant 2 flowers (2 times 1x1)\n" +
                "12: plant 2 trees (2 times 2x2)\n" +
                "5 or 10: the rabbit will eat something that you have planted - might be a flower or part of a tree(1x1)\n" +
                "Any other EVEN rolls: plant a tree (2x2)\n" +
                "Any other ODD rolls: plant a flower (1x1)");

        System.out.println("\nMinimum number of players: 2.\n" +
                "Minimum garden size: 3x3.\n" +
                "You can only plant in empty locations. To plant a tree you give the top left coordinates of the 2x2 space\n" +
                "and I will check to make sure all 4 locations are free.");

        System.out.println("\nThe default garden size is a 3x3 square. You can use this default board size or change the size.");

        //Asks a user to enter the preferred garden size that they would like to play and then stores that preferred size into the integer variable size.
        System.out.println("So, if you would like to keep the default size of a 3x3 square enter -1. \n" +
                "If not, then enter an integer N that is bigger than 3 which will make the garden be a square of size NxN. ");
        size = keyboard.nextInt();

        //A while loop which makes sure that the user enters a valid garden size.
        while(check) {
            if(size == -1) {
                size = 3;
                check = false;
            }

            else if (size <= 3) {
                System.out.println("Invalid size. Please enter -1 to keep the size of the garden to the default 3X3 square" +
                        "\n or enter an integer N that is bigger than 3 to change the garden size to a NxN square.");
                size = keyboard.nextInt();
            }

            else
                check = false;
        }

        //Asks the user how many players will play and stores that integer into the variable playerCount.
        System.out.println("\nHow many players are there in total? Enter an integer between 2 and 10 inclusive.");
        playerCount = keyboard.nextInt();

        //A while loop which makes sure that the user enters a valid number of players.
        while(playerCount<2 || playerCount>10) {
            System.out.println("Invalid player count. The player count must be between 2 and 10 inclusive. Please enter another integer.");
            playerCount = keyboard.nextInt();
        }

        empty = keyboard.nextLine();//Registers the "enter" character from the previous statement so that it doesn't affect the name-entering in the next for loop.

        //Initializes the players array with the size of the array being the amount of players playing.
        players = new Player [playerCount];

        //Initializes the dice array with the size of the array being the amount of players playing.
        dice = new Dice [playerCount];

        //A for loop which iterates through the array containing all the players while, at the same time, asking the name of each player in order to be able to initialize the player object that corresponds to each player.
        for(int i = 0; i<playerCount; i++){
            System.out.println("\nPlayer " + (i+1) + ", please enter your name: ");
            playerName = keyboard.nextLine();
            players[i] = new Player(playerName, size);//Initializes the player object that will represent a specific player and do so by using their name and the preferred garden size that was previously entered.
        }

        //A for loop which iterates through the array containing all the dice and initializes each dice object that corresponds to each player.
        for(int i = 0; i<playerCount; i++){
            dice[i] = new Dice();
        }

        System.out.println("");

        //Calls the method whoGoesFirst which determines which player goes first based on the roles of dice of each player. For more detail on what the method does, check out the comments of the method itself.
        whoGoesFirst(playerCount, players, dice);

        //Calls the method gameTime which simulates turn after turn after turn where players each plant things into their respective garden until one of the players win. For more detail on what the method does, check out the comments of the method itself.
        gameTime(playerCount, players, dice, size);

        //Prints out a closing message.
        System.out.println("\n*********************************************************************");

        System.out.println("\nThank you for playing my version of Crazy Nancy's Garden Game!");

        System.out.println("\n*********************************************************************");

        System.out.println("\nThis program was written by Razvan Ivan on the 24th of November 2018.");

        System.out.println("\n*************************END OF THE PROGRAM.*************************");
    }

    /**
     * Purpose: To determine the order in which the players will play (i.e. who goes first, second, third, etc.).
     * @param playerCount (the amount of players playing the game)
     * @param players (array which contains the player objects)
     * @param dice (array which contains the dice objects)
     */
    public static void whoGoesFirst(int playerCount, Player[] players, Dice[] dice){
        boolean check = true;//A boolean variable which stays true until all players have rolled a different number.
        int indexOfHighestDiceSum = 0;//Index that tells us the place of the highest roll of the previous loop iteration.
        int p = 0;//Index that moves up by one once we are sure that the value at index p has been put in the right order.
        Player tempPlayer;//Holds reference to a player that will be swapped place.
        int input;//Takes 1 as input which signifies that the user is ready to continue. The value isn't actually used anywhere in the program.
        Dice tempDice;//Holds reference to a dice that will be swapped place.

        Scanner in = new Scanner(System.in);//Scanner object which is used to get input from the user.

        //A while loop which keep on going until every player has rolled a different number.
        while(check) {
            System.out.println("***************");
            System.out.println("Rolling results:");
            System.out.println("***************\n");

            //Prints out the name of the player and what he/she rolled.
            for (int i = 0; i < playerCount; i++) {
                System.out.println(players[i].getName() + " rolled " + dice[i].rollDice() + "\n");
            }

            //We assume that all the roles were different so we put the condition of the while loop to false so that we can exit.
            //However, we check with the two nested for loops below if all the roles done before are actually all different.
            //If they are not, then we make check become true again and the program performs another iteration of the while loop.
            check = false;

            outerloop: //A tag which defines the outer loop of the two nested for loops below. The tag is used in order for the program to break from both nested loops while being inside the inner for loop.

            //Two nested for loops which check to see if two players rolled the same amount. If they did, the condition of the while loop is made true
            //again which means that all players will have to roll again.
            for(int a = 0; a< playerCount; a++){

                for(int b = a+1; b<playerCount; b++){
                    if(dice[a].getDiceSum() == dice[b].getDiceSum()) {
                        check = true;
                        System.out.println("Two or more players have rolled " + dice[a].getDiceSum() + ". Time for everyone to roll again!\n\n");
                        break outerloop;
                    }
                }
            }
        }

        //The sorting algorithm portion of the code which figures out who goes first, second, third, etc.
        //The way this algorithm works is it assumes the first entry in the array is max and then a for loop iterates
        //through the array and checks to see if there is an entry that has a higher value than the assumed max.
        //If it doesn't find a higher max, then the first entry is deemed the highest value of the array. However,
        //if the program does find a higher value, the place (index) of that new max value is registered and the program swaps
        //places between the older "assumed" max value and true max value so that the true max value becomes first in the array
        //and the older "assumed" max value takes the old place of the true max value. Afterwards, the program does not touch
        //the first entry of the array as it has been sorted and so it does the same thing for the second place of the array and
        //so on and so forth until all the array is sorted.
        while(p<playerCount){
            //Sorting algo.
            for (int i = p; i < playerCount; i++) {
                if(dice[i].getDiceSum()>dice[indexOfHighestDiceSum].getDiceSum()){//An if statement that checks if the present value at which the for loop has arrived is bigger than the assumed max value.
                    indexOfHighestDiceSum = i;//We assume that the place where the for loop is currently is where the max value is located at.
                }
            }

            tempDice = dice[p];
            dice[p] = dice[indexOfHighestDiceSum];
            dice[indexOfHighestDiceSum] = tempDice;
            tempPlayer = players[p];
            players[p] = players[indexOfHighestDiceSum];
            players[indexOfHighestDiceSum] = tempPlayer;

            p++;//Since the variable p dictates where the sorting will start, increasing the value means that the value that is in the place in the array which had the old value of p is now sorted.
            indexOfHighestDiceSum = p;//After incrementing p, we assume that the first place from where we start sorting is where the max value is.
        }


        //Displays the order of the players to the user.
        System.out.println("\nHere is the order in which the players will play: ");
        for(int i = 0; i<playerCount; i++){
            System.out.print((i+1) + "#: ");
            System.out.println(players[i].getName());
        }

        //Prompts the user to enter 1 in order to continue.
        System.out.println("\n\nEnter 1 to continue.");
        input = in.nextInt();
    }

    /**
     * Purpose: To simulate the actual game. Once this method is called, the game starts and can only be ended through this method.
     * @param playerCount (the amount of players playing the game)
     * @param players (array which contains the player objects)
     * @param dice (array which contains the dice objects)
     * @param size (size of the garden of the players)
     */
    public static void gameTime(int playerCount, Player[] players, Dice[] dice, int size){

        boolean check = true;//A boolean variable which stays true to keep the game going until a player fills up his/her garden. When that happens, this boolean variable is flipped to false and the game ends and the program declares that player the winner.
        int count = 0;//A variable which is used to count how many turns it took for the winner to win the game.
        int input; //Takes 1 as input which signifies that the user is ready to continue. The value isn't actually used anywhere in the program.

        Scanner scan = new Scanner(System.in);

        System.out.println("\n\n******************************************");
        System.out.println("TIME TO PLAY! May the best gardener win!!!");
        System.out.println("******************************************");

        //This is what simulates a turn of the game (a turn is when all players have played once). The program stays in the while loop until
        //a player is declared a winner.
        while(check){

            count++;//Number of turns

            System.out.println("\n\n**************");
            System.out.println("Turn number " + count + ":");
            System.out.println("**************");

            //A for loop which iterates through the player array which makes the players play from whoever starts first to whoever finishes last.
            for(int i = 0; i<playerCount;i++){

                System.out.println("\n\n***********************");

                System.out.println("It's the turn of " + players[i].getName() + ":");

                System.out.println("***********************\n");

                //Displays the garden of the current player at the beginning of his/her turn.
                System.out.println("Here is your garden " + players[i].getName() + ": \n\n" + players[i].showGarden());

                dice[i].rollDice();//Rolls the dice for the current player.

                //Tells the current player how much he rolled (the value of each die and their sum).
                System.out.println(players[i].getName() + ", you rolled " + dice[i].getDiceSum() + " " + dice[i].toString() + ".");

                //Below are a few if else statements where each one is activated when the player rolls a specific number.

                //Player must plant a tree and/or a flower if they roll 3.
                if(dice[i].getDiceSum()==3){
                    System.out.println("You must plant a tree of a 2X2 dimensions and a flower of a 1X1 dimensions.");

                    //First scenario: The player doesn't have place to plant a tree and so he/she is asked to plant a flower.
                    if(players[i].howManyTreesPossible()==0){
                        System.out.println("However, you have no place to plant a tree.");
                        System.out.println("But you can still plant a flower (1X1).");
                        plantFlower(players, i, size);

                    }

                    //Second scenario: Makes the player plant the tree because he/she has the place to do so.
                    else{
                        System.out.println("Let's start by planting the tree.");
                        plantTree(players, i, size);

                        //Checks to see if the player's garden is full after planting the tree and if it is, sends the
                        //player out the for loop so that he can be later declared the winner.
                        if(players[i].isGardenFull())
                            break;

                        //Shows the current player's garden and makes him/her plant a flower.
                        else{
                            System.out.println("\nHere is what your garden looks like after planting that tree:\n" + players[i].showGarden());

                            System.out.println("Enter 1 to continue.");
                            input = scan.nextInt();

                            System.out.println("Now, you have to plant a flower (1X1).");
                            plantFlower(players, i, size);
                        }
                    }
                }

                //The current player must plant one or two flowers if they roll 6.
                else if(dice[i].getDiceSum()==6){
                    System.out.println("You must plant two flowers.");

                    //Scenario #1:The current player has to plant a flower.
                    if(players[i].howManyFlowersPossible()==1){
                        System.out.println("However, you only have the place to plant one flower (1X1).");
                        plantFlower(players, i, size);
                    }

                    //Scenario #2:The current player has to plant two flowers.
                    else{
                        System.out.println("Let's start by planting the first flower (1X1).");
                        plantFlower(players, i, size);

                        System.out.println("\nHere is what your garden looks like after planting that flower:\n" + players[i].showGarden());

                        System.out.println("Enter 1 to continue.");
                        input = scan.nextInt();

                        System.out.println("\nNow you must plant your second flower.");
                        plantFlower(players, i, size);
                    }
                }

                //The player must plant 1 or 2 trees depending on the space available in his garden. He may also have no place to plant
                //even a single tree which means that his turn is lost.
                else if(dice[i].getDiceSum()==12){

                    System.out.println("You must plant two trees.");

                    //Scenario #1: The current player has no place to plant a tree and thus loses their turn.
                    if(players[i].howManyTreesPossible()==0) {
                        System.out.println("However, you have no place to plant them and therefore you lose your turn.");
                        //System.out.println("Enter 1 to continue.");
                        //input = scan.nextInt();
                    }


                    //Scenario #2: The current player will only plant one tree.
                    else if(players[i].howManyTreesPossible()==1){
                        System.out.println("However, you only have the place to plant one tree (2X2).");
                        plantTree(players, i, size);
                    }

                    //Scenario #3: The current player will plant two trees.
                    else{
                        System.out.println("Let's start by planting the first tree (2X2).");
                        plantTree(players, i, size);

                        System.out.println("\nNow let's plant your second tree.");
                        plantTree(players, i, size);
                    }
                }

                //If the current player rolls 5, the method rabbitEats is called which will make the rabbit that lives
                //in the current player's garden eat something that is planted there. If nothing is planted in the
                //current player's garden then the turn of the player still ends.
                else if(dice[i].getDiceSum()==5 || dice[i].getDiceSum()==10){
                    rabbitEats(players,i,size);
                }

                //The player must plant a tree (if he/she has the place) if they roll an amount that is even (excluding 6 and 12).
                else if(dice[i].getDiceSum()%2 == 0){
                    System.out.println("You must plant a tree (2X2).");

                    if(players[i].howManyTreesPossible()==0) {
                        System.out.println("However, you have no place to plant it and therefore you lose your turn.");
                        //System.out.println("Enter 1 to continue.");
                        //input = scan.nextInt();
                    }

                    else
                        plantTree(players, i, size);
                }

                //The player must plant a flower if they roll an amount that is odd (excluding 5).
                else if(dice[i].getDiceSum()%2 == 1){
                    System.out.println("You must plant a flower (1X1).");
                    plantFlower(players, i, size);
                }

                //Shows current player what their garden looks like at the end of their turn.
                System.out.println("\nHere is what your garden looks like at the end of your turn:\n" + players[i].showGarden());

                System.out.println("Enter 1 to continue.");
                input = scan.nextInt();

                //An if statement which checks to see if the garden of the current player is full. If it is, then the game will stop
                //and will declare the current player the winner.
                if(players[i].isGardenFull()){
                    System.out.println("\nCongratulations " + players[i].getName() + "! You won the game after " + count + " turns!");
                    System.out.println("I hope you had fun making an awesome garden!");

                    System.out.println("Enter 1 to continue.");
                    input = scan.nextInt();

                    System.out.println("\n*************************************");
                    System.out.println("Here are the final results of the game:");
                    System.out.println("*************************************");

                    //Displays the gardens of all the players one last time.
                    for(int y = 0; y<playerCount; y++)
                        System.out.println("\nThe garden of " + players[y].getName() + ":\n" + players[y].showGarden());

                    //Makes the check boolean variable false so that the program exits the while which will make the game end
                    //and breaks from the for loop so that no one continues playing after the winner has been declared.
                    check = false;
                    break;
                }
            }
        }
    }

    /**
     * Purpose: To plant a flower at a specific location in a player's garden.
     * @param players (array which contains the player objects)
     * @param i (represents the index of the current player in the players array)
     * @param size (size of the garden of the players)
     */
    public static void plantFlower (Player[] players, int i, int size){

        int r; //The row in which the player wants to plant a flower.
        int c; //The column in which the player wants to plant a flower.
        boolean check = true; //Boolean which serves as a condition in a while loop and that stays true until the user enters valid input.
        Scanner keyboard = new Scanner(System.in);

        //Asks the player where they want to plant the flower.
        System.out.println("You have " + players[i].howManyFlowersPossible() + " place(s) to do so.");
        System.out.println("Please enter the coordinates of the flower (enter them as row *space* column):");
        r = keyboard.nextInt();
        c = keyboard.nextInt();

        //Checks to see if the inputted coordinates of the player are valid.
        while(check) {
            if (r > size || c > size || r <= 0 || c <= 0) {
                System.out.println("Invalid coordinates. The flower would be planted off the garden. Please enter valid coordinates of the flower (enter them as row column): ");
                r = keyboard.nextInt();
                c = keyboard.nextInt();
            }

            else if (players[i].whatIsPlanted(r - 1, c - 1) > '-') {//Since the displayed garden has rows and columns that start from 1 and not 0, we must subtract 1 from the coordinates entered by the user.
                System.out.println("Invalid coordinates. There is already something planted there. Please enter valid coordinates of the flower (enter them as row column): ");
                r = keyboard.nextInt();
                c = keyboard.nextInt();
            }

            //If the player's input is valid, the program exits the while loop by making the condition of the while loop equal to false.
            else
                check = false;
        }

        //Plants the flower in the player's garden at the wanted coordinates but we first subtract one from each since the rows and columns on the displayed gardens start at 1 and not 0.
        players[i].plantFlowerInGarden(r-1,c-1);
    }

    /**
     * Purpose: To plant a tree at a specific location in a player's garden.
     * @param players (array which contains the player objects)
     * @param i (represents the index of the current player in the players array)
     * @param size (size of the garden of the players)
     */
    public static void plantTree (Player[] players, int i, int size){

        int r ;//The row in which is situated the top-left coordinate of the tree that player wants to plant.
        int c ;//The column in which is situated the top-left coordinate of the tree that player wants to plant.
        boolean check = true;//Boolean which serves as a condition in a while loop and that stays true until the user enters valid input.
        Scanner keyboard = new Scanner(System.in);

        //Asks the player to enter the top-left coordinate of the tree that they want to plant in their garden.
        System.out.println("You have " + players[i].howManyTreesPossible() + " place(s) to do so.");
        System.out.println("Please enter the top-left coordinates of the tree (enter them as row *space* column):");
        r = keyboard.nextInt();
        c = keyboard.nextInt();

        //Checks to see if the inputted coordinates of the player are valid.
        while(check) {
            if (r >= size || c >= size || r <= 0 || c <= 0) { //r>=size and not only r>size because a tree is a 2X2.
                System.out.println("Invalid coordinates. The tree would be planted off the garden. Please enter valid top-left coordinates of the tree (enter them as row column):");
                r = keyboard.nextInt();
                c = keyboard.nextInt();
            }

            //As before, since the displayed gardens start at both row and column 1, we must subtract 1 from the entered coordinates of the player.
            else if (players[i].whatIsPlanted((r - 1), (c - 1)) > '-' || players[i].whatIsPlanted((r - 1), c) > '-' || players[i].whatIsPlanted(r, (c - 1)) > '-' || players[i].whatIsPlanted(r, c) > '-') {
                System.out.println("Invalid coordinates. A tree or a plant is in the way. Please enter valid top-left coordinates of the tree (enter them as row column):");
                r = keyboard.nextInt();
                c = keyboard.nextInt();
            }

            //If the coordinates of the player are valid, then we exit the while loop by making its condition be equal to false.
            else
                check = false;
        }

        //Plants the tree in the player's garden so that the upper-left coordinates of the tree are exactly as coordinates the player entered.
        //Again, we subtract 1 from the coordinates entered by the player since the displayed in-game gardens have rows and columns that start at 1 instead of 0.
        players[i].plantTreeInGarden(r-1,c-1);
    }

    /**
     * Purpose: A rabbit that lives in the garden of the player eats something planted in a random 1X1 square of the player's garden.
     * @param players (array which contains the player objects)
     * @param i (represents the index of the current player in the players array)
     * @param size (size of the garden of the players)
     */
    public static void rabbitEats (Player[] players, int i, int size){

        int r;//The row in which is located the square where the rabbit will eat the planted thing that is in that 1X1 square.
        int c ;//The column in which is located the square where the rabbit will eat the planted thing that is in that 1X1 square.
        int input;//Empty integer that takes 1 and that is used no where. Its only uses to make the player continue by them entering 1.

        Scanner read = new Scanner(System.in);

        System.out.println("The rabbit that lives in your garden wants to eat something that you planted! It can be a flower or a 1X1 part of a tree.");

        //If the garden is empty then the rabbit won't eat anything because there is nothing in the player's garden to eat.
        if(players[i].isGardenEmpty())
            System.out.println("However, the garden is empty and the rabbit has nothing to eat! He is sad and just leaves you be. Your turn is now over.");

        //If the garden is not empty, then there will be randomly generated coordinates for both the row and the column until
        //there is something planted there. As soon as the program finds something planted, the rabbit eats it and the square
        //where the rabbit ate becomes empty i.e. has the empty character '-' inside it.
        else{
            r = (int) (Math.random() *(size-1));
            c = (int) (Math.random() *(size-1));

            while(players[i].whatIsPlanted(r,c) == '-'){
                r = (int) (Math.random() *(size-1));
                c = (int) (Math.random() *(size-1));
            }

            players[i].eatHere(r,c);

            System.out.println("The rabbit ate whatever was at " + "(" + (r+1) + "," + (c+1) + "). Your turn is now over.");
        }
    }
}
