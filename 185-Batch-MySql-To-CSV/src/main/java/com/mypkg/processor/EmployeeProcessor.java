package com.mypkg.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.mypkg.model.Employee;

@Component
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee item) throws Exception {
		if (item.getSalary() <= 400000)
			return null;
		item.setGrosssalary(item.getSalary() - (item.getSalary() * 10) / 100);
		item.setNetsalary(item.getSalary() - (item.getSalary() * 20) / 100);

		return item;
	}

}
