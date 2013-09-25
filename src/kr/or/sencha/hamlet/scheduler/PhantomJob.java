package kr.or.sencha.hamlet.scheduler;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PhantomJob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		// 스케쥴러에서 넘어온 Context.
		JobDetail jobDetail = arg0.getJobDetail();
		
		JobDataMap jobMap = jobDetail.getJobDataMap();
				
		String casperjsHome = (String)jobMap.get("casperjs_home");
		String hamletScriptPath = (String)jobMap.get("hamlet_bot_script_path");
		String botReceiver = (String)jobMap.get("bot_receiver");
		String serverPerf = (String)jobMap.get("server_perf");
		String imagePath = (String)jobMap.get("image_path");
		
		HashMap jobInfo = (HashMap)jobMap.get("job_info_map");
		String serverId = jobInfo.get("server_id").toString();
		String serverUrl = (String)jobInfo.get("server_url");
		String successString = (String)jobInfo.get("success_string");
		String isLoginFlow = (String)jobInfo.get("is_login_flow");
		String loginFormQuery = (String)jobInfo.get("login_form_query");
		String idName = (String)jobInfo.get("id_name");
		String idValue = (String)jobInfo.get("id_value");
		String passwordName = (String)jobInfo.get("password_name");
		String passwordValue = (String)jobInfo.get("password_value");

		// 명령어 리스트
		List <String> commandList = new ArrayList<String>();
		
		if("3".equals(serverId)){
			Random oRandom = new Random();
			if(oRandom.nextBoolean()){
				serverUrl = serverUrl + "/404.html";				
			}
		}
		
		commandList.add(casperjsHome == null ? "" : casperjsHome);
		commandList.add(hamletScriptPath == null ? "" : hamletScriptPath);
		commandList.add(serverId == null ? "" : serverId);
		commandList.add(serverUrl == null ? "" : serverUrl);
		commandList.add(botReceiver == null ? "" : botReceiver);
		commandList.add(successString == null ? "" : successString);
		commandList.add(serverPerf == null ? "" : serverPerf);
		commandList.add(imagePath == null ? "" : imagePath);
		commandList.add("Y".equals(isLoginFlow) ? "true" : "false");
		
		// 로그인 처리를 하는 경우
		if("Y".equals(isLoginFlow)){
			commandList.add(loginFormQuery == null ? "" : loginFormQuery);
			commandList.add("{\""+ idName + "\":\""+ idValue + "\", \""+ passwordName + "\":\""+ passwordValue + "\"}");
		// 로그인 처리를 하지 않는 경우
		}else{
			commandList.add("");
			commandList.add("\'\'");
		}

		try{
			String osName = System.getProperty("os.name");

			// 경로 구분자
			String pathDelimiter = osName.toLowerCase().startsWith("window") ? "\\" : "/";
			ProcessBuilder pb = null;

			// 윈도우 실행 명령어
			if(osName.toLowerCase().startsWith("window")) {
				
				pb = new ProcessBuilder(commandList);
				
			// 맥 실행 명령어
			} else if(osName.toLowerCase().startsWith("mac")){
				pb = new ProcessBuilder(commandList);
			}else{
				pb = new ProcessBuilder(commandList);
			}

			System.out.print(pb.command()); 
			Process proc = pb.start(); 
			
			/*// any error message?
			StreamGobbler errorGobbler = new 
					StreamGobbler(proc.getErrorStream(), "ERROR");            

			// any output?
			StreamGobbler outputGobbler = new 
					StreamGobbler(proc.getInputStream(), "OUTPUT");
			
			// kick them off
            errorGobbler.start();
            outputGobbler.start();*/
            
            // any error???
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal + ". serverId:" + serverId + " on " + (new Date()).toString() + ", next fire date is " + arg0.getNextFireTime());
            proc.destroy();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
