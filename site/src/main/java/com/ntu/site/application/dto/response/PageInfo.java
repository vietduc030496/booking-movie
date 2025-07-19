package com.ntu.site.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageInfo {
    @JsonIgnore
    public static final int DEFAULT_PAGE_NUM = 1;
    @JsonIgnore
    public static final int DEFAULT_PAGE_SIZE = 10;

    private int currentPage;
    private int total;
    private int size;
    private String sortBy;
    private String sortOrder;
}
