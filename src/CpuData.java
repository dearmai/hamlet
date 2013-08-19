import java.math.BigInteger;
import java.util.Random;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;


public class CpuData {
	private static Sigar sigar;

    public CpuData(Sigar s) throws SigarException {
        sigar = s;
        System.out.println(cpuInfo());
    }

    public static void main(String[] args) throws InterruptedException, SigarException {
        new CpuData(new Sigar());
        CpuData.startMetricTest();
    }

    private static void startMetricTest() throws InterruptedException, SigarException {
        new Thread() {
            public void run() {
                while(true) 
                BigInteger.probablePrime(MAX_PRIORITY, new Random());
            };
        }.start();
        while(true) {
            String pid = ""+sigar.getPid();
            System.out.print(getMetric(pid));
            for(Double d:getMetric()){
                System.out.print("\t"+d);
            }
            System.out.println();
            Thread.sleep(1000);
        }       
    }

    public String cpuInfo() throws SigarException {
        CpuInfo[] infos = sigar.getCpuInfoList();
        CpuInfo info = infos[0];

        String infoString = info.toString();
        if ((info.getTotalCores() != info.getTotalSockets())
                || (info.getCoresPerSocket() > info.getTotalCores())) {
            infoString+=" Physical CPUs: " + info.getTotalSockets();
            infoString+=" Cores per CPU: " + info.getCoresPerSocket();
        }

        long cacheSize = info.getCacheSize();
        if (cacheSize != Sigar.FIELD_NOTIMPL) {
            infoString+="Cache size...." + cacheSize;
        }
        return infoString;
    }

    public static Double[] getMetric() throws SigarException {
        CpuPerc cpu = sigar.getCpuPerc();
        double system = cpu.getSys();
        double user = cpu.getUser();
        double idle = cpu.getIdle();
//      System.out.println("idle: " +CpuPerc.format(idle) +", system: "+CpuPerc.format(system)+ ", user: "+CpuPerc.format(user));
        return new Double[] {system, user, idle};
    }

    public static double getMetric(String pid) throws SigarException {
        ProcCpu cpu = sigar.getProcCpu(pid);
//      System.out.println(sigar.getProcFd(pid));
//      System.err.println(cpu.toString());
        return cpu.getPercent();
    }
}
