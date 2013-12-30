package com.bren.service;

import com.bren.console.CommandView;

public class CommandListener extends Thread {
    private CommandView view;
    private boolean running;
    private CommandHandler curCommandHandler;

    public void deal(String line) {
	String[] commandArr = line.split("\\s+");

	if (commandArr.length < 1) {
	    view.write("Illegal command");
	}

	String commandName = commandArr[0];
	commandName = commandName.toUpperCase();
	try {
	    curCommandHandler = CommandHandler.valueOf(commandName);
	    curCommandHandler.getTracer().execute(commandArr);// 执行命令

	    this.waitForStop();
	    //stop command executing
	   curCommandHandler.getTracer().stop();
	} catch (IllegalArgumentException e) {
	    view.write("Unsupported parameter,run help for usage detail ");
	} catch (Exception e) {
	    view.write(e.getMessage());
	}
    }

    private void waitForStop() {
	while (view.readCharacter() != 113){
	  }
    }

    @Override
    public void run() {
	while (running) {
	    String line = Session.getHandleResponse();
	    view.write(line);
	}
    }

    @Override
    public void start() {
//	view.bindKey('q', new ActionListener() {
//	    @Override
//	    public void actionPerformed(ActionEvent e) {
//		view.write("aha");
//		if (curCommandHandler != null)
//		    curCommandHandler.getTracer().stop();
//	    }
//	}); WAIT FOR NEW VERSION JLINE2 TO RESOLVE THIS

	this.running = true;
	super.start();
    }

    @Override
    public void destroy() {
	this.running = false;
	if (curCommandHandler != null)
	    curCommandHandler.getTracer().stop();
    }

    public void setCommandView(CommandView commandView) {
	this.view = commandView;
    }

}
