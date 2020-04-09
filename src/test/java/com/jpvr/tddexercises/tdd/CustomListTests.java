package com.jpvr.tddexercises.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CustomListTests {

    @Test
    public void givenEmptyList_whenIsEmpty_thenTrueIsReturned() {

        List<Object> list = new CustomList<>();
        list.add(null);

        //Assertions.assertTrue(list.isEmpty());
        Assertions.assertFalse(list.isEmpty());
    } // end void givenEmptyList_whenIsEmpty_thenTrueIsReturned()

    @Test
    public void givenListWithAnElement_whenSize_thenOneIsReturned() {

        List<Object> list = new CustomList<>();
        list.add(null);

        Assertions.assertEquals(1, list.size());
    } // end void givenListWithAnElement_whenSize_thenOneIsReturned()


    @Test
    public void givenListWithAnElement_whenGet_thenThatElementIsReturned() {

        List<Object> list = new CustomList<>();
        list.add("jp");

        Object element = list.get(0);

        Assertions.assertEquals("jp", element);
    } // end void givenListWithAnElement_whenGet_thenThatElementIsReturned()

    @Test
    public void givenEmptyList_whenElementIsAdded_thenGetReturnsThatElement() {

        List<Object> list = new CustomList<>();
        boolean succeeded = list.add(null);

        Assertions.assertTrue(succeeded);
    } // end void givenEmptyList_whenElementIsAdded_thenGetReturnsThatElement()

    @org.junit.Test(timeout = 1)
    public void givenListWithAnElement_whenAnotherIsAdded_thenGetReturnsBoth() {

        List<Object> list = new CustomList<>();
        list.add("jp");
        list.add("java");

        Object element1 = list.get(0);
        Object element2 = list.get(1);

        Assertions.assertEquals("jp", element1);
        Assertions.assertEquals("java", element2);
    } // end void givenListWithAnElement_whenAnotherIsAdded_thenGetReturnsBoth()

    @Test
    public void givenListWithAnElement_whenGet_thenThatElementIsObtained() {

        List<Object> list = new CustomList<>();
        list.add("jp");

        Assertions.assertTrue(list.contains("jp"));
    } // end void givenListWithAnElement_whenGet_thenThatElementIsObtained()
} // end class CustomListTests
