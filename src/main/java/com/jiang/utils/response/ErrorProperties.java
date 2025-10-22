package com.jiang.utils.response;


import lombok.Data;

import java.util.ArrayList;

@Data
public class ErrorProperties extends ArrayList<ErrorObjectProperty> {

   public ErrorObjectProperty addErrorProperty(String errorMessage,String errorProperty,String errorObject){
       ErrorObjectProperty property=new ErrorObjectProperty();
       property.setErrorMessage(errorMessage);
       property.setErrorProperty(errorProperty);
       property.setErrorObject(errorObject);
       this.add(property);
       return property;
   }

   public boolean removeErrorProperty(ErrorObjectProperty property){
      return  remove(property);
   }
}
