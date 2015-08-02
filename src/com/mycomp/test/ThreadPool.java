package com.mycomp.test;

import java.util.ArrayList;

public class ThreadPool {
	 private int numThreads;
     private ArrayList<WorkerThread> workerThreads = new ArrayList<WorkerThread>();
     
     public ThreadPool() {
    	
     }
     public ThreadPool(int numThreads, ArrayList<TestStatus> status, int numIterations, ArrayList<HttpClient> clients) {
    	 this.numThreads = numThreads;
    	 for(int i = 0; i < numThreads; ++i) {
    		 WorkerThread wh = new WorkerThread(i, numIterations, status.get(i), clients.get(i));
    		 workerThreads.add(wh);
    	 }
     }
     public void start() {
    	 for (int i = 0; i < numThreads; ++i) {
    		 WorkerThread wh = workerThreads.get(i);
    		 wh.start();
    	 }
     }
     public void join() {
    	 for (int i = 0; i < numThreads; ++i) {
    		 WorkerThread wh = workerThreads.get(i);
    		 try {
    			 wh.join();
    		 }catch (InterruptedException e) {
    			 e.printStackTrace();
    		 }
    	 }
     }
}
