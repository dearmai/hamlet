package kr.or.sencha.hamlet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.CaseInsensitiveMap;

//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


public class CommonUtil extends HttpServlet {
	SimpleDateFormat dateTime = new SimpleDateFormat("yyyy.MM.dd HH:mm ss");
	Calendar currTime;
	Date currDate;
	long currMillTime;

	public CommonUtil() {
		currTime= Calendar.getInstance();
		currDate=currTime.getTime();
		currMillTime=currDate.getTime();
	}

		
	/**
	 * DESC : session 유효 체크
	 * @return : EmUserVO 로그인 사용자 정보
	 */
	public static CaseInsensitiveMap isSessionEmUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CaseInsensitiveMap user = (CaseInsensitiveMap) request.getSession().getAttribute("MemberSession");
		
		String retUrl = "/";
		if(request.getAttribute("returnUrl") != null)
			retUrl = (String)request.getAttribute("returnUrl");
		// System.out.println("#################################### " + retUrl);
		if(user == null)
			throw new Exception("{\"success\":false,\"errMsg\":\"오랫동안 사용하지 않아 세션이 종료되었습니다. 다시 로그인 해주세요\",\"errTitle\":\"세션종료\",\"goToUrl\":\""+retUrl+"\"}");
		return user;
	}
	
	public void getProgressTime(String txt){
//		/현재의 시간 설정	
		Calendar cal= Calendar.getInstance();
		Date endDate=cal.getTime();
		long endTime=endDate.getTime();
		StringBuffer diffTime=new StringBuffer();
		if(txt != null) diffTime.append("			"+ txt + "\n");
		diffTime.append("			최초 시간 : "+ dateTime.format(currDate) +"\n");
		diffTime.append("			측정 시간 : "+ dateTime.format(endDate) + "  "+Math.round((endTime-currMillTime)/1000.0f) +"초 경과" );
		// System.out.println(diffTime.toString());
	}
	 
	
	/**
	 * DESC : request 관련, parameter 정보를 보여준다.
	 * @return : 
	 */
	public static String getRetURL(HttpServletRequest request, boolean URLEncode) throws Exception {
		return getRetURL(request, URLEncode, true);
	}
	
	public static String getRetURL(HttpServletRequest request, boolean URLEncode, boolean print) throws Exception {
		if(print){
			System.out.println("################## MarketUtil.getRetURL Start.. ##################");
			System.out.println("전송 방식 : "+ request.getMethod());
		}
		try {
			String returl = request.getServletPath();
			Enumeration keys = request.getParameterNames();
			for( int i = 0; keys.hasMoreElements(); i++) {
				String key = (String) keys.nextElement();
				String[] aVal = request.getParameterValues(key);
				returl += (i == 0) ? "?" : "&";
				for( int j = 0; j < aVal.length; j++) {
					if(j>0) returl += "&";
					
					returl += key + "=" + (URLEncode?URLDecoder.decode( aVal[j] ):aVal[j]);
					
					
					if(print){
						if ( URLEncode )
							System.out.println("Field : "+ key + " = "+ URLDecoder.decode( aVal[j] ));
						else
							System.out.println("Field : "+ key + " = "+ aVal[j]);
					}
				}
			}
			if(print)
				System.out.println("################## MarketUtil.getRetURL End.. ##################");
			if ( URLEncode )
				return URLEncoder.encode( returl );
			else
				return returl;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/***
	 * Ajax call에 대한 에러 처리다.
	 * 모든 요청에 대해 아래와 같이 사용하면 된다. 
	 * try { } catch (Throwable e) {
	 * 		e.printStackTrace();
	 * 		writer.println(MarketUtil.failSubmitMsg(e.getMessage()));
	 * 	}
	 * use case 1 [store]  : proxy 내부 listeners : 	{ exception: function(proxy, response, operation){
	 * 										           	oak.common.MessageBroker.sendMessage(response);
	 * 												}
	 * use case 2 [form submit] : failure: 	function(form, action, c) { 
	 * 											oak.common.MessageBroker.sendMessage(action);
	 * 										}
	 * @param errMsg
	 * @return
	 * @throws Exception
	 */
	public static String failSubmitMsg(String errMsg) throws Exception {
		System.out.println("에러 메시지 ::::::::::::::" + errMsg);
//		if(errMsg == null) errMsg = "처리 중 문제가 발생하였습니다. 관리자에게 문의하세요";
		errMsg = Formatter.remove(errMsg,"\n");
		if(errMsg.indexOf("session") == -1){
//			errMsg = "처리 중 문제가 발생하였습니다. 관리자에게 문의하세요";
			return new String("{\"success\":false,\"errMsg\":\""+errMsg+"\",\"errTitle\":\"alert\"}");
		}else{
			return errMsg;
		}
	}
	
	public static HashMap getHashMapParameter(HttpServletRequest request, boolean URLEncode) throws Exception {
		try {
			HashMap map = new HashMap();
			String returl = request.getServletPath();
			Enumeration keys = request.getParameterNames();
			for( int i = 0; keys.hasMoreElements(); i++) {
				String key = (String) keys.nextElement();
				String[] aVal = request.getParameterValues(key);
				returl += (i == 0) ? "?" : "&";
				for( int j = 0; j < aVal.length; j++) {
					System.out.println(key + aVal[j]);
					map.put(key, aVal[j]);
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static String getPayLoadData(HttpServletRequest request) throws IOException {

	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}
}
