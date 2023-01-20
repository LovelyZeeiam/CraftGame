package xueli.clock.service;

import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import xueli.clock.bean.SystemInfoBean;
import xueli.swingx.Service;

public class SystemInfoService extends Service<SystemInfoBean> {
	
	private static final SystemInfo sysInfoBean = new SystemInfo();
	private static final OperatingSystem osInfoBean = sysInfoBean.getOperatingSystem();
	private static final HardwareAbstractionLayer harderLayerBean = sysInfoBean.getHardware();
	
	private static final String sysInfoStr = osInfoBean.getFamily().trim() + " " + osInfoBean.getVersionInfo().getVersion().trim();
	private static final boolean isWindows;
	private static final boolean elevated = osInfoBean.isElevated();
	
	private static final GlobalMemory memoryInfoBean = harderLayerBean.getMemory();
	private static final CentralProcessorLoadTicker cpuLoadBean = new CentralProcessorLoadTicker(harderLayerBean.getProcessor());
	
	private static final Sensors sensorsBean = harderLayerBean.getSensors();
	
	private static final PowerSource powerSourceBean;
	static {
		List<PowerSource> powerSources = harderLayerBean.getPowerSources();
		if(powerSources.size() > 0) {
			powerSourceBean = powerSources.get(0);
		} else {
			powerSourceBean = null;
		}
		
		String osName = System.getProperty("os.name");
		isWindows = osName.contains("Windows");
		
	}
	
	public SystemInfoService(SystemInfoBean bean) {
		super(bean, 1000);
	}
	
	public void start() {
		super.start();
		this.initBean();
	}
	
	private void initBean() {
		bean.setSystemInfo(sysInfoStr);
		
		if(!elevated && isWindows) {
			bean.setCpuInfoEnabled(false);
		}
		
	}
	
	@Override
	protected void updateTimer() {
		long memoryTotal = memoryInfoBean.getTotal();
		long memoryUsed = memoryTotal - memoryInfoBean.getAvailable();
		double memoryUsedPercentage = (double) memoryUsed / memoryTotal * 100.0;
		bean.setMemoryInfo(memoryUsedPercentage, String.format("%s / %s", FormatUtil.formatBytes(memoryUsed), FormatUtil.formatBytes(memoryTotal)));
		
		bean.setCpuInfo(cpuLoadBean.getLoadPercentage(), sensorsBean.getCpuTemperature());
		
		if(powerSourceBean != null) {
			powerSourceBean.updateAttributes();
			
			// The unit in Ubuntu and Windows differs! Why...
			double curCapacity = powerSourceBean.getCurrentCapacity();
			double maxCapacity = powerSourceBean.getMaxCapacity();
			bean.setPowerCharging(powerSourceBean.isCharging(), 100.0 * curCapacity / maxCapacity, formatEfficiency(powerSourceBean.getPowerUsageRate() / 1000));
			
		}
		
	}
	
	private String formatEfficiency(double mw) {
		if(mw < 1000)
			return String.format("%.1fmW", mw);
		return String.format("%.1fW", mw / 1000);
	}
	
}
