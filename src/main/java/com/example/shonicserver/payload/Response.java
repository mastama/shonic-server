package com.example.shonicserver.payload;

public class Response {
    public int code;
    public String message;
    public Object data;
    public Object error;

    public Response(int status, String message, Object data, Object error) {
        this.code = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }
}
