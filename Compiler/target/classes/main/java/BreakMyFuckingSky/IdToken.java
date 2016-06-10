package BreakMyFuckingSky;

/**
 * Created by Doom on 28.04.2016.
 */
public class IdToken extends Token {
    public final String value;
    public IdToken(String value){
        super(Tag.IDF);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
