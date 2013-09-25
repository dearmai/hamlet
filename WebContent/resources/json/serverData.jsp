<%@page import="kr.or.sencha.hamlet.util.Page"%>
<%@page import="org.apache.commons.collections.map.CaseInsensitiveMap"%>
<%@page import="java.util.List"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="kr.or.sencha.hamlet.db.HamletMyBatis"%>
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
	Desc : 시스템이하 프로그램 리스트 제공
 --%>

<%
	PrintWriter output = response.getWriter();
	HamletMyBatis hamletMyBatis = new HamletMyBatis();
	List<CaseInsensitiveMap> result = hamletMyBatis.getServerData(request, response);
	Page.getArrayJsonString(request, result, output,"", "");
%>
<%
if(isCallback){
%>
)
<%}%>