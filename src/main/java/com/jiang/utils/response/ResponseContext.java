package com.jiang.utils.response;


import com.jiang.utils.status.ResponseMessage;
import com.jiang.utils.status.ResponseMessageFactory;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

@Data
public class ResponseContext<R>{

    private ResponseMessage message;

    private R data;

    public ResponseContext(){

    }

    protected ResponseContext(ResponseMessage responseMessage, R data){
        this.message=responseMessage;
        this.data=data;
    }

    public static ResponseMessage.ResponseStatus getR200SuccessStatus(){
        return ResponseMessageFactory.getSuccessMessage().getStatus();
    }

    public static  ResponseMessage.ResponseStatus getR400DataErrorStatus(){
        return ResponseMessageFactory.getR400ErrorMessage().getStatus();
    }

    public static ResponseMessage.ResponseStatus getR300WaingStatus(){
        return ResponseMessageFactory.getWarningMessage().getStatus();
    }

    public static ResponseMessage.ResponseStatus getR500FailStatus(){
        return ResponseMessageFactory.getFailMessage().getStatus();
    }

    public static ResponseMessage getR200SuccessMessage(){
        return ResponseMessageFactory.getSuccessMessage();
    }

    public static  ResponseMessage getR400DataErrorMessage(){
        return ResponseMessageFactory.getR400ErrorMessage();
    }

    public static ResponseMessage getR300WaingMessage(){
        return ResponseMessageFactory.getWarningMessage();
    }

    public static ResponseMessage getR500FailMessage(){
        return ResponseMessageFactory.getFailMessage();
    }

    public static<T> ResponseContext responseMessage(ResponseMessage responseMessage, T data){
        return new ResponseContext(responseMessage,data);
    }

    public static<T> ResponseContext responseMessage(ResponseMessage.ResponseStatus status,String messageCode,String messageMessage, T data){
        ResponseMessage responseMessage=new ResponseMessage(){
            ErrorProperties errorProperties;
            @Override
            public ResponseStatus getStatus() {
                return status;
            }

            @Override
            public String getMessageCode() {
                return messageCode;
            }

            @Override
            public String getMessageDesc() {
                return messageMessage;
            }

            @Override
            public ErrorProperties getErrorProperties() {
                if(status== ResponseStatus.SUCCESS){
                    return null;
                }else {
                    if(errorProperties!=null){
                        return errorProperties;
                    }
                    return errorProperties=new ErrorProperties();
                }
            }

            @Override
            public ResponseMessage addErrorProperty(String errorMessage, String errorProperty, String errorObject) {
                if(getStatus()==ResponseStatus.SUCCESS){
                    return this;
                }
                ErrorProperties errorProperties=getErrorProperties();
                if(errorProperties==null){
                    return this;
                }
                errorProperties.addErrorProperty(errorMessage,errorProperty,errorObject);
                return this;
            }
        };
        return responseMessage(responseMessage,data);
    }

    /**
     * 响应成功
     * @return
     */
    public static ResponseContext success(){
        return responseMessage(ResponseMessageFactory.getSuccessMessage(),null);
    }




    public static<T> ResponseContext success(T data){
        return responseMessage(ResponseMessageFactory.getSuccessMessage(),data);
    }



    /**
     * 响应失败
     * @return
     */
    public static ResponseContext fail(){
        return responseMessage(ResponseMessageFactory.getFailMessage(),null);
    }

    public static ResponseContext fail(BindingResult bindingResult){
        ResponseContext context=ResponseContext.failMessage(ResponseMessageFactory.getR400ErrorMessage().getMessageDesc());

        List<ObjectError>  errors=bindingResult.getAllErrors();
        if(errors==null ||errors.size()==0){
            return ResponseContext.fail();
        }
        for (ObjectError error : errors) {
            //1 数组返回
//            if (error instanceof FieldError) {
//                FieldError fieldError = (FieldError) error;
//                context.addErrorProperty(error.getDefaultMessage(),fieldError.getField(),fieldError.getObjectName());
//            }else{
//                context.addErrorProperty(error.getDefaultMessage(),null,error.getObjectName());
//            }

            //2一条一条返回
           if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                return ResponseContext.failMessage(error.getDefaultMessage())
                        .addErrorProperty(error.getDefaultMessage(), fieldError.getField(), fieldError.getObjectName());
            }else{
               return ResponseContext.failMessage(error.getDefaultMessage())
                       .addErrorProperty(error.getDefaultMessage(), null, error.getObjectName());
            }
        }


//        List<ErrorObjectProperty> errorFieldList = new ArrayList<ErrorObjectProperty>();
//        ErrorObjectProperty errorObjectProperty;
//        for (ObjectError error : errors) {
//            errorObjectProperty = new ErrorObjectProperty();
//            if (error instanceof FieldError) {
//                FieldError fieldError = (FieldError) error;
//                errorObjectProperty.setErrorProperty(fieldError.getField());
//                errorObjectProperty.setErrorMessage(error.getDefaultMessage());
//                errorObjectProperty.setErrorObject(fieldError.getObjectName());
//                context.addErrorProperty(error.getDefaultMessage(),fieldError.getField(),fieldError.getObjectName());
//            } else {
//                errorObjectProperty.setErrorProperty(error.getObjectName());
//                errorObjectProperty.setErrorMessage(error.getDefaultMessage());
//            }
//
//            errorFieldList.add(errorObjectProperty);
//        }
//        return responseMessage(ResponseMessageFactory.getR400ErrorMessage(),errorFieldList);
        return context;
    }

    /**
     * 响应失败
     * @return
     */
    public static<T> ResponseContext fail(T data){
        return responseMessage(ResponseMessageFactory.getFailMessage(),data);
    }

    public static<T> ResponseContext fail(String messageCode,String messageMessage, T data){
        ResponseMessage responseMessage=new ResponseMessage(){
            ErrorProperties errorProperties;
            @Override
            public ResponseStatus getStatus() {
                return ResponseStatus.FAIL;
            }

            @Override
            public String getMessageCode() {
                return messageCode;
            }

            @Override
            public String getMessageDesc() {
                return messageMessage;
            }

            @Override
            public ErrorProperties getErrorProperties() {
                if(errorProperties!=null){
                    return errorProperties;
                }
                return errorProperties=new ErrorProperties();
            }

            @Override
            public ResponseMessage addErrorProperty(String errorMessage, String errorProperty, String errorObject) {
                if(getStatus()==ResponseStatus.SUCCESS){
                    return this;
                }
                ErrorProperties errorProperties=getErrorProperties();
                if(errorProperties==null){
                    return this;
                }
                errorProperties.addErrorProperty(errorMessage,errorProperty,errorObject);
                return this;
            }
        };
        return responseMessage(responseMessage,data);
    }

    public static<T> ResponseContext fail(String messageMessage, T data){
        return fail(ResponseMessage.ResponseStatus.FAIL.messageCode(),messageMessage, data);
    }

    public static<T> ResponseContext failMessage(String messageMessage){
        return fail(ResponseMessageFactory.getFailMessage().getMessageCode(),messageMessage, null);
    }

    public ResponseContext addErrorProperty(String errorMessage,String errorProperty,String errorObject){
        if(this.getMessage().getErrorProperties()==null){
            return this;
        }
        this.getMessage().addErrorProperty(errorMessage,errorProperty,errorObject);
        return this;
    }

    /**
     * 响应警告
     * @return
     */
    public static ResponseContext warning(){
        return responseMessage(ResponseMessageFactory.getWarningMessage(),null);
    }

    /**
     * 响应警告
     * @return
     */
    public static<T> ResponseContext warning(T data){
        return responseMessage(ResponseMessageFactory.getWarningMessage(),data);
    }
}
