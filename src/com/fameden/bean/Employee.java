package com.fameden.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="asdsadsadsa")
public class Employee {
	@Id
	private int empId;
	private String empName;
	private String empDOB;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpDOB() {
		return empDOB;
	}

	public void setEmpDOB(String empDOB) {
		this.empDOB = empDOB;
	}
}
