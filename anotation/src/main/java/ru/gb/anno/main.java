package ru.gb.anno;
/***
 * не понятно только одно.
 * со статиками работает а вот как этоже сделать
 * не со статик методами в ДЕМОклассе???
 * хотелось бы увидеть пример.
 *
 */

public class main {
    public static void main(String[] args) {
        MyJU my = new MyJU();
        //DemoClass demoClass = new DemoClass();
        try {
            my.start(DemoClass.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
