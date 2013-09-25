<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="kr.or.sencha.hamlet.api.ServerListDao" %>

<%
String serverId = request.getParameter("serverId");
int limit = Integer.parseInt( request.getParameter("limit") );

out.println( new ServerListDao().getServerData(serverId, limit) );
%>