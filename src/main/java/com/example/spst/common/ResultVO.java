package com.example.spst.common;

import lombok.Data;

@Data
public class ResultVO<T> {
    private Integer code;
    private String errorMsg;
    private T data;
    private ResultVO() {}

    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> tResultVO = new ResultVO<>();
        tResultVO.code = ResultCode.SUCCESS.getCode();
        tResultVO.setData(data);
        return tResultVO;
    }

    public static <T> ResultVO<T> fail(String errorMsg) {
        ResultVO<T> tResultVO = new ResultVO<>();
        tResultVO.code = ResultCode.FAIL.getCode();
        tResultVO.setData(null);
        tResultVO.setErrorMsg(errorMsg);
        return tResultVO;
    }

    public static <T> ResultVO<T> fail(String errorMsg, T data) {
        ResultVO<T> tResultVO = new ResultVO<>();
        tResultVO.code = ResultCode.FAIL.getCode();
        tResultVO.setData(data);
        tResultVO.setErrorMsg(errorMsg);
        return tResultVO;
    }

    enum ResultCode {
        SUCCESS(0),
        FAIL(1);
        private int code;
        ResultCode(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
