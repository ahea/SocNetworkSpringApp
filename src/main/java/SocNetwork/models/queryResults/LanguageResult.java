package SocNetwork.models.queryResults;

import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * Created by aleksei on 05.05.17.
 */

@QueryResult
public class LanguageResult {
    public LanguageName name;
    public LanguageLevel level;
}
