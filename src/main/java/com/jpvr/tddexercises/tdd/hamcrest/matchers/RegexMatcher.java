package com.jpvr.tddexercises.tdd.hamcrest.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RegexMatcher extends TypeSafeMatcher<String> {

    private final String regex;

    public RegexMatcher(final String regex) {
        this.regex = regex;
    } // end RegexMatcher(final String regex)

    @Override
    public void describeTo(final Description description) {

        description.appendText("matches regular expression=`" + regex + "`");
    } // end void describeTo(final Description description)

    @Override
    public boolean matchesSafely(final String string) {

        return string.matches(regex);
    } // end boolean matchesSafely(final String string)

    public static RegexMatcher matchesRegex(final String regex) {

        return new RegexMatcher(regex);
    } // end static RegexMatcher matchesRegex(final String regex)

} // end class RegexMatcher
