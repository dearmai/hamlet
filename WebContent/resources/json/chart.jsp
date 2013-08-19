<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%>
<%
	String callback = request.getParameter("callback");
	boolean isCallback = (callback==null && !"".equals(callback)) ? false : true;
	
	if(isCallback){
%>
<%=callback%>(
<%
}
%>
<%--
Parameters
	targetID : "1"	-- Target Server ID
	chartPeriod : "1D" or "2D" or "7D" or "1M" or "6M" or "ALL" -- Chart 종류(기간별)


Results
	data [
		{
			"time" : 		"2013-08-30 17:30",
			"status" :		"true" , "false",
			"latency" : 	"350"
			"screenshot" :	"http://estar.sencha.or.kr/screenshot/1/2013.08.30_17.30.jpg"
		} , {
			"time" : 		"2013-08-30 17:30",
			"status" :		"true" , "false",
			"latency" : 	"370"
			"screenshot" :	"http://estar.sencha.or.kr/screenshot/1/2013.08.30_17.30.jpg"
		} 

	]
 --%>
{
	"data" : [
		{
			"time" : 		"2013-08-30 17:30",
			"status" :		"true",
			"latency" : 	"350",
			"screenshot" :	"http://sencha.or.kr/wp-content/uploads/2013/07/2013.08.30_17.30.png"
		} , {
			"time" : 		"2013-08-30 17:30",
			"status" :		"false",
			"latency" : 	"370",
			"screenshot" :	"http://sencha.or.kr/wp-content/uploads/2013/07/2013.08.30_17.30.png"
		} , {
			"time" : 		"2013-08-30 17:25",
			"status" :		"true",
			"latency" : 	"320",
			"screenshot" :	"http://sencha.or.kr/wp-content/uploads/2013/07/2013.08.30_17.30.png"
		} , {
			"time" : 		"2013-08-30 17:20",
			"status" :		"true",
			"latency" : 	"250",
			"screenshot" :	"http://sencha.or.kr/wp-content/uploads/2013/07/2013.08.30_17.30.png"
		} , {
			"time" : 		"2013-08-30 17:15",
			"status" :		"false",
			"latency" : 	"473",
			"screenshot" :	"http://sencha.or.kr/wp-content/uploads/2013/07/2013.08.30_17.30.png"
		} , {
			"time" : 		"2013-08-30 17:10",
			"status" :		"true",
			"latency" : 	"320",
			"screenshot" :	"http://sencha.or.kr/wp-content/uploads/2013/07/2013.08.30_17.30.png"
		} , {
			"time" : 		"2013-08-30 17:05",
			"status" :		"true",
			"latency" : 	"385",
			"screenshot" :	"http://sencha.or.kr/wp-content/uploads/2013/07/2013.08.30_17.30.png"
		}

	]
}
<%
if(isCallback){
%>
)
<%}%>