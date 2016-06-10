package BreakMyFuckingSky;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Doom on 02.05.2016.
 */

/**
 * Класс используется для реализации нескольких таблиц идентификаторов
 * ключ: лексема
 * значение: объект класса Token
 */
public class Environ extends Hashtable<String, Token> {

    /**
     * Таблица-родитель для текущей таблицы
     */
    private Environ parent;

    /**
     * Потомки для текущей таблицы
     */
    private ArrayList<Environ> children = new ArrayList<Environ>();

    public Environ(Environ parent){
        this.parent = parent;
    }

    public Environ getParent(){
        return parent;
    }

    public void addChild(Environ child){
        children.add(child);
    }

    /**
     * Позволяет получить список потомков данной области видимости
     * @return массив потомков
     */
    public ArrayList<Environ> getChildren(){return children;}

    /**
     * Позволяет узнать, был ли обьявлен идентификатор выше уровнями
     * @param idf Имя идентификатора
     * @return область видимости, если найдено, null - иначе
     */
    public Environ isUpper(String idf){
        Environ parent = getParent();
        while (parent!=null && !parent.containsKey(idf)) parent.getParent();
        return parent;
    }

}
