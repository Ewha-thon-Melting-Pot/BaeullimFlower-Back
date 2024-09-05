package com.meltingpot.baeullimflower.global.apiResponse.exception.handler;


import com.meltingpot.baeullimflower.global.apiResponse.code.BaseErrorCode;
import com.meltingpot.baeullimflower.global.apiResponse.exception.GeneralException;

public class ErrorHandler extends GeneralException {

    public ErrorHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
