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
public class IbatisTemplate{

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
    private static String ibatisSQLID;

    private static String ibatisParam;

    /**
     * record all ibatis sql execution ,include maximum,minimum,sum time spent
     * and call number of times
     *
     * @param args
     */
    @OnMethod(clazz = "+org.apache.ibatis.executor.Executor", method = "/query|update/", location = @Location(Kind.RETURN))
    public static void onIbatisQueryReturn(@Duration long durationL) {
	if(ibatisSQLID==null){
	    return;
	}

	int duration = (int) durationL / (1000 * 1000);//get milliseconds

	String param=ibatisParam==null?"":strcat(strcat("[",ibatisParam),"]");
	//MARK<AGG-CLOSE>
	println(strcat(strcat(strcat(ibatisSQLID," "),param),strcat("  ", str(duration))));


	AggregationKey key = Aggregations.newAggregationKey(ibatisSQLID);
	Aggregations.addToAggregation(histogram, key, duration);
	Aggregations.addToAggregation(average, key, duration);
	Aggregations.addToAggregation(max, key, duration);
	Aggregations.addToAggregation(min, key, duration);
	Aggregations.addToAggregation(sum, key, duration);
	Aggregations.addToAggregation(count, key, duration);
	Aggregations.addToAggregation(globalCount, duration);

	ibatisSQLID=null;
    }

    @OnMethod(clazz = "+org.apache.ibatis.executor.Executor", method = "/query|update/")
    public static void onQuery(AnyType[] args) {
	ibatisSQLID = str(get(field(classOf(args[0]), "id"), args[0]));
	ibatisParam= str(args[1]);
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
