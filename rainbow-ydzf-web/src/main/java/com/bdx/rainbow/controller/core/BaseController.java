package com.bdx.rainbow.controller.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.bdx.rainbow.mapp.core.JsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
public class BaseController {
	
	protected static JsonMapper binder = JsonMapper.nonDefaultMapper();
	

	
	@InitBinder    
	   protected void initBinder(WebDataBinder binder) {    
//	       binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	       binder.registerCustomEditor(java.sql.Timestamp.class,new TimestampEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	       //binder.registerCustomEditor(int.class, new CustomNumberEditor(int.class, true));    
	       binder.registerCustomEditor(int.class, new IntegerEditor());    
	       //binder.registerCustomEditor(long.class, new CustomNumberEditor(long.class, true));  
	     //  binder.registerCustomEditor(long.class, new LongEditor());    
	       binder.registerCustomEditor(double.class, new DoubleEditor());    
	       binder.registerCustomEditor(float.class, new FloatEditor());    
	      
	   }   

	
	public class JsonObject {
		private boolean success;
		private String message;
		private String exCode;
		private Object data;
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		public String getExCode() {
			return exCode;
		}
		public void setExCode(String exCode) {
			this.exCode = exCode;
		}
		
		public JsonObject(boolean success,String msg) {
			this.success =success;
			this.message=msg;
		}
		public JsonObject(boolean success,String exCode,String msg) {
			this.success =success;
			this.exCode=exCode;
			this.message=msg;
			
		}
		public JsonObject(boolean success,String msg,Object data) {
			this.success =success;
			this.message=msg;
			this.data=data;
		}
		public JsonObject(Object data) {
			this.success =true;
			this.message="SUC";
			this.data=data;
		}
		
		public String toJsonString() {
			ObjectMapper om=new ObjectMapper();
			try {
				return om.writeValueAsString(this);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
		

	}




}
