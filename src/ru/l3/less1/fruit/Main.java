package ru.l3.less1.fruit;

public class Main {

    public static void main(String[] args) {
        // write your code here
        sklad();



    }

    public static void sklad(){
        //create fruits
        Fruit apple = new Fruit("Apple", 1.0f);
        Fruit orange = new Fruit("Orange", 1.5f);

        //create box
        Box box1 = new Box();
        Box box2 = new Box();
        Box box3 = new Box();

        //add fruit to box
        if (box1.add(apple)) System.out.println("OK");;
        if (box1.add(apple)) System.out.println("OK");;
        if (box1.add(apple)) System.out.println("OK");;
        if (box1.add(apple)) System.out.println("OK");;
        if (box1.add(orange)) System.out.println("OK");;
        System.out.println("BOX1=" + box1.getWeight());

        if (box2.add(orange)) System.out.println("OK");;
        if (box2.add(orange)) System.out.println("OK");;
        if (box2.add(orange)) System.out.println("OK");;
        if (box2.add(orange)) System.out.println("OK");;
        if (box2.add(orange)) System.out.println("OK");;
        if (box2.add(orange)) System.out.println("OK");;
        if (box2.add(orange)) System.out.println("OK");;
        if (box2.add(orange)) System.out.println("OK");;
        if (box2.add(orange)) System.out.println("OK");;
        System.out.println("BOX2=" + box2.getWeight());

        System.out.println("Compare:BOX1-BOX2:" + box1.compare(box2) );

        System.out.println("MOVE BOX1->BOX2:" + box1.move(box2));
        System.out.println("MOVE BOX1->BOX3:" + box1.move(box3));
        System.out.println("MOVE BOX2->BOX1:" + box2.move(box1));


    }


}
