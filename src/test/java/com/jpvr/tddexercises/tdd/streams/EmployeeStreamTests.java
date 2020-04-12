package com.jpvr.tddexercises.tdd.streams;

import com.jpvr.tddexercises.tdd.Employee;
import com.jpvr.tddexercises.tdd.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Several stream usage tests.
 *
 * Based on:
 * - https://stackify.com/streams-guide-java-8/
 *
 * To understand this material, you need to have a basic, working knowledge of Java 8
 * (lambda expressions, Optional, method references).
 *
 * A stream does not store data and, in that sense, is not a data structure.
 * It also never modifies the underlying data source.
 *
 * stream operations are divided into intermediate and terminal operations.
 *
 * Intermediate operations such as filter() return a new stream on which
 * further processing can be done. Terminal operations, such as forEach(),
 * mark the stream as consumed, after which point it can no longer be used further.
 */
public class EmployeeStreamTests {

    private final String FILENAME = "test.txt";

    private Employee[] arrayOfEmployees;
    private List<Employee> employeeList;
    private EmployeeRepository employeeRepository;

    @Before
    public void setUpBefore() {

        arrayOfEmployees = new Employee[] {
                new Employee(1, "Jeff Bezos", 100000.0),
                new Employee(2, "Bill Gates", 200000.0),
                new Employee(3, "Mark Zuckerberg", 300000.0)
        };

        // (List<Employee>)
        employeeList = Arrays.asList(arrayOfEmployees);

        employeeRepository = new EmployeeRepository(employeeList);
    } // end void setUpBefore()

    @Test
    public void shouldCreateValidStreams() {

        // Stream.of()
        Stream firstStream = Stream.of(arrayOfEmployees);

        // stream() method from the Collections interface
        Stream secondStream = employeeList.stream();

        // We can create a stream from individual objects using Stream.of():
        Stream thirdStream = Stream.of(
                arrayOfEmployees[0],
                arrayOfEmployees[1],
                arrayOfEmployees[2]);

        // Or simply using Stream.builder():
        Stream.Builder<Employee> employeeBuilder = Stream.builder();

        employeeBuilder.accept(arrayOfEmployees[0]);
        employeeBuilder.accept(arrayOfEmployees[1]);
        employeeBuilder.accept(arrayOfEmployees[2]);

        Stream forthStream = employeeBuilder.build();
    } // end shouldCreateValidStreams()

    /**
     * forEach() is simplest and most common operation;
     * it loops over the stream elements, calling the supplied function on each element.
     *
     * The method is so common that is has been introduced directly in Iterable, Map etc:
     */
    @Test
    public void whenIncrementSalaryForEachEmployee_thenApplyNewSalary() {

        employeeList
                .stream()
                .forEach(e -> e.salaryIncrement(10.0));

        assertThat(employeeList,
                contains(
                        hasProperty("salary", equalTo(110000.0)),
                        hasProperty("salary", equalTo(220000.0)),
                        hasProperty("salary", equalTo(330000.0))
                ));
    } // end whenIncrementSalaryForEachEmployee_thenApplyNewSalary()

    /**
     * map
     *
     * map() produces a new stream after applying a function to each element
     * of the original stream. The new stream could be of different type.
     *
     * Here, we obtain an Integer stream of employee ids from an array.
     * Each Integer is passed to the function employeeRepository::findById()
     * which returns the corresponding Employee object; this effectively forms an Employee stream.
     */
    @Test
    public void whenMapIdToEmployees_thenGetEmployeeStream() {

        Integer[] empIds = {1, 2, 3};

        List<Employee> employees = Stream.of(empIds)
                .map(employeeRepository::findById)
                .collect(Collectors.toList());

        assertEquals(employees.size(), empIds.length);
    } // end void whenMapIdToEmployees_thenGetEmployeeStream()

    /**
     * collect
     *
     * We saw how collect() works in the previous example; its one of the common ways
     * to get stuff out of the stream once we are done with all the processing.
     *
     * collect() performs mutable fold operations
     * (repackaging elements to some data structures and applying some additional logic, concatenating them, etc.)
     * on data elements held in the Stream instance.
     *
     * The strategy for this operation is provided via the Collector interface implementation.
     * In the example below, we used the toList collector to collect all Stream elements into a List instance.
     */
    @Test
    public void whenCollectStreamToList_thenGetList() {

        List<Employee> employees = employeeList
                .stream()
                .collect(Collectors.toList());

        assertEquals(employeeList, employees);
    } // end void whenCollectStreamToList_thenGetList()

    /**
     * filter
     *
     * Next, let’s have a look at filter(); this produces a new stream that contains
     * elements of the original stream that pass a given test (specified by a Predicate).
     */
    @Test
    public void whenFilterEmployees_thenGetFilteredStream() {

        Integer[] empIds = {1, 2, 3, 4};

        List<Employee> employees = Stream.of(empIds)
                .map(employeeRepository::findById)
                .filter(e -> e != null) // to get rid of an invalid id ('4' in this case)
                .filter(e -> e.getSalary() > 200000)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(arrayOfEmployees[2]), employees);
    } // end void whenFilterEmployees_thenGetFilteredStream()

    /**
     * findFirst
     *
     * findFirst() returns an Optional for the first entry in the stream;
     * the Optional can, of course, be empty or null.
     */
    @Test
    public void whenFindFirst_thenGetFirstEmployeeInStream_usingFindFirst() {

        Integer[] empIds = {1, 2, 3, 4};

        Employee employee = Stream.of(empIds)
                .map(employeeRepository::findById)
                .filter(e -> e != null)
                .filter(e -> e.getSalary() > 100000)
                .findFirst()
                .orElse(null);

        assertEquals(employee.getSalary(), new Double(200000));

        employee = Stream.of(empIds)
                .map(employeeRepository::findById)
                .filter(e -> e != null)
                .filter(e -> e.getSalary() > 400000)
                .findFirst()
                .orElse(null);

        assertNotEquals(employee, Optional.empty());

        assertNull(employee);
    } // end void whenFindFirst_thenGetFirstEmployeeInStream_usingFindFirst()

    /**
     * toArray
     *
     * We saw how we used collect() to get data out of the stream.
     * If we need to get an array out of the stream, we can simply use toArray().
     *
     * The syntax Employee[]::new creates an empty array of Employee
     * which is then filled with elements from the stream.
     */
    @Test
    public void whenStreamToArray_thenGetArray() {

        Employee[] employees = employeeList
                .stream()
                .toArray(Employee[]::new);

        assertThat(employeeList.toArray(), equalTo(employees));
    } // end void whenStreamToArray_thenGetArray()

    /**
     * flatMap
     *
     * A stream can hold complex data structures like Stream<List<String>>.
     * In cases like this, flatMap() helps us to flatten
     * the data structure to simplify further operations.
     *
     * Notice how we were able to convert the Stream<List<String>>
     * to a simpler Stream<String> – using the flatMap() API.
     */
    @Test
    public void whenFlatMapEmployeeNames_thenGetNameStream() {

        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Mark", "Zuckerberg")
        );

        // nested: [[Jeff, Bezos], [Bill, Gates], [Mark, Zuckerberg]]
        // System.out.println("nested: " + namesNested);

        List<String> namesFlatStream = namesNested
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // flattened: [Jeff, Bezos, Bill, Gates, Mark, Zuckerberg]
        // System.out.println("flattened: " + namesFlatStream);

        assertEquals(namesFlatStream.size(), namesNested.size() * 2);
    } // end void whenFlatMapEmployeeNames_thenGetNameStream()

    /**
     * peek
     *
     * We saw forEach() earlier in this section, which is a terminal operation.
     * However, sometimes we need to perform multiple operations on each element
     * of the stream before any terminal operation is applied.
     *
     * peek() can be useful in situations like this. Simply put,
     * it performs the specified operation on each element of the stream
     * and returns a new stream which can be used further.
     * peek() is an intermediate operation.
     *
     * Here, the first peek() is used to increment the salary of each employee.
     * The second peek() is used to print the employees.
     * Finally, collect() is used as the terminal operation.
     */
    @Test
    public void whenIncrementSalaryUsingPeek_thenApplyNewSalary() {

        employeeList.stream()
                .peek(e -> e.salaryIncrement(10.0))
                .peek(System.out::println)
                .collect(Collectors.toList());

        assertThat(employeeList,
                contains(
                        hasProperty("salary", equalTo(110000.0)),
                        hasProperty("salary", equalTo(220000.0)),
                        hasProperty("salary", equalTo(330000.0))
                ));
    } // end void whenIncrementSalaryUsingPeek_thenApplyNewSalary()

    /**
     * Pipelines
     *
     * A stream pipeline consists of a stream source,
     * followed by zero or more intermediate operations,
     * and a terminal operation.
     */
    @Test
    public void whenStreamCount_thenGetElementCount() {

        Long employeeCount = employeeList
                .stream()
                .filter(e -> e.getSalary() > 200000)
                .count();

        assertEquals(employeeCount, new Long(1));
    } // end void whenStreamCount_thenGetElementCount()

    /**
     * Short-circuiting operations
     *
     * Some operations are deemed short-circuiting operations.
     * Short-circuiting operations allow computations on
     * infinite streams to complete in finite time.
     *
     * Here, we use short-circuiting operations skip() to skip first 3 elements,
     * and limit() to limit to 5 elements from the infinite stream generated using iterate()
     */
    @Test
    public void whenLimitInfiniteStream_thenGetFiniteElements() {

        Stream<Integer> infiniteStream = Stream.iterate(2, i -> i * 2);

        List<Integer> collect = infiniteStream
                .skip(3)
                .limit(5)
                .collect(Collectors.toList());

        assertEquals(collect, Arrays.asList(16, 32, 64, 128, 256));
    } // end void whenLimitInfiniteStream_thenGetFiniteElements()

    /**
     * Lazy Evaluation
     *
     * One of the most important characteristics of streams is that
     * they allow for significant optimizations through lazy evaluations.
     *
     * Computation on the source data is only performed when the terminal operation
     * is initiated, and source elements are consumed only as needed.
     *
     * All intermediate operations are lazy, so they’re not executed until
     * a result of a processing is actually needed.
     *
     * For example, consider the findFirst() example we saw earlier.
     * How many times is the map() operation performed here?
     * 4 times, since the input array contains 4 elements?
     *
     * Processing streams lazily allows avoiding examining all the data
     * when that’s not necessary. This behavior becomes even more important
     * when the input stream is infinite and not just very large.
     */
    @Test
    public void whenFindFirst_thenGetFirstEmployeeInStream_usingLazyEvaluation() {

        Integer[] empIds = {1, 2, 3, 4};

        Employee employee = Stream.of(empIds)
                // 4 times
                .map(employeeRepository::findById)
                // Stream performs the map and two filter operations, one element at a time.
                .filter(e -> e != null)
                .filter(e -> e.getSalary() > 100000)
                .findFirst()
                .orElse(null);

        assertEquals(employee.getSalary(), new Double(200000));
    } // end void whenFindFirst_thenGetFirstEmployeeInStream_usingLazyEvaluation()

    /**
     * sorted
     *
     * Let’s start with the sorted() operation
     * this sorts the stream elements based on the comparator passed we pass into it.
     *
     * For example, we can sort Employees based on their names.
     *
     * Note that short-circuiting will not be applied for sorted().
     *
     * This means, in the example above, even if we had used findFirst() after the sorted(),
     * the sorting of all the elements is done before applying the findFirst().
     * This happens because the operation cannot know what the
     * first element is until the entire stream is sorted.
     */
    @Test
    public void whenSortStream_thenGetSortedStream() {

        // Previously in @Before
        //
        // arrayOfEmployees = new Employee[] {
        //         new Employee(1, "Jeff Bezos", 100000.0),
        //         new Employee(2, "Bill Gates", 200000.0),
        //         new Employee(3, "Mark Zuckerberg", 300000.0)};

        List<Employee> employees = employeeList
                .stream()
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                .collect(Collectors.toList());

        assertEquals(employees.get(0).getName(), "Bill Gates");
        assertEquals(employees.get(1).getName(), "Jeff Bezos");
        assertEquals(employees.get(2).getName(), "Mark Zuckerberg");
    } // end void whenSortStream_thenGetSortedStream()

    /**
     * min and max
     *
     * As the name suggests, min() and max() return the minimum and maximum element
     * in the stream respectively, based on a comparator.
     * They return an Optional since a result
     * may or may not exist (due to, say, filtering).
     */
    @Test
    public void whenFindMin_thenGetMinElementFromStream() {

        Employee firstEmployee = employeeList
                .stream()
                .min((e1, e2) -> e1.getId() - e2.getId())
                .orElseThrow(NoSuchElementException::new);

        assertEquals(firstEmployee.getId(), new Integer(1));
    } // end void whenFindMin_thenGetMinElementFromStream()

    /**
     * We can also avoid defining the comparison logic by using Comparator.comparing().
     */
    @Test
    public void whenFindMax_thenGetMaxElementFromStream() {

        Employee maxSalaryEmployee = employeeList
                .stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(NoSuchElementException::new);

        assertEquals(maxSalaryEmployee.getSalary(), new Double(300000.0));
    } // end void whenFindMax_thenGetMaxElementFromStream()

    /**
     * distinct
     *
     * distinct() does not take any argument and returns the distinct
     * elements in the stream, eliminating duplicates. It uses the equals()
     * method of the elements to decide whether two elements are equal or not.
     */
    @Test
    public void whenApplyDistinct_thenRemoveDuplicatesFromStream() {

        List<Integer> integerList = Arrays.asList(2, 5, 3, 2, 4, 3);

        List<Integer> distinctList = integerList
                .stream()
                .distinct()
                .collect(Collectors.toList());

        assertEquals(distinctList, Arrays.asList(2, 5, 3, 4));

        // distinct() is applied in order
        assertNotEquals(distinctList, Arrays.asList(2, 5, 4, 3));
    } // end void whenApplyDistinct_thenRemoveDuplicatesFromStream()

    /**
     * allMatch, anyMatch, and noneMatch
     *
     * These operations all take a predicate and return a boolean.
     * Short-circuiting is applied and processing is stopped as soon as the answer is determined.
     */
    @Test
    public void whenApplyMatch_thenReturnBoolean() {

        List<Integer> integerList = Arrays.asList(2, 4, 5, 6, 8);

        // allMatch() checks if the predicate is true for all the elements
        // in the stream. Here, it returns false as soon as it encounters 5, which is not divisible by 2.
        boolean allEven = integerList.stream()
                .allMatch(i -> i%2 == 0);


        // anyMatch() checks if the predicate is true for any one element in the stream.
        // Here, again short-circuiting is applied and true is returned immediately after the first element.
        boolean oneEven = integerList.stream()
                .anyMatch(i -> i%2 == 0);


        // noneMatch() checks if there are no elements matching the predicate.
        // Here, it simply returns false as soon as it encounters 6, which is divisible by 3.
        boolean noneMultipleOfThree = integerList.stream()
                .noneMatch(i -> i%3 == 0);

        assertFalse(allEven);

        assertTrue(oneEven);

        assertFalse(noneMultipleOfThree);
    } // end void whenApplyMatch_thenReturnBoolean()

    /**
     * Stream Specializations
     *
     * From what we discussed so far, Stream is a stream of object references.
     * However, there are also the IntStream, LongStream, and DoubleStream
     * which are primitive specializations for int, long and double respectively.
     * These are quite convenient when dealing with a lot of numerical primitives.
     *
     * These specialized streams do not extend Stream but extend
     * BaseStream on top of which Stream is also built.
     *
     * As a consequence, not all operations supported by Stream are present in
     * these stream implementations. For example, the standard min() and max()
     * take a comparator, whereas the specialized streams do not.
     */

    /**
     * Creation
     *
     * The most common way of creating an IntStream is
     * to call mapToInt() on an existing stream.
     */
    @Test
    public void whenFindMaxOnIntStream_thenGetMaxInteger() {

        Integer latestEmployeeId = employeeList
                .stream()
                .mapToInt(Employee::getId)
                .max()
                .orElseThrow(NoSuchElementException::new);

        assertEquals(latestEmployeeId, new Integer(3));
    } // end void whenFindMaxOnIntStream_thenGetMaxInteger()

    /**
     * Alternative ways to create an IntStream
     */
    @Test
    public void shouldCreateValidIntegerStreams() {

        assertNotNull(IntStream.of(1, 2, 3));

        IntStream integerStream = IntStream.range(10, 20);

        // Stream.of(1, 2, 3) returns a Stream<Integer> and not an IntStream
        Stream<Integer> anotherIntegerStream = Stream.of(1, 2, 3);

        Stream<Integer> yetAnotherIntegerStream = employeeList
                .stream()
                // Similarly, using map() instead of mapToInt()
                // returns a Stream<Integer> and not an IntStream.
                .map(Employee::getId);
    } // end void shouldCreateValidIntegerStreams()

    /**
     * Specialized Operations
     *
     * Specialized streams provide additional operations as compared
     * to the standard Stream – which are quite convenient when dealing with numbers.
     *
     * For example sum(), average(), range(), etc.
     */
    @Test
    public void whenApplySumOnIntegerStream_thenGetSum() {

        Double averageSalary = employeeList
                .stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElseThrow(NoSuchElementException::new);

        assertEquals(averageSalary, new Double(200000));
    } // end void whenApplySumOnIntegerStream_thenGetSum()

    /**
     * Reduction Operations
     *
     * A reduction operation (also called as fold) takes a sequence
     * of input elements and combines them into a single summary
     * result by repeated application of a combining operation.
     * We already saw few reduction operations
     * like findFirst(), min() and max().
     *
     * Let’s see the general-purpose reduce() operation in action.
     */

    /**
     * reduce
     *
     * The most common form of reduce() is:
     *
     * T reduce(T identity, BinaryOperator<T> accumulator)
     *
     * where identity is the starting value and accumulator
     * is the binary operation we repeated apply.
     */
    @Test
    public void whenApplyReduceOnStream_thenGetValue() {

        Double sumSalary = employeeList
                .stream()
                .map(Employee::getSalary)
                .reduce(0.0, Double::sum);

        assertEquals(new Double(600000), sumSalary);
    } // end void whenApplyReduceOnStream_thenGetValue()

    /**
     * Advanced collect
     *
     * We already saw how we used Collectors.toList() to get the list out of the stream.
     * Let’s now see few more ways to collect elements from the stream.
     */
    @Test
    public void whenCollectByJoining_thenGetJoinedString() {

        String employeeNames = employeeList
                .stream()
                .map(Employee::getName)
                // Collectors.joining() will insert the delimiter between the two String
                // elements of the stream.
                // It internally uses a java.util.StringJoiner to perform the joining operation.
                .collect(Collectors.joining(", "))
                .toString();

        assertEquals("Jeff Bezos, Bill Gates, Mark Zuckerberg", employeeNames);
    } // end void whenCollectByJoining_thenGetJoinedString()

    /**
     * toSet
     *
     * We can also use toSet() to get a set out of stream elements.
     */
    @Test
    public void whenCollectBySet_thenGetSet() {

        Set<String> employeeNames = employeeList
                .stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());

        assertEquals(3, employeeNames.size());
    } // end void whenCollectBySet_thenGetSet()

    /**
     * toCollection
     *
     * We can use Collectors.toCollection() to extract the elements
     * into any other collection by passing in a Supplier<Collection>.
     * We can also use a constructor reference for the Supplier:
     */
    @Test
    public void whenToVectorCollection_thenGetVector() {

        // Here, an empty collection is created internally, and its add() method
        // is called on each element of the stream.
        Vector<String> employeeNames = employeeList
                .stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(Vector::new));

        assertEquals(3, employeeNames.size());
    } // end void whenToVectorCollection_thenGetVector()

    /**
     * summarizingDouble
     *
     * summarizingDouble() is another interesting collector which applies a
     * double-producing mapping function to each input element and returns
     * a special class containing statistical information for the resulting values.
     */
    @Test
    public void whenApplySummarizing_thenGetBasisStats() {

        DoubleSummaryStatistics stats = employeeList
                .stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        // Notice how we can analyze the salary of each employee and get
        // statistical information on that data – such as min, max, average, etc.
        assertEquals(3, stats.getCount());
        assertEquals(600000.0, stats.getSum(), 0);
        assertEquals(100000.0, stats.getMin(), 0);
        assertEquals(300000.0, stats.getMax(), 0);
        assertEquals(200000.0, stats.getAverage(), 0);
    } // end void whenApplySummarizing_thenGetBasisStats()

    /**
     * summaryStatistics() can be used to generate similar result
     * when we’re using one of the specialized streams
     */
    @Test
    public void whenApplySummaryStatistics_thenGetBasicsStats() {

        DoubleSummaryStatistics stats = employeeList
                .stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        assertEquals(3, stats.getCount());
        assertEquals(600000.0, stats.getSum(), 0);
        assertEquals(100000.0, stats.getMin(), 0);
        assertEquals(300000.0, stats.getMax(), 0);
        assertEquals(200000.0, stats.getAverage(), 0);
    } // end void whenApplySummaryStatistics_thenGetBasicsStats()

    /**
     * partitioningBy
     *
     * We can partition a stream into two
     * based on whether the elements satisfy certain criteria or not.
     *
     * Let’s split our List of numerical data, into even and ods.
     */
    @Test
    public void whenStreamPartition_thenGetMap() {

        List<Integer> integerList = Arrays.asList(2, 4, 5, 6, 8);

        // Here, the stream is partitioned into a Map,
        // with even and odds stored as true and false keys, respectively.
        Map<Boolean, List<Integer>> isEven = integerList
                .stream()
                .collect(Collectors.partitioningBy(i -> i%2 == 0));

        assertEquals(4, isEven.get(true).size());
        assertEquals(1, isEven.get(false).size());
    } // end void whenStreamPartition_thenGetMap()

    /**
     * groupingBy
     *
     * groupingBy() offers advanced partitioning
     * where we can partition the stream into more than just two groups.
     *
     * It takes a classification function as its parameter.
     * This classification function is applied to each element of the stream.
     *
     * The value returned by the function is used as a key to the map
     * that we get from the groupingBy collector.
     */
    @Test
    public void whenStreamGroupingBy_thenGetMap() {

        Map<Character, List<Employee>> groupByAlphabet = employeeList
                .stream()
                .collect(
                        // Grouped the employees based on the initial character of their first name.
                        Collectors.groupingBy(
                                e -> new Character(e.getName().charAt(0))
                        )
                );

        assertEquals("Bill Gates", groupByAlphabet.get('B').get(0).getName());
        assertEquals("Jeff Bezos", groupByAlphabet.get('J').get(0).getName());
        assertEquals("Mark Zuckerberg", groupByAlphabet.get('M').get(0).getName());
    } // end void whenStreamGroupingBy_thenGetMap()

    /**
     * mapping
     *
     * groupingBy() discussed in the section above,
     * groups elements of the stream with the use of a Map.
     *
     * However, sometimes we might need to group data
     * into a type other than the element type.
     *
     * Here’s how we can do that; we can use mapping() which can actually
     * adapt the collector to a different type – using a mapping function.
     */
    @Test
    public void whenStreamMapping_thenGetMap() {

        Map<Character, List<Integer>> idGroupedByAlphabet = employeeList
                .stream()
                .collect(
                        Collectors.groupingBy(
                                e -> new Character(e.getName().charAt(0)),
                                Collectors.mapping(Employee::getId, Collectors.toList())
                        )
                );

        // Here mapping() maps the stream element Employee into just the employee id
        // which is an Integer, using the getId() mapping function. These ids are still
        // grouped based on the initial character of employee first name.
        assertEquals(new Integer(2), idGroupedByAlphabet.get('B').get(0));
        assertEquals(new Integer(1), idGroupedByAlphabet.get('J').get(0));
        assertEquals(new Integer(3), idGroupedByAlphabet.get('M').get(0));
    } // end void whenStreamMapping_thenGetMap()

    /**
     * reducing
     *
     * reducing() is similar to reduce() – which we explored before. It simply returns
     * a collector which performs a reduction of its input elements.
     */
    @Test
    public void whenStreamReducing_thenGetValue() {

        Double percentage = 10.0;
        Double salaryIncreaseOverhead = employeeList
                .stream()
                .collect(
                        Collectors.reducing(
                                0.0,
                                e -> e.getSalary() * percentage / 100,
                                (s1, s2) -> s1 + s2
                        )
                );

        // Here reducing() gets the salary increment
        // of each employee and returns the sum.
        assertEquals(60000.0, salaryIncreaseOverhead, 0);
    } // end void whenStreamReducing_thenGetValue()

    /**
     * reducing() is most useful when used in a multi-level reduction,
     * downstream of groupingBy() or partitioningBy().
     * To perform a simple reduction on a stream, use reduce() instead.
     *
     * For example, let’s see how we can use reducing() with groupingBy().
     */
    @Test
    public void whenStreamGroupingAndReducing_thenGetMap() {

        Comparator<Employee> comparatorByNameLength = Comparator.comparing(Employee::getName);

        Map<Character, Optional<Employee>> longestNameByAlphabet = employeeList
                .stream()
                .collect(
                        Collectors.groupingBy(
                                e -> new Character(e.getName().charAt(0)),
                                Collectors.reducing(BinaryOperator.maxBy(comparatorByNameLength))
                        )
                );

        // Here we group the employees based on the initial character of
        // their first name. Within each group, we find the employee with the longest name.
        assertEquals("Bill Gates", longestNameByAlphabet.get('B').get().getName());
        assertEquals("Jeff Bezos", longestNameByAlphabet.get('J').get().getName());
        assertEquals("Mark Zuckerberg", longestNameByAlphabet.get('M').get().getName());
    } // end whenStreamGroupingAndReducing_thenGetMap()

    /**
     * Parallel Streams
     *
     * Using the support for parallel streams, we can perform stream operations
     * in parallel without having to write any boilerplate code;
     * we just have to designate the stream as parallel.
     */
    @Test
    public void whenParallelStream_thenPerformOperationsInParallel() {

        employeeList
                .stream()
                .parallel()
                .forEach(e -> e.salaryIncrement(10.0));

        // Here salaryIncrement() would get executed in parallel on multiple
        // elements of the stream, by simply adding the parallel() syntax.
        //
        // This functionality can, of course, be tuned and configured further,
        // if you need more control over the performance characteristics of the operation.
        //
        // As is the case with writing multi-threaded code, we need to be aware
        // of few things while using parallel streams:
        //
        // 1. We need to ensure that the code is thread-safe. Special care needs
        //    to be taken if the operations performed in parallel modifies shared data.
        // 2. We should not use parallel streams if the order in which operations
        //    are performed or the order returned in the output stream matters.
        //    For example operations like findFirst() may generate the different
        //    result in case of parallel streams.
        // 3. Also, we should ensure that it is worth making the code execute in parallel.
        //    Understanding the performance characteristics of the operation in particular,
        //    but also of the system as a whole – is naturally very important here.
        assertThat(employeeList,
                contains(
                        hasProperty("salary", equalTo(110000.0)),
                        hasProperty("salary", equalTo(220000.0)),
                        hasProperty("salary", equalTo(330000.0))
                ));
    } // end void whenParallelStream_thenPerformOperationsInParallel()

    /**
     * Infinite Streams
     *
     * Sometimes, we might want to perform operations while the elements are still
     * getting generated. We might not know beforehand how many elements we’ll need.
     * Unlike using list or map, where all the elements are already populated,
     * we can use infinite streams, also called as unbounded streams.
     *
     * There are two ways to generate infinite streams.
     */

    /**
     * generate
     *
     * We provide a Supplier to generate() which gets called whenever
     * new stream elements need to be generated.
     */
    @Test
    public void whenGenerateStream_thenGetInfiniteStream() {

        // Here, we pass Math::random() as a Supplier,
        // which returns the next random number.
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        // With infinite streams, we need to provide a condition to eventually
        // terminate the processing. One common way of doing this is using limit().
        // In above example, we limit the stream to 5 random numbers
        // and print them as they get generated.
        //
        // Please note that the Supplier passed to generate() could be stateful
        // and such stream may not produce the same result when used in parallel.
    } // end whenGenerateStream_thenGetInfiniteStream()

    /**
     * iterate
     *
     * iterate() takes two parameters:
     * - an initial value, called seed element, and
     * - a function which generates next element using the previous value.
     * iterate(), by design, is stateful and hence may not be useful in parallel streams.
     */
    @Test
    public void whenIterateStream_thenGetInfiniteStream() {

        // Here, we pass 2 as the seed value, which becomes the first element of our stream.
        // This value is passed as input to the lambda, which returns 4.
        // This value, in turn, is passed as input in the next iteration.
        //
        // This continues until we generate the number of elements
        // specified by limit() which acts as the terminating condition.
        Stream<Integer> evenNumbersStream = Stream.iterate(2, i -> i * 2);

        // The first element of the list is the "seed" of the iterator.
        List<Integer> collectList = evenNumbersStream
                .limit(5)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(2, 4, 8, 16, 32), collectList);
    } // end void whenIterateStream_thenGetInfiniteStream()

    /**
     * File Operations
     *
     * Let's see how we could use the stream in file operations.
     */

    /**
     * File Write Operation.
     */
    @Test
    public void whenStreamToFile_thenGetFile() throws IOException {

        String[] words = {
                "hello", "refer", "world", "level"
        };

        // Here we use forEach() to write each element of the stream
        // into the file by calling PrintWriter.println().
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(FILENAME)))) {

            Stream.of(words).forEach(pw::println);
        }
    } // end void whenStreamToFile_thenGetFile()

    /**
     * File Read Operation.
     */
    private List<String> getPalindrome(Stream<String> stream, int length) {

        return stream
                .filter(s -> s.length() == length)
                .filter(s -> s.compareToIgnoreCase(new StringBuilder(s).reverse().toString()) == 0)
                .collect(Collectors.toList());
    } // end List<String> getPalindrome(Stream<String> stream, int length)
    /**
     * Here Files.lines() returns the lines from the file as a Stream
     * which is consumed by the getPalindrome() for further processing.
     *
     * getPalindrome() works on the stream, completely unaware of
     * how the stream was generated.
     * This also increases code reusability and simplifies unit testing.
     */
    @Test
    public void whenFileToStream_thenGetStream() throws IOException {

        if ( !Files.exists(Paths.get(FILENAME))) {
            whenStreamToFile_thenGetFile();
        }

        List<String> palindrome = getPalindrome(Files.lines(Paths.get(FILENAME)), 5);

        assertThat(palindrome, contains("refer", "level"));
    } // end void whenFileToStream_thenGetStream()

} // end class EmployeeStreamTests
