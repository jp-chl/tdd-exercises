package com.jpvr.tddexercises.tdd.streams;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

/**
 * Several stream usage tests.
 *
 * Based on:
 * - https://www.codingame.com/playgrounds/31592/java-8-stream-tutorial
 * - https://www.codingame.com/playgrounds/3312/java-8-streams-cookbook/streams-cookbook
 */
public class StringStreamTests {

    @Test
    public void shouldSortStringArrayWithCertainPrefix() {

    } // end

    public void shouldFilterStringBeginningWithCLetter_transformThemToUpperCase_andPrintToTerminal() {

        List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    } // end void shouldFilterStringBeginningWithCLetter_transformThemToUpperCase_andPrintToTerminal()

    /**
     * This doesn't have terminal operation so no output will be displayed.
     * That is because intermediate operations will only be executed when a terminal operation is present.
     */
    @Test
    public void shouldNotPrintAnythingToTerminal() {

        //
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.print("filter: " + s);
                    return true;
                });
    } // end void streamPracticingTests()

    /**
     * The above example have terminal operation.
     *
     * forEach() is a terminal operation, which means that,
     * after the operation is performed, the stream pipeline is considered consumed, and can no longer be used
     */
    @Test
    public void shouldPrintAnythingToTerminal() {

        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));
    } // end void streamPracticingTests()

    /**
     * To avoid unnecessary calls, instead of executing
     * the operations horizontally we can do it vertically,
     * executing more than one operation in every iteration.
     *
     * The operation anyMatch returns true as soon as the
     * predicate applies to the given input element.
     * This is true for the second element passed "A2".
     * Due to the vertical execution of the stream chain,
     * map has only to be executed twice in this case. So instead of
     * mapping all elements of the stream,
     * map will be called as few as possible.
     */
    @Test
    public void shouldFindCapitalStringAsSoonAsPossible() {

        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });
    } // end shouldFindCapitalStringAsSoonAsPossible()

    /**
     * Java 8 streams cannot be reused. As soon as you call
     * any terminal operation the stream is closed:
     * Calling noneMatch after anyMatch on the same stream results in Exception.
     */
    @Test
    public void shouldThrowAnException_whenReuseStream() {

        Stream<String> stream =
                Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> s.startsWith("a"));

        // should work
        assertTrue(stream.anyMatch(s -> true));

        // It will raise an exception
        try {
            stream.noneMatch(s -> true);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(),
                    is("stream has already been operated upon or closed"));
        }
    } // end shouldThrowAnException_whenReuseStream()

    /**
     * To overcome this limitation we have to to create a new stream chain
     * for every terminal operation we want to execute, e.g. we could create
     * a stream supplier to construct a new stream
     * with all intermediate operations already set up:
     */
    @Test
    public void reuseStreamWithSupplier() {

        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        assertTrue(streamSupplier.get().anyMatch(s -> true)); // ok
        assertFalse(streamSupplier.get().noneMatch(s -> true)); // ok
    } // end reuseStreamWithSupplier()

    // https://www.codingame.com/playgrounds/3312/java-8-streams-cookbook/streams-cookbook

    private List<Winner> tdfWinners;

    @Before
    public void setUpBefore() {

        tdfWinners = Arrays.asList(
                new Winner(2006, "Spain", "Óscar Pereiro",
                        "Caisse d'Epargne–Illes Balears", 3657,
                        Duration.parse("PT89H40M27S"), 8),

                new Winner(2007, "Spain", "Alberto Contador",
                        "Discovery Channel", 3570,
                        Duration.parse("PT91H00M26S"), 4),

                new Winner(2008, "Spain", "Carlos Sastre",
                        "Team CSC", 3559, Duration.parse("PT87H52M52S"),
                        5),

                new Winner(2009, "Spain", "Alberto Contador",
                        "Astana", 3459,
                        Duration.parse("PT85H48M35S"), 7),

                new Winner(2010, "Luxembourg", "Andy Schleck",
                        "Team Saxo Bank", 3642,
                        Duration.parse("PT91H59M27S"), 12),

                new Winner(2011, "Australia", "Cadel Evans",
                        "BMC Racing Team", 3430,
                        Duration.parse("PT86H12M22S"), 2),

                new Winner(2012, "Great Britain", "Bradley Wiggins",
                        "Team Sky", 3496,
                        Duration.parse("PT87H34M47S"), 14),

                new Winner(2013, "Great Britain", "Chris Froome",
                        "Team Sky", 3404,
                        Duration.parse("PT83H56M20S"), 14),

                new Winner(2014, "Italy", "Vincenzo Nibali",
                        "Astana", 3661,
                        Duration.parse("PT89H59M06S"), 19),

                new Winner(2015, "Great Britain", "Chris Froome",
                        "Team Sky", 3360,
                        Duration.parse("PT84H46M14S"), 16),

                new Winner(2016, "Great Britain", "Chris Froome",
                        "Team Sky", 3529,
                        Duration.parse("PT89H04M48S"), 14 )
        );
    } // end void setUpBefore()

    /**
     * There are 3 steps in converting the tdfWinners list to
     * a list of winners of tours greater than or less than 3500km
     *
     * - filter - Here we use a lambdas - for less than -
     *      .filter(d -> d.getLengthKm() < 3500)
     *
     * - filter - Here we use a lambdas - for more than -
     *      .filter(d -> d.getLengthKm() >= 3500)
     *
     * - We then use the map method and collect method to store to a list
     */
    @Test
    public void shouldReturnWinnersWithLessThan3500Km() {

        List<String> winners = tdfWinners
                .stream()
                .filter(d -> d.getLengthKm() < 3500)
                .map(Winner::getName)
                .collect(Collectors.toList());

        System.out.println("Winners with less than 3500 Km: " + winners);

        assertEquals(5, winners.size());
    } // end shouldReturnWinnersWithLessThan3500Km()

    @Test
    public void shouldReturnWinnersWithMoreThan3500Km() {

        List<String> winners = tdfWinners
                .stream()
                .filter(d -> d.getLengthKm() >= 3500)
                .map(Winner::getName)
                .collect(Collectors.toList());

        System.out.println("Winners with more than 3500 Km: " + winners);

        assertEquals(6, winners.size());
    } // end shouldReturnWinnersWithLessThan3500Km()

    /**
     * Limit, Distinct and Skip
     *
     *     limit - limit the number of records to be returned
     *     distinct - only return distinct records
     *     skip - skip every n records
     */
    @Test
    public void shouldReturnTwoWinnerObjects_withLessThan3500Km() {

        List<Winner> winners = tdfWinners
                .stream()
                .filter(d -> d.getLengthKm() < 3500)
                .limit(2)
                .collect(Collectors.toList());

        System.out.println("Winner objects with less than 3500 Km: " + winners);

        assertEquals(2, winners.size());
    } // end shouldReturnTwoWinnerObjects_withLessThan3500Km()

    @Test
    public void shouldReturnTwoWinnerNames_withLessThan3500Km() {

        List<String> winners = tdfWinners
                .stream()
                .filter(d -> d.getLengthKm() < 3500)
                .map(Winner::getName)// with this Map the result is a String Stream
                .limit(2)
                .collect(Collectors.toList());

        System.out.println("Winner names with less than 3500 Km: " + winners);

        assertEquals(2, winners.size());
    } // end shouldReturnTwoWinnerNames_withLessThan3500Km()

    @Test
    public void shouldReturnEightUniqueWinnerNames() {

        List<String> winners = tdfWinners
                .stream()
                .map(Winner::getName)
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Unique winner names: " + winners);

        assertEquals(8, winners.size());


        long uniqueWinners = tdfWinners
                .stream()
                .map(Winner::getName)
                .distinct()
                .count();

        assertEquals(8, uniqueWinners);
    } // end shouldReturnEightUniqueWinnerNames()

    @Test
    public void shouldSkipElementsInList() {

        final int elementsToSkip = 2;

        List<Winner> winners = tdfWinners
                .stream()
                .skip(elementsToSkip)
                .collect(Collectors.toList());

        System.out.println("Skipped winner names: " + winners);

        assertEquals(tdfWinners.size() - elementsToSkip, winners.size());
    } // end shouldSkipElementsInList()




    @Ignore
    public void tests() {
        assertEquals( "Incorrect", "camel Casing", breakCamelCase("camelCasing"));
        assertEquals( "Incorrect", "camel Casing Test", breakCamelCase("camelCasingTest"));
        assertEquals( "Incorrect", "camelcasingtest", breakCamelCase("camelcasingtest"));
    }

    @Test
    public void test() {

//        String s = "camelCasing";
//
//        StringBuilder t = new StringBuilder();
//
//        s.codePoints()
//                //.filter(Character::isLetter)
//                //.filter(Character::isUpperCase)
//                .filter(Character::isLowerCase)
//                .map(Character::toUpperCase)
//                //.forEachOrdered(t::appendCodePoint);
//                .forEach(t::appendCodePoint);
//
//
//        String u = t.toString();
//
//        System.out.println(u);
//
//        System.out.println("----*****-----****---");
//        String v = "abc-de3-2fg";
//        String s1 = v.chars().filter(Character::isLetter)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
//        System.out.println(s1);
//
//
//                //.forEach(System.out::println);
//
//        System.out.println("---");
//
//        s.chars()
//                //.mapToObj(Character::toUpperCase)
//                .forEach(System.out::println);
    } // end

    /**
     * Complete the solution so that the function will break up camel casing, using a space between words.
     *
     * Example:
     * solution("camelCasing")  ==  "camel Casing"
     *
     * @param input original camelCase String
     * @return "breaked" camel cased as output
     */
    private String breakCamelCase(String input) {
        return null;
    } // end String breakCamelCase(String input)

} // end class StringStreamTests
