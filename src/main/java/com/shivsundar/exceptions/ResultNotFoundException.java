package com.shivsundar.exceptions;

public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException(){
        super("Result Not Found!");
    }
    public ResultNotFoundException(String message){
        super(message);
    }
}
