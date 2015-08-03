package com.mycomp.test;

import java.util.ArrayList;

public class TestStatus {
	private static final int RESPONSE_OK = 1;
	private static final int RESPONSE_4XX = 2;
	private static final int RESPONSE_5XX = 3;
	private int numRequests;
	private int numSuccess;
	private int num4XXRequests;
	private int num5XXRequests;
	
	class RequestStatus{
		private int requestNumber;
		private double responseTime;
		
		public RequestStatus() {
			
		}
		public RequestStatus(int requestNumber, double responseTime) {
			this.requestNumber = requestNumber;
			this.responseTime = responseTime;
		}
	}
	private ArrayList<RequestStatus> reqStatus;
	
	public TestStatus() {
		this.numRequests = 0;
		this.numSuccess = 0;
		this.num4XXRequests = 0;
		this.num5XXRequests = 0;
		reqStatus = new ArrayList<RequestStatus>();
	}
	public void addRequest(int status, int reqNumber, double responseTime) {
		this.numRequests += 1;
		switch (status) {
		case RESPONSE_OK:
			this.numSuccess += 1;
			break;
		case RESPONSE_4XX:
			this.num4XXRequests += 1;
			break;
		case RESPONSE_5XX:
			this.num5XXRequests += 1;
			break;
		default:
			System.out.println("Invalid status code "+ status);
			break;
		}
		RequestStatus req = new RequestStatus(reqNumber, responseTime);
		this.reqStatus.add(req);
	}
	public void print() {
		System.out.println("Num of Requests: " + this.numRequests);
		System.out.println("Num of Success: " + this.numSuccess);
		System.out.println("Num of 4XX errors: " + this.num4XXRequests);
		System.out.println("Num of 5XX errors: " + this.num5XXRequests);
		System.out.println("Requests Info: ");
		for (int i = 0; i < this.numRequests; ++i) {
			RequestStatus reqStatus = this.reqStatus.get(i);
			System.out.println(reqStatus.requestNumber + " " + reqStatus.responseTime);
		}
	}
}
