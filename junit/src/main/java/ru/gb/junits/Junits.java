package ru.gb.junits;

import java.util.LinkedList;

public class Junits {

    public Integer[] massSearch(Integer[] mass){
        LinkedList<Integer> t = new LinkedList<>();
        boolean four = false;
        for (int i = 0; i < mass.length; i++) {
            if (four == true) t.add(mass[i]);
            if (mass[i] == 4) four = true;
        }
        if (four == false) {
            throw new IllegalArgumentException("not int 4");
        }
        return t.toArray(new Integer[0]);
    }

    public boolean massSearch2(Integer[] mass){
        for (int i = 0; i < mass.length; i++) {
            if (mass[i] == 4 || mass[i] == 1) return true;
        }
        return false;
    }
}
