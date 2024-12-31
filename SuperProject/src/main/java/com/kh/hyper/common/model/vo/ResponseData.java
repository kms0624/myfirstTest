package com.kh.hyper.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ResponseData {

	private String status;
	private String message;
	private Object data;
}
