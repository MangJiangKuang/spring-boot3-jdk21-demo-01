package com.jiang.utils.response;


import com.jiang.utils.status.ResponseMessage;

public class ResponseContextPage<T> extends ResponseContext<T> {

    ResponseContextPage(ResponseMessage responseMessage, T data){
       super(responseMessage,data);
    }

}
