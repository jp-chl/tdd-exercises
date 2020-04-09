package com.jpvr.tddexercises.tdd.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MatcherCombinator<T> extends BaseMatcher<T> {

    private final List<Matcher<? super T>> matchers = new ArrayList<>();
    private final List<Matcher<? super T>> failedMatchers = new ArrayList<>();

    private MatcherCombinator(final Matcher<? super T> matcher) {

        matchers.add(matcher);
    } // end MatcherCombinator(final Matcher<? super T> matcher)

    public MatcherCombinator<T> and(final Matcher<? super T> matcher) {

        matchers.add(matcher);
        return this;
    } // end MatcherCombinator(final Matcher<? super T> matcher)

    @Override
    public boolean matches(final Object item) {

        boolean matchesAllMatchers = true;

        for(final Matcher<? super T> matcher : matchers) {

            if ( !matcher.matches(item) ) {

                failedMatchers.add(matcher);
                matchesAllMatchers = false;
            }
        } // end iteration

        return matchesAllMatchers;
    } // end boolean matches(final Object item)

    @Override
    public void describeTo(final Description description) {

        description.appendValueList("\n", " " + "and" + "\n", "", matchers);
    } // end void describeTo(final Description description)

    @Override
    public void describeMismatch(final Object item, final Description description) {

        description.appendText("\n");

        for(Iterator<Matcher<? super T>> iterator = failedMatchers.iterator(); iterator.hasNext(); ) {

            final Matcher<? super T> matcher = iterator.next();

            description.appendText("Expected: <");
            description.appendDescriptionOf(matcher).appendText(" but ");

            matcher.describeMismatch(item, description);

            if (iterator.hasNext()) {

                description.appendText(">\n");
            }
        } // end iteration
    } // end void describeMismatch(final Object item, final Description description)

    public static <LHS> MatcherCombinator<LHS> matches(final Matcher<? super LHS> matcher) {

        return new MatcherCombinator<LHS>(matcher);
    } // end static <LHS> MatcherCombinator<LHS> matches(final Matcher<? super LHS> matcher)
} // end class MatcherCombinator
