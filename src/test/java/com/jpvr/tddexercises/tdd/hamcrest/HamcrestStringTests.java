package com.jpvr.tddexercises.tdd.hamcrest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

/**
 * IMPORTANT: Uses deprecated methods
 */
public class HamcrestStringTests {

    private String stringToTest = null;

    @Before
    public void setUpBefore() {

        stringToTest = "";
    } // end void setUpBefore()

    @Test
    public void isStringEmpty() {

        assertThat(stringToTest, isEmptyString());
    } // end void isStringEmpty()

    @Ignore
    public void isStringEmptyOrNull() {

        stringToTest = null;
        assertEquals(stringToTest, isEmptyOrNullString());
    } // end void isStringEmptyOrNull()

} // end class HamcrestStringTests
