package vo;

//******************************************************************
/**
 * 类名: com.isoftstone.platform.constants.PltmConstants <pre>
 * 描述: 平台常用常熟定义
 * 版权: Copyright (C) 2013  软通动力版权所有
 * 创建时间: 2014-5-13 上午9:28:50
 * </pre>
 */
//******************************************************************
public final class PltmConstants {
	/**  日志包需要保存标志  		PlatformCoreService使用 **/
	public static final String SAVE = "1";
	
	/** 日志包需要更新标志  		PlatformCoreService使用 **/
	public static final String UPDATE = "2";
	
	/** 报文与平台交互成功  		PlatformCoreService使用**/
	public static final String SUCCESS = "1";
	
	/** 报文与平台交互失败 		PlatformCoreService使用**/
	public static final String FAIL = "0";
	
	/** 该报文被访问一次 			PlatformCoreService使用**/
	public static final long ONCE = 1L;
	
	/** 业务核心参数 				PlatformService使用**/
	public static final String IMPORTANT_PARAMS = "ImportantControlParameter";
	
	/** <字段>可以为空<字段> 			**/
	public static final String NULL_ON = "1";
	
	/** <字段>不能为空<字段> 	PltmHelperService使用**/
	public static final String NULL_OFF = "0";
	
	/** <字段>需要转义<字段> 	PlatformService使用，PltmHelperService使用**/
	public static final String TRANSFER_ON = "1";
	
	/** <字段>不需要转义<字段> 			**/
	public static final String TRANSFER_OFF = "0";
	
	/**  外部到平台标志			PlatformService使用, PltmHelperService使用**/
	public static final String TO_PLATFORM = "1";
	
	/** 平台到外部标志			PlatformService使用**/
	public static final String TO_OUTER = "0";
	
	
	/**  启用标志 即虚拟增加   **/
	public static final String ENABLE = "1";
	
	/**  不启用标志  ，即虚拟删除 **/
	public static final String DISABLE = "0";
	
}
