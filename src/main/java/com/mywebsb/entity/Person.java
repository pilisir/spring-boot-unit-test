package com.mywebsb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Persons")
@Setter @Getter
public class Person {
	@Id
	@Column(name = "PersonID")
	private int personID;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "Address")
	private String address;
	
	@Column(name = "City")
	private String city;
	
}
