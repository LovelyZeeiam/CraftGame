package xueli.clock.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import xueli.clock.ClockFrame;
import xueli.clock.bean.ClockBean;
import xueli.swingx.Service;

public class ClockService extends Service<ClockBean> {
	
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd");
	
	public ClockService(ClockBean bean) {
		super(bean, 20);
	}
	
	@Override
	protected void updateTimer() {
//		long start = System.currentTimeMillis();
		ClockFrame.M_ANIMATION_MANAGER.tick();
		
		Date date = new Date();
		bean.setTimeString(TIME_FORMAT.format(date));
		bean.setDateString(DATE_FORMAT.format(date));
		
//		System.out.println(System.currentTimeMillis() - start);
		
	}
	
}
