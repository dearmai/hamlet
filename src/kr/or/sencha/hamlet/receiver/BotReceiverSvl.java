package kr.or.sencha.hamlet.receiver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.sencha.hamlet.db.MyBatisManager;

import org.apache.ibatis.session.SqlSession;

/**
 * Servlet implementation class BotReceiverSvl
 */
public class BotReceiverSvl extends HttpServlet {
	private String IMAGE_PREFIX = "http://hamlet.sencha.or.kr/hamlet_images/";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BotReceiverSvl() {
        super();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("##### HAMLET.BotReceiverSvl Start !!!");
		
		SqlSession session = MyBatisManager.getSqlMapper().openSession();
		HashMap p = new HashMap();
		p.put("server_id", req.getParameter("serverId"));
		p.put("created_date", req.getParameter("createdDate"));
		p.put("status", req.getParameter("status"));
		p.put("image", getImageUrl( req.getParameter("image")) );
		p.put("latency", req.getParameter("latency"));
		p.put("cpu", req.getParameter("cpu"));
		p.put("vm_total_mem", req.getParameter("p1"));
		p.put("vm_free_mem", req.getParameter("p2"));
		p.put("os_total_mem", req.getParameter("p3"));
		p.put("os_free_mem", req.getParameter("p4"));
		p.put("disk_total_space", req.getParameter("p5"));
		p.put("disk_free_space", req.getParameter("p6"));
		
		try {
			int result = session.insert("Default.insertServerData", p);
			System.out.println( result );
			
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
		return fileName.replaceAll( "/home/www/hamlet.sencha.or.kr/hamlet_images/" , IMAGE_PREFIX );	//TODO 수정 필요 
	}

}
