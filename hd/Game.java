package hd;

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
    private ArrayList<Buffer> multipleList;
    private ArrayList<Multiple> mList;

    public Game() {
        this.playerName = "";
        this.gameTotal = 0;
        this.multipleList = new ArrayList<>();
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
        initializeMultipleList();
        this.readFile();
        while (!endCheck()) 
            play();
    }

    public void initializeMultipleList() {
        Buffer b1 = new Buffer(new ArrayList<Multiple>(), 5);
        Buffer b2 = new Buffer(new ArrayList<Multiple>(), 3);
        multipleList.add(b1);
        multipleList.add(b2);
    }

    public void play() {
        generateMultipleToTotal();
        makeChoice();
    }

    public void generateMultipleToTotal() {
        int index = RNG.getRandomNumber(0, mList.size() - 1);
        gameTotal = mList.get(index).getValue();
    }

    public void makeChoice() {
        displayNumbersInBuffer();
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

    public void displayNumbersInBuffer() {
        String buffer1Numbers = "{";
        for (int i = 0; i < this.multipleList.get(0).getList().size(); i++) 
            buffer1Numbers += (" " + this.multipleList.get(0).getList().get(i).getValue());
        buffer1Numbers += " }";
        String buffer2Numbers = "{";
        for (int i = 0; i < this.multipleList.get(1).getList().size(); i++) 
            buffer2Numbers += (" " + this.multipleList.get(1).getList().get(i).getValue());
        buffer2Numbers += " }";
        System.out.println(String.format("Items in buffer 1: %s", buffer1Numbers));
        System.out.println(String.format("Items in buffer 2: %s", buffer2Numbers));
    }

    public void split() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to split to buffer 1");
        System.out.println("Enter 2 to split to buffer 2");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
            this.multipleList.get(0).getList().add(new Multiple(this.gameTotal));
            break;
            case 2:
            this.multipleList.get(1).getList().add(new Multiple(this.gameTotal));
            break;
            default:
            System.out.println("Enter 1 or 2");
            break;
        }
    }

    public void merge() {
        System.out.println("Press 1 to merge to buffer 1");
        System.out.println("Press 2 to merge to buffer 2");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
            mergeB1();
            break;
            case 2:
            mergeB2();
            break;
            default:
            System.out.println("Please enter a valid number");
        }
        makeChoice();
    }

    public void mergeB1() {
        boolean merge = false;
        for (int i = 0; i < this.multipleList.get(0).getList().size(); i++) 
            if (this.multipleList.get(0).getList().get(i).getValue() == gameTotal) {
                this.multipleList.get(0).getList().remove(i);
                gameTotal += gameTotal;
                merge = true;
            }
        if (!merge) 
            System.out.println("No match value find, not able to merge");
    }

    public void mergeB2() {
        boolean merge = false;
        for (int i = 0; i < this.multipleList.get(1).getList().size(); i++) 
            if (this.multipleList.get(1).getList().get(i).getValue() == gameTotal) {
                this.multipleList.get(1).getList().remove(i);
                gameTotal += gameTotal;
                merge = true;
            }
        if (!merge) 
            System.out.println("No match value find, not able to merge");
    }

    public boolean endCheck() {
        if (gameTotal < 256
        && this.multipleList.get(0).getList().size() < this.multipleList.get(0).getMaxElements()
        && this.multipleList.get(1).getList().size() < this.multipleList.get(1).getMaxElements()) 
            return false;
        else if (gameTotal >= 256) {
            System.out.println("You win the game!");
            writeFile();
            return true;
        } else if (this.multipleList.get(0).getList().size() > this.multipleList.get(0).getMaxElements()
        || this.multipleList.get(1).getList().size() > this.multipleList.get(1).getMaxElements()) {
            System.out.println("You lose the game!");
            return true;
        } else if (this.multipleList.get(0).getList().size() < this.multipleList.get(0).getMaxElements()
        || this.multipleList.get(1).getList().size() < this.multipleList.get(1).getMaxElements()) 
            return false;
        else {
            for (int i = 0; i < this.multipleList.get(0).getMaxElements(); i++) 
                if (this.multipleList.get(0).getList().get(i).getValue() == gameTotal) 
                    return false;
            for (int i = 0; i < this.multipleList.get(1).getMaxElements(); i++) 
                if (this.multipleList.get(1).getList().get(i).getValue() == gameTotal) 
                    return false;
            return true;
        }
    }

    public void readFile() {
        mList.add(new Multiple(2));
        mList.add(new Multiple(4));
        mList.add(new Multiple(8));
        try {
            FileReader fileReader = new FileReader("multiples-hd.txt");
            ArrayList<ArrayList> readList = new ArrayList<>();
            Scanner sc = new Scanner(fileReader);
            System.out.println("Please choose number set from following:");
            int lineNum = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(lineNum + ". "+ line);
                lineNum ++;
                String[] multipleLine = line.split(",");
                ArrayList<Multiple> mLine = new ArrayList<>();
                mLine.add(new Multiple(Integer.parseInt(multipleLine[0])));
                mLine.add(new Multiple(Integer.parseInt(multipleLine[1])));
                mLine.add(new Multiple(Integer.parseInt(multipleLine[2])));
                readList.add(mLine);
            }
            sc.close();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            mList = readList.get(choice - 1);
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
