package com.bren.trace;

public interface Tracer {
    public void execute(String[] args) throws IllegalArgumentException, Exception;
    public void stop();
}
