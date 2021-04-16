package ru.gb.anno;

public class DemoClass {


    @BeforeSuite
    public static void metodBS(){
        System.out.println("Start");
    }

//    @BeforeSuite
//    public static void metodBS2(){
//        System.out.println("Start");
//    }


    @Test(priority = 2)
    public static void metod1(){
        System.out.println("prior 2");
    }

    @Test
    public static void metod2(){
        System.out.println("prior def 1");
    }

    @Test(priority = 6)
    public static void metod3(){
        System.out.println("prior 6");
    }

    @Test(priority = 9)
    public static void metod4(){
        System.out.println("prior 9");
    }

    @AfterSuite
    public static void metodAS(){
        System.out.println("stop");
    }

//    @AfterSuite
//    public static void metodAS2(){
//        System.out.println("stop");
//    }

}
