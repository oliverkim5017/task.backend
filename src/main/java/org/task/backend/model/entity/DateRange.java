package org.task.backend.model.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-06
 */
@Data
public class DateRange {

	private LocalDate startDate;
	private LocalDate endDate;

}
