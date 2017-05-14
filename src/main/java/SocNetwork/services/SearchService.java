package SocNetwork.services;

import SocNetwork.models.nodeEntities.User;
import SocNetwork.models.other.SearchRequestParams;

import java.util.List;

/**
 * Created by aleksei on 06.05.17.
 */
public interface SearchService {

    List<User> searchUsersByParams(SearchRequestParams params, Integer offset, Integer count);

}
