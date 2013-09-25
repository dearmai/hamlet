<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="kr.or.sencha.hamlet.db.HamletMyBatis" %>
<%
	String callback = request.getParameter("callback");
	boolean isCallback = (callback==null && !"".equals(callback)) ? false : true;

	String server_name = request.getParameter("server_name");
	String parent_id = request.getParameter("parent_id");
	String server_url = request.getParameter("server_url");
	String is_login_flow = request.getParameter("is_login_flow");
	String login_form_query = request.getParameter("login_form_query");
	String id_name = request.getParameter("id_name");
	String id_value = request.getParameter("id_value");
	String password_name = request.getParameter("password_name");
	String password_value = request.getParameter("password_value");
	String success_string = request.getParameter("success_string");

	
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
	out.println( hamletMyBatis.createSite(server_name, parent_id, server_url, is_login_flow, login_form_query, id_name, id_value, password_name, password_value, success_string)); 
%>

<% 
if(isCallback){
%>
)
<%}%> 