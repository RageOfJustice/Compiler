package BreakMyFuckingSky;

/**
 * Created by Doom on 28.04.2016.
 */
public class IntToken extends Token {
    public final int value;
    public IntToken(int value){
        super(Tag.INT);
        this.value=value;
    }
    public int getValue() {
        return value;
    }
}
