package kr.or.sencha.hamlet.util;



import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.CaseInsensitiveMap;



import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Page {
	public int start;
	public int end;
	public int totalCnt;
	public int limit;
	public int page;
	public Page() {
		// TODO Auto-generated constructor stub
	}
	public Page(HttpServletRequest request, int totalCnt) {
		try {
			CommonUtil.getRetURL(request, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.totalCnt =  totalCnt;
		String page = request.getParameter("page");
		if(page == null) page = "1";
		int limit = Integer.parseInt(Formatter.envl(request.getParameter("limit"),"0"));
		if(limit > this.totalCnt) limit = this.totalCnt;

		int z = Integer.parseInt(page);
		if(z == 0) z = 1;
		this.start 	= (z==1)?0:((z-1)*limit);
		this.end 	= (start+limit)>totalCnt?totalCnt:(start+limit);
		System.out.println("1.>> Page 	:: "+ page );
		System.out.println("2.>> Total Count:: "+ totalCnt);
		System.out.println("3.>> Data Start :: "+ this.start);
		System.out.println("4.>> Data End 	:: "+ this.end);
	}
	/**
	 * 리스트 객체에서 넘어온 전체 레코드 갯수를 리턴한다.
	 * 이 메소드는 VO객체에 totalCnt필드와 그에 따른 getter, setter메소드가
	 * 있다고 전제하고 있다.
	 * @param array
	 * @return
	 */
	public static int getTotalCnt(List array) {
		if(array == null)	return 0;
		if(array.size() == 0) return 0;
		// 클래스 가져오기
		try {
			Class getClass1 = Class.forName(array.get(0).getClass().getName());
			
			if(getClass1.getName().equals("org.apache.commons.collections.map.CaseInsensitiveMap")){
				CaseInsensitiveMap map = (CaseInsensitiveMap) array.get(0);
				return Formatter.envlInt(map.get("totalCnt").toString(),0);
			}else{
				// 클래스 객체를 이용한 객체 생성
				Object xx = array.get(0);
	
				Class getClass11 = xx.getClass();
	
				// 생성자에 인자가 없는 경우
				Object object1 = xx;
				Method method1 = getClass11.getMethod("getTotalCnt");
				return (Integer) method1.invoke(object1);
			}
		} catch (Exception e) {
			return array.size();
		}
	}
	/**
	 * @param request
	 * @param array
	 * @param writer
	 * @param sap_msg_type
	 * @param msg
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void getArrayJsonString(HttpServletRequest request, List array, PrintWriter writer, String sap_msg_type,
			String msg) {
		System.out.println("5.>> MSG Type 	:: " + sap_msg_type);
		System.out.println("6.>> MSG Str	:: " + msg);
		Dummy dummy = new Dummy();
		dummy.setEntitys(array);
		dummy.setTotalCount(Page.getTotalCnt(array)+"");
		dummy.setSuccess(sap_msg_type.equals("E")?false:true);
		dummy.setErrMsg(msg);
		dummy.setErrTitle("검색결과");
		JSONObject jsonobj = new JSONObject();
		jsonobj = JSONObject.fromObject(JSONSerializer.toJSON(dummy));
		
		String re = jsonobj.toString();
		writer.println(re);
	}
	
	public static void getArrayJsonPString(HttpServletRequest request, List array, PrintWriter writer, String sap_msg_type,
			String msg) {
		System.out.println("5.>> MSG Type 	:: " + sap_msg_type);
		System.out.println("6.>> MSG Str	:: " + msg);
		Dummy dummy = new Dummy();
		dummy.setEntitys(array);
		dummy.setTotalCount(Page.getTotalCnt(array)+"");
		dummy.setSuccess(sap_msg_type.equals("E")?false:true);
		dummy.setErrMsg(msg);
		dummy.setErrTitle("검색결과");
		JSONObject jsonobj = new JSONObject();
		jsonobj = JSONObject.fromObject(JSONSerializer.toJSON(dummy));
		
		String re = jsonobj.toString();
		writer.println(request.getParameter("callback")+"("+re+")");
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
