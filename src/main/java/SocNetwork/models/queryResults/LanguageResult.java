package SocNetwork.models.queryResults;

import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;
import org.springframework.data.neo4j.annotation.QueryResult;


@QueryResult
public class LanguageResult {
    public LanguageName name;
    public LanguageLevel level;
}
