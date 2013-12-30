package com.bren.trace;

import java.util.List;

import com.bren.service.Session;

public class AllCallsTracer extends AbstractTracer{
    @Override
    public void execute(String[] args) throws Exception {
	if (args == null) {
	    throw new IllegalArgumentException();
	}
	if (args.length < 3) {
	    throw new IllegalArgumentException();
	}

	bsEdt = new BScriptEditor(ScriptTemplates.ALLCALLS);

	String className = args[1];
	String methodName = args[2];
	boolean showTimeSpent = false;
	boolean showStack = false;

	if (args.length >= 4) {
	    if (args[3].equals("time")) {
		showTimeSpent = true;
	    } else if (args[3].equals("stack")) {
		showStack = true;
	    }
	}
	if (args.length >= 5) {
	    if (args[4].equals("time")) {
		showTimeSpent = true;
	    } else if (args[4].equals("stack")) {
		showStack = true;
	    }
	}

	bsEdt.setValue("<CLASSNAME>", className);
	bsEdt.setValue("<METHODNAME>", methodName);
	if (showTimeSpent) {
	    bsEdt.open("MARK<TIME-SPENT>");
	}
	if (showStack) {
	    bsEdt.open("MARK<STACK>");
	}
	String btraceScriptFileName = bsEdt.save();

	List<String> bCommand = this.getCommand(Session.PID,
		btraceScriptFileName);
	agent = new ProcessAgent(bCommand);
	agent.start();

    }
}
