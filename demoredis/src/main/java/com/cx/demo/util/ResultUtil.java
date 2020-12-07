package com.cx.demo.util;


import com.cx.demo.vo.ResponseResult;
import org.springframework.stereotype.Component;

@Component
public class ResultUtil {

    public ResponseResult getResponseResult(Integer id, String message, Object obj){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(id);
        responseResult.setMessage(message);
        responseResult.setData(obj);
        return responseResult;
    }

    public ResponseResult getResponseResult(Integer id){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(id);
        return responseResult;
    }

    public ResponseResult getResponseResult(Integer id, String message){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(id);
        responseResult.setMessage(message);
        return responseResult;
    }

    public ResponseResult getResponseResult(Integer id, Object obj){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(id);
        responseResult.setData(obj);
        return responseResult;
    }
}
