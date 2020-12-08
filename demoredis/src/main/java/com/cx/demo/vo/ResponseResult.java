package com.cx.demo.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Getter
@Setter
public class ResponseResult {
    private Integer code;
    private String message;
    private Object data;
}
