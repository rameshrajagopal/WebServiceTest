package com.mycomp.test;

public class WebServiceTest {

	public static void main(String[] args) {
		final int NUM_OF_PARALLEL_TESTS = 2;
		final int MAX_NUMBER_REQUESTS = 100;
		final String HOST_NAME = "127.0.0.1:8080";
		TestController testController = new TestController(NUM_OF_PARALLEL_TESTS, MAX_NUMBER_REQUESTS, HOST_NAME);
		testController.start();
		testController.join();
		testController.stats();
	}

}
