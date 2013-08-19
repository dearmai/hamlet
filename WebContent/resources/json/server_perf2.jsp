<%@page import="java.lang.management.ThreadMXBean"%>
<%@page import="java.lang.management.ClassLoadingMXBean"%>
<%@page import="java.lang.management.MemoryUsage"%>
<%@page import="java.lang.management.MemoryMXBean"%>
<%@page import="com.sun.management.OperatingSystemMXBean"%>
<%@page import="java.lang.management.RuntimeMXBean"%>
<%@page import="java.lang.management.ManagementFactory"%>
<%@page import="java.io.File"%>
<%
	Test t = new Test();
%>
<%!
 class Test{
    public Test( )
    {
        showOSBean( );
        showThreadBean( );
        showClassLoading( );
        showMemory( );
        showDisk( );
        showCPU( );
    }


    /*
     * 디스크용량
     */
    public  void showDisk( )
    {
        File root = null;
        try
        {
            root = new File( "C:/" );
            System.out.println( "Total  Space: " + root.getTotalSpace( ) );
            System.out.println( "Usable Space: " + root.getUsableSpace( ) );
        }
        catch ( Exception e )
        {
            e.printStackTrace( );
        }
    }

    /*
     * cpu 사용량
     */
    public void showCPU( )
    {
        OperatingSystemMXBean osbean = ( OperatingSystemMXBean ) ManagementFactory.getOperatingSystemMXBean( );
        RuntimeMXBean runbean = ManagementFactory.getRuntimeMXBean( );
        long bfprocesstime = osbean.getProcessCpuTime( );
        long bfuptime = runbean.getUptime( );
        long ncpus = osbean.getAvailableProcessors( );
        for ( int i = 0 ; i < 1000000 ; ++i )
        {
            ncpus = osbean.getAvailableProcessors( );
        }
        long afprocesstime = osbean.getProcessCpuTime( );
        long afuptime = runbean.getUptime( );
        float cal = ( afprocesstime - bfprocesstime ) / ( ( afuptime - bfuptime ) * 10000f );
        float usage = Math.min( 99f , cal );
        System.out.println( "Calculation: " + cal );
        System.out.println( "CPU Usage: " + usage );
    }

    public void showRuntime( )
    {
        RuntimeMXBean runbean = ManagementFactory.getRuntimeMXBean( );
    }

    /*
     * 메모리 사용량
     */
    public void showMemory( )
    {
        MemoryMXBean membean = ManagementFactory.getMemoryMXBean( );
        MemoryUsage heap = membean.getHeapMemoryUsage( );
        System.out.println( "Heap Memory: " + heap.toString( ) );
        MemoryUsage nonheap = membean.getNonHeapMemoryUsage( );
        System.out.println( "NonHeap Memory: " + nonheap.toString( ) );
    }

    public void showClassLoading( )
    {
        ClassLoadingMXBean classbean = ManagementFactory.getClassLoadingMXBean( );
        System.out.println( "TotalLoadedClassCount: " + classbean.getTotalLoadedClassCount( ) );
        System.out.println( "LoadedClassCount: " + classbean.getLoadedClassCount( ) );
        System.out.println( "UnloadedClassCount: " + classbean.getUnloadedClassCount( ) );
    }

    public void showThreadBean( )
    {
        ThreadMXBean tbean = ManagementFactory.getThreadMXBean( );
        long[] ids = tbean.getAllThreadIds( );
        System.out.println( "Thread Count: " + tbean.getThreadCount( ) );
        for ( long id : ids )
        {
            System.out.println( "Thread CPU Time(" + id + ")" + tbean.getThreadCpuTime( id ) );
            System.out.println( "Thread User Time(" + id + ")" + tbean.getThreadCpuTime( id ) );
        }
    }

    /**
     * OS 정보
     */
    public void showOSBean( )
    {
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
    }
}
%>