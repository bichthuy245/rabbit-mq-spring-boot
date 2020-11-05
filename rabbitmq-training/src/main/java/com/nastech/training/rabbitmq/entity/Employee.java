package com.nastech.training.rabbitmq.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Employee.class)
@Getter
@Setter
public class Employee {
    private String name;
    private String id;
    private int salary;

    @Override
    public String toString() {
        return "Employee [Name=" + name + ", id=" + id + ", salary=" + salary + "]";
    }
}
