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
        int turns=0;
        Boolean end=false;

        while (!end) {
            for (int i=0;i<players.length;i++) {
                newTurn(players, positions, penalties, turnPlayer, turns, dices, s, i);
            }
        }
    }

    public void newTurn(String[] players, int[] positions, int[] penalties, int[] turnPlayer, int turns, int dices, Scanner s, int player) {
        System.out.printf("\nIt's the turn of player %d, %s",(player+1),players[player]);
        String r = "";
        int dice1=0,dice2=0;
        Boolean newTurn=false;
        int[] gooses = {5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63};
        int[] bridges = {6, 12};
        int fonda = 19;
        int well = 31;
        int labyrinth = 42;
        int jail = 52;
        int death = 58;
        int gooseGarden = 63;
        int dicesResult=0;
        Boolean penalty = checkPenalties(penalties, player);

        if (!penalty) {
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
            int nextPosition = positions[player]+dicesResult;
            newTurn = checkSpecialDices(positions, player, turnPlayer, dice1, dice2);
            if (nextPosition==death){
                positions[player]=0;
                System.out.println(RED+"\nYou fell into death box, you will be redirected to position 0.");
            }
        }
        turnPlayer[player]++;
        turns++;
        if(newTurn) {
            newTurn(players, positions, penalties, turnPlayer, turns, dicesResult, s, player);
        }
    }

    public boolean checkSpecialDices(int[] positions, int player, int[] turnPlayer, int dice1, int dice2) {
        Boolean newTurn=false;
        if (turnPlayer[player]==0) {
            if((dice1==3&&dice2==6)||(dice1==6&&dice2==3)) {
                positions[player]=26;
                System.out.println("\nFrom dice to dice, and I throw because it's my turn");
                newTurn=true;
            } else if((dice1==4&&dice2==5)||(dice1==5&&dice2==4)) {
                positions[player]=53;
                System.out.println("\nFrom dice to dice, and I throw because it's my turn");
                newTurn=true;
            }
        }
        return newTurn;
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
            System.out.printf(YELLOW+"\n(?) Please enter player %s name: %s",(i+1),RESET);
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
