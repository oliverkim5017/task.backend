package org.task.backend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.StringTokenizer;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
public class DateRangeParser {

	public static LocalDateTime[] parseDateTimeRange(String registerDateRange) {
		try {
			StringTokenizer st = new StringTokenizer(registerDateRange, "~");
			String startDateStr = st.nextToken() + "-" + st.nextToken() + "-" + st.nextToken();
			String endDateStr = st.nextToken() + "-" + st.nextToken() + "-" + st.nextToken();

			LocalDate startDate = LocalDate.parse(startDateStr);
			LocalDate endDate = LocalDate.parse(endDateStr);

			LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);
			LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
			return new LocalDateTime[]{startDateTime, endDateTime};
		} catch (Exception e) {
			return new LocalDateTime[]{};
		}
	}


	public static LocalDate[] parseDateRange(String dateRange) {
		try {
			String[] dates = dateRange.split("~");
			if (dates.length != 2) {
				throw new IllegalArgumentException("日期范围格式不正确，应为'YYYY-MM-DD~YYYY-MM-DD'");
			}

			LocalDate startDate = LocalDate.parse(dates[0]);
			LocalDate endDate = LocalDate.parse(dates[1]);

			return new LocalDate[]{startDate, endDate};
		} catch (Exception e) {
			System.err.println("解析日期出错: " + e.getMessage());
			return new LocalDate[]{};
		}
	}
}
