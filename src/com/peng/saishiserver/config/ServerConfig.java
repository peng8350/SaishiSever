package com.peng.saishiserver.config;

public class ServerConfig {
	
	
	//七牛的key
	public static final String qiniu_key = "m3ocnb0NbFNVJxl7ngL2yXl0nixd_hg90zZV48BJ";
	//七牛的密码
	public static final String qiniu_pswd= "BsXq8os85h7mfjliO-l3xVs1P512Of46JFKT2uoX";
	// 暂时存储图片的位置
	public static final String statement = "..";
	public static final String pic_pos = statement + "/images/";
	public static final String radio_pos = statement + "/radios/";
	// 当前App
	public static final String versionName = "1.2";
	// 二维码位置
	// 数据哭的地址
	public static final String ADDRESS = "jdbc:mysql://localhost:3306/saishi";
	// 数据库账号
	public static final String USER = "peng";
	// 数据库密码
	public static final String PSWD = "28117896";
	// 数据库端口
	public static final int PORT = 3306;


}
