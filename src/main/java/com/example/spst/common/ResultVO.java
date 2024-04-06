package com.example.spst.common;

import lombok.Data;

@Data
public class ResultVO<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;
    private ResultVO() {
        this.success = true;
    }


    public static <T> ResultVO<T> getInstance() {
       return new ResultVO<>();
    }

    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> tResultVO = new ResultVO<>();
        tResultVO.setData(data);
        return tResultVO;
    }

    public static <T> ResultVO<T> success(String msg) {
        ResultVO<T> tResultVO = new ResultVO<>();
        tResultVO.setMessage(msg);
        return tResultVO;
    }

    public static <T> ResultVO<T> fail(String errorMsg) {
        ResultVO<T> tResultVO = new ResultVO<>();
        tResultVO.code = "1001";
        tResultVO.setMessage(errorMsg);
        tResultVO.setSuccess(false);
        return tResultVO;
    }

    public static <T> ResultVO<T> fail(String errorMsg, String errorCode) {
        ResultVO<T> tResultVO = new ResultVO<>();
        tResultVO.code = errorCode;
        tResultVO.setMessage(errorMsg);
        return tResultVO;
    }
}
