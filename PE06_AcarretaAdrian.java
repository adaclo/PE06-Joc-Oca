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
        System.out.println(n);
        int[] players = new int[n];
        int[] positions = new int[n];
        int[] penalties = new int[n];
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

    public int readInt(Scanner s) {
        int number=0;
        Boolean validNum=false;
        while (!validNum) {
            try {
                number=s.nextInt();
                validNum=true;
            } catch (InputMismatchException e) {
                System.out.println(RED+"(!) Please enter a valid integer number."+RESET);
                s.nextLine();
                continue;
            } catch (Exception e) {
                System.out.println(RED+"(!) Unknown error: "+e+RESET);
                s.nextLine();
                continue;
            }
        }
        return number;
    }
}
