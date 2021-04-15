package ru.gb.junits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Jtest {
    private Junits ms;

    @BeforeEach
    public void init(){
        ms = new Junits();
    }

    @Test
    public void testMass1(){
        Integer[] i ={5,4,7,4,8,9,2};
        Integer[] j = {7,4,8,9,2};
        Assertions.assertArrayEquals(j,ms.massSearch(i));
    }

    @Test
    public void testMass2(){
        Integer[] i ={5,4,7,8,9,2};
        Integer[] j = {7,8,9,2};
        Assertions.assertArrayEquals(j,ms.massSearch(i));
    }

    @Test
    public void testMass3(){
        Integer[] i ={5,7,8,9,2};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {ms.massSearch(i);});
    }

    @Test
    public void testBoolean1(){
        Integer[] i ={5,7,4,9,2};
        Assertions.assertEquals(true, ms.massSearch2(i));
    }

    @Test
    public void testBoolean2(){
        Integer[] i ={5,7,1,9,2};
        Assertions.assertEquals(true, ms.massSearch2(i));
    }

    @Test
    public void testBoolean3(){
        Integer[] i ={5,7,0,9,2};
        Assertions.assertEquals(false, ms.massSearch2(i));
    }

}
