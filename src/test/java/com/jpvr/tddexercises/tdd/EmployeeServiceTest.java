package com.jpvr.tddexercises.tdd;

import org.hamcrest.CoreMatchers;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * This class contains Unit Test cases of {@link EmployeeService}
 */
public class EmployeeServiceTest {

    private EmployeeService employeeService = new EmployeeService();

    private Employee employee = null;

    @BeforeClass
    public static void beforeClass() {

        System.out.println("Executing before class...");
    } // end static void beforeClass()

    @Before
    public void before() {

        System.out.println("Executing before...");
        employee = new Employee();
        employee.setSalary(1000000.0);

        employee.setId(1);
        employee.setName("jp");
    } // end void before()

    @AfterClass
    public static void afterClass() {

        System.out.println("Executing after class...");
    } // end static void afterClass()

    @After
    public void after() {

        System.out.println("Executing after...");
    } // end void after()

    @Test
    public void isValidEmployee_withNullEmployee() {

        System.out.println("Entered in isValidEmployee_withNullEmployee");

        assertEquals(new Double(0.0), employeeService.calculateTax(null));
        System.out.println("Exited from isValidEmployee_withNullEmployee");
    } // end void isValidEmployee_withNullEmployee()

    @Test
    public void isValidEmployee_withNegativeSalary() {

        System.out.println("Entered in isValidEmployee_withNegativeSalary");

        employee.setSalary(-2.0);

        assertEquals(new Double(0.0), employeeService.calculateTax(null));
        System.out.println("Exited from isValidEmployee_withNegativeSalary");
    } // end void isValidEmployee_withNegativeSalary()

    @Test
    public void isValidEmployee_withSalaryLessThanFiveHundredThousand() {

        System.out.println("Entered in isValidEmployee_withSalaryLessThanFiveHundredThousand");

        Double salary = 400000.0;
        Double expectedTax = salary * 0.05;

        employee.setSalary(salary);

        assertEquals(expectedTax, employeeService.calculateTax(employee));
        System.out.println("Exited from isValidEmployee_withSalaryLessThanFiveHundredThousand");
    } // end void isValidEmployee_withSalaryLessThanFiveHundredThousand()

    @Test
    public void assertEquals_example() {

        Employee employeeNew = new Employee();

        employeeNew.setSalary(1000000.0);
        employeeNew.setId(1);
        employeeNew.setName("jp");

        assertEquals("EMPLOYEE OBJECT", employee, employeeNew);
    } // end void assertEquals_example()

    @Test
    public void assertTrue_assertFalse_example() {

        assertTrue("VALID EMPLOYEE OBJECT", employeeService.isValidEmployee(employee));
        assertFalse("INVALID EMPLOYEE OBJECT", employeeService.isValidEmployee(null));
    } // end void assertTrue_assertFalse_example()

    /**
     * The assertNotNull() method works oppositely of the assertNull() method,
     * throwing an exception if a null value is passed to it,
     * and returning normally if a non-null value is passed to it.
     */
    @Test
    public void assertNull_assertNotNull_example() {

        assertNotNull(employeeService.getEmployeeFromId(1));
        assertNull(employeeService.getEmployeeFromId(2));
    } // end void assertNull_assertNotNull_example()

    /**
     * The assertSame() and assertNotSame() methods tests if two object references point
     * to the same object or not. It is not enough that the two objects pointed to are
     * equals according to their equals() methods. It must be exactly the same object pointed to.
     *
     * The assertNotSame() method works oppositely of the assertSame() method. If the two objects
     * do not point to the same object, the assertNotSame() method will return normally.
     * Otherwise, an exception is thrown and the test stops here.
     */
    @Test
    public void assertSame_assertNotSame_example() {

        assertSame(employeeService.getEmployeeFromId(1), employeeService.getEmployeeFromId(1));
        assertNotSame(employee, employeeService.getEmployeeFromId(1));
    } // end void assertSame_assertNotSame_example()

    /**
     * The assertThat() method compares an object to an org.hamcrest.Matcher to see if the given
     * object matches whatever the Matcher requires it to match.
     *
     * Examples:
     * 1st assertThat - its taking the  employeeService.getEmployeeFromId(1) and comparing it
     * with employeeService.getEmployeeFromId(1) with the help of is Matcher.
     *
     * 2nd assertThat is taking employeeService.getEmployeeFromId(1) and checking if the object
     * is not null with is(CoreMatchers.notNullValue()) Matcher.
     */
    @Test
    public void assertThat_example() {

        assertThat(employeeService.getEmployeeFromId(1), is(employeeService.getEmployeeFromId(1)) );
        assertThat(employeeService.getEmployeeFromId(1), is(CoreMatchers.notNullValue()));
    } // end void assertThat_example()
} // end class EmployeeServiceTest
