package xueli.clock.service;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Timer;

import xueli.clock.ClockFrame;
import xueli.clock.bean.ClockBean;

public class ClockService {
	
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd");
	
	private final ClockBean bean;
	private final Timer updateTimer = new Timer(16, this::updateTimer);
	
	public ClockService(ClockBean bean) {
		this.bean = bean;
		this.updateTimer.start();
		
	}
	
	private void updateTimer(ActionEvent e) {
		ClockFrame.M_ANIMATION_MANAGER.tick();
		
		Date date = new Date();
		bean.setTimeString(TIME_FORMAT.format(date));
		bean.setDateString(DATE_FORMAT.format(date));
		
	}
	
	public void close() {
		this.updateTimer.stop();
		
	}
	
}
