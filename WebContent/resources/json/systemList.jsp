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
	Desc : 시스템 리스트 제공
 --%>
{"entitys":[
		{"pgm_syscd":"F001","pgm_sysicon":"grid","pgm_sysnm":"한국센챠유저그룹","title":""},
		{"pgm_syscd":"F002","pgm_sysicon":"grid","pgm_sysnm":"한국센챠유저그룹DB","title":""},
		{"pgm_syscd":"F003","pgm_sysicon":"grid","pgm_sysnm":"백업서버","title":"회계관리"}
			],
			"errMsg":"","errTitle":"검색결과","message":"","success":true,"totalCount":"8"}

<%
if(isCallback){
%>
)
<%}%>