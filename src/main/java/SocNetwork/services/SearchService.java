package SocNetwork.services;

import SocNetwork.models.nodeEntities.User;
import SocNetwork.models.other.SearchRequestParams;

import java.util.List;


public interface SearchService {

    List<User> searchUsersByParams(SearchRequestParams params, Integer offset, Integer count);

}
