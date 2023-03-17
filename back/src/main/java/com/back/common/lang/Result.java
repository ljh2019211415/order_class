package com.back.common.lang;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Result implements Serializable {
    private int code;//200-correct,!200-error
    private  String msg;
    private  Object data;
    private int total;

    public static Result succ() {

        return succ(200, "操作成功", 0);
    }
    public static Result succ(Object data) {

        return succ(200, "操作成功", data);
    }
    public static Result succ(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    public static Result fail(String msg) {
        return fail(400, msg, null);
    }
    public static Result fail(String msg, Object data) {
        return fail(400, msg, data);
    }
    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }





}