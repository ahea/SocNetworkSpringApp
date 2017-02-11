package SocNetwork.models.enums;

/**
 * Created by aleksei on 11.02.17.
 */
public enum Country {
    RU("Russian Federation"),
    UK("United Kingdom"),
    US("USA");

    private final String text;

    private Country(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
