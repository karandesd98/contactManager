package com.sk.contactManager.contactManager.security;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class MultipleResultQuery {
	private Connection con;
	private java.sql.CallableStatement cStatement;
	private Integer parameterCounter = 1;
	private Map<String, Object> namedParameterMap;
	private String query;
	
	
	public MultipleResultQuery(Connection con, String query) throws SQLException {
		this.con = con;
		this.query = query;
		this.namedParameterMap = new HashMap<String, Object>();
		
	}
	
	
	public MultipleResultQuery setString(String namedParameter, String value) {
		this.namedParameterMap.put(namedParameter, value);
		return this;
	}
	
	public MultipleResultQuery setInt(String namedParameter, Integer value) {
		this.namedParameterMap.put(namedParameter, value);
		return this;
	}
	
	public MultipleResultQuery setDate(String namedParameter, Date value) {
		this.namedParameterMap.put(namedParameter, value);
		return this;
	}
	
	public MultipleResultQuery setObject(String namedParameter, Object value) {
		this.namedParameterMap.put(namedParameter, value);
		return this;
	}
	
	
	public CallableStatement getCallableStatement() {
		return this.cStatement;
	}

	public MultipleResultQuery setDouble(String namedParameter, Double value) {
		this.namedParameterMap.put(namedParameter, value);
		return this;
		
	}
	
	private void prepareCallWithNamedParameterToIndexedParameter() throws SQLException {
		Map<Integer, String> keyMap = new TreeMap<Integer, String>();
    	if(!this.namedParameterMap.isEmpty())
		for(String str : this.namedParameterMap.keySet()) {
    		keyMap.put(this.query.indexOf(":"+str), str);	
    	}
    	for(String str : this.namedParameterMap.keySet()) {
    		this.query = this.query.replace(":"+str, "?");	
    	}
    	
    	
    	
		java.sql.Connection conn = this.con;
    	cStatement = conn.prepareCall(this.query);
    	
    	if(!keyMap.isEmpty()) {
	    	for(Map.Entry<Integer, String> en : keyMap.entrySet()) {
	    		
	    		cStatement.setObject(parameterCounter, this.namedParameterMap.get(en.getValue()));
	    		
	    		System.out.println("Index - "+parameterCounter+" = "+en.getValue()+" == "+this.namedParameterMap.get(en.getValue()));
	    		
	    		
	    		parameterCounter++;
	    	}
    	}
    	
	}
	
	
	
	public Map<Integer, List<Object[]>> execute() {
		Map<Integer, List<Object[]>> allResults = new HashMap<Integer, List<Object[]>>();
		try {
	
			prepareCallWithNamedParameterToIndexedParameter();
		    boolean isResultSet = cStatement.execute();
		  
			Integer countResult = 0;
			while(isResultSet){
				
				ResultSet res = cStatement.getResultSet();
				ResultSetMetaData md = res.getMetaData();
				int columns = md.getColumnCount();
				
				
				
				List<Object[]> resultList = new ArrayList<Object[]>();
				while (res.next()){
					Object[] myArray= new Object[columns];
					for(int i = 1; i <= columns; i++){
						myArray[i-1] = res.getObject(i);
					}
					resultList.add(myArray);
				}
				allResults.put(countResult, resultList);
				isResultSet = cStatement.getMoreResults();
				countResult++;
			}
		}catch(Exception e){
			System.err.println("MultipleResult ##~"+query);
			e.printStackTrace();
		}
		return allResults;
	}
	

	
	
}


