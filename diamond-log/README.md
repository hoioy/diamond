# hoioy开源框架之Diamond-log


## 如何使用

- 如果需要在其他项目上增加日志则需要引入diamond-log的依赖并且修改切入规则，否则日志是不生效的

~~~ xml
<dependency>
	<groupId>com.hoioy.diamond</groupId>
	<artifactId>diamond-log</artifactId>
</dependency>
~~~
- eg：
	本项目记录的是web层的日志，所以在diamond-sys-web的pom文件中引入diamond-log的依赖；
	其切入规则为："within(com.hoioy.diamond.*.web..*)"
	
~~~ java
	/**
	 * 前置通知，在目标方法完成之后调用通知，此时不会关 心方法的输出是什么 方法调用前触发 -记录开始时间
	 */

	@Before("within(com.hoioy.diamond.*.web..*)")
	public void beforeAdvice(JoinPoint joinPoint) {
 		......
 	}

	/**
	 * 后置通知，在目标方法完成之后调用通知，此时不会关 心方法的输出是什么
	 */
	@After("within(com.hoioy.diamond.*.web..*)")
	public void afterAdvice(JoinPoint joinPoint) {
		......
	}
~~~


## 日志开关

我们可以通过"开关"来控制是否开启日志记录功能，比如在开发环境下可以选择暂时关闭，而在生产环境中则需要开启日志记录功能。

- 开关配置

通过sample主项目中的配置文件"application.properties"中的"diamond.log.lock=off"来控制是否记录日志，off为不记录，on为记录。

~~~ properties
#日志开关  off关闭  on打开
diamond.log.lock=off
~~~

##日志规则
日志的记录有两种规则：系统默认的记录日志方法以及自定义注解的方式

### 默认

- 日志会根据切入点（"within(com.hoioy.diamond.*.web..*)"---包路径）自定记录日志，切入点在"WebMonitorLog"类中；如路径不同，则需要修改切入点。

### 自定义

- 还支持自定义注解方式记录日志，通过在想要记录日志的方法上增加自定义注解"OperationLogAnno"

- eg:
 
 ~~~ java
	@OperationLogAnno(module="模块名称 ",logInfo="方法描述",logClassName="dept",logOperationType= LogOperationType.query)
	@RequestMapping(value = "/dept-state", method = { RequestMethod.POST })
	public String DeptStateList(Model model, @RequestParam(value = "models", required = false) String models) {
		Map searchParameters = new HashMap();
		 
		 ......
		 
		return ".....";
	} 
 ~~~
 - LogOperationType是自定义的枚举类，一些常用操作方法。
 
 ~~~ java
    public final static String add = "add";
	public final static String delete ="delete";
	public final static String edit ="edit";
	public final static String query = "query";
	...
	...
 ~~~
 
 ## WebLogs实体说明：
 
 ~~~ java
    private String id; //主键ID
	private String logUserName;//操作人
	private String startTime;//开始时间
	private String endTime;//结束时间 
	private String logInfo;//描述
	private String logOperationType;//操作类型 add  edit  。。。
	private String logTableName;//表名
	private String logPrimaryKey;//表主键
	private String logClassName;//controller名
	private String module;//模块名 sys级别
	private String remark;//备注
	private String logMethodName;//方法名
	private String logUrl;//日志路径
	private Integer flag;//是否删除
	private String logServerIp;//服务器ip
	private String logClientIp;//操作ip
 ~~~
