package com.bren.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Session {
    private static BlockingQueue<String> handleResponses = new LinkedBlockingDeque<String>();
    private static String viewInput;
    public static Integer PID;// 监听端口

    public static String getHandleResponse() {
	String line = null;
	try {
	    line = handleResponses.take();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	return line;
    }

    public static  void addHandleResponse(String line) {
	try {
	    handleResponses.put(line);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}
