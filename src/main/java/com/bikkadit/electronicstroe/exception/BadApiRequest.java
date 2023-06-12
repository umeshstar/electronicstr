package com.bikkadit.electronicstroe.exception;



public class BadApiRequest extends RuntimeException{

    public BadApiRequest(String message){

        super(message);
    }
    public BadApiRequest(){
        super("Bad Request");
    }
}
