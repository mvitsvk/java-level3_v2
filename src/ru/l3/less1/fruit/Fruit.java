package ru.l3.less1.fruit;

public class Fruit<Q, W> {
    private Q name;
    private W weigth;

    public Fruit(Q name, W weigth) {
        name = name;
        this.weigth = weigth;
    }

    public Q getName() {
        return name;
    }

    public W getWeigth() {
        return weigth;
    }

}
