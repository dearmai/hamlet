package kr.or.sencha.hamlet.scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;

import kr.or.sencha.hamlet.db.HamletMyBatis;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;


public class HamletScheduler extends HttpServlet {
	
	private static final long serialVersionUID = -4013616887475315494L;
	private SchedulerFactory schedulerFactory;
	private Scheduler scheduler;
	private List jobList;
	
	public HamletScheduler () {
		
		try {
			schedulerFactory  = new StdSchedulerFactory();
			scheduler    = schedulerFactory.getScheduler();
			jobList = new ArrayList();
			
			// job 목록 검색(parent_id=0 인 데이터 제외)
			HamletMyBatis hamletMyBatis = new HamletMyBatis();
			List resultList = hamletMyBatis.getJobList();
			
			for(int i=0; i<resultList.size(); i++){
				HashMap result = (HashMap)resultList.get(i);
				result.put("seconds", 600);
				
				jobList.add(result);
			}
			
			System.out.println(jobList.size() + " jobs registed.");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {

		try{
			
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
			
			for(int i=0; i < jobList.size(); i++){
				HashMap map = (HashMap)jobList.get(i);

				// JOB 생성
				JobDetail job = org.quartz.JobBuilder.newJob(PhantomJob.class)
						.withIdentity(map.get("server_id").toString(), "group1")
						.build();

				// 트리거 생성
				Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
						.withIdentity(map.get("server_id").toString(), "group1")
						.startNow()
						.withSchedule(org.quartz.SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds((Integer)map.get("seconds"))		// 위에서 스케쥴링 세팅한 주기
								.repeatForever())            
								.build();
				job.getJobDataMap().put("job_info_map", map);
				
				job.getJobDataMap().put("hamlet_bot_script_path", hamlet_bot_script_path);
				job.getJobDataMap().put("casperjs_home", casperjs_home);
				job.getJobDataMap().put("bot_receiver", bot_receiver);
				job.getJobDataMap().put("server_perf", server_perf);
				job.getJobDataMap().put("image_path", image_path);

				// 스케쥴러에 Job을 추가. 실행주기는 트리거에 설정된 주기
				scheduler.scheduleJob(job, trigger);
			}
			scheduler.start();
		}catch(SchedulerException e){
			e.printStackTrace();
		}
	}
}
