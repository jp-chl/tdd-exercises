package com.jpvr.tddexercises.tdd;

import java.util.Collections;
import java.util.Map;

public final class EmployeeService {

    private Map<Integer, Employee> employeeMap = Collections.singletonMap(1, new Employee());

    public boolean isValidEmployee(Employee employee) {

        boolean isValid = false;

        if ( employee != null) {

            if ( employee.getId() != null && employee.getName() != null) {
                isValid = true;
            }
        }

        return isValid;
    } // end boolean isValidEmployee(Employee employee)

    public Double calculateTax(Employee employee) {

        Double tax = 0.0;

        if ( employee != null ) {

            Double salary = employee.getSalary();
            if ( salary > 0 ) {

                if ( salary < 500000 ) {

                    tax = salary * 0.05;
                } else if ( salary > 500000 && salary < 1000000 ) {

                    tax = salary * 0.1;
                } else {

                    tax = salary * 0.2;
                }
            }
        }

        return tax;
    } // end Double calculateTax(Employee employee)

    public Employee getEmployeeFromId(Integer id) {

        return employeeMap.get(id);
    } // end Employee getEmployeeFromId(Integer id)
} // end class EmployeeService
