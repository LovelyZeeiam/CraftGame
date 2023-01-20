package xueli.clock.bean;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

public class SystemInfoBean {
	
	public static final String PROPERTY_SYSTEM_INFO = "0";
	public static final String PROPERTY_CPU_ENABLED = "3";
	public static final String PROPERTY_CPU = "4";
	public static final String PROPERTY_POWER = "5";
	public static final String PROPERTY_MEMORY = "6";
	
	private final SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this, true);
	
	private String systemInfo = "";
	
	private double memoryUsedPercentage = 0.0;
	private String memoryInfo = "";
	
	private boolean cpuInfoEnabled = true;
	private double cpuLoad = 0.0;
	private double cpuTemperature = 0.0;
	
	private boolean isPowerCharging = true;
	private double powerRemaining = 100.0;
	private String powerUsageRateString = "";
	
	public SystemInfoBean() {}
	
	public void setSystemInfo(String systemInfo) {
		pcs.firePropertyChange(PROPERTY_SYSTEM_INFO, this.systemInfo, systemInfo);
		this.systemInfo = systemInfo;
	}
	
	public String getSystemInfo() {
		return systemInfo;
	}
	
	public void setMemoryInfo(double usedPercentage, String memoryInfo) {
		this.memoryInfo = memoryInfo;
		this.memoryUsedPercentage = usedPercentage;
		pcs.firePropertyChange(PROPERTY_MEMORY, null, null);	
	}
	
	public double getMemoryUsedPercentage() {
		return memoryUsedPercentage;
	}
	
	public String getMemoryInfo() {
		return memoryInfo;
	}
	
	public void setCpuInfoEnabled(boolean cpuInfoEnabled) {
		pcs.firePropertyChange(PROPERTY_CPU_ENABLED, this.cpuInfoEnabled, cpuInfoEnabled);
		this.cpuInfoEnabled = cpuInfoEnabled;
	}
	
	public boolean isCpuInfoEnabled() {
		return cpuInfoEnabled;
	}
	
	public void setCpuInfo(double cpuLoad, double cpuTemperature) {
		this.cpuLoad = cpuLoad;
		this.cpuTemperature = cpuTemperature;
		pcs.firePropertyChange(PROPERTY_CPU, null, null);
	}
	
	public double getCpuLoad() {
		return cpuLoad;
	}
	
	public double getCpuTemperature() {
		return cpuTemperature;
	}
	
	public void setPowerCharging(boolean isCharging, double remain, String usageRateStr) {
		this.isPowerCharging = isCharging;
		this.powerRemaining = remain;
		this.powerUsageRateString = usageRateStr;
		pcs.firePropertyChange(PROPERTY_POWER, null, null);
	}

	public boolean isPowerCharging() {
		return isPowerCharging;
	}
	
	public double getPowerRemaining() {
		return powerRemaining;
	}
	
	public String getPowerUsageRateString() {
		return powerUsageRateString;
	}
	
	public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(name, listener);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
}
