package kr.or.sencha.hamlet.receiver;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.sencha.hamlet.db.MyBatisManager;
import kr.or.sencha.hamlet.db.StringMap;
import kr.or.sencha.hamlet.util.Dummy;
import kr.or.sencha.hamlet.util.PropertiesManager;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.ibatis.session.SqlSession;

import kr.or.sencha.hamlet.util.Page;

/**
 * Servlet implementation class BotReceiverSvl
 */
public class ServerDataSvl extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerDataSvl() {
        super();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");			
		
		SqlSession session = MyBatisManager.getSqlMapper().openSession();
		HashMap p = new HashMap();
		p.put("serverId", req.getParameter("serverId"));
		p.put("created_date", req.getParameter("created_date"));
		String type = req.getParameter("type");
		PrintWriter out = res.getWriter();
		System.out.println("serverId " +  req.getParameter("serverId"));
		System.out.println("created_date " +  req.getParameter("created_date"));
		try {
			List<CaseInsensitiveMap> result = session.selectList("Default.serverDatas"+type, p);
			
			Page.getArrayJsonString(req, result, out,"", "");
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("## Finish");
	}

	private String getNowToText() {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		System.out.println(format.format(now));
		
		return format.format(now);
	}
	
	private String getImageUrl( String fileName ) {
		return fileName.replaceAll( PropertiesManager.getKey("screenshot.stored.path") , PropertiesManager.getKey("screenshot.url.prefix") );	//TODO 수정 필요 
		
	}

}
