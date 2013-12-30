package com.bren.trace;

public enum ScriptTemplates {
    ALLCALLS("AllCallsTemplate"),IBATIS("IbatisTemplate");

    private String fileName;

    private ScriptTemplates(String fileName){
	this.fileName=fileName;
    }
    public String getFileName() {
	return fileName;
    }
}
