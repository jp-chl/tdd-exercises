package com.jpvr.tddexercises.tdd.hamcrest.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * Utility class to group matchers and import them easily within tests
 */
public class MyMatchers {

    public static <T> Matcher<T> instanceOf(Class<T> target) {

        return Matchers.instanceOf(target);
    } // end static <T> Matcher<T> instanceOf(Class<T> target)

    public static Matcher<String> matchesRegex(String target) {

        return RegexMatcher.matchesRegex(target);
    } // end static Matcher<String> matchesRegex(String target)
} // end class MyMatchers
