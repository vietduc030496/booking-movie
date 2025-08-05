package com.ntu.site.application.dto.response;

import com.ntu.common.util.I18n;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collection;

import static com.ntu.common.constant.MessagesConstant.SUCCESS_MSG;

@Getter
@Setter
public class CollectionDataResponse<T> extends BaseResponse {
    private PageInfo pageInfo;
    private Collection<T> list;

    public static <T> CollectionDataResponse<T> success(Collection<T> list) {
        var response = new CollectionDataResponse<T>();
        response.list = list;
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(I18n.getMessage(SUCCESS_MSG));
        response.setType(ResponseType.SUCCESS);
        return response;
    }

    public static <T> CollectionDataResponse<T> success(Collection<T> list,  PageInfo pageInfo) {
        var response = new CollectionDataResponse<T>();
        response.list = list;
        response.pageInfo = pageInfo;
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(I18n.getMessage(SUCCESS_MSG));
        response.setType(ResponseType.SUCCESS);
        return response;
    }

}
