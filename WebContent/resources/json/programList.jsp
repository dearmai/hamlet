<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="kr.or.sencha.hamlet.db.HamletMyBatis" %>
<%
	String callback	= request.getParameter("callback");
	String syscd		= request.getParameter("pgm_syscd");


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
	HamletMyBatis hamletMyBatis = new HamletMyBatis();
	out.println( hamletMyBatis.getMenuInfo(syscd) );
%>

<% 
if(isCallback){
%>
)
<%}%>