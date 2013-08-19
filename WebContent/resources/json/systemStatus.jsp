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
	targetID : "1" -- Target Server ID

Results
	Hamlet Status : true / false
	Target Status : true / false
	Target Name : 한국센차유저그룹 웹서버
	Target URL : http://sencha.or.kr
	Target Latency : 350ms
	Monitor Cycle : 30min
 --%>
<%java.util.Calendar now = java.util.Calendar.getInstance();%>

{
	 "hamlet_status" : "true",
	 "target_status" : "false",
	 "target_name" : "한국센차유저그룹 웹서버",
	 "target_url" : "http://sencha.or.kr",
	 "target_latency" : "350",
	 "target_memory" : "25",
	 "monitor_cycle" : "<%=(now.get(java.util.Calendar.SECOND))%>",
	 "success":true
}
<%
if(isCallback){
%>
)
<%}%>