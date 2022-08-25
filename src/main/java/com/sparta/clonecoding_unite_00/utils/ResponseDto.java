package com.sparta.clonecoding_unite_00.utils;


import lombok.AllArgsConstructor;
import lombok.Data;


//@Getter
//@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@AllArgsConstructor
//public class ResponseDto<T> {
//  private boolean success;
//  private T data;
//  private Error error;
//
//  public static <T> ResponseDto<T> success(T data) {
//    return new ResponseDto<>(true, data, null);
//  }
//
//  public static <T> ResponseDto<T> success1(T data, T data1) {
//    return new ResponseDto<>(true, data, null);
//  }
//
//  public static <T> ResponseDto<T> fail(String code, String message) {
//    return new ResponseDto<>(false, null, new Error(code, message));
//  }
//
//  @Getter
//  @AllArgsConstructor
//  static class Error {
//    private String code;
//    private String message;
//  }
//
//}

@Data
@AllArgsConstructor
public class ResponseDto<T> {

    private int statusCode;
    private String msg;
    private T data;


    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(200, "OK", data);
    }

    public static <T> ResponseDto<T> fail(String message) {
        return new ResponseDto<>(500, message, null);
    }


    public static ResponseDto<?> successToMessage(int i, String some, Object t) {
        return new ResponseDto<>(200, "OK", t);
    }
}
