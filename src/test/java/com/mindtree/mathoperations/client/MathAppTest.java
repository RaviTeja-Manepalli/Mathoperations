package com.mindtree.mathoperations.client;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mindtree.mathoperations.service.Service;
import com.mindtree.mathoperations.service.ServiceImpl;

public class MathAppTest {

	MathApp math = new MathApp();
	Service ser = new ServiceImpl();
	int value1 = 4;
	int value2 = 5;

	@Test
	public void addtest() {
		
		int result = ser.addition(value1, value2);
		assertTrue(result == 9);

	}
	
	@Test
	public void subtest() {

		int result2 = ser.subtraction(value1, value2);
		assertTrue(result2 == -1);

	}


}
