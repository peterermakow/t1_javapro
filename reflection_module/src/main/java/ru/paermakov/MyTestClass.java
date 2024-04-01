package ru.paermakov;


import ru.paermakov.annotations.AfterSuite;
import ru.paermakov.annotations.AfterTest;
import ru.paermakov.annotations.BeforeSuite;
import ru.paermakov.annotations.BeforeTest;
import ru.paermakov.annotations.Test;

public class MyTestClass {

    @BeforeSuite
    public static void runBeforeSuite() {
        System.out.print("runBeforeSuite\n");
    }

    @AfterSuite
    public static void runAfterSuite() {
        System.out.print("runAfterSuite");
    }

    @Test(priority = 10)
    public void runAnnotatedMethodPriority10() {
        System.out.print("runTestAnnotatedMethod priority 10\n");
    }

    @Test(priority = 4)
    private void runAnnotatedMethodPriority4() {
        System.out.print("runTestAnnotatedMethod priority 4\n");
    }

    @Test()
    public void runAnnotatedMethodDefaultPriority() {
        System.out.print("runTestAnnotatedMethod default priority\n");
    }

    private void someNotAnnotatedMethod() {
        System.out.print("runNotAnnotatedMethod\n");
    }

    @BeforeTest
    public void runBeforeTest() {
        System.out.print("runBeforeTest\n");
    }

    @AfterTest
    public void runAfterTest() {
        System.out.print("runAfterTest\n");
    }
}
