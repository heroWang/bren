对btrace的命令行封装框架。

目前支持的命令有:
1.CALL <full class path> <methodname> [options] 用于调试任何方法的调用,默认显示方法传入参数
  参数:
  time:显示方法执行时间
  stack:显示方法调用栈
2.IBATIS [options] 监视所有ibatis的数据库访问,默认显示SQL ID,传入参数和执行时间
  参数:
  agg:对监视结果进行统计.
