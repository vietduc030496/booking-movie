package com.ntu.site.application.dto.response;

import com.ntu.common.util.I18n;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static com.ntu.common.constant.MessagesConstant.SUCCESS_MSG;


@Getter
@Setter
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

}
