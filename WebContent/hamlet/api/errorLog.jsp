<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="kr.or.sencha.hamlet.api.ServerListDao" %>

<%
int limit = 10;

try {
	limit = Integer.parseInt( request.getParameter("limit") );
} catch( Exception e ) {
	limit = 10;
	e.printStackTrace();
}

out.println( new ServerListDao().getErrorLog(limit) );
%>