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
public class Game
{
    private String playerName;
    private int gameTotal;
    private Buffer b1;
    private ArrayList<Multiple> mList;

    public Game() {
        this.playerName = "";
        this.gameTotal = 0;
        this.b1 = new Buffer();
        this.b1.setMaxElements(5);
        this.mList = new ArrayList<>();
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
        b1.setMaxElements(5);
        this.readFile();
        while (!endCheck()) 
            play();
    }

    public void play() {
        generateMultipleToTotal();
        makeChoice();
    }

    public void makeChoice() {
        System.out.println(String.format("Items in buffer 1: %s", strNumbersInBuffer()));
        System.out.println(String.format("Game total: %s", this.gameTotal));
        Scanner sc = new Scanner(System.in);
        System.out.println("Press 1 to split to buffer");
        System.out.println("Press 2 to merge to total");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
            split();
            break;
            case 2:
            merge();
            break;
        }
    }

    public void split() {
        b1.getList().add(new Multiple(gameTotal));
    }

    public void merge() {
        boolean merge = false;
        for (int i = 0; i < b1.getList().size(); i++) 
            if (b1.getList().get(i).getValue() == gameTotal) {
                b1.getList().remove(i);
                gameTotal += gameTotal;
                merge = true;
            }
        if (!merge) 
            System.out.println("No match value find, not able to merge");
        makeChoice();
    }

    public void generateMultipleToTotal() {
        int index = RNG.getRandomNumber(0, mList.size() - 1);
        gameTotal = mList.get(index).getValue();
    }

    public String strNumbersInBuffer() {
        String bufferNumbers = "{";
        for (int i = 0; i < b1.getList().size(); i++) 
            bufferNumbers += (" " + b1.getList().get(i).getValue());
        bufferNumbers += " }";
        return bufferNumbers;
    }

    public boolean endCheck() {
        boolean finish = true;
        if (gameTotal < 256 && b1.getList().size() < b1.getMaxElements())
            finish = false;
        else if (gameTotal >= 256) {
            System.out.println("You win the game!");
            writeFile();
            return true;
        } else if (b1.getList().size() > b1.getMaxElements()) 
            finish = true;
        else 
            for (int i = 0; i < b1.getMaxElements(); i++) 
                if (b1.getList().get(i).getValue() == gameTotal) 
                    finish = false;
        if (finish) 
            System.out.println("You lose the game!");
        return finish;
    }

    public void readFile() {
        try {
            FileReader fileReader = new FileReader("multiples.txt");
            Scanner sc = new Scanner(fileReader);
            String[] multipleLine = sc.nextLine().split(",");
            mList.add(new Multiple(Integer.parseInt(multipleLine[0])));
            mList.add(new Multiple(Integer.parseInt(multipleLine[1])));
            mList.add(new Multiple(Integer.parseInt(multipleLine[2])));
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read drivers file!");
        }
    }

    public void writeFile() {
        try {
            PrintWriter pw = new PrintWriter("outcome.txt");
            pw.println(playerName + " " + gameTotal);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write drivers file!");
        }
    }
}
