package test.quartz;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HelloJob implements Job {
	

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		JobDetail temp = arg0.getJobDetail();
		
		System.out.println("now process name is " + temp.getJobDataMap().get("process_name") + " on " + new Date());
		
	}

}
