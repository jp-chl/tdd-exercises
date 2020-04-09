package com.jpvr.tddexercises.tdd;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.jpvr.tddexercises.tdd.hamcrest.matchers.MatcherCombinator.matches;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

//import static com.jpvr.tddexercises.tdd.hamcrest.matchers.MyMatchers.*;

public class HamcrestCombiningMatchersTests {

    /**
     * Expected:
     *
     * java.lang.AssertionError:
     * Expected: (a collection with size <1> and iterable containing [<42>])
     *      but: a collection with size <1> collection size was <0>
     */
    @Test
    public void methodWithHardToReadError() {

        List<Integer> list = new ArrayList<>();
        //list.add(1);
        //list.add(42);

        assertThat(list, both(hasSize(1)).and(contains(42)));

    } // end void methodWithHardToReadError()

    /**
     * Expected:
     *
     * java.lang.AssertionError:
     * Expected:
     * <a collection with size <1>> and
     * <iterable containing [<42>]>
     *      but:
     * Expected: <a collection with size <1> but collection size was <0>>
     * Expected: <iterable containing [<42>] but no item was <42>
     */
    @Test
    public void customMatcherWithReadableError() {

        List<Integer> list = new ArrayList<>();
        //list.add(1);
        //list.add(42);

        assertThat(list, matches(hasSize(1)).and(contains(42)));
    } // end void customMatcherWithReadableError()
} // end class HamcrestCombiningMatchersTests
