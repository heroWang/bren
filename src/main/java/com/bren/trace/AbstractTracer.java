package com.bren.trace;

import java.util.Arrays;
import java.util.List;

import com.bren.util.Constants;

public abstract class AbstractTracer implements Tracer {
    protected BScriptEditor bsEdt;
    protected ProcessAgent agent;



    protected List<String> getCommand(Integer pID, String btraceScriptFileName) {
	String[] command = { Constants.BTRACE_PATH+"/bin/btrace.bat", "-p", "2021", pID + "",
		btraceScriptFileName };
	return Arrays.asList(command);
    }

    @Override
    public void finalize() throws Throwable {
	if (bsEdt != null) {
	    bsEdt.delete();
	}
	super.finalize();
    }

    @Override
    public void stop() {
	if (this.agent != null) {
	    this.agent.destroy();
	}
	if (this.bsEdt != null) {
	    this.bsEdt.delete();
	}

    }

}
