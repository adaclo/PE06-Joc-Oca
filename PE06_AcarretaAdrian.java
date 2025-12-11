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
        int[] gooses = {5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63};
        int[] bridges = {6, 12};
        int fonda = 19;
        int labyrinth = 42;
        int jail = 52;
        int death = 58;
        int gooseGarden = 63;
        setDefaultValues(positions, penalties);
        chooseNames(s, players);
        newGame(players, positions, penalties);
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

    public void setDefaultValues(int[] positions, int[] penalties) {
        for (int i=0;i<positions.length;i++) {
            positions[i]=0;
            penalties[i]=0;
        }
    }

    public void newGame(String[] players, int[] positions, int[] penalties) {
        int dices=2;
        int turns=0;
        Boolean end=false;

        while (!end) {
            for (int i=0;i<players.length;i++) {

                turns++;
            }
        }
    }

    public void newTurn(String[] players, int[] positions, int[] penalties, int turns, int dices) {
        int dice1=throwDices();

        if (turns==0) {

        }
    }

    public int throwDices() {
        int number=(int)(Math.random()*6)+1;
        return number;
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
