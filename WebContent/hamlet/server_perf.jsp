<%@page import="java.lang.management.ManagementFactory"%>
<%@page import="com.sun.management.OperatingSystemMXBean"%>
<%@page import="org.apache.commons.collections.map.CaseInsensitiveMap"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONSerializer"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.File" %>
<%
SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월dd일 a hh시mm분 ss초", Locale.KOREA); 
Runtime rt = Runtime.getRuntime();
long free = rt.freeMemory();
long total = rt.totalMemory();
long usedRatio = (total - free) * 100 / total;
long unusedRatio = free * 100 / total;

OperatingSystemMXBean osbean = ( OperatingSystemMXBean ) ManagementFactory.getOperatingSystemMXBean( );
System.out.println( "OS Name: " + osbean.getName( ) );
System.out.println( "OS Arch: " + osbean.getArch( ) );
System.out.println( "Available Processors: " + osbean.getAvailableProcessors( ) );
System.out.println( "TotalPhysicalMemorySize: " + osbean.getTotalPhysicalMemorySize( ) );
System.out.println( "FreePhysicalMemorySize: " + osbean.getFreePhysicalMemorySize( ) );
System.out.println( "TotalSwapSpaceSize: " + osbean.getTotalSwapSpaceSize( ) );
System.out.println( "FreeSwapSpaceSize: " + osbean.getFreeSwapSpaceSize( ) );
System.out.println( "CommittedVirtualMemorySize: " + osbean.getCommittedVirtualMemorySize( ) );
System.out.println( "SystemLoadAverage: " + osbean.getSystemLoadAverage( ) );

long phisical_free = osbean.getFreePhysicalMemorySize( );
long phisical_total = osbean.getTotalPhysicalMemorySize( );
long phisical_used_ratio = (phisical_total - phisical_free) * 100 / phisical_total;
long phisical_unused_ratio = phisical_free * 100 / phisical_total;

HashMap map = new HashMap();

map.put("osName", osbean.getName( ) );
map.put("osArch", osbean.getArch( ) );
map.put("availableProcess", osbean.getAvailableProcessors( ) );
map.put("totalPhysicalMemorySize", osbean.getTotalPhysicalMemorySize( ) / 1024 / 1024 );	//byte를 MByte로 변경 
map.put("freePhysicalMemorySize", osbean.getFreePhysicalMemorySize( ) / 1024 / 1024 );	//byte를 MByte로 변경 
map.put("phisical_used_ratio", phisical_used_ratio );
map.put("phisical_unused_ratio", phisical_unused_ratio );



map.put("host",InetAddress.getLocalHost().getHostName());
map.put("host_id", InetAddress.getLocalHost().getHostAddress());
map.put("java_vm_vendor", System.getProperty("java.vm.vendor"));
map.put("java_version", System.getProperty("java.vm.version"));
map.put("java_home", System.getProperty("java.home"));
map.put("read_datetime", formatter.format(new Date()));
map.put("vm_total_mem", total/1024/1024);	//byte를 MByte로 변경 
map.put("vm_use_mem", (total - free)/1024/1024);	//byte를 MByte로 변경 
map.put("vm_free_mem", free/1024/1024);		//byte를 MByte로 변경 
map.put("vm_use_mem_ratio", usedRatio);
map.put("vm_free_mem_ratio", unusedRatio);
List<HashMap> disks = new ArrayList();

File[] roots = File.listRoots();
File root = roots[0];

	map.put("diskName" , root.getAbsolutePath());
	map.put("freeSpace", root.getFreeSpace() / 1024 / 1024);		//byte를 MByte로 변경 
	map.put("totalSpace", root.getTotalSpace() / 1024 / 1024);	//byte를 MByte로 변경 
	map.put("useSpace", (root.getTotalSpace() - root.getFreeSpace()) /1024 /1024); //byte를 MByte로 변경
	
JSONObject jsonobj = new JSONObject();
jsonobj = JSONObject.fromObject(JSONSerializer.toJSON(map));
		
String re = jsonobj.toString();
out.println(re);
%>
		