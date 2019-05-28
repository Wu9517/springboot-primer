package com.wzy.springboot.websocket;

/**
 * @author wzy
 */
public class WsResponse {
    private String responseMessage;

    public WsResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
