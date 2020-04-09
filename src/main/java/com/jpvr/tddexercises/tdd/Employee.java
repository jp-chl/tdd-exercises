package com.jpvr.tddexercises.tdd;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Employee {

    private Integer id;
    private String name;
    private Double salary;

    public Employee() {
    }

    @Override
    public boolean equals(Object that) {

        return EqualsBuilder.reflectionEquals(this, that);
    } // end boolean equals(Object that) {

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    } // end int hashCode()

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
