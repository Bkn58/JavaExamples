package javaanimals;

import java.util.ArrayList;

/**
 *
 * @author Bkn
 */
public class cAnimal {
     ArrayList <String> propAni;   // динамический массив свойств одного животного
    
     cAnimal (){
        propAni = new ArrayList <> (); 
     }
    /**
     * Поиск лексемы в атрибутах конкретного животного
     * @param sLexem - текущая лексема текущего правила
     * @return  tue - если лексема встречается в атрибутах животного,
     *          false - если лексема не встречается ни в одном атрибуте
     */
    boolean isRuleMatch(String sLexem){
        boolean isExist=false;
        String str = sLexem.trim();
        // перебираем все атрибуты животного и сравниваем ее с текущей лексемой
        for (String spropAni : this.propAni) {
            if (str.indexOf("^")==0) { //если лексема отрицание
                isExist = str.substring(1).contains(spropAni);
                if (isExist)
                    // содержит атрибут, которого не должно быть, дальше не смотрим
                    return !isExist;
                else
                    // атрибут не совпал - смотрим дальше
                    isExist = true;
            }
            else {
                isExist = str.contains(spropAni);
                if (isExist)
                    // содержит нужный атрибут, дальше не смотрим
                    return isExist;
            }
        }
        return isExist;
    }

}
