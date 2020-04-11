package com.jpvr.tddexercises.tdd.hamcrest;

import com.jpvr.tddexercises.tdd.hamcrest.Fellowship;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

/**
 * Based on: https://www.vogella.com/tutorials/Hamcrest/article.html
 */
public class HamcrestListMatcherTests {

    private List<Integer> list = null;
    private Integer[] ints = null;

    @Before
    public void setUpBefore() {

        list = Arrays.asList(5, 2, 4);

        ints = new Integer[] {7, 5, 12, 16};
    } // end void setUpBefore()

    @Test
    public void listShouldInitiallyBeEmpty() {

        assertThat(list, hasSize(3));

        // ensure the order is correct
        assertThat(list, contains(5, 2, 4));

        assertThat(list, containsInAnyOrder(2, 4, 5));

        assertThat(list, everyItem(greaterThan(1)));
    } // end void listShouldInitiallyBeEmpty()

    @Test
    public void hasSizeOfThree() {

        assertThat(list, hasSize(3));
    } // end void hasSizeOfThree()

    @Test
    public void containsNumbersInAnyOrder() {

        assertThat(list, containsInAnyOrder(2, 4, 5));
    } // end void containsNumbersInAnyOrder()

    @Test
    public void everyItemGreaterThanOne() {

        assertThat(list, everyItem(greaterThan(1)));
    } // end void everyItemGreaterThanOne()

    @Test
    public void arrayHasSizeOfFour() {

        assertThat(ints, arrayWithSize(4));
    } // end void arrayHasSizeOfFour()

    @Test
    public void arrayContainsNumbersInGivenOrder() {

        assertThat(ints, arrayContaining(7, 5, 12, 16));
    } // end void arrayContainsNumbersInGivenOrder()


    @Ignore
    public void everyListItemShouldHaveProperty() {

        Fellowship fellowship1 = new Fellowship();
        Fellowship fellowship2 = new Fellowship();
        fellowship1.setFellowshipRace(Fellowship.race.HUMAN);
        fellowship2.setFellowshipRace(Fellowship.race.ELF);

        List<Fellowship> list = Arrays.asList(fellowship1, fellowship2);

        //assertThat(list, contains(fellowship1));
        //assertThat(list, everyItem(hasProperty("race", is(not(Fellowship.race.ORC)))));
    } // end everyListItemShouldHaveProperty()
} // fin class HamcrestListMatcherTests
