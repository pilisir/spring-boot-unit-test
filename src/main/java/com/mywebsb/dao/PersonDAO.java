package com.mywebsb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.mywebsb.entity.Person;

@Repository
public interface PersonDAO extends JpaRepository<Person, Integer> {
	
	Person findByPersonID(int personID);
	
	@NativeQuery("SELECT * FROM Persons p WHERE p.PersonID = 3")
	Person findFirstPerson();
}
