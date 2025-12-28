package Activitats.PE06;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PE06_AcarretaAdrian {
    // COLORS
    final String RESET = "\u001B[0m";
    final String RED = "\u001B[31m";
    final String GREEN = "\u001B[32m";
    final String YELLOW = "\u001B[33m";
    final String BLUE = "\u001B[34m";
    public static void main(String[] args) {
        PE06_AcarretaAdrian p = new PE06_AcarretaAdrian();
        p.principal();
    }
    public void principal() {
        Scanner s = new Scanner(System.in);
        int n=chooseNumPlayers(s);
        String[] players = new String[n];
        int[] positions = new int[n];
        int[] penalties = new int[n];
        int[] turnPlayer = new int[n];
        setDefaultValues(positions, penalties, turnPlayer);
        chooseNames(s, players);
        newGame(players, positions, penalties, turnPlayer, s);
    } 

    public int chooseNumPlayers(Scanner s) {
        Boolean validNum=false;
        int number=0;
        while (!validNum) {
            System.out.print(YELLOW+"\n(?) Please enter the number of players: "+RESET);
            number=readInt(s);
            if (number>4||number<2) {
                System.out.println(RED+"(!) Please enter a number between 2 and 4."+RESET);
            } else {
                validNum=true;
            }
        }
        return number;
    }

    public void setDefaultValues(int[] positions, int[] penalties, int[] turnPlayer) {
        for (int i=0;i<positions.length;i++) {
            positions[i]=0;
            penalties[i]=0;
            turnPlayer[i]=0;
        }
    }

    public void newGame(String[] players, int[] positions, int[] penalties, int[] turnPlayer, Scanner s) {
        int dices=2;
        Boolean end=false;
        Boolean playAgain=false;

        while (!end) {
            for (int i=0;i<players.length;i++) {
                if (!end) {
                    playAgain = newTurn(players, positions, penalties, turnPlayer, dices, s, i, playAgain);
                    while (playAgain) {
                        playAgain = newTurn(players, positions, penalties, turnPlayer, dices, s, i, playAgain);
                    }
                    end = checkLastPosition(positions, i);
                } 
                if (end) {
                    int turns=0;
                    for (int j=0;j<positions.length;j++) {
                        turns+=turnPlayer[j];
                    }
                    System.out.println(YELLOW+"\n"+players[i]+" has won the game!"+RESET);
                    System.out.println("\nThe game ended with a total of "+YELLOW+turns+RESET+" turns.");
                    for (int player=0;player<players.length;player++) {
                        System.out.printf("\n%s: %s%d%s turns",players[player],YELLOW,turnPlayer[player],RESET);
                    }
                    
                }
            }
        }
    }

    public Boolean newTurn(String[] players, int[] positions, int[] penalties, int[] turnPlayer, int dices, Scanner s, int player, Boolean playAgain) {
        System.out.printf("\nIt's the turn of player %d, %s",(player+1),players[player]);
        String r = "";
        int dice1=0,dice2=0;
        int dicesResult=0;
        Boolean penalty = checkPenalties(penalties, player);

        if (!penalty) {
            if (positions[player]>=60) { // Comprobar si sobrepasa la casilla 60 para usar 1 dado
                dices=1;
            }
            while(!r.equalsIgnoreCase("tiro")){
                System.out.print("\n>> ");
                r = s.next();
                if (!r.equalsIgnoreCase("tiro")) {
                    System.out.println("\nPlease enter 'tiro' to throw dices");
                } else {
                    dices = checkDices(positions, player);
                    if (dices==2) {
                        dice1 = throwDices();
                        dice2 = throwDices();
                    } else {
                        dice1 = throwDices();
                        dice2 = 0;
                    }
                }
            }
            if(dices==2){
                System.out.printf("\nYou got a %d and a %d = %d",dice1,dice2,(dice1+dice2));
                dicesResult=dice1+dice2;
            } else {
                System.out.printf("\nYou got a %d",dice1);
                dicesResult=dice1;
            }
            playAgain=false;
            int oldPosition=positions[player];
            if (positions[player]+dicesResult<63) {
                positions[player]+=dicesResult;
            } else { // Si se sobrepasa de 63 se le resta el sobrante
                int toRest = dicesResult-(63-positions[player]);
                positions[player]=63-toRest;
            }
            playAgain = checkSpecialDices(positions, player, turnPlayer, dice1, dice2, playAgain);
            checkDeath(positions, player);
            playAgain = checkGooses(positions, player, playAgain);
            playAgain = checkBridges(positions, player, playAgain);
            checkHostel(positions, player, penalties);
            checkWell(positions, player, penalties, players);
            checkLabyrinth(positions, player);
            checkJail(positions, player, penalties);

            if (oldPosition<positions[player]) {
                System.out.println(YELLOW+"\nAdvance to position "+positions[player]+RESET);
            } else {
                System.out.println(YELLOW+"\nYou go back to position "+positions[player]+RESET);
            }
        }
        turnPlayer[player]++;
        return playAgain;
    }

    public boolean checkLastPosition(int[] positions, int player) {
        boolean end = false;
        if (positions[player]==63) {
            end=true;
            System.out.println(GREEN+"You have reached the last position!"+RESET);
        }
        return end;
    }

    public void checkJail(int[] positions, int player, int[] penalties) {
        int jail = 52;
        if (positions[player]==jail) {
            penalties[player]=3;
            System.out.println(RED+"\nYou have been sent to jail and now you cannot move for 3 turns."+RESET);
        }
    }

    public void checkLabyrinth(int[] positions, int player) {
        int labyrinth = 42;
        if (positions[player]==labyrinth) {
            positions[player]=39;
            System.out.println(RED+"\nYou've gotten lost in the labyrinth."+RESET);
        }
    }

    public void checkWell(int[] positions, int player, int[] penalties, String[] players) {
        int well = 31;
        if (positions[player]==well) {
            for (int otherPlayer=0;otherPlayer<positions.length;otherPlayer++) {
                if (positions[otherPlayer]==well) {
                    if (otherPlayer!=player) {
                        penalties[otherPlayer]=0;
                        System.out.printf("\n%sThere was already someone in the well, and now %s will go on the next turn.%s",YELLOW,players[otherPlayer],RESET);
                    }
                }
            }
            penalties[player]=2;
            System.out.println(RED+"\nYou have fallen into the well and now you cannot throw for 2 turns."+RESET);
        }
    }

    public void checkHostel(int[] positions, int player, int[] penalties) {
        int hostel = 19;
        if (positions[player]==hostel) {
            System.out.println(RED+"\nYou stay one night at the inn, so you miss a turn."+RESET);
            penalties[player]=1;
        }
    }

    public boolean checkBridges(int[] positions, int player, boolean playAgain) {
        int[] bridges = {6, 12};
        Boolean alreadyMoved=false;
        for (int i=0; i<bridges.length; i++) {
            if (bridges[i]==positions[player]) {
                if (!alreadyMoved) {
                    System.out.printf("\n%sPosition #%d: From bridge to bridge and shoot because the current carries me away%s",GREEN,positions[player],RESET);
                    if (i==bridges.length-1) {
                        positions[player]=bridges[i-1];
                    } else {
                        positions[player]=bridges[i+1];
                    }
                    playAgain=true;
                    alreadyMoved=true;
                }
            }
        }

        return playAgain;
    }

    public boolean checkGooses(int[] positions, int player, boolean playAgain) {
        int[] gooses = {5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63};
        Boolean alreadyMoved=false;
        for (int i=0; i<gooses.length; i++) {
            if (gooses[i]==positions[player]) {
                if (positions[player]!=63) {
                    if (!alreadyMoved) {
                        System.out.printf("\n%sPosition #%d: Goose. From goose to goose and on I go because it's my turn.%s",GREEN,positions[player],RESET);
                        positions[player]=gooses[i+1];
                        playAgain=true;
                        alreadyMoved=true;
                    }
                }
            }
        }
        return playAgain;
    }

    public void checkDeath(int[] positions, int player) {
        int death = 58;
        if (positions[player]==death) {
            positions[player]=0;
            System.out.println(RED+"\nYou fell into death position, you will be redirected to position 0.");
        }
    }

    public boolean checkSpecialDices(int[] positions, int player, int[] turnPlayer, int dice1, int dice2, boolean playAgain) {
        if (turnPlayer[player]==0) {
            if((dice1==3&&dice2==6)||(dice1==6&&dice2==3)) {
                positions[player]=26;
                System.out.println("\nFrom dice to dice, and I throw because it's my turn");
                playAgain=true;
            } else if((dice1==4&&dice2==5)||(dice1==5&&dice2==4)) {
                positions[player]=53;
                System.out.println("\nFrom dice to dice, and I throw because it's my turn");
                playAgain=true;
            }
        }
        return playAgain;
    }

    public int throwDices() {
        int number=(int)(Math.random()*6)+1;
        return number;
    }

    public boolean checkPenalties(int[] penalties, int player) {
        Boolean penalty = false;
        if (penalties[player]!=0) {
            penalty=true;
            System.out.println(RED+"\nYou have a penalty of "+penalties[player]+" turns without playing"+RESET);
            penalties[player]-=1;
            if(penalties[player]==0) {
                System.out.println(GREEN+"Next turn you can continue playing!"+RESET);
            } else {
                System.out.println(YELLOW+"Wait "+penalties[player]+" more turns to continue playing..."+RESET);
            }
        }
        return penalty;
    }

    public int checkDices(int[] positions, int player) {
        int dices=2;
        if (positions[player]>=60) {
            dices=1;
        }
        return dices;
    }

    //public void chooseOrder(int[] players,int n) {
    //    (int)(Math.random()*n)+1;
    //}

    public void chooseNames(Scanner s, String[] players) {
        for (int i=0;i<players.length;i++) {
            System.out.printf("\n%s(?) Please enter player %s name: %s",YELLOW,(i+1),RESET);
            players[i] = s.nextLine();
        }
    }

    public int readInt(Scanner s) {
        int number=0;
        Boolean validNum=false;
        while (!validNum) {
            try {
                number=s.nextInt();
                validNum=true;
            } catch (InputMismatchException e) {
                System.out.println(RED+"(!) Please enter a valid integer number."+RESET);
                continue;
            } catch (Exception e) {
                System.out.println(RED+"(!) Unknown error: "+e+RESET);
                continue;
            } finally {
                s.nextLine();
            }
        }
        return number;
    }
}
