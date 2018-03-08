package com.ldc.springLearn.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SpringControllerAdvice {

//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<Object> handleOtherExceptions(final Exception ex, final WebRequest req) {
//        TResult tResult = new TResult();
//        tResult.setStatus(CodeType.V_500);
//        tResult.setErrorMessage(ex.getMessage());
//        return new ResponseEntity<Object>(tResult,HttpStatus.OK);
//    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleBusinessException(Exception e){
        return "EOOR500001 -> " + e.getMessage();
    }

}
