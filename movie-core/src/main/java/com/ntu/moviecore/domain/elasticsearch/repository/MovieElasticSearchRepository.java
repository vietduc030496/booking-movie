package com.ntu.moviecore.domain.elasticsearch.repository;

import com.ntu.moviecore.domain.elasticsearch.entity.MovieElasticSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieElasticSearchRepository extends ElasticsearchRepository<MovieElasticSearch, Long> {
}
