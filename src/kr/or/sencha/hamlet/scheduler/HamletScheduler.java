package kr.or.sencha.hamlet.scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;

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

			// job 목록
			jobList = new ArrayList();

			HashMap aa = new HashMap();
			aa.put("name", "naver");
			aa.put("seconds", 60 * 10); // 10분마다 생성시 한달에 1.4GB 소요 된다.	//TODO 수정 필요
			jobList.add(aa);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {

		try{

			String osName = System.getProperty("os.name");
			String home = "";
			
			// 윈도우 실행경로
			if(osName.toLowerCase().startsWith("windows")){
				home = "C:\\Users\\semanticker\\workspace\\02.eclipse_workspace\\HAMLET\\WebContent\\WEB-INF\\HAMLET-BOT";
			// 맥 실행경로
			}else if(osName.toLowerCase().startsWith("mac")){
				home = "/Users/semanticker/Documents/eclipse/HAMLET/WebContent/WEB-INF/HAMLET-BOT";
				home = "/Users/JongKwang/eclipse_workspace/HAMLET/WebContent/WEB-INF/HAMLET-BOT";//TODO 수정 필요
			}else {
				home = "/home/www/hamlet.sencha.or.kr/WEB-INF/HAMLET-BOT";//TODO 수정 필요
			}
			
			for(int a=0; a<jobList.size();a++){
				HashMap map = (HashMap)jobList.get(a);

				// JOB 생성
				JobDetail job = org.quartz.JobBuilder.newJob(PhantomJob.class)
						.withIdentity((String)map.get("name"), "group1")
						.build();

				// 트리거 생성
				Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
						.withIdentity((String)map.get("name"), "group1")
						.startNow()
						.withSchedule(org.quartz.SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds((Integer)map.get("seconds"))		// 위에서 스케쥴링 세팅한 주기
								.repeatForever())            
								.build();

				job.getJobDataMap().put("process_name", (String)map.get("name"));
				job.getJobDataMap().put("home", home);
				
				// 스케쥴러에 Job을 추가. 실행주기는 트리거에 설정된 주기
				scheduler.scheduleJob(job, trigger);
			}
			scheduler.start();
		}catch(SchedulerException e){
			e.printStackTrace();
		}
	}
}
