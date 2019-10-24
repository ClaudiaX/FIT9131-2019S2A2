package d;

import java.util.ArrayList;

/**
 * Write a description of class Buffer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Buffer {

    private ArrayList<Multiple> list;
    private int maxElements;

    public Buffer() {
        this.list = new ArrayList<>();
        this.maxElements = 5;
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

    public void split(int value) {
        this.list.add(new Multiple(value));
    }

    public boolean merge(int value) {
        boolean merge = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == value) {
                merge = true;
                list.remove(i);
                break;
            }
        }
        return merge;
    }

    public String displayBuffer() {
        String bufferNumbers = "{";
        for (int i = 0; i < list.size(); i++) 
            bufferNumbers += (" " + list.get(i).getValue());
        bufferNumbers += " }";
        return bufferNumbers;
    }

    public int getMaxInBuffer() {
        int max = 0;
        for (int i = 0; i < list.size(); i++) 
            if (max < list.get(i).getValue()) 
                max = list.get(i).getValue();
        return max;
    }
}
