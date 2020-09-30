package com.qixi.mq.delay.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 统一接口响应格式
 *
 * @author ZhengNC
 * @date 2020/9/15 14:24
 */
@Getter
@Setter
public class ResponseEntity<T> {

    private String code = "200";

    private String msg = "success";

    private String timestamp = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .format(LocalDateTime.now());

    private T data;

    public ResponseEntity(){}

    public ResponseEntity(T data) {
        this.data = data;
    }

    public ResponseEntity(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseEntity success(){
        return new ResponseEntity();
    }

    public static ResponseEntity fail(){
        return new ResponseEntity("500", "fail", null);
    }
}
