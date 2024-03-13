package org.task.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
	private Integer id;
	private String name;
	private List<String> operations;
	private String token;
}
