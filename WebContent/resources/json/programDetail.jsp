<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="kr.or.sencha.hamlet.db.HamletMyBatis" %>
<%
	String callback = request.getParameter("callback");
	boolean isCallback = (callback==null && !"".equals(callback)) ? false : true;
	String pgmKey = request.getParameter("pgmKey");
	if(isCallback){
%>
<%=callback%>(
<%
}
%>
<%--
	Desc : 시스템 리스트 제공
 --%>

<%
	HamletMyBatis hamletMyBatis = new HamletMyBatis();
	out.println( hamletMyBatis.getProgramDetail(pgmKey) ); 
%>

<%
if(isCallback){
%>
)
<%}%>