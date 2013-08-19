package kr.or.sencha.hamlet.scheduler;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PhantomJob implements Job {
	

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		// 스케쥴러에서 넘어온 Context.
		JobDetail temp = arg0.getJobDetail();
	
		try{
			Process process = null;
			String osName = System.getProperty("os.name");
			
			// 경로 구분자
			String pathDelimiter = osName.toLowerCase().startsWith("window") ? "\\" : "/";
			// phantom 실행경로
			String home = (String)temp.getJobDataMap().get("home");
			
			// 명령어
			String[] cmd = null;
			
			System.out.println("### osName.toLowerCase() : " + osName.toLowerCase() );//TODO 수정 필요
			
			// 윈도우 실행 명령어
			if(osName.toLowerCase().startsWith("window")) {
			    cmd = new String[] { "cmd.exe", "/y", "/c", home + pathDelimiter + "serverId_1_windows.bat " + home};
			// 맥 실행 명령어
			} else if(osName.toLowerCase().startsWith("mac")){
				// sh는 파라메터가 전달되지 않아서 bash로 실행
				cmd = new String[] { "/bin/bash", "-c",  home + pathDelimiter + "serverId_1_macosx.sh " + home};
			// 리눅스 실행 명령어
			}else{
				// sh는 파라메터가 전달되지 않아서 bash로 실행
				cmd = new String[] { "/bin/bash", "-c",  home + pathDelimiter + "serverId_1_linux64.sh " + home};//TODO 수정 필요
			}
			
			// 명령어 실행
			process = Runtime.getRuntime().exec(cmd);
			
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			// 명령어 상에서 에러가 발생했을경우 출력
			String s = null;
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
	        }
			
			//int exitStatus = process.waitFor();
			// 명령어 실행후 결과를 읽어들임
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader (process.getInputStream()));

		    String currentLine=null;
		    StringBuilder stringBuilder = new StringBuilder();
		    currentLine= bufferedReader.readLine();
		    while(currentLine !=null)
		    {
		        stringBuilder.append(currentLine+"\n");
		        currentLine = bufferedReader.readLine();
		    }
		    // 명렁어 실행후 결과를 화면에 출력
		    System.out.println(stringBuilder.toString());

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
