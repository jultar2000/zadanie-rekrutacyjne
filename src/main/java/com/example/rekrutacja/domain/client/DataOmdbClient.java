package com.example.rekrutacja.domain.client;

import com.example.rekrutacja.domain.dto.MovieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "data-ombd-client", url = "${omdb.api.data-url}")
public interface DataOmdbClient {

  @GetMapping("?plot=short&type=movie")
  MovieDto searchMovie(@RequestParam("t") String title, @RequestParam("apikey") String apiKey);

}
