package com.mypkg.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Batch_Employee")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	private String id;
	private String name;
	private String address;
	private Float salary;
	private Float grosssalary;
	private Float netsalary;
}
