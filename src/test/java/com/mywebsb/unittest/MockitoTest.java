package com.mywebsb.unittest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MockitoTest {
	
	@Spy
	List<String> mockList = new ArrayList<String>();
	
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

}
