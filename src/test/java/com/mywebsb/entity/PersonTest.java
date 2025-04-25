package com.mywebsb.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mywebsb.dao.PersonDAO;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@AllArgsConstructor
class PersonTest {

//	private static final Logger logger = LoggerFactory.getLogger(PersonTest.class);
	
	private Person p = new Person();
	private String stubFirstName = "傑森2";

	@Mock
	private PersonDAO personDao;

//	@InjectMocks
//	private WelcomeService welcomeService;

	@Test
	void givenFirstPerson_whenPerson_returnFirstPersonName() {
//		logger.debug("===== Person Test Start =====");
//		logger.debug("===== Person Test TRUE =====");

		Mockito.when(personDao.findFirstPerson()).thenReturn(p);
		String actual = personDao.findFirstPerson().getFirstName();
//		String actual = welcomeService.getFirstPersonFirstName();

		Assertions.assertNotNull(actual);
		Assertions.assertEquals(this.stubFirstName, actual);
	}
	
	@BeforeEach
	void beforeTest() {
		this.p.setFirstName(this.stubFirstName);
	}
}
