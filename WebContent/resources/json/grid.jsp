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


Results

	"day1" : {
		"failureCount" 		: "0",
		"averageLatency"	: "125",
	},
	"day7" : {
		"failureCount" 		: "0",
		"averageLatency"	: "176",
	}
 --%>
{
	"day1" : {
		"failureCount" 		: "0",
		"averageLatency"	: "125"
	},
	"day7" : {
		"failureCount" 		: "0",
		"averageLatency"	: "176"
	},
	"day15" : {
		"failureCount" 		: "2",
		"averageLatency"	: "252"
	},
	"month1" : {
		"failureCount" 		: "7",
		"averageLatency"	: "178"
	},
	"month6" : {
		"failureCount" 		: "12",
		"averageLatency"	: "125"
	},
	"all" : {
		"failureCount" 		: "28",
		"averageLatency"	: "192"
	}
}
<%
if(isCallback){
%>
)
<%}%>