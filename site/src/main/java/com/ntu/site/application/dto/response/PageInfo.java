package com.ntu.site.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageInfo {
    private int currentPage;
    private int total;
    private int size;
    private String sortBy;
    private String sortOrder;
}
