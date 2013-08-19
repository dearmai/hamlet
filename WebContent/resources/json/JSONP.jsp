<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="kr.or.sencha.hamlet.db.HamletMyBatis" %>
<%
	String callback = request.getParameter("callback");
	boolean isCallback = (callback==null && !"".equals(callback)) ? false : true;
	
	if(isCallback){
%>
<%=callback%>(
<% } %>


<%
	String pgm_syscd = request.getParameter("pgm_syscd");
	HamletMyBatis hamletMyBatis = new HamletMyBatis();
	out.println( hamletMyBatis.getMenuInfo(pgm_syscd) );
%>

<% if(isCallback){ %>
)
<% } %>