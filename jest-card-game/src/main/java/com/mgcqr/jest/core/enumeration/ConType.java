package com.mgcqr.jest.core.enumeration;

/**
 * Define type of Condition.
 * @author Yufei Wu
 *
 */
public enum ConType {
    plus,moin,joker,plus_sans_joker;

    /**
     * Convert input String to enum ConType
     * @param s String read from file
     * @return enum ConType
     */
    public static ConType convertString(String s) {
        switch(s) {
            case "plus": return ConType.plus;
            case "moin": return ConType.moin;
            case "joker": return ConType.joker;
            case "plus_sans_joker": return ConType.plus_sans_joker;
            case "null": return null;
            default: return null;
        }
    }

    //把文件中读到的字符串转换成枚举型
}
//四种奖杯颁奖条件 见附录3

