package com.bren.console;

import java.awt.event.ActionListener;
import java.io.IOException;

import jline.console.ConsoleReader;

import com.bren.service.CommandListener;
import com.bren.service.Session;

public class CommandView extends Thread {
    private ConsoleReader console;
    private CommandListener listener;
    private boolean running;

    public CommandView() {
	try {
	    console = new ConsoleReader();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void run() {
	usage();
	if (listener == null) {
	    return;
	}

	try {
	    String line;
	    String PID;
	    do{
	       this.write("input the PID of Process you monitor ");
	       PID = console.readLine("\u001B[0m$ ");
	    }  while (!PID.matches("^[0-9]+$")) ;
		Session.PID = Integer.parseInt(PID);

	    while ((line = console.readLine("\u001B[0m$ ")) != null && running) {
		if((line=line.trim()).equals("")){
		    console.delete();
		    continue;
		}
		if (line.equalsIgnoreCase("quit")
			|| line.equalsIgnoreCase("exit")) {
		    this.destroy();
		    listener.destroy();
		    System.exit(0);
		    break;
		}
		listener.deal(line);
	    }

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }


    public void write(String str) {
	try {
	    this.console.println("\u001B[33m" + str);
	    console.flush();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private void usage() {
	this.write("\u001B[33mwelcome to use bren monitor,run \"help\" to display the usage\u001B[0m");
    }

    public static void main(String[] args) {
	new CommandView().start();
    }

    public void addCommandListener(CommandListener listener) {
	this.listener = listener;
	listener.setCommandView(this);
    }

    public void bindKey(char key,ActionListener listener){
	console.addTriggeredAction(key, listener);
    }

    @Override
    public void start(){
	this.running=true;
	super.start();
    }

    @Override
    public void destroy(){
	this.running=false;
    }


    public int readCharacter() {
	try {
	    return this.console.readCharacter();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return -1;
    }

}
