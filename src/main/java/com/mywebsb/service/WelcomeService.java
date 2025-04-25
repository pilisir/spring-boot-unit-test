package com.mywebsb.service;

import org.springframework.stereotype.Service;

import com.mywebsb.dao.PersonDAO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WelcomeService {
	
	private final PersonDAO personDAO;
	
	public String getCurrentPersonFirstName(int personID) {
		return personDAO.findByPersonID(personID).getFirstName();
//		return personDAO.findFirstPerson().getCity();
	}
	
	public String getFirstPersonFirstName() {
		return personDAO.findFirstPerson().getFirstName();
	}

}
