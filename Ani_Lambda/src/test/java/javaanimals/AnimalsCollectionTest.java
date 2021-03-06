package javaanimals;

//import org.junit.Test;

//import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
public class AnimalsCollectionTest {

    @org.junit.Test
    public void readAllAnimals() {
    }

    @org.junit.Test
    public void insertIntoCollection() {
    }

    @org.junit.Test
    public void readRules() {
    }

    @org.junit.Test
    public void calculate() {
        AnimalsCollection ex = new AnimalsCollection ();
        int result;
        int expResult;


        String [] aAttr = {"курица", "травоядное", "маленькое", "легкое"};
        ex.InsertIntoCollection(aAttr);
        String [] aAttr1 = {"слон","тяжелое","высокое","травоядное"};
        ex.InsertIntoCollection(aAttr1);
        String [] aAttr2 = {"коза","травоядное","невысокое","среднее"};
        ex.InsertIntoCollection(aAttr2);
        String [] aAttr3 = {"тигр","плотоядное","невысокое","тяжелое","злое"};
        ex.InsertIntoCollection(aAttr3);
        String [] aAttr4 = {"лиса","плотоядное","маленькое","среднее"};
        ex.InsertIntoCollection(aAttr4);
        String [] aAttr5 = {"свинья","всеядное","невысокое","среднее"};
        ex.InsertIntoCollection(aAttr5);
        String [] aAttr6 = {"еж","всеядное","маленькое","легкоe"};
        ex.InsertIntoCollection(aAttr6);
        String [] aAttr7 = {"корова","травоядное","невысокое","тяжелое","желтое"};
        ex.InsertIntoCollection(aAttr7);

        String sRule = "(травоядное|плотоядное,маленькое)";
        expResult = 2;
        result = ex.Calculate(sRule);
        assertEquals(expResult, result);

        String sRule1 = "(травоядное)";
        expResult = 4;
        result = ex.Calculate(sRule1);
        assertEquals(expResult, result);

        String sRule2 = "(всеядное,^высокое)";
        expResult = 2;
        result = ex.Calculate(sRule2);
        assertEquals(expResult, result);

        String sRule3 = "(злое)";
        expResult = 1;
        result = ex.Calculate(sRule3);
        assertEquals(expResult, result);

        String sRule4 = "(травоядное,желтое)";
        expResult = 1;
        result = ex.Calculate(sRule4);
        assertEquals(expResult, result);

        String sRule5 = "(травоядное|плотоядное,маленькое)(тяжелое, высокое)";
        expResult = 3;
        result = ex.Calculate(sRule5);
        assertEquals(expResult, result);

        String sRule6 = "(^высокое)(невысокое|маленькое)";
        expResult = 7;
        result = ex.Calculate(sRule6);
        assertEquals(expResult, result);

    }

    @org.junit.Test
    public void executeRule() {
        boolean result;
        boolean expResult;
        String [] aAttr = {"курица", "травоядное", "маленькое", "легкое"};
        AnimalsCollection instance = new AnimalsCollection();

        String sRule = "маленькое";
        instance.InsertIntoCollection(aAttr);
        expResult = true;
        result = instance.ExecuteRule(sRule, instance.cAnimals.get(0));
        assertEquals(expResult, result);

        result = instance.ExecuteRule(sRule, aAttr);
        assertEquals(expResult, result);

        instance.cAnimals.clear();
        sRule = "плотоядное";
        instance.InsertIntoCollection(aAttr);
        expResult = false;
        result = instance.ExecuteRule(sRule, instance.cAnimals.get(0));
        assertEquals(expResult, result);

        result = instance.ExecuteRule(sRule, aAttr);
        assertEquals(expResult, result);

        instance.cAnimals.clear();
        sRule = "травоядное|плотоядное";
        instance.InsertIntoCollection(aAttr);
        expResult = true;
        result = instance.ExecuteRule(sRule, instance.cAnimals.get(0));
        assertEquals(expResult, result);

        result = instance.ExecuteRule(sRule, aAttr);
        assertEquals(expResult, result);


        instance.cAnimals.clear();
        sRule = "^невысокое";
        instance.InsertIntoCollection(aAttr);
        expResult = true;
        result = instance.ExecuteRule(sRule, aAttr);
        assertEquals(expResult, result);

        result = instance.ExecuteRule(sRule, instance.cAnimals.get(0));
        assertEquals(expResult, result);
    }

    @org.junit.Test
    public void readAndExecute() {
    }
}