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
} // end class CustomListTests
