package xueli.clock.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import xueli.clock.bean.SystemInfoBean;
import xueli.clock.service.SystemInfoService;
import xueli.swingx.layout.VerticalFilledLayout;
import xueli.swingx.layout.VerticalFilledLayout.HorizontalAlign;
import xueli.swingx.layout.VerticalFilledLayout.VerticalAlign;

public class SystemInfoPanel extends JPanel {

	private static final long serialVersionUID = 1297687095446027141L;

	private final SystemInfoBean bean = new SystemInfoBean();

	private final SystemInfoService service;

	/**
	 * Create the panel.
	 */
	public SystemInfoPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		add(topPanel, BorderLayout.NORTH);

		JPanel topLeftPanel = new JPanel();
		topPanel.add(topLeftPanel);
		topLeftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

		JPanel powerInfoPanel = new JPanel();
		powerInfoPanel.setBorder(new EmptyBorder(0, 2, 0, 2));
		FlowLayout flowLayout_1 = (FlowLayout) powerInfoPanel.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		topLeftPanel.add(powerInfoPanel);

		BatteryInfoPanel powerIndicatorPanel = new BatteryInfoPanel();
		powerInfoPanel.add(powerIndicatorPanel);
		powerIndicatorPanel.setPreferredSize(new Dimension(45, 18));

		JLabel powerUsageRateLabel = new JLabel("");
		powerUsageRateLabel.setBorder(new EmptyBorder(0, 3, 0, 0));
		powerInfoPanel.add(powerUsageRateLabel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		FlowLayout flowLayout_2 = (FlowLayout) bottomPanel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		add(bottomPanel, BorderLayout.SOUTH);

		JPanel bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout(new VerticalFilledLayout(HorizontalAlign.FILL, VerticalAlign.CENTER));
		bottomPanel.add(bottomLeftPanel);

		JLabel systemInfoLabel = new JLabel("");
		systemInfoLabel.setFont(systemInfoLabel.getFont().deriveFont(systemInfoLabel.getFont().getSize() - 2f));
		bottomLeftPanel.add(systemInfoLabel);

		JLabel cpuInfoLabel = new JLabel("");
		bottomLeftPanel.add(cpuInfoLabel);

		JLabel memInfoLabel = new JLabel("");
		bottomLeftPanel.add(memInfoLabel);

		bean.addPropertyChangeListener(SystemInfoBean.PROPERTY_POWER, e -> {
			powerIndicatorPanel.setBean(bean.isPowerCharging(), bean.getPowerRemaining());
			powerUsageRateLabel.setText(bean.getPowerUsageRateString());
		});
		bean.addPropertyChangeListener(SystemInfoBean.PROPERTY_SYSTEM_INFO, e -> {
			systemInfoLabel.setText((String) e.getNewValue());
		});
		bean.addPropertyChangeListener(SystemInfoBean.PROPERTY_CPU_ENABLED, e -> {
			boolean enabled = (boolean) e.getNewValue();
			cpuInfoLabel.setVisible(enabled);
			cpuInfoLabel.setEnabled(enabled);
		});
		bean.addPropertyChangeListener(SystemInfoBean.PROPERTY_CPU, e -> {
			cpuInfoLabel.setText(String.format("CPU: %.1f%% %.1f℃", bean.getCpuLoad(), bean.getCpuTemperature()));
		});
		bean.addPropertyChangeListener(SystemInfoBean.PROPERTY_MEMORY, e -> {
			memInfoLabel
					.setText(String.format("Memory: %s %.1f%%", bean.getMemoryInfo(), bean.getMemoryUsedPercentage()));
		});

		service = new SystemInfoService(bean);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				service.start();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				service.stop();
			}
		});

		service.start();

	}

}
