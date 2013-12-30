package com.bren.trace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.bren.service.Session;

public class ProcessAgent extends Thread {
    private final List<String> command;
    private Process proc;
    private InputStream inputStream;
    private boolean running;

    public ProcessAgent(List<String> bCommand) throws IOException {
	this.command = bCommand;
    }

    @Override
    public void run() {
	final ProcessBuilder builder = new ProcessBuilder(command);
	try {
	    proc = builder.start();
	    this.running = true;

	    this.inputStream = proc.getInputStream();
	    BufferedReader br = new BufferedReader(new InputStreamReader(
		    inputStream));

	    String line = null;
	    while ((line = br.readLine()) != null && this.running) {
		Session.addHandleResponse(line);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		inputStream.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    public void destroy() {
	this.running = false;
	proc.destroy();
    }

}
