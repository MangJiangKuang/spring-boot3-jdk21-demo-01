package com.jiang.utils.response;

import lombok.Data;

@Data
public class ErrorObjectProperty {

    //错误对象
    private String errorObject;

    //错误字段

    private String errorProperty;

    //错误描述
    private String errorMessage;
}
