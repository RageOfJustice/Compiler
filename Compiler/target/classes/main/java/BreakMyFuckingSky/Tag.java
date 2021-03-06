package BreakMyFuckingSky;

/**
 * Created by Doom on 27.04.2016.
 */

/**
 * Класс содержит числовые эквиваленты ключевым словам, идентификаторам,
 * логическим выражениям, операторам и их сокращениям.
 * P.S.: думаю, он еще не завершен, но для начала хватит
 */
public final class Tag {
    public static final int
        IDF = 300, // идентификатор
    /* типы */
        INT = 301,
        DOUBLE = 302,
        STRING = 303,
        LOGIC = 304,
        VOID = 305,
    /* ************************ */

    /* некоторые значения */
        NULL = 350,
        TRUE = 351,
        FALSE = 352,
    /* ************************ */

    /* слова конструкций */
        IF = 400,
        ELSE =401,
        CYCLE = 402,
        SWITCH = 403,
        CASE = 404,
        BREAK = 405,
        RETURN = 406,
    /* ************************ */

    /* операторы сравнения */
        EQ = 451, // ==
        NE = 452, // !=
        LT = 453, // <
        LE = 454, // <=
        GT = 455, // >
        GE = 456, // >=
    /* ************************ */

    /* арифметические операторы и сокращенные операторы присваивания*/
        APPEND = 500, // =
        POW = 501, // **
        POW_SHORT = 502, // **=
        PLUS = 503, // +
        PLUS_SHORT = 504, // +=
        MINUS = 505, // -
        MINUS_SHORT = 506, // -=
        MULTI = 507, // *
        MULTI_SHORT = 508, // *=
        DIV = 510, // /
        DIV_SHORT = 511, // /=
        MOD = 512, // %
        MOD_SHORT = 513, // %=
        INCR = 514, // ++
        DECR = 515, // --
    /* ************************ */

    /* логические операторы */
        NOT = 550, // !
        AND = 551, // &&
        OR = 552; // ||
    /* ************************ */
}
