package com.jpvr.tddexercises.tdd.hamcrest;

import com.jpvr.tddexercises.tdd.hamcrest.ToDo;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;


public class HamcrestClassToDoTests {
    private ToDo toDo = null;

    @Before
    public void setUpBefore() {

        toDo = new ToDo(1, "Learn Hamcrest", "Important");
    } // end void setUpBefore()

    @Test
    public void objectHasSummaryProperty() {

        assertThat(toDo, hasProperty("summary"));
    } // end void objectHasSummaryProperty()

    @Test
    public void objectHasCorrectSummaryValue() {

        assertThat(toDo, hasProperty("summary", equalTo("Learn Hamcrest")));
    } // end void objectHasCorrectSummaryValue()

    @Test
    public void objectsHaveSameProperties() {

        ToDo toDo1 = new ToDo(1, "Learn Hamcrest", "Important");
        ToDo toDo2 = new ToDo(1, "Learn Hamcrest", "Important");

        assertThat(toDo1, samePropertyValuesAs(toDo2));
    } // end void objectsHaveSameProperties()

} // end class HamcrestClassToDoTests
