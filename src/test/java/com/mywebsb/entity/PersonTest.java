package com.mywebsb.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mywebsb.dao.PersonDAO;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@AllArgsConstructor
class PersonTest {

	private static final Logger logger = LoggerFactory.getLogger(PersonTest.class);

	@Spy
	List<String> mockList = new ArrayList<String>();
	
	@Mock
	private PersonDAO personDao;

	private Person p = new Person();

//	@InjectMocks
//	private WelcomeService welcomeService;

	@Test
	void givenFirstPerson_whenPerson_returnFirstPersonName() {
		logger.debug("===== Person Test Start =====");
//		logger.debug("===== Person Test TRUE =====");

		Mockito.when(personDao.findFirstPerson()).thenReturn(p);
		String actual = personDao.findFirstPerson().getFirstName();
//		String actual = welcomeService.getFirstPersonFirstName();
		String expect = "傑森2";

		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expect, actual);
	}
	
	@Test
	void mockArray() {
//		List<String> mockList = Mockito.mock(ArrayList.class);
	    
	    mockList.add("one");
	    mockList.add("two");
	    System.out.println("===== Array Test SIZE ===== " + mockList.size());
//	    Mockito.verify(mockList).add("one");
//	    Mockito.verify(mockList, Mockito.times(2));
	    Mockito.doReturn("100").when(mockList).get(2);
	    System.out.println("===== Array Test SIZE ===== " + mockList.size());
	    Assertions.assertEquals("100", mockList.get(2));
	    
	    
//	    Mockito.when(mockList.size()).thenReturn(100);
//	    Assertions.assertEquals(2, mockList.size());
	}

	@BeforeEach
	void beforeTest() {
		this.p.setFirstName("傑森");
	}
}
