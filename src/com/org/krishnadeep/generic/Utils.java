package com.org.krishnadeep.generic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Utils {
	
	public static String getString(Object param){
		return param == null ? "" : param.toString();
		
	}
	
	public static Integer getInt(Object param){
		return (param == null || param =="") ? new Integer(0) : Integer.parseInt(param.toString());
	}
	
	
	public static Double getDouble(Object param){
		return (param == null || param =="") ? new Double(0) : Double.parseDouble(param.toString());
	}
	
	public static String[] getStringArray(Object param){
		return param == null ? new String[0] : (String[])param;
		
	}
	
	public static Integer[] getIntegerArray(Object param){
		Integer[] intArray = new Integer[0];
		String[] strArray = (String[])param;
		
		if(param != null){
			intArray = new Integer[((String[])param).length];
			for(int i=0; i < strArray.length; i++){
				intArray[i] = Integer.parseInt(strArray[i]); 
			}
		}
		return intArray;
	}
	
	public static String getValidCharge(Object param){
		return param != null ? (Float.parseFloat(param.toString()) > 0 ? param.toString() : "") : "";
		
	}
	
	public static String convertArrayToString(Object param){
		String ids = "";
		if(param != null){
			for(String paramValue : (String[])param){
				ids += paramValue + ",";
			}
			ids = ids.replaceAll(",$", "");
		}
		
		return ids;
	}
	
	public static String getWeekStartDate(Integer weekNumber, Integer year){
		
		String weekDate;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		
		cal.set(Calendar.WEEK_OF_YEAR, weekNumber);        
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);		
		weekDate = sdf.format(cal.getTime());
		//System.out.println(weekDate);
		
		return weekDate;
	}
	
	public static String getFormattedDate(Date date, String format){
		
		format = format.equals("") ? "dd-MMM-yy" : format;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		String formattedDate = sdf.format(date);
		
		return formattedDate;
	}
	
	public static Date parseDate(String date, String format) throws ParseException
	{
	    SimpleDateFormat formatter = new SimpleDateFormat(format);
	    return formatter.parse(date);
	}
	
	public static Integer getWeekNumber(String date){
		
		Integer weekNumber = 0;
		
		try{
		if(date.equals("")){
			return weekNumber;
		}
			
		DateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date passedDate = formatter.parse(date);

		Calendar cal = Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);	
		cal.setTime(passedDate);
		weekNumber = cal.get(Calendar.WEEK_OF_YEAR);
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error While fetching week number for " + date);
		}
		return weekNumber;
	}
	
public static Integer getYear(String date){
		
		Integer year = 0;
		
		try{
		if(date.equals("")){
			return year;
		}
			
		DateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date passedDate = formatter.parse(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(passedDate);
		year = cal.get(Calendar.YEAR) % 100;
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error While fetching year number for " + date);
		}
		return year;
	}

	public static JsonObject getJSONObjectFromString(String data){
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject)jsonParser.parse(data);
		
		return jsonObject;
	}
	
public JSONObject getConfig(){
		
	JSONObject jsonObject = new JSONObject();
		try{
		ConnectionsUtil connectionsUtil = new ConnectionsUtil();
		
		Connection conn =  connectionsUtil.getConnection();
		
		String query = "select * from config where is_active = 1";
		ResultSet dataRS = conn.createStatement().executeQuery(query);
		while(dataRS.next()){
			jsonObject.put(dataRS.getString("config_key"), dataRS.getString("config_value"));
		}
		System.out.println("jsonObject of config==>" + jsonObject);
		
		connectionsUtil.closeResultSet(dataRS);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		
		return jsonObject;
	}
}
