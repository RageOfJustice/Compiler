package BreakMyFuckingSky;

/**
 * Created by Doom on 28.04.2016.
 */
public class DoubleToken extends Token {
    public final double value;
    public DoubleToken(double value){
        super(Tag.DOUBLE);
        this.value=value;
    }
    public String getValue() {
        return String.valueOf(value);
    }
}
