package javaanimals;

import java.util.ArrayList;

/**
 * Автомат проверки синтаксиса строки правила
 * @author Bkn
 */

public class Validator {
    
    private final ArrayList <State> programm = new ArrayList <> (); // программа автомата
    private int numCurState;                                        //номер текущего состояния автомата
    private String errorMsg;                                        // сообщение об ошибке
    private int curErrorPosition;                                   // позиция в строке, где произошла ошибка
    
    void Validator (){
        numCurState = 0;
    }
    
    int GetErrorPosition (){
        return curErrorPosition;
    }
    
    String GetErrorMessage (){
        return errorMsg;
    }
    /**
     * Добавляет в программу автомата инструкцию (состояние с условиями перехода)
     * @param condition - условие типа State
     */
    void AddCondition (State condition){
       programm.add(condition);
    }

    /**
     * Добавляет в программу автомата инструкцию (состояние с условиями перехода)
     * @param nState - номер сотояния
     * @param chWait - ожидаемый в этом состоянии символ (символ с кодом 0x16 означает любой алфавитно-цифровой)
     * @param nextIfEq - номер следующего состояния, если входной символ совпал с ожидаемым 
     * @param nextIfNotEq - номер следующего состояния, если входной символ НЕ совпал с ожидаемым ("-1" - конец работы - зафиксирована ОШИБКА)
     * @param sndToNextIfEQ - необходимость передать входной символ в следующее состояние, если символ совпал
     * @param sndToNextIfNotEQ- необходимость передать входной символ в следующее состояние, если символ НЕ совпал
     * @param ErrorMsg - сообщение об ошибке
     */
    void AddCondition (int nState,char chWait,int nextIfEq,int nextIfNotEq,boolean sndToNextIfEQ,boolean sndToNextIfNotEQ,String ErrorMsg){
       State condition = new State(nState,chWait,nextIfEq,nextIfNotEq,sndToNextIfEQ,sndToNextIfNotEQ,ErrorMsg);
       programm.add(condition);
    }

    
    /**
     * Проверяет строку на корректный синтаксис
     * @param strRule - исходная строка с правилом
     * @return true - если синтаксис верный
     */
    boolean IsRuleValid (String strRule) {
        boolean isValid=false;
        char [] chArray = strRule.toCharArray();
        curErrorPosition=0;
        for (char curCh : chArray) {
            if (!ToRealizeChange (curCh)) {
                isValid=false;
                break;
            }
            isValid=true;
            curErrorPosition++;
        }
        return isValid;
    }
    
    /**
     * Реализует переход автомата в новое состояние
     * @param inChar - входной символ
     * @return true - если нет ошибки
     */
    boolean ToRealizeChange (char inChar){
        boolean isValid;
        boolean isEQ;
        State curState = programm.get(numCurState);
            if (inChar == ' ') return true;
            if (curState.charWait == (char)0x16){ // если ожидается любой алфавитно-цифровой символ
                isEQ = Character.isAlphabetic(inChar) || Character.isDigit(inChar); 
            }
            else isEQ = curState.charWait==inChar;
        
            if (isEQ) {
                numCurState=curState.nextIfEQ;
                if (curState.sndToNextIfEQ) return ToRealizeChange (inChar);
                else                        isValid = true;
            }
            else {
                if (curState.nextIfNotEQ !=-1){
                    numCurState=curState.nextIfNotEQ;
                    if (curState.sndToNextIfNotEQ)  return ToRealizeChange (inChar);
                    else                            isValid = true;
                }
                else {
                    errorMsg = curState.GetErrorMsg();
                    numCurState = 0;
                    isValid=false;
                }
            }
        return isValid;
    }

}
