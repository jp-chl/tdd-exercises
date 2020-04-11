package com.jpvr.tddexercises.tdd.hamcrest;

//import com.jpvr.tddexercises.tdd.hamcrest.matchers.RegexMatcher;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static com.jpvr.tddexercises.tdd.hamcrest.matchers.MyMatchers.*;

/**
 * Custom matchers
 */
public class HamcrestFeatureMatchersTests {

    @Test
    public void testRegularExpressionMatcher() throws Exception {

        String s = "aaabbbaaaa";

        assertThat(s, matchesRegex("a*b*a*"));
    } // end void testRegularExpressionMatcher() throws Exception

} // end class HamcrestFeatureMatchersTests
