package system;
/*
 * 제작 : being20c@naver.com
 * 일자 : 05.6.11
 * http://blog.naver.com/being20c
 * */
import java.util.*;
import java.io.*;
/** CPU사용량을 측정, 윈도우 계열 운영체제에서만 동작합니다.*/
public class cpuMonitor implements Runnable{
 long cycleTime;
 Process process;
 Vector v_beforCpuData;
 
 /** cycleTime = 측정 주기 : 1. 측정 주기값이 커질수록 값의 정확도는 증가 */
 public cpuMonitor(long cycleTime)
 {
  this.cycleTime = cycleTime;
  this.v_beforCpuData = new Vector();
 }
 
 public cpuMonitor()
 {
  this.v_beforCpuData = new Vector();
 }
 
 /** 해당 파일을 실행 */
 void excuteFile()
 {
  try
  {
   this.process = Runtime.getRuntime().exec("system/pslist.exe");
  }
  catch(Exception e)
  {
   System.err.println("윈도우 응용프로그램 'pslist.exe'를 실행할 수 없습니다..\n"+e);
   System.exit(0);
  }
 }
 
 /** 이전내용과 비교를 통한 */
 public float checkData()
 {
  this.excuteFile();
  float result = -1f;
  Scanner reader;
  int pid = 0;
  long time = 0;
  long idleUsage = 0, realUsage = 0;
  //this.v_Temp.clear();
  
  try
  {
   reader = new Scanner(
     new InputStreamReader(this.process.getInputStream()));
   while(!reader.nextLine().startsWith("Name"));  // sync
   if(this.v_beforCpuData.size() == 0)
   {
    while(reader.hasNext())
    {
     reader.next();
     pid = reader.nextInt();
     reader.next();reader.next();reader.next();reader.next();
     time = time2long(reader.next());
     reader.next();
     this.v_beforCpuData.add(new vList(pid,time));
    }
    return -1f;
   }
   // 이전 정보가 존재한다면
   else
   {
    
    String procName = "";
    vList vTemp = null;
    while(reader.hasNext())
    {
     
     procName = reader.next();
     pid = reader.nextInt();
     reader.next();reader.next();reader.next();reader.next();
     time = time2long(reader.next());
     reader.next();
     
     if(procName.startsWith("Idle"))
     {
      for(int i=0;i<this.v_beforCpuData.size();i++){
       vTemp=(vList)this.v_beforCpuData.get(i);
       if(vTemp.pid == pid) break;
      }
      idleUsage = (time - vTemp.cpuTime);
      vTemp.cpuTime = time;
     }
     //else if(procName.equals("pslist")){}
     else
     {
      // 해당 정보의 존재여부 , 없으면 추가
      boolean isNew = true;
      for(int i=0;i<this.v_beforCpuData.size();i++){
       vTemp=(vList)this.v_beforCpuData.get(i);
       if(vTemp.pid == pid){ 
        isNew = false;
        break;
       }
      }
      if(!isNew){
       realUsage += (time - vTemp.cpuTime);
       vTemp.cpuTime = time;
      }
      else{
       realUsage += time;
       this.v_beforCpuData.add(new vList(pid,time));
      }
     }
    }
   }
   reader.close();
  }
  catch(Exception e)
  {
   System.out.println("분석과정에서 문제발생..\n" + e);
  }
  
  //System.out.println("r>"+realUsage+", >"+idleUsage);
  result = (float)realUsage/(float)(realUsage+idleUsage);
  
  return result;
 }
 
 /** 결과값의 시간 String을 long타입으로 변환 */
 public static long time2long(String strTime)
 {
  long time = 0;
  byte tCount = 0;
  String token = "";
  char c;
  int tTime;
  for(int i=0;i<strTime.length();i++)
  {
   c = strTime.charAt(i);
   if(c == ':' || c == '.')
   {
    tTime = Integer.parseInt(token);
    if(tCount == 0) time += tTime * 60 * 60 * 1000;
    else if(tCount == 1) time += tTime * 60 * 1000;
    else if(tCount == 2) time += tTime * 1000;
    //else if(tCount == 3) time += tTime;
    tCount++;
    token = "";
   }
   else
    token += c;
  }
  tTime = Integer.parseInt(token);
  time += tTime;
  return time;
 }
 
 public void run()
 {
  for(;;)
  {
   try{Thread.sleep(this.cycleTime);}catch(Exception e){}
   System.out.println("cpu Usage >" + this.checkData());
  }
 }
 
 
 
 public static void main(String[] args) {
  // TODO Auto-generated method stub
  cpuMonitor test = new cpuMonitor(1000);
  //test.checkData();
  new Thread(test).start();
 }
}
class vList
{
 public Long cpuTime;
 public int pid;
 
 public vList(int pid, long cpuTime)
 {
  this.pid = pid;
  this.cpuTime = cpuTime;
 }