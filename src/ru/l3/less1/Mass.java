package ru.l3.less1;

import java.util.ArrayList;
import java.util.Iterator;

public class Mass<T> {

    public T[] getObj() {
        return obj;
    }

    private T[] obj;

    public Mass(T... obj) {
        this.obj = obj;
    }

    public void roker (Integer A, Integer B){
        T temp = this.obj[A];
        this.obj[A] = this.obj[B];
        this.obj[B] = temp;
    }

public void printMass (){
    System.out.println("MASS=");
    for (int i = 0; i < this.obj.length; i++) System.out.print(this.obj[i] + " ");
    System.out.println("");
}


public ArrayList massTOarray (T... obj){
    ArrayList list = new ArrayList();
    for (int i = 0; i < obj.length; i++) list.add(obj[i]);
    return list;
}

public void arrayPrint(ArrayList lists){
    Iterator<T> list = lists.iterator();
    System.out.println("ARRAY=");
    while (list.hasNext()) {
        System.out.print(list.next() + " ");
    }
    System.out.println("");
}


}
