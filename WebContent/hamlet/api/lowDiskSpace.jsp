<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="kr.or.sencha.hamlet.api.ServerListDao" %>

<%
out.println( new ServerListDao().getLowDiskSpace() );
%>