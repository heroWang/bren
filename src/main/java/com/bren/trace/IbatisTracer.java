package com.bren.trace;

import java.util.List;

import com.bren.service.Session;

public class IbatisTracer extends AbstractTracer{
    @Override
    public void execute(String[] args) throws Exception {
	if (args == null) {
	    throw new IllegalArgumentException();
	}
	if (args.length < 1) {
	    throw new IllegalArgumentException();
	}

	bsEdt = new BScriptEditor(ScriptTemplates.IBATIS);

	boolean showAgg = false;

	if (args.length >= 2) {
	    if (args[1].equals("agg")) {
		showAgg = true;
	    }
	}

	if (showAgg) {
	    bsEdt.open("MARK<AGG>");
	    bsEdt.close("MARK<AGG-CLOSE>");
	}
	String btraceScriptFileName = bsEdt.save();

	List<String> bCommand = this.getCommand(Session.PID,
		btraceScriptFileName);
	this.agent = new ProcessAgent(bCommand);
	agent.start();
    }
}
