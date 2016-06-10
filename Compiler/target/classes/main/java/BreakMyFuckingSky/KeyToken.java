package BreakMyFuckingSky;

/**
 * Created by Doom on 02.05.2016.
 */
public class KeyToken extends Token {
    public final String value;
    public KeyToken(int tag, String value){
        super(tag);
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
