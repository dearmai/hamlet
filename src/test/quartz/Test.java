package test.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{

			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			// JOB 생성
			JobDetail job = org.quartz.JobBuilder.newJob(HelloJob.class)
					.withIdentity("job", "group1")
					.build();
			
			// 트리거 생성
			Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
					.withIdentity("trigger", "group1")
					.startNow()
					.withSchedule(org.quartz.SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(3)		// 위에서 스케쥴링 세팅한 주기
							.repeatForever())            
							.build();

			// HelloJob.class에 process_name 이름으로 값을 넘김.
			job.getJobDataMap().put("process_name", "job");

			// 스케쥴러에 Job을 추가. 실행주기는 트리거에 설정된 주기
			scheduler.scheduleJob(job, trigger);

		}catch(SchedulerException e){
			e.printStackTrace();
		} 
	}

}
