package com.ntu.adminservice.service.movie;

import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieElasticSearchAdminService {

    private final ElasticsearchOperations elasticsearchOperations;

}
