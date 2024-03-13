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

	public static LocalDateTime[] parseDateRange(String registerDateRange) {
		StringTokenizer st = new StringTokenizer(registerDateRange, "-");
		String startDateStr = st.nextToken() + "-" + st.nextToken() + "-" + st.nextToken();
		String endDateStr = st.nextToken() + "-" + st.nextToken() + "-" + st.nextToken();

		LocalDate startDate = LocalDate.parse(startDateStr);
		LocalDate endDate = LocalDate.parse(endDateStr);

		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);

		return new LocalDateTime[]{startDateTime, endDateTime};
	}
}
