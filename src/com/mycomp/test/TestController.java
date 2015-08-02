package com.mycomp.test;

import java.util.ArrayList;

public class TestController {
	private int numParallel;
	private int maxNumIterations;
	private String hostName;
	private ThreadPool threadPool;
	private ArrayList<TestStatus> statusArray;
	private ArrayList<HttpClient> clients;
	
	public TestController() {
		
	}
	public TestController(int numParallel, int maxNumIterations,
			String hostName) {
		this.numParallel = numParallel;
		this.maxNumIterations = maxNumIterations;
		this.hostName = hostName;
		statusArray = new ArrayList<TestStatus>();
		clients = new ArrayList<HttpClient>();
		for (int i = 0; i < numParallel; ++i) {
			TestStatus status = new TestStatus();
			statusArray.add(status);
			HttpClient client = new HttpClient(hostName);
			clients.add(client);
		}
		int numIterations = maxNumIterations/numParallel;
		this.threadPool = new ThreadPool(numParallel, statusArray, numIterations, clients);
	}
	/**
	 * @param numParallel 
	 * @return the numParrallelTests
	 */
	public int getNumParrallelTests(int numParallel) {
		return numParallel;
	}
	/**
	 * @param numParrallelTests the numParrallelTests to set
	 */
	public void setNumParrallelTests(int numParallel) {
		this.numParallel = numParallel;
	}
	/**
	 * @return the maxNumIterations
	 */
	public int getMaxNumIterations() {
		return maxNumIterations;
	}
	/**
	 * @param maxNumIterations the maxNumIterations to set
	 */
	public void setMaxNumIterations(int maxNumIterations) {
		this.maxNumIterations = maxNumIterations;
	}
	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}
	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	/**
	 *
	 */
	public void start() {
		this.threadPool.start();
	}
	public void stats() {
		for (int i = 0; i < this.numParallel; ++i) {
			System.out.println("Thread Info: " + i);
		    TestStatus status = this.statusArray.get(i);
			status.print();
			System.out.println();
		}
	}
	public void join() {
		this.threadPool.join();
	}
}
