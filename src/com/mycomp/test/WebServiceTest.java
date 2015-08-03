package com.mycomp.test;

public class WebServiceTest {

	public static void main(String[] args) {
		final int NUM_OF_PARALLEL_TESTS = 10;
		final int MAX_NUMBER_REQUESTS = 100;
		final String HOST_NAME = "surya-interview.appspot.com";
		final String URI_NAME = "message";
		TestController testController = new TestController(NUM_OF_PARALLEL_TESTS, MAX_NUMBER_REQUESTS, HOST_NAME, URI_NAME);
		testController.start();
		testController.join();
		testController.stats();
	}

}
