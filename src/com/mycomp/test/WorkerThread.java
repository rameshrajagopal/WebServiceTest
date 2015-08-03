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
		//Thread t = Thread.currentThread();
		long startTime, endTime;
		//System.out.println("Thread ID : " + t.getName());
		for (int i = 0; i < this.numIterations * 2; i += 1) {
			String response, uuid;
			String email = "mail2.rameshr@gmail.com";
			
			startTime = System.nanoTime();
			response = this.client.handleHttpGETMessage(email);
			endTime = System.nanoTime();
			if (response.equals("4XX")) {
				this.status.addRequest(2, 1, endTime - startTime);
			} else if(response.equals("5XX")) {
				this.status.addRequest(3, i, endTime - startTime);
			} else {
				//process the response and get the e-mail id and uuid
				uuid = response;
				this.status.addRequest(1, i, endTime - startTime);
				startTime = System.nanoTime();
				response = this.client.handleHttpPOSTMessage(email, uuid);
				endTime = System.nanoTime();
				if (response.equals("4XX")) {
					this.status.addRequest(2, i+1, endTime - startTime);
				} else if (response.equals("5XX")) {
					this.status.addRequest(3, i+1, endTime - startTime);
				} else {
					this.status.addRequest(1, i+1, endTime - startTime);
				}
			}
		}
	}
}
