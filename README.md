对btrace的命令行封装框架。

目前支持的命令有:<br/>
<p>
<ol>
  <li>CALL &lt;full class path&gt; &lt;methodname&gt; [options] 用于调试任何方法的调用,默认显示方法传入参数<br/>
  参数:<br/>
  time:显示方法执行时间<br/>
  stack:显示方法调用栈<br/>
  </li>
<li>IBATIS [options] 监视所有ibatis的数据库访问,默认显示SQL ID,传入参数和执行时间<br/>
  参数:<br/>
  agg:对监视结果进行统计.<br/>
</li>
</p>
