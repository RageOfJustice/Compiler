package BreakMyFuckingSky;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Doom on 27.04.2016.
 */
public class Lexi {

    /**
     *  Номер текущей линии
     */
    private int line = 1;

    /**
     *  Здесь вся программа посимвольно
     */
    private byte[] program;

    /**
     *  Текущий символ (индекс)
     */
    private int peek = 0;

    /**
     * Переменная используется для заполнения таблицы идентификаторов функций
     */
    private boolean isTableOpened = false;

    /**
     * Массив токенов, которые встретились в программе последовательно
     */
    public ArrayList<Token> tokens = new ArrayList<Token>();

    /**
     * Функция возвращает последний добавленный токен
     * @return последний добавленный токен
     */
    private Token getLastAddedToken(){
        return tokens.get(tokens.size()-1);
    }
    /**
     * Переменная указывает на используемю в данный момент таблицу идентификаторов
     */
    public Environ top = new Environ(null);

    /**
     * Таблица ключевых слов, которая должна быть заполнена перед началом компиляции
     * ключ: строковое значение ключевого слова
     * значение: объект класса KeyToken
     */
    public static final Hashtable<String,KeyToken> keyWords = new Hashtable<String, KeyToken>();

    /**
     * Функция для удобного заполнения таблицы ключевых слов
     * @param t токен ключевого слова
     */
    private void putKeyWord(KeyToken t){
        keyWords.put(t.getValue(),t);
    }
    /**
     * Функция обрабатывает символы, пропуская его через ДКА, и, образуя токен, возвращает его.
     * @return Следующий токен
     * @throws Exception
     */
    public Token getNextToken() throws Exception {
        // переменная состояния ДКА
        int state = 1;
        // переменная для строковых лексем
        String lexeme="";
        // переменная для численных лексем
        double value=0;
        // для обработки дробных значений
        int offs = 10;
        // цикл работает пока функция не вернет значение или не выдаст ошибку
        while (true) {
            char symb = (char) program[peek];
            switch (state) {
                case 1:
                    if (symb == ' ' || symb == '\t'|| symb == '\r') break;
                    else if (symb == '\n') line++;
                    else if (symb == '"' || symb == '\'') state = 2;
                    else if (symb == '+') state = 4;
                    else if (symb == '-') state = 5;
                    else if (symb == '/') state = 6;
                    else if (symb == '*') state = 7;
                    else if (symb == '%') state = 8;
                    else if (symb == '&') state = 9;
                    else if (symb == '|') state = 10;
                    else if (symb == '=') state = 11;
                    else if (symb == '>') state = 12;
                    else if (symb == '<') state = 13;
                    else if (symb == '!') state = 14;
                    else if (Character.isLetter(symb)){lexeme+=symb; state = 15;}
                    else if (Character.isDigit(symb)) {value =value* 10 + Character.digit(symb,10);state = 16;}
                    else if (symb == '('){
                        if(getLastAddedToken().getTag()==Tag.IDF){
                            isTableOpened = true;
                            Environ child = new Environ(top);
                            top.addChild(child);
                            top=child;

                        }
                        peek++;
                        return new Token((int)symb);
                    }
                    else if (symb == '{') {
                        if(!isTableOpened) {
                            Environ child = new Environ(top);
                            top.addChild(child);
                            top = child;
                        }
                        isTableOpened = false;
                        peek++;
                        return new Token((int) symb);
                    }
                    else if (symb == '}') {
                        peek++;
                        top=top.getParent();
                        return new Token((int)symb);
                    }
                    else {peek++;return new Token((byte) symb);}
                    break;
                case 2: if(symb == '"' || symb == '\'') state = 3;
                    else if(peek == program.length-1) throw new Exception("Lexi error at line: "+ line);
                    else lexeme+= symb;
                    break;
                case 3: return new StringToken(lexeme);
                case 4:
                    if (symb == '+') { peek++; return new Token(Tag.INCR);}
                    else if(symb == '=') { peek++; return new Token(Tag.PLUS_SHORT);}
                    else return new Token(Tag.PLUS);
                case 5:
                    if (symb == '-') { peek++; return new Token(Tag.DECR);}
                    else if(symb == '=') { peek++; return new Token(Tag.MINUS_SHORT);}
                    else return new Token(Tag.MINUS);
                case 6:
                    if(symb == '=') { peek++; return new Token(Tag.DIV_SHORT);}
                    else if(symb == '/') state = 19;
                    else if(symb == '*') state = 20;
                    else return new Token(Tag.DIV);
                    break;
                case 7:
                    if(symb == '=') { peek++; return new Token(Tag.MULTI_SHORT);}
                    else if(symb == '*') state = 17;
                    else return new Token(Tag.MULTI);
                    break;
                case 8:
                    if(symb == '=') { peek++; return new Token(Tag.MOD_SHORT);}
                    else return new Token(Tag.MOD);
                case 9:
                    if(symb == '&') {peek++; return new Token(Tag.AND);}
                    else throw new Exception("Lexi error at line: "+ line);
                case 10:
                    if(symb == '|') {peek++; return new Token(Tag.OR);}
                    else throw new Exception("Lexi error at line: "+ line);
                case 11:
                    if(symb == '=') {peek++; return new Token(Tag.EQ);}
                    else return new Token(Tag.APPEND);
                case 12:
                    if(symb == '=') {peek++; return new Token(Tag.GE);}
                    else return new Token(Tag.GT);
                case 13:
                    if(symb == '=') {peek++; return new Token(Tag.LE);}
                    else return new Token(Tag.LT);
                case 14:
                    if(symb == '=') {peek++; return new Token(Tag.NE);}
                    else return new Token(Tag.NOT);
                case 15:
                    if(Character.isLetterOrDigit(symb) || symb=='_') lexeme+=symb;
                    else{
                        /* проверки: на существование в таблице идентификаторов,
                        в таблице ключeвых слов, на то, что это обьявление переменной
                         */
                        if (keyWords.containsKey(lexeme)) return keyWords.get(lexeme);
                        IdToken idToken = new IdToken(lexeme);
                        if(!keyWords.containsKey(lexeme) && getLastAddedToken().getTag()>300
                                && getLastAddedToken().getTag()<305)
                            top.put(lexeme,idToken);
                        return idToken;
                    }
                    break;
                case 16:
                    if (Character.isDigit(symb)) value = value*10 + Character.digit(symb,10);
                    else if(symb == '.') state = 18;
                    else return new IntToken((int)value);
                    break;
                case 17:
                    if(symb == '=') {peek++; return new Token(Tag.POW_SHORT);}
                    else return new Token(Tag.POW);
                case 18:
                    if(Character.isDigit(symb)){
                        value+= (double) Character.digit(symb,10)/offs;
                        offs*=10;
                    }
                    else if(Character.isLetter(symb)) throw new Exception("Lexi error at line: "+ line);
                    else return new DoubleToken(value);
                    break;
                case 19:
                    if(symb == '\n') {
                        line++;
                        state = 1;
                    }
                    break;
                case 20:
                    if (symb == '*') state = 21;
                    break;
                case 21:
                    if (symb == '/') state = 1;
                    else state = 20;
            }
            peek++;
            if (peek>=program.length) return null;
        }
    }

    /**
     * Конструктор инициализирует поля: переводит исходный код программы в байтовый массив,
     * заполняет массив токенов и таблицы идентификаторов
     * @param file - файл с исходным кодом
     * @throws IOException
     */
    public Lexi(File file) throws Exception {

        // заполнение таблицы ключевых слов
        putKeyWord(new KeyToken(Tag.IF,"if"));
        putKeyWord(new KeyToken(Tag.ELSE,"else"));
        putKeyWord(new KeyToken(Tag.SWITCH,"switch"));
        putKeyWord(new KeyToken(Tag.BREAK,"break"));
        putKeyWord(new KeyToken(Tag.RETURN,"return"));
        putKeyWord(new KeyToken(Tag.TRUE,"true"));
        putKeyWord(new KeyToken(Tag.FALSE,"false"));
        putKeyWord(new KeyToken(Tag.NULL,"null"));
        putKeyWord(new KeyToken(Tag.INT,"int"));
        putKeyWord(new KeyToken(Tag.DOUBLE,"double"));
        putKeyWord(new KeyToken(Tag.STRING,"string"));
        putKeyWord(new KeyToken(Tag.BOOL,"bool"));
        putKeyWord(new KeyToken(Tag.VOID,"void"));
        putKeyWord(new KeyToken(Tag.CYCLE,"cycle"));
        putKeyWord(new KeyToken(Tag.CASE,"case"));


        InputStream stream = new BufferedInputStream(new FileInputStream(file));

        // инициализация поля program
        program = new byte[stream.available()];

        // считывание исходного кода из потока в массив
        stream.read(program,0,stream.available());

        // заполнения массива токенов, встреченных в программе
        while (peek<program.length)
            tokens.add(getNextToken());
    }

    public static void main(String args[]){
        Lexi lexi=null;
        try {
            // файл с программой
           lexi= new Lexi(new File("D:\\3 курс\\Compiler\\src\\main\\resources\\prog.txt"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
