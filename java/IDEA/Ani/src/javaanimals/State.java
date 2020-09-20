package javaanimals;

/**
 *
 * @author Bkn
 */

/**
 * состояние автомата
 */
public class State {

    protected int numState;                   // номер состояния (0,1,......)
    protected char charWait;                  // ожидаемый символ
    protected int nextIfEQ;                   // номер следующего состояние, если ожидаемый символ совпал с входным
    protected int nextIfNotEQ;                // номер следующего состояние, если ожидаемый символ не совпал с входным (-1 - ошибка!)
    protected boolean sndToNextIfEQ;          // необходимость передачи входного символа следующему состоянию если символ совпал
    protected boolean sndToNextIfNotEQ;       // необходимость передачи входного символа следующему состоянию если символ НЕ совпал
    protected String ErrorMsg;                // текст сообщения об ошибке
    
    public State (int nState,char chWait,int nextIfEq,int nextIfNotEq,boolean sndToNextIfEQ,boolean sndToNextIfNotEQ,String ErrorMsg) {
        
        this.numState = nState;
        this.charWait = chWait;
        this.nextIfEQ = nextIfEq;
        this.nextIfNotEQ = nextIfNotEq; 
        this.sndToNextIfEQ = sndToNextIfEQ;
        this.sndToNextIfNotEQ = sndToNextIfNotEQ;
        this.ErrorMsg = ErrorMsg;
    }
    
    public int ChangeState (char inChar) {
        int nextState;
        
        if (inChar==charWait) nextState = this.nextIfEQ;
        else                  nextState = this.nextIfNotEQ;
        
        return nextState;
    }
    
    public boolean NeedToSndIfEQ (){
    
        return this.sndToNextIfEQ;
    }
    public boolean NeedToSndIfNotEQ (){
    
        return this.sndToNextIfNotEQ;
    }
    public String GetErrorMsg (){
        
        return this.ErrorMsg; 
    }

}
