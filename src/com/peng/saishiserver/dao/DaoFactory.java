package com.peng.saishiserver.dao;

public class DaoFactory {
	
	public static DaoProcessor createProcessor(){
		return new DaoProcessor();
	}

}
