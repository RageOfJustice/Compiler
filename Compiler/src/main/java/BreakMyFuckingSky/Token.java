package BreakMyFuckingSky;

/**
 * Created by Doom on 27.04.2016.
 */
public class Token {
    private final int tag;
    public Token(int tag){ this.tag = tag;}
    public int getTag(){return tag;}
    public char getValue() {return (char)tag;}
}
