package com.jpvr.tddexercises.tdd;

import java.util.List;

public class EmployeeRepository {

    private List<Employee> employeeList;

    public EmployeeRepository(List<Employee> employeeList) {
        this.employeeList = employeeList;
    } // end EmployeeRepository(List<Employee> employeeList)

    public Employee findById(int id) {

        for(Employee employee : employeeList) {

            if ( employee.getId() == id ) {
                return employee;
            }
        } // end iteration

        return null;
    } // end Employee findById(int id)
} // end class EmployeeRepository
