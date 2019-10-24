package d;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Write a description of class FileIO here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FileIO {

    public static int[] readFile() {
        int[] mList = new int[3];
        try {
            FileReader fileReader = new FileReader("multiples.txt");
            Scanner sc = new Scanner(fileReader);
            String[] multipleLine = sc.nextLine().split(",");
            for (int i = 0; i < multipleLine.length; i++) 
                mList[i] = Integer.parseInt(multipleLine[i]);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read drivers file!");
        }
        return mList;
    }

    public static void writeFile(String playerName, int maxScore) {
        try {
            PrintWriter pw = new PrintWriter("outcome.txt");
            pw.println(playerName + " got the highest score " + maxScore);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write drivers file!");
        }
    }
}
