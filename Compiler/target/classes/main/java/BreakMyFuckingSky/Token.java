package BreakMyFuckingSky;

/**
 * Created by Doom on 27.04.2016.
 */
public class Token {
    // числовое представления токена
    private final int tag;

    public Token(int tag){ this.tag = tag;}

    public int getTag(){return tag;}

    /**
     * Позволяет посмотреть символ по его коду (ASCII)
     * @return символ в строке
     */
    public String getValue() {return String.valueOf((char)tag);}
}
