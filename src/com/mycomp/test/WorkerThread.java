package com.mycomp.test;

public class WorkerThread extends Thread{
	private TestStatus status;
	private int threadNumber;
	private String hostName;
	private int numIterations;
	private HttpClient client;
	
	public WorkerThread() {
		
	}
	public WorkerThread(int tNumber, int numIterations, TestStatus status, HttpClient http) {
		this.status = status;
		this.threadNumber = tNumber;
		this.numIterations = numIterations;
		this.client = http;
	}
	@Override
	public void run() {
		testServerAPI();
	}
	
	private void testServerAPI() {
		Thread t = Thread.currentThread();
		System.out.print("Thread ID : " + t.getName());
		for (int i = 0; i < this.numIterations * 2; i += 1) {
			String response;
			response = this.client.handleHttpGETMessage("index");
			if (response.equals("4XX")) {
				this.status.addRequest(2, 1, 0);
			} else if(response.equals("5XX")) {
				this.status.addRequest(3, i, 0);
			} else {
				//process the response and get the e-mail id and uuid
				System.out.println(t.getName() + " POST");
				this.status.addRequest(1, i, 0);
				response = this.client.handleHttpPOSTMessage("index", "uuid");
				if (response.equals("4XX")) {
					this.status.addRequest(2, i+1, 0);
				} else if (response.equals("5XX")) {
					this.status.addRequest(3, i+1, 0);
				} else {
					this.status.addRequest(1, i+1, 0);
				}
			}
		}
	}
}
