package ru.l3.less1.fruit;

public class Box {

    private String boxFor = "";
    private float weigth;
    private int fruitCount;

    public void setBoxFor(String boxFor) {
        this.boxFor = boxFor;
    }

    public void setWeigth(float weigth) {
        this.weigth = weigth;
    }

    public void setFruitCount(int fruitCount) {
        this.fruitCount = fruitCount;
    }

    public String getBoxFor() {
        return boxFor;
    }

    public boolean add(Fruit boxForFruit){

        //make box for individual fruits
        if (this.boxFor.equals("")) {
            this.boxFor = (String) boxForFruit.getName();
            this.weigth = (float) boxForFruit.getWeigth();
            this.fruitCount = 0;
        }
        //if box used and fruit compatible then add fruit
        else if (this.boxFor.equals(boxForFruit.getName())) {
            this.fruitCount++;
        } else return false;
        return true;
    }

    public float getWeight(){
        return this.weigth * this.fruitCount;
    }

    public boolean compare (Box box){
        return (Math.abs(this.getWeight() - box.getWeight()) < 0.0001) ? true : false;
    }

    public boolean move (Box box){
        if (box.getBoxFor().equals("")) {
            box.setBoxFor(this.boxFor);
            this.boxFor = "";
            box.setWeigth(this.weigth);
            this.weigth = 0;
            box.setFruitCount(this.fruitCount);
            this.fruitCount = 0;
            return true;
        }
        return false;
    }
}
