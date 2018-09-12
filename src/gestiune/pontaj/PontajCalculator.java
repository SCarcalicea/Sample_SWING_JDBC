package gestiune.pontaj;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PontajCalculator {
	
	private static LocalDateTime startTime = null;
	
	public static LocalDateTime getStartTime() {
		return startTime;
	}

	public static String startTime() {
		startTime = LocalDateTime.now();
		return "Ora intrare: " + startTime.toString();
	}
	
	public static String endTime() {
		if (startTime != null) {
			long workedHours = LocalDateTime.now().until(startTime, ChronoUnit.HOURS);
			return "Ai lucrat " + workedHours + " ore.";
		}
		
		return null;
	}
	
	public static void automaticTimer() {
		startTime = LocalDateTime.now();
		
		Thread timer = new Thread (new Runnable () {
			public void run() {
				LocalDateTime time = LocalDateTime.now();
//				time = time.plus(8, ChronoUnit.HOURS);
				time = time.plus(8, ChronoUnit.SECONDS);
				
				while (true) {
					LocalDateTime end = LocalDateTime.now();
					if (end.isAfter(time)) {
						System.exit(0);
					} else {
						System.out.println(end);
					}
				}
			}
		});
		timer.start();
	}

}
