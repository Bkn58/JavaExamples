package javaanimals;

import java.io.*;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Базовый Класс для классов конкретных Стратегий
 * @author Bkn
 */
public class JavaAnimals {
    
    Validator checker = new Validator();    // автомат проверки синтаксиса строк правила

    JavaAnimals (){    }

    /**
     * Проверяет строку на корректный синтаксис
     * @param strRule - исходная строка с правилом
     * @return true - если синтаксис верный
     */
    public boolean IsRuleValid (String strRule) {

        return checker.IsRuleValid(strRule);
        
    }

    /**
     * Нормализует входную строку.
     * ищет парные скобки и представляет результирующую последовательность
     * в виде массива строк "подправил" без скобок.
     * @param inRule - входная строка
     * @return outSubRules - массив выходных строк "подправил"
     */
    
    ArrayList <String>  doNormalization (String inRule) {
        ArrayList <String> outSubRules = new ArrayList <String> ();
        
                String strPattern = "[(].+?[)]";
                boolean hasBrackets = inRule.matches(strPattern);
                if (hasBrackets){
                    // ищем парные скобки
                    Pattern patBrackets = Pattern.compile(strPattern); 
                    Matcher matBrackets = patBrackets.matcher(inRule);
                    while (matBrackets.find()) {
                        int start = matBrackets.start()+1;
                        int end   = matBrackets.end()-1;
                        if (start!=end) outSubRules.add(inRule.substring(start, end));// записываем содержимое парных скобок в результат
                     }
                }
                else {
                    // парных скобок не обнаружено
                    outSubRules.add(inRule);
                }

        
        return outSubRules;
    }

    /**
     * Разбиение лексемы, имеющей символ "или" - "|", на атомарные лексемы
     * @param inRule
     * @return outSubRules - массив атомарных лексем
     */
    String[] doSplit (String inRule) {
        String [] outSubRules;

        outSubRules = inRule.split("\\|");


        return outSubRules;
    }

    /**
     * Поиск лексемы в атрибутах конкретного животного
     * @param sLexem - текущая лексема текущего правила
     * @param selectedAnimal - массив с атрибутами животного
     * @return  tue - если лексема встречается в атрибутах животного,
     *          false - если лексема не встречается ни в одном атрибуте
     */
    boolean ExecuteRule (String sLexem, String[] selectedAnimal){
        boolean isExist=false;
        String str = sLexem.trim();
        // перебираем все атрибуты животного и сравниваем ее с текущей лексемой
            for (String propAni : selectedAnimal) {
                if (str.indexOf("^")==0) { //если лексема отрицание
                    isExist = str.substring(1).contains(propAni);
                    if (isExist)
                        // содержит атрибут, которого не должно быть, дальше не смотрим
                        return !isExist;
                    else
                        // атрибут не совпал - смотрим дальше
                        isExist = true;
                }
                else {
                    isExist = str.contains(propAni);
                    if (isExist)
                        // содержит нужный атрибут, дальше не смотрим
                        return isExist;
                }
            }
            return isExist;
    }

   /**
     * выводит всю коллекцию на экран
     */
   void DisplayAll (ArrayList <cAnimal> cAni){
        for (int i =0;i<cAni.size();i++) {
           cAnimal sAni = cAni.get(i);
           Display (sAni);
        }
   }
   void Display (cAnimal cAni){
       for (int j=0;j<cAni.propAni.size();j++){
               System.out.print(cAni.propAni.get(j)+" ");
       }
       System.out.println();
    }

}
