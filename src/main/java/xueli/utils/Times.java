package xueli.utils;

public class Times {

	public static String toString(long date) {
		long current = System.currentTimeMillis() / 1000;
		long seconds = current - date;

		if (seconds == 0)
			return "in 1 second";
		if (seconds == 1)
			return "1 second ago";
		if (seconds < 60)
			return seconds + " seconds ago";

		long minute = seconds / 60;
		if (minute == 1)
			return "1 minute ago";
		if (minute < 60)
			return minute + " minutes ago";

		long hour = minute / 60;
		if (hour == 1)
			return "1 hour ago";
		if (hour < 24)
			return hour + " hours ago";

		long day = hour / 24;
		if (day == 1)
			return "1 day ago";
		return day + " days ago";
	}

}
