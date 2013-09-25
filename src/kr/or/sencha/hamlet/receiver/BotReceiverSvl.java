package kr.or.sencha.hamlet.receiver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.sencha.hamlet.db.MyBatisManager;
import kr.or.sencha.hamlet.util.MailSender;
import kr.or.sencha.hamlet.util.PropertiesManager;

import org.apache.ibatis.session.SqlSession;

/**
 * Servlet implementation class BotReceiverSvl
 */
public class BotReceiverSvl extends HttpServlet {
       
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
		System.out.println("- serverId : " + req.getParameter("serverId") );
		System.out.println("- createdDate : " + req.getParameter("createdDate") );
		System.out.println("- status : " + req.getParameter("status") );
		System.out.println("- image : " + req.getParameter("image") );
		System.out.println("- latency : " + req.getParameter("latency") );
		System.out.println("- cpu : " + req.getParameter("cpu") );
		System.out.println("- p1 : " + req.getParameter("p1") );
		System.out.println("- p2 : " + req.getParameter("p2") );
		System.out.println("- p3 : " + req.getParameter("p3") );
		System.out.println("- p4 : " + req.getParameter("p4") );
		System.out.println("- p5 : " + req.getParameter("p5") );
		System.out.println("- p6 : " + req.getParameter("p6") );
		System.out.println("- Msg : " + req.getParameter("msg") );
		
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
		p.put("msg", req.getParameter("msg"));
		
		try {
			int result = session.insert("Default.insertServerData", p);
			System.out.println( result );
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		// status가 false인 경우 알람 메일을 발송 한다.
		if( "false".equals(req.getParameter("status")) ) {
			sendMail( req );
		}
		
		System.out.println("## Finish");
	}

	private String getNowToText() {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		System.out.println(format.format(now));
		
		return format.format(now);
	}
	
	private String getImageUrl( String fileName) {

		return fileName.replaceAll( PropertiesManager.getKey("screenshot.stored.path") , PropertiesManager.getKey("screenshot.url.prefix") );	//TODO 수정 필요 
	}
	
	private void sendMail( HttpServletRequest req ) {
		
		String title = "[Hamlet] 서버가 정상작동 하지 않습니다. - " + req.getParameter("targetUrl");
		String msg = "서버가 정상작동 하지 않습니다.\n"
				+ "- Target URL : " + req.getParameter("targetUrl") + "\n"
				+ "- Latency : " + req.getParameter("latency") + "\n"
				+ "- Test Time : " + req.getParameter("createdDate") + "\n"
				+ "- Screenshot : " + getImageUrl( req.getParameter("image")) + "\n";
		
		String[] mailReceiver = PropertiesManager.getKey("mail.receiver").split(",");
		String mailSender = PropertiesManager.getKey("mail.sender.id");
		String imageFile = req.getParameter("image");
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {
			new MailSender().sendSSLMessage(mailReceiver, title, msg, mailSender, imageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sucessfully Sent mail to All Users");
	}

}
