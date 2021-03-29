package ru.l3.less1;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        massRac();

    }

    public static void massRac(){
        String [] objStr = {"STR0","STR1","STR2","STR3","STR4","STR5"};
        Integer [] obj = {1,3,5,7,9};

        ArrayList list = new ArrayList();

        Mass mass= new Mass(objStr);

        mass.printMass();
        mass.roker(3, 0);
        mass.printMass();

        Mass mass2= new Mass(obj);

        list = mass2.massTOarray(obj);

        System.out.println(list.toString());

    }

}
