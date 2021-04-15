package ru.gb.junits;

import java.util.LinkedList;


public class Main {
    public static void main(String[] args) {
        Integer [] m = {1,5,8,22,45};
        Junits ms = new Junits();
        Integer[] res = ms.massSearch(m);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]+" ");
        }
        System.out.println();

     }


}
