package com.example.shonicserver.payload;

import javafx.beans.binding.ObjectExpression;

public class Response {
    public String status;
    public String message;
    public Object data;
    public Object error;

    public Response(String status, String message, Object data, Object error) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }
}
