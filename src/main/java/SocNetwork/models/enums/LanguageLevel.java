package SocNetwork.models.enums;

/**
 * Created by aleksei on 11.02.17.
 */
public enum LanguageLevel {
    A1("Beginner"),
    A2("Elementary"),
    B1("Intermediate"),
    B2("Upper intermediate"),
    C1("Advanced"),
    C2("Mastery");

    private final String text;

    private LanguageLevel(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
