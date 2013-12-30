package templates;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Self;


@BTrace
public class AllCallsTemplate {
    @OnMethod(clazz = "<CLASSNAME>", method = "<METHODNAME>", location=@Location(Kind.RETURN))
    public static void getArgs(/*AnyType[] args*/@Duration long durationL) {
	//MARK<TIME-SPENT>
	/*double seconds=durationL/(1000*1000); println(strcat("time spent(millisecond):",str(seconds)));*/
	//MARK<STACK>
	//BTraceUtils.jstack();

	println("-----------------------------------------end-----------------------------------------");
    }

    @OnMethod(clazz="<CLASSNAME>",method="<METHODNAME>")
    public static void callThirdPartyFunc(@Self Object self, @ProbeMethodName String pmn, AnyType[] args){
	//println("Executor  is called");//DEBUG EXECUTED
	println(strcat(strcat(strcat(BTraceUtils.str(classOf(self)),"."),pmn)," is called"));
	print("params:");printArray(args);

    }
}
