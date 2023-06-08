package xueli.clock.service;

public class CentralProcessorLoadTicker {

//	private final CentralProcessor cpuInfoBean;
//
//	public CentralProcessorLoadTicker(CentralProcessor cpuInfo) {
//		this.cpuInfoBean = cpuInfo;
//	}
//
//	private long[] prevTicks;
//
//	public double getLoadPercentage() {
//		long[] ticks = cpuInfoBean.getSystemCpuLoadTicks();
//		if (prevTicks == null)
//			return 0;
//
//		long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
//		long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
//		long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
//		long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
//		long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
//		long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
//		long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
//		long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
//		long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
//		double result = 1 - (double) idle / totalCpu;
//
//		this.prevTicks = ticks;
//		return result;
//	}

}
