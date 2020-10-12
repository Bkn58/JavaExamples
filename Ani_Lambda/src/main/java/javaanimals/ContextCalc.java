package javaanimals;

/**
 * Контекст для паттерна "Стратегия"
 */
public class ContextCalc {
    private ReadAndExecute strategy;

    void SetStrategy (ReadAndExecute strategy) {
        this.strategy = strategy;
    }

    String ExecuteStrategy (String sFileAni,String cFileRules) {
        return strategy.ReadAndExecute(sFileAni,cFileRules);
    }
}
