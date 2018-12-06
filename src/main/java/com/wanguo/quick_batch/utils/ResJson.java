package com.wanguo.quick_batch.utils;

/**
 * 普通的
 * 返回Json 的格式化类
 * <p>
 * 格式统一为
 * <p>
 * {
 * "code": "1",               同样代表请求是否成功，成功的话为1
 * "msg": "",                 包含的信息，用以存储错误提示，或者成功提示
 * "data": {},             当返回的数据需要包含 map数据 的时候使用
 * }
 * <p>
 * <p>
 * Created by Ericwyn on 17-11-4.
 */
public class ResJson {
    private Integer code;
    private String msg;
    private Object data;

    public ResJson(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //    public static ResJson errorAccessToken(){
//        return new ResJson(1100,"access token error or expired",null);
//    }
    public static ResJson errorAccessToken() {
        return new ResJson(1100, "登录过期，请重新打开小程序", null);
    }

    public static ResJson failJson(Integer code, String msg, Object data) {
        return new ResJson(code, msg, data);
    }

    public static ResJson errorRequestParam() {
        return new ResJson(1200, "the request param error", null);
    }

    public static ResJson errorRequestParam(String msg) {
        return new ResJson(1200, "the request param error --> " + msg, null);
    }

    // 服务器出错
    public static ResJson serverErrorJson() {
        return new ResJson(1001, "系统出错, 服务器出现故障", null);
    }

    public static ResJson serverErrorJson(String errorMsg) {
        return new ResJson(1001, "系统出错, 服务器出现故障 " + errorMsg, null);
    }

    //请求成功
    public static ResJson successJson(String msg) {
        return new ResJson(1000, msg, null);
    }

    public static ResJson successJson(String msg, Object data) {
        return new ResJson(1000, msg, data);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResJson() {
    }
}
