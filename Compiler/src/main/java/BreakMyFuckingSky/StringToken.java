package BreakMyFuckingSky;

/**
 * Created by Doom on 28.04.2016.
 */
public class StringToken extends Token {
    public final String value;
    public StringToken(String value){
        super(Tag.STRING);
        this.value=value;
    }
    public String getValue() {
        return value;
    }
}
