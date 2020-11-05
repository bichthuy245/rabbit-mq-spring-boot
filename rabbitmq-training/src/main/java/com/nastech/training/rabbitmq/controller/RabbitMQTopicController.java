package com.nastech.training.rabbitmq.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.training.rabbitmq.entity.Employee;

@RestController
@RequestMapping(value = "/tranning-rabbitmq/topic")
public class RabbitMQTopicController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "/producer")
    public String producer(@RequestParam("empName") String empName, @RequestParam("empId") String empId, @RequestParam("salary") int salary) {
        //Change to RequestBody
        Employee emp = new Employee();
        emp.setId(empId);
        emp.setName(empName);
        emp.setSalary(salary);

        amqpTemplate.convertAndSend("training.exchange", "tranning.queue", emp);
        return "Message sent to the Training RabbitMQ Topic Exchange Successfully";
    }
}
