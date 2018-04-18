package com.kuretru.game.sokoban;

public class TimeUtiliy {

	public static String getTimeString(long time) {
		if (time <= 0)
			return String.valueOf(time);
		long milSecond = time % 1000;
		time /= 1000;
		long second = time % 60;
		time /= 60;
		long minute = time % 60;
		return String.format("%d·Ö%dÃë %d", minute, second, milSecond);
	}
}
