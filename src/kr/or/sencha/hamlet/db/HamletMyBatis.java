package kr.or.sencha.hamlet.db;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.sencha.hamlet.scheduler.PhantomJob;
import kr.or.sencha.hamlet.util.Formatter;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

public class HamletMyBatis {
	/** JOB 기본 주기 */
	public static final int SCHEDULE_TIME = 600;
	/** 기본 스케쥴러 이름 */
	public static final String DEFAULT_SCHEDULER_NAME = "DefaultQuartzScheduler";

	public static void main(String[] args) {
		System.out.println("### HamletMyBatis.main() start ###");
		
		SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
		
		HashMap p = new HashMap();	// Parameter 담을 HashMap
		p.put("id", "1");			// Parameter
		
		try {  
			StringMap result = new StringMap( (HashMap)session.selectOne("Default.selectSample",p) );	// 1건 조회
			System.out.println( result.get("server_id") );		
			System.out.println( result.get("server_name") );
			
			result.printList();	// session.selectList() 함수와 함께 쓰인다. 디버깅 용도로, 여러건의 결과를 화면에 출력한다.
			result.print();		// session.selectOne()  함수와 함께 쓰인다. 디버깅 용도로, 한건의 결과를 화면에 출력한다.
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("### HamletMyBatis.main() End ###");
	}
	
    public HamletMyBatis() {
        // TODO Auto-generated constructor stub
    }
    
    public String removeGroup(String parent_id){
    	JSONObject obj = new JSONObject();
    	boolean success = true;
    	String ROOT = "0";
    	
    	SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
    	
		try {
			HashMap<String, String> p = new HashMap<String, String>();	// Parameter 담을 HashMap
			p.put("parent_id", parent_id);
			
			System.out.println("parameter_map:"+ p);
			
			// 모두 삭제
			if(ROOT.equals(parent_id)){
				// remove all
				session.delete("Default.deleteAllSite",p);
				success = true;
				
				// 스케쥴러에 등록된 모든 Job 삭제
				removeAllJobs();
				
				// 모든 데이터 삭제
				session.delete("Default.deleteServerAllData");
				
			// 그룹만 삭제
			}else{
				
				// 그룹에 속한 서버 아이템 검색
				List <HashMap> serverList = session.selectList("Default.getServerIdList", p);
				for(HashMap map:serverList){
					String serverId = map.get("server_id").toString();
					
					// 해당 서버 데이터 삭제
					session.delete("Default.deleteServerData", serverId);
					
					deleteJob(serverId);
				}
				// 해당 아이템 삭제
				
				// 그룹에 속한 아이템 삭제
				session.delete("Default.deleteChildSite",p);
				// 본인 그룹 삭제을 위한 값입력
				p.put("server_id", parent_id);
				// 그룹 본인 삭제
				session.delete("Default.deleteSite",p);
				
				success = true;				
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
			success = false;
		} finally {
			session.close();
		}
    	
		obj.put("success", success);
    	
		return obj.toString();
    }
    
    public String updateSite(String action, String server_id, String server_name, String  parent_id, String  server_url, String  is_login_flow, String  login_form_query, String id_name, String  id_value, String password_name, String password_value, String success_string){
    	
    	JSONObject obj = new JSONObject();
    	boolean success = true;
    	
    	SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
    	
		try {
			HashMap<String, String> p = new HashMap<String, String>();	// Parameter 담을 HashMap
			p.put("server_id", server_id);
			p.put("server_name", server_name);
			p.put("parent_id", parent_id);
			p.put("server_url", server_url);
			p.put("is_login_flow", is_login_flow);
			p.put("login_form_query", login_form_query);
			p.put("id_name", id_name);
			p.put("id_value", id_value);
			p.put("password_name", password_name);
			p.put("password_value", password_value);
			p.put("success_string", success_string);
			
			System.out.println("parameter_map:"+ p);
			
			// 하나의 사이트 삭제
			if("delete".equals(action)){
				System.out.println("do delete");
				int resultCnt = session.delete("Default.deleteSite",p);
				success = resultCnt == 1 ? true : false;
				if(success){
					// 해당 서버 데이터 삭제
					session.delete("Default.deleteServerData", server_id);;
					// job 삭제
					deleteJob(server_id);
				}
			// 하나의 사이트 수정
			}else{
				int resultCnt = session.update("Default.updateSite",p);
				
				success = resultCnt == 1 ? true : false;
				if(success){
					// 삭제
					boolean result = deleteJob(server_id);
					
					if(result){
						// 새로운 job 등록
						addJob(p);
					}
				}
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
			success = false;
		} finally {
			session.close();
		}
    	
		obj.put("success", success);
    	
		return obj.toString();
    }
    
    public String getProgramDetail(String pgmKey){
    	
    	JSONObject obj = new JSONObject();
    	boolean success = true;
    	
    	SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
    	
		try {
			HashMap<String, String> p = new HashMap<String, String>();	// Parameter 담을 HashMap
			p.put("server_id", pgmKey);		
			
			HashMap programDetail = (HashMap) session.selectOne("Default.getProgramDetail", p);
			
			obj.put("entitys", programDetail);
			success = true;
			
		} catch( Exception e ) {
			e.printStackTrace();
			success = false;
		} finally {
			session.close();
		}
    	
		obj.put("success", success);
    	
		return obj.toString();
    	
    }
    
    public String createSite(String server_name, String parent_id, String server_url, String is_login_flow, String login_form_query, String id_name, String id_value, String password_name, String password_value, String success_string){
    	
    	JSONObject obj = new JSONObject();
    	boolean success = true;
    	
    	SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
    	
		try {
			HashMap<String, Object> p = new HashMap<String, Object>();	// Parameter 담을 HashMap
			p.put("server_name", server_name);
			p.put("parent_id", parent_id);
			
			p.put("server_url", server_url);
			p.put("is_login_flow", is_login_flow);
			p.put("login_form_query", login_form_query);
			p.put("id_name", id_name);
			p.put("id_value", id_value);
			p.put("password_name", password_name);
			p.put("password_value", password_value);
			p.put("success_string", success_string);	
			
			String server_id = (String)session.selectOne("Default.getMaxServerId");
			p.put("server_id", server_id);	
			
			int resultCnt = session.insert("Default.insertSite",p);
			System.out.println("resultCnt:"+ resultCnt);
			success = resultCnt == 1 ? true : false;
			
			// 그룹 타입이 아닌경우만 스케쥴링 추가
			if(success && !"0".equals(parent_id)){
				addJob(p);
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
			success = false;
		} finally {
			session.close();
		}
    	
		obj.put("success", success);
    	
		return obj.toString();
    }
    
    public String getGroupInfo(){
    	JSONObject obj = new JSONObject();
    	JSONArray result = getGroupList();
    	
    	obj.put("entitys", result);
    	obj.put("totalCount", result.size());
    	obj.put("success", true);
    	
    	return obj.toString();
    }
    
    private JSONArray getGroupList() {
    	JSONArray jsonList = new JSONArray();
    	
    	SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
    	
		try {
			HashMap<String, String> p = new HashMap<String, String>();	// Parameter 담을 HashMap
			p.put("parent_id", null);		// Parameter
			
			StringMap result = new StringMap( session.selectList("Default.getGroupList",p) );
			
			result.printList();	// session.selectList() 함수와 함께 쓰인다. 디버깅 용도로, 여러건의 결과를 화면에 출력한다.
			
			JSONObject obj = null;
			
			obj = new JSONObject();
			obj.put( "code", "0");
			obj.put( "value", "최상위");
			jsonList.add(obj);
			
			for( int i = 0; i < result.size(); i++ ) {
				obj = new JSONObject();
				obj.put( "code", result.get(i).get("server_id") );
				obj.put( "value", result.get(i).get("server_name") );
				jsonList.add(obj);
			}
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return jsonList;
	}

    public String getMenuInfo(){
    	
    	JSONObject obj = new JSONObject();
    	JSONArray result = getMenuList();
    	
    	obj.put("entitys", result);
    	obj.put("totalCount", result.size());
    	obj.put("success", true);
    	
    	return obj.toString();
    }
    
	public String getMenuInfo(String pgm_syscd){
    	
    	JSONObject obj = new JSONObject();
    	JSONArray result = getSubMenuList(pgm_syscd);
    	
    	obj.put("entitys", result);
    	obj.put("totalCount", result.size());
    	obj.put("success", true);
    	
    	return obj.toString();
    }
    
	private JSONArray getMenuList() {
    	
    	JSONArray jsonList = new JSONArray();
    	
    	SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
    	
		try {
			HashMap<String, String> p = new HashMap<String, String>();	// Parameter 담을 HashMap
			
			StringMap result = new StringMap( session.selectList("Default.getMenuList",p) );
			
			result.printList();	// session.selectList() 함수와 함께 쓰인다. 디버깅 용도로, 여러건의 결과를 화면에 출력한다.
			
			JSONObject obj = null;
			for( int i = 0; i < result.size(); i++ ) {
				obj = new JSONObject();
				obj.put( "pgm_syscd", result.get(i).get("server_id") );
				obj.put( "pgm_sysicon", "grid");
				obj.put( "pgm_sysnm", result.get(i).get("server_name") );
				jsonList.add(obj);
			}
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return jsonList;
	}
	
    private JSONArray getSubMenuList(String pgm_syscd) {
    	
    	JSONArray jsonList = new JSONArray();
    	
    	SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
    	
		try {
			HashMap<String, String> p = new HashMap<String, String>();	// Parameter 담을 HashMap
			p.put("pgm_syscd", pgm_syscd);		// Parameter
			
			StringMap result = new StringMap( session.selectList("Default.getMenuList",p) );
			
			result.printList();	// session.selectList() 함수와 함께 쓰인다. 디버깅 용도로, 여러건의 결과를 화면에 출력한다.
			
			JSONObject obj = null;
			for( int i = 0; i < result.size(); i++ ) {
				obj = new JSONObject();
				obj.put( "pgm_key", result.get(i).get("server_id") );
				obj.put( "pgm_sysicon", "grid");
				obj.put( "title", result.get(i).get("server_name") );
				obj.put( "pgm_class", "HAMLET.view.ServerChart");
				jsonList.add(obj);
			}
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return jsonList;
	}

	public String sampleInfo() {
    	JSONObject obj = new JSONObject();
    	
    	obj.put("test1", "REPLACE");
    	obj.put("test2", 3);
    	obj.put("sampleSelectOne", sampleSelectOne());
    	obj.put("sampleSelectList", sampleSelectList());
    	
    	return obj.toString();
    }
    
    /**
     * 여러건 조회 샘플 
     * @return
     */
	public JSONArray sampleSelectList() {
		JSONArray jsonList = new JSONArray();
		SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
		
		try {
			HashMap p = new HashMap();	// Parameter 담을 HashMap
			p.put("serverId", "1");		// Parameter
			
			StringMap result = new StringMap( session.selectList("Default.sampleSelectList",p) );
			
			result.printList();	// session.selectList() 함수와 함께 쓰인다. 디버깅 용도로, 여러건의 결과를 화면에 출력한다.
			
			JSONObject obj = null;
			for( int i = 0; i < result.size(); i++ ) {
				obj = new JSONObject();
				obj.put( "server_id", result.get(i).get("server_id") );
				obj.put( "server_name", result.get(i).get("latency") );
				jsonList.add(obj);
			}
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return jsonList;
	}
    
	/**
	 * 한 건 조회 샘플 
	 * @return
	 */
	public String sampleSelectOne() {

		SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
		
		HashMap p = new HashMap();	// Parameter 담을 HashMap
		p.put("serverId", "1");		// Parameter
		
		String returnData = null;
		
		try {  
			StringMap result = new StringMap( (HashMap)session.selectOne("Default.sampleSelectOne",p) );
			returnData = result.get("server_name");
			
			result.print();		// session.selectOne()  함수와 함께 쓰인다. 디버깅 용도로, 한건의 결과를 화면에 출력한다.
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return returnData;
	}
	
	public List<CaseInsensitiveMap>  getServerData(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");			
		
		SqlSession session = MyBatisManager.getSqlMapper().openSession();
		HashMap p = new HashMap();
		p.put("serverId", req.getParameter("serverId"));
		p.put("created_date", req.getParameter("created_date"));
		p.put("displayDataCount", Formatter.envl(req.getParameter("displayDataCount"),"30"));
		
		String type = req.getParameter("type");
		
		System.out.println("serverId " +  req.getParameter("serverId"));
		System.out.println("created_date " +  req.getParameter("created_date"));
		System.out.println("type " +  req.getParameter("type"));
		
		try {
			List<CaseInsensitiveMap> result = session.selectList("Default.serverDatas"+type, p);
			return result;
//			Page.getArrayJsonString(req, result, out,"", "");
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("## Finish");
		return null;
	}

	public List getJobList() {
		
		SqlSession session = MyBatisManager.getSqlMapper().openSession();
		
		List resultList = null;
		try {
			HashMap<String, String> p = new HashMap<String, String>();	// Parameter 담을 HashMap
			p.put("parent_id", null);		// Parameter
			
			resultList = session.selectList("Default.getJobList");
			
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultList;
	}
	
	/**
     * 모든 JOB 삭제
     * @throws Exception
     */
    private void removeAllJobs() throws Exception{
    	
    	SchedulerFactory schedulerFactory  = new StdSchedulerFactory();
    	Scheduler scheduler    = schedulerFactory.getScheduler("DefaultQuartzScheduler");
    	
    	if(scheduler != null){
    		for(String group: scheduler.getJobGroupNames()) {
    			System.out.println("group: " + group);
    		   for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
    			   scheduler.deleteJob(jobKey);
    			   
    			   System.out.println("delete 1 job("+ jobKey.getName() +").");
    		   }
    		}
    	}
    }
    
    private void displayJob() throws Exception{
    	SchedulerFactory schedulerFactory  = new StdSchedulerFactory();
    	Scheduler scheduler    = schedulerFactory.getScheduler("DefaultQuartzScheduler");
    	
    	if(scheduler != null){
    		for(String group: scheduler.getJobGroupNames()) {
    			System.out.println("group: " + group);
    			for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
    				
    				System.out.println("group:" + jobKey.getGroup() + ", name:" + jobKey.getName());
    			}
    		}
    	}else{
    		
    		System.out.println("scheduler is null.");
    	}
    }
    
    /**
     * 특정 JOB 삭제
     * @param serverId
     * @return
     * @throws Exception
     */
    private boolean deleteJob(String serverId) throws Exception{
    	
    	SchedulerFactory schedulerFactory  = new StdSchedulerFactory();
    	Scheduler scheduler    = schedulerFactory.getScheduler("DefaultQuartzScheduler");
    	
    	JobKey jobKey = new JobKey(serverId, "group1");
    	
    	if(scheduler.checkExists(jobKey)){
    		
        	boolean result = scheduler.deleteJob(jobKey); 
        	System.out.println("delete 1 job("+ serverId +").");
    		return result;
    	}
    	
    	return false;
    }
    
    private void addJob(HashMap p) throws Exception{
    	
    	p.put("seconds", SCHEDULE_TIME);
    	
    	String osName = System.getProperty("os.name");
		String hamlet_bot_script_path = null;
		String casperjs_home = null;
		String bot_receiver = null;
		String server_perf = null;
		String image_path = null;
		
		if(osName.toLowerCase().startsWith("window")) {
			hamlet_bot_script_path = "/home/www/hamlet.sencha.or.kr/WEB-INF/HAMLET-BOT";
			casperjs_home = "/usr/local/bin/casperjs";
			bot_receiver = "http://hamlet.sencha.or.kr/servlet/BotReceiverSvl?";
			server_perf = "http://hamlet.sencha.or.kr/hamlet/server_perf.jsp";
			image_path = "/home/www/hamlet.sencha.or.kr/hamlet/capture_images/";
		}else if(osName.toLowerCase().startsWith("mac")){
			hamlet_bot_script_path = "";
		}else{
			// TODO 경로 프로퍼티 처리
			hamlet_bot_script_path = "/home/www/hamlet.sencha.or.kr/WEB-INF/HAMLET-BOT/HAMLET_Scripts/hamletBot.js";
			casperjs_home = "/usr/local/bin/casperjs";
			bot_receiver = "http://hamlet.sencha.or.kr/servlet/BotReceiverSvl?";
			server_perf = "http://hamlet.sencha.or.kr/hamlet/server_perf.jsp";
			image_path = "/home/www/hamlet.sencha.or.kr/hamlet/capture_images/";
		}
		
		SchedulerFactory schedulerFactory  = new StdSchedulerFactory();
    	Scheduler scheduler = schedulerFactory.getScheduler("DefaultQuartzScheduler");

		// JOB 생성
		JobDetail job = org.quartz.JobBuilder.newJob(PhantomJob.class)
				.withIdentity(p.get("server_id").toString(), "group1")
				.build();

		// 트리거 생성
		Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
				.withIdentity(p.get("server_id").toString(), "group1")
				.startNow()
				.withSchedule(org.quartz.SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds((Integer)p.get("seconds"))		// 위에서 스케쥴링 세팅한 주기
						.repeatForever())       
						.build();

		job.getJobDataMap().put("job_info_map", p);
		
		job.getJobDataMap().put("hamlet_bot_script_path", hamlet_bot_script_path);
		job.getJobDataMap().put("casperjs_home", casperjs_home);
		job.getJobDataMap().put("bot_receiver", bot_receiver);
		job.getJobDataMap().put("server_perf", server_perf);
		job.getJobDataMap().put("image_path", image_path);

		// 스케쥴러에 Job을 추가. 실행주기는 트리거에 설정된 주기
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
		
		System.out.println("add 1 job.");
    }
}
