package com.dominys.proxydemo.application;

import com.dominys.proxydemo.businesslogic.MyService;
import com.dominys.proxydemo.omdb.api.dto.OmdbApiSearchResponse;
import com.dominys.proxydemo.omdb.api.dto.Role;
import com.dominys.proxydemo.omdb.api.dto.SimpleMovie;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final MyService myService;

    @GetMapping
    public OmdbApiSearchResponse<List<SimpleMovie>> searchMovie(@RequestParam Role role, @RequestParam String title, @RequestParam int page) {
        return myService.searchMovie(role, title, page);
    }

}
