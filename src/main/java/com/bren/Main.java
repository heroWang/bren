package com.bren;

import java.io.File;
import java.net.URLDecoder;
import java.security.CodeSource;

import com.bren.console.CommandView;
import com.bren.service.CommandListener;
import com.bren.util.Constants;

public class Main {
    public static void main(String[] args) throws Exception {
	if(!initSettings(args)){
	    System.out.println("缺失参数");
	    return;
	}
	CommandView view=new CommandView();
	CommandListener listener =new CommandListener();
	view.addCommandListener(listener);
	view.start();
	listener.start();
    }

    public static boolean initSettings(String[] args) throws Exception{
	if(args==null){
	    return false;
	}
	if(args.length<2){
	    return false;
	}
	Constants.BTRACE_PATH=getAbsolutePath(args[0]);
	Constants.SCRIPT_PATH=getAbsolutePath(args[1]);

	return true;
    }

    public static String getAbsolutePath(String path) throws Exception{
	if(new File(path).isAbsolute()){
	    return new File(path).getCanonicalPath();
	}
	return new File(getExecutedPath(Main.class)+path).getCanonicalPath();
    }


    public static String getExecutedPath(Class<?> aclass) throws Exception {
  	CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();

  	File file;

  	if (codeSource.getLocation() != null) {
  	    file = new File(codeSource.getLocation().toURI());
  	} else {
  	    String path = aclass.getResource(aclass.getSimpleName() + ".class")
  		    .getPath();
  	    String filePath = path.substring(path.indexOf(":") + 1,
  		    path.indexOf("!"));
  	    filePath = URLDecoder.decode(filePath, "UTF-8");
  	    file = new File(filePath);

  	}
  	if (file.isFile()) {//if executed with jar file
  	    return file.getParentFile().getCanonicalPath();
  	} else {
  	    return file.getCanonicalPath();
  	}
      }
}
