package com.villcore.protector.rpc.request;

import java.io.Serializable;

public class ActionRequest implements Serializable {

    private Integer commandCode;
    private String msg;

    public ActionRequest(Integer commandCode, String msg) {
        this.commandCode = commandCode;
        this.msg = msg;
    }

    public Integer getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(Integer commandCode) {
        this.commandCode = commandCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
