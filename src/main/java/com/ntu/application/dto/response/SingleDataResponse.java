package com.ntu.application.dto.response;

import com.ntu.infrastructure.util.I18n;
import org.springframework.http.HttpStatus;

import static com.ntu.infrastructure.constant.MessagesConstant.SUCCESS_MSG;

public class SingleDataResponse<T> extends BaseResponse {
    private T data;

    public static <T> SingleDataResponse<T> success(T data) {
        var response = new SingleDataResponse<T>();
        response.data = data;
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(I18n.getMessage(SUCCESS_MSG));
        response.setType(ResponseType.SUCCESS);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
