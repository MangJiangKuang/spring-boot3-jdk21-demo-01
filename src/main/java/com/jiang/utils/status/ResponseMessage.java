package com.jiang.utils.status;


import com.jiang.utils.response.ErrorProperties;

public interface ResponseMessage {
    public enum ResponseStatus{
        SUCCESS("0","成功"),
        FAIL("1","失败"),
        WAINING("2","警告");

        private String code;
        private String msg;
        ResponseStatus(String code,String msg){
            this.code=code;
            this.msg=msg;
        }

        public String messageCode(){
            return this.code;
        }

        public String message(){
            return msg;
        }
    }

    ResponseStatus getStatus();

    String getMessageCode();

    String getMessageDesc();

    ErrorProperties getErrorProperties();

    public ResponseMessage addErrorProperty(String errorMessage,String errorProperty,String errorObject);

}
