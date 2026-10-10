package com.ntu.persistence.search;

import com.ntu.persistence.search.MovieSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieElasticSearchRepository extends ElasticsearchRepository<MovieElasticSearch, Long> {
}
