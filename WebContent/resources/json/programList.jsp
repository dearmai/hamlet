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
{"entitys":[
{
	"pgm_class":"HAMLET.view.ServerChart","pgm_key":"hamlet1","pgm_icon":"grid","title":"웹서버 - Blog"
},
{
	"pgm_class":"HAMLET.view.Monitor","pgm_key":"hamlet2","pgm_icon":"grid","title":"웹서버 - Site"
},{
	"pgm_class":"HAMLET.view.Monitor","pgm_key":"hamlet3","pgm_icon":"grid","title":"개발 서버1"
}								
],
"errMsg":"","errTitle":"검색결과","message":"","success":true,"totalCount":"2"}
<%
if(isCallback){
%>
)
<%}%>