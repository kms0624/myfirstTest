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
	private Object data;	// 어떤 값이든 담아가기 위해서 object 형태로 담는다.
}
