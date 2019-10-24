package d;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Game {

    private String playerName;
    private int gameTotal;
    private Buffer b1;
    private int[] mList;

    public Game() {
        this.playerName = "";
        this.gameTotal = 0;
        this.b1 = new Buffer();
        this.mList = new int[3];
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        while (playerName.length() == 0) {
            System.out.println("Please enter your name: ");
            playerName = sc.nextLine().trim();
        }
        this.mList = FileIO.readFile();
        while (!endCheck())
            play();
    }

    public void play() {
        generateMultipleToTotal();
        makeChoice();
    }

    public void makeChoice() {
        System.out.println(String.format("Items in buffer 1: %s", b1.displayBuffer()));
        System.out.println(String.format("Game total: %s", this.gameTotal));
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Press 1 to split to buffer");
            System.out.println("Press 2 to merge to total");
            System.out.println("Please enter your choice:");
            String input = sc.nextLine();
            choice = validateNumInput(input);
        } while (choice != 1 && choice != 2);
        switch (choice) {
            case 1:
            b1.split(gameTotal);
            break;
            case 2:
            merge();
            break;
        }
    }

    public void merge() {
        boolean merge = b1.merge(gameTotal);
        if (!merge) 
            System.out.println("No match value found, can't merge");
        else 
            gameTotal += gameTotal;
        makeChoice();
    }

    public void generateMultipleToTotal() {
        int index = RNG.getRandomNumber(0, mList.length - 1);
        gameTotal = mList[index];
    }

    public boolean endCheck() {
        boolean finish = true;
        int max = 0;
        if (gameTotal < 256 && b1.getList().size() < b1.getMaxElements()) {
            finish = false;
        } else if (gameTotal >= 256) {
            System.out.println("You win the game!");
            max = gameTotal;
            finish = true;
        } else if (b1.getList().size() > b1.getMaxElements()) {
            finish = true;
            max = b1.getMaxInBuffer();
            System.out.println(String.format("you lose the game. Buffer can only contain %s elements.", b1.getMaxElements()));
        } else {
            for (int i = 0; i < b1.getMaxElements(); i++) 
                if (b1.getList().get(i).getValue() == gameTotal) {
                    finish = false;
                    break;
                }
            if (finish == true) {
                System.out.println("You lose the game. Nothing can be merged");
                max = b1.getMaxInBuffer();
            }
        }
        if (finish) 
            FileIO.writeFile(playerName, max);
        return finish;
    }

    public int validateNumInput(String input) {
        int returnNum = 0;
        try {
            returnNum = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            returnNum = -1;
        }
        return returnNum;
    }
}
