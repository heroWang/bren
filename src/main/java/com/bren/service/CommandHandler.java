package com.bren.service;

import com.bren.trace.AllCallsTracer;
import com.bren.trace.IbatisTracer;
import com.bren.trace.Tracer;

public enum CommandHandler {
    CALL( new AllCallsTracer()),IBATIS(new IbatisTracer());
    private Tracer tracer;

    CommandHandler(Tracer tracer) {
	this.tracer = tracer;
    }


    public Tracer getTracer() {
        return tracer;
    }

}
