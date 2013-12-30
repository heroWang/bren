package com.bren.trace;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bren.util.Byte_File_Object;
import com.bren.util.Constants;

/**
 * Btrace脚本生成工具
 *
 * @author hawkinswang
 *
 */
public class BScriptEditor {
    private String buffer;
    private static final String TEMPLATE_PATH = Constants.SCRIPT_PATH
	    + "/templates/";
    private static final String SCRIPT_PATH = Constants.SCRIPT_PATH + "/temp/";
    private final ScriptTemplates scriptTemplate;

    public BScriptEditor(ScriptTemplates st) throws Exception {
	scriptTemplate = st;
	File file = null;
	try {
	    file = new File(TEMPLATE_PATH + st.getFileName() + ".java");
	} catch (NullPointerException nullE) {
	    throw nullE;
	}
	;
	buffer = new String(Byte_File_Object.getBytesFromFile(file));
    }

    public void setValue(String key, String value) {
	buffer = buffer.replace(key, value);
    }

    public void open(String mark) {
	String regex = mark;
	int index=buffer.indexOf(regex);
	Matcher matcher = Pattern.compile("(/\\*.+\\*/)|(//).*", Pattern.MULTILINE).matcher(buffer.substring(index));
	if (matcher.find()) {
	    String matched = matcher.group();
	    buffer = buffer.replace(matched,
		    matched.replaceAll("(/\\*)|(\\*/)|(//)", ""));
	}
    }
    public void close(String mark){
	String regex = mark;
	int index=buffer.indexOf(regex);
	Matcher matcher = Pattern.compile("\\n.*", Pattern.MULTILINE).matcher(buffer.substring(index));
	if (matcher.find()) {
	    String matched = matcher.group();
	    buffer = buffer.replace(matched,
		    "/*"+matched+"*/");
	}
    }

    public String save() {
	String fileFullPath = SCRIPT_PATH + scriptTemplate.getFileName()
		+ ".java";
	Byte_File_Object.getFileFromBytes(buffer.getBytes(), fileFullPath);
	return fileFullPath;
    }

    public void delete() {
	new File(SCRIPT_PATH + scriptTemplate.getFileName() + ".java").delete();
    }

}
