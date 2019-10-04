package d;

import java.util.ArrayList;

/**
 * Write a description of class Buffer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Buffer
{
    private ArrayList<Multiple> list;
    private int maxElements;

    public Buffer() {
        this.list = new ArrayList<>();
        this.maxElements = 0;
    }

    public Buffer(ArrayList<Multiple> list, int maxElements) {
        this.list = list;
        this.maxElements = maxElements;
    }

    public ArrayList<Multiple> getList() {
        return list;
    }

    public void setList(ArrayList<Multiple> list) {
        this.list = list;
    }

    public int getMaxElements() {
        return maxElements;
    }

    public void setMaxElements(int maxElements) {
        this.maxElements = maxElements;
    }
}
