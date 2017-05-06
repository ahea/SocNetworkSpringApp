package SocNetwork.controllers;

import SocNetwork.models.other.SearchRequestParams;
import SocNetwork.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by aleksei on 06.05.17.
 */

@RestController
public class SearchController {

    private SearchService searchService;

    @Autowired
    public void setSearchService(SearchService searchService){
        this.searchService = searchService;
    }

    @RequestMapping(value = "/api/search/offset={offset}&count={count}",
            method = RequestMethod.POST)
    public List searchByParams(@PathVariable int offset,
                               @PathVariable int count,
                               @RequestBody SearchRequestParams searchRequestParams){
        List list = searchService.searchUsersByParams(searchRequestParams, offset, count);
        return list;
    }

}
