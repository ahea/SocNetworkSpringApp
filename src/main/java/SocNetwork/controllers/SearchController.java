package SocNetwork.controllers;

import SocNetwork.models.other.SearchRequestParams;
import SocNetwork.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SearchController {

    private SearchService searchService;

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/api/search", method = RequestMethod.POST)
    public List searchByParams(@Param("offset") Integer offset,
                               @Param("count") Integer count,
                               @RequestBody SearchRequestParams searchRequestParams) {
        return searchService.searchUsersByParams(searchRequestParams, offset, count);
    }

}
