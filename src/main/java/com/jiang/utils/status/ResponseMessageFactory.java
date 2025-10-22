package com.jiang.utils.status;


import com.jiang.utils.response.ErrorProperties;

public class ResponseMessageFactory implements  ResponseMessage{

    private ResponseStatus status;

    private String messageCode;

    private String messageDesc;

    private ResponseMessageFactory(ResponseStatus status, String messageCode, String message){
        this.status=status;
        this.messageCode=messageCode;
        this.messageDesc=message;
    }



    public static ResponseMessage getSuccessMessage(){
        return addResponseMessage(ResponseStatus.SUCCESS,"R200","请求处理成功");
    }

    public static ResponseMessage addResponseMessage(ResponseStatus status,String messageCode,String messageDesc){
        ResponseMessageFactory  messageFactory=new ResponseMessageFactory(status,messageCode,messageDesc);
        return messageFactory;
    }

    public static ResponseMessage getFailMessage(){
        return addResponseMessage(ResponseStatus.FAIL,"R500","请求处理异常");
    }

    public static ResponseMessage getWarningMessage(){
        return addResponseMessage(ResponseStatus.WAINING,"R300","成功处理");
    }

    public static ResponseMessage getFailMessage(String messageCode,String messageDesc){
        return addResponseMessage(ResponseStatus.FAIL,messageCode,messageDesc);
    }

    public static ResponseMessage getR400ErrorMessage(){
        return addResponseMessage(ResponseStatus.FAIL,"R400","请求传入的参数错误");
    }

    @Override
    public ResponseStatus getStatus(){
        return status;
    }

    public String getMessageCode(){
        return this.messageCode;
    }

    public String getMessageDesc(){
        return this.messageDesc;
    }

    @Override
    public ErrorProperties getErrorProperties() {
        if(this.status==ResponseStatus.SUCCESS){
            return null;
        }
        return new ErrorProperties();
    }

    @Override
    public ResponseMessage addErrorProperty(String errorMessage, String errorProperty, String errorObject) {
        if(this.status==ResponseStatus.SUCCESS){
            return this;
        }
        if( getErrorProperties() ==null){
            return this;
        }
        getErrorProperties().addErrorProperty(errorMessage,errorProperty,errorObject);
        return this;
    }

}
