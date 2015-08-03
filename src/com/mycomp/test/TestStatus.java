package com.mycomp.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestStatus {
	private static final int RESPONSE_OK = 1;
	private static final int RESPONSE_4XX = 2;
	private static final int RESPONSE_5XX = 3;
	private int numRequests;
	private int numSuccess;
	private int num4XXRequests;
	private int num5XXRequests;
	private int totalResponseTime;
	
	class RequestStatus{
		private int requestNumber;
		private double responseTime;
		
		public RequestStatus() {
			
		}
		public RequestStatus(int requestNumber, double responseTime) {
			this.requestNumber = requestNumber;
			this.responseTime = responseTime;
		}
		public double getResponseTime() {
			return this.responseTime;
		}
	}
	private List<RequestStatus> reqStatus;
	
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
		this.totalResponseTime += (responseTime/1000000);
		RequestStatus req = new RequestStatus(reqNumber, responseTime/1000000);
		//System.out.println(responseTime/1000000);
		this.reqStatus.add(req);
	}
	private void printSDAndMean(List<RequestStatus> reqStatus, double totalSum, int numRequests) {
		double mean = 0, sum = 0;
		
		mean = totalSum/numRequests;
		System.out.println("TotalSum: " + totalSum + "Mean: " + mean);
		sum = 0;
		for (int i = 0; i < numRequests; ++i) {
			RequestStatus req = reqStatus.get(i);
			
			//System.out.println(req.responseTime);
		
			sum += Math.pow(req.responseTime - mean, 2);
		}
		double sd = Math.sqrt((sum/numRequests));
		System.out.println("Standard Deviation: " + sd + " " + reqStatus.get(this.numRequests-1).getResponseTime());
	}
	private int printPercentile(List<RequestStatus> reqStatus, double min, double max, int startIdx) {
		int requests = 0;
		for  (int i = startIdx; i < this.numRequests; ++i) {
			RequestStatus req = reqStatus.get(i);
			if (req.responseTime >= min && req.responseTime <= max) {
				requests += 1;
			} else {
				break;
			}
		}
		return requests;
	}
	private void printAllPercentile(List<RequestStatus> reqStatus) {
		double min = reqStatus.get(0).responseTime;
		double max = reqStatus.get(this.numRequests-1).getResponseTime();
		double onePercentile = (max-min)/100;
		int  requests = 0, totalRequests = 0;
		//printing for 99 percentile
		double tempMax = min + onePercentile;
		requests = printPercentile(reqStatus, min, tempMax, 0);
		System.out.println("99 percentile: " +  requests);
		totalRequests += requests;
		min = tempMax;
		tempMax = min + (4 * onePercentile);
		requests = printPercentile(reqStatus, min, tempMax, this.numRequests - totalRequests);
		totalRequests += requests;
		System.out.println("95 percentile: " +  requests);
		min = tempMax;
		tempMax = min + (5 * onePercentile);
		requests = printPercentile(reqStatus, min, tempMax, this.numRequests - totalRequests);
		totalRequests += requests;
		min = tempMax;
		tempMax = min + (40 * onePercentile);
		requests = printPercentile(reqStatus, min, tempMax, this.numRequests - totalRequests);
		System.out.println("50 percentile: " + requests);
		//10th percentile
		totalRequests += requests;
		min = tempMax;
		tempMax = min + (40 * onePercentile);
		requests = printPercentile(reqStatus, min, tempMax, this.numRequests - totalRequests);
		System.out.println("10 percentile: " + requests);
	}
	
	public void print() {
		System.out.println("Num of Requests: " + this.numRequests);
		System.out.println("Num of Success: " + this.numSuccess);
		System.out.println("Num of 4XX errors: " + this.num4XXRequests);
		System.out.println("Num of 5XX errors: " + this.num5XXRequests);
		System.out.println("Requests Info: ");
		Collections.sort(this.reqStatus, new Comparator<RequestStatus>() {
			@Override
			public int compare(RequestStatus req1, RequestStatus req2) {
				if (req1.getResponseTime() > req2.getResponseTime()) {
					return 1;
				} else if (req1.getResponseTime() < req2.getResponseTime()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		printSDAndMean(this.reqStatus, this.totalResponseTime, this.numRequests);
		printAllPercentile(this.reqStatus);
	}
}
