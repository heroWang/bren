package templates;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils.Aggregations;
import com.sun.btrace.aggregation.Aggregation;
import com.sun.btrace.aggregation.AggregationFunction;
import com.sun.btrace.aggregation.AggregationKey;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.TLS;

@BTrace
public class HttpRequestTemplate {
    private static Aggregation histogram = Aggregations
	    .newAggregation(AggregationFunction.QUANTIZE);

    private static Aggregation average = Aggregations
	    .newAggregation(AggregationFunction.AVERAGE);

    private static Aggregation max = Aggregations
	    .newAggregation(AggregationFunction.MAXIMUM);

    private static Aggregation min = Aggregations
	    .newAggregation(AggregationFunction.MINIMUM);

    private static Aggregation sum = Aggregations
	    .newAggregation(AggregationFunction.SUM);

    private static Aggregation count = Aggregations
	    .newAggregation(AggregationFunction.COUNT);

    private static Aggregation globalCount = Aggregations
	    .newAggregation(AggregationFunction.COUNT);

    @TLS
    private static String requestURL;

    private static String body;

    /**
     * record all ibatis sql execution ,include maximum,minimum,sum time spent
     * and call number of times
     *
     * @param args
     */
    @OnMethod(clazz = "+javax.servlet.http.HttpServlet", method = "service",type="void service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)", location = @Location(Kind.RETURN))
    public static void onRequestReturn(@Duration long durationL) {
	if(requestURL==null){
	    return;
	}

	int duration = (int) durationL / (1000 * 1000);//get milliseconds

	String bodyStr=body==null?"":strcat(strcat("[",body),"]");
	//MARK<AGG-CLOSE>
	println(strcat(strcat(strcat(requestURL," "),bodyStr),strcat("  ", str(duration))));


	AggregationKey key = Aggregations.newAggregationKey(requestURL);
	Aggregations.addToAggregation(histogram, key, duration);
	Aggregations.addToAggregation(average, key, duration);
	Aggregations.addToAggregation(max, key, duration);
	Aggregations.addToAggregation(min, key, duration);
	Aggregations.addToAggregation(sum, key, duration);
	Aggregations.addToAggregation(count, key, duration);
	Aggregations.addToAggregation(globalCount, duration);

	requestURL=null;
    }

    @OnMethod(clazz = "+javax.servlet.http.HttpServlet", method = "service",type="void service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)")
    public static void onRequest(AnyType[] args) {
	requestURL = str(get(field(classOf(args[0]), "requestURI"), args[0]));

	println(requestURL);//DEBUG
    }

    @OnTimer(2000)
    public static void onTimer() {
	//MARK<AGG>
	/*println("---------------------------------------------");	Aggregations.truncateAggregation(average,   10);	Aggregations.printAggregation("Min", min);	Aggregations.printAggregation("Max", max);	Aggregations.printAggregation("Average", average);	println("---------------------------------------------");*/
	//Aggregations.printAggregation("Count", count);
	//Aggregations.printAggregation("Sum", sum);
	//Aggregations.printAggregation("Histogram", histogram);
	//Aggregations.printAggregation("Global Count", globalCount);
    }
}
