package javaanimals;

//import org.junit.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

//import static org.junit.Assert.*;

public class JavaAnimalsTest {

    @org.junit.Test
    public void isRuleValid() {
        JavaAnimals ex = new JavaAnimals();
        boolean actual = ex.IsRuleValid("");
        boolean expected = false;
        assertEquals(expected,actual);

        actual = ex.IsRuleValid("(высокое)");
        expected = true;
        assertEquals(expected,actual);

        actual = ex.IsRuleValid("(высокое|тяжелое)");
        expected = true;
        assertEquals(expected,actual);

        actual = ex.IsRuleValid("(высокое)(^легкое)");
        expected = true;
        assertEquals(expected,actual);

        actual = ex.IsRuleValid("(высокое,тяжелое)(^легкое)");
        expected = true;
        assertEquals(expected,actual);

        actual = ex.IsRuleValid("(высокое,тяжелое)(легкое,)");
        expected = false;
        assertEquals(expected,actual);
    }

    @org.junit.Test
    public void doNormalization() {
        JavaAnimals ex = new JavaAnimals();
        ArrayList result;

        ArrayList actual = ex.doNormalization("(высокое,тяжелое)(^легкое)");
        ArrayList expected = new ArrayList();
        expected.add("высокое,тяжелое");
        expected.add("^легкое");
        assertEquals(expected,actual);

        String  aAttr = "(травоядное|плотоядное,маленькое)(тяжелое, высокое)";
        ArrayList expResult = new ArrayList();
        expResult.add("травоядное|плотоядное,маленькое");
        expResult.add("тяжелое, высокое");

        result = ex.doNormalization (aAttr);
        assertEquals (expResult,result);

        String  aAttr1 = "(травоядное|плотоядное,маленькое)";
        ArrayList expResult1 = new ArrayList();
        expResult1.add("травоядное|плотоядное,маленькое");

        result = ex.doNormalization (aAttr1);
        assertEquals (expResult1,result);


    }

    @org.junit.Test
    public void executeRule() {
        boolean result;
        boolean expResult;
        String [] aAttr = {"курица", "травоядное", "маленькое", "легкое"};
        JavaAnimals instance = new JavaAnimals();

        String sRule = "маленькое";
        expResult = true;

        result = instance.ExecuteRule(sRule, aAttr);
        assertEquals(expResult, result);

        String sRule1 = "^маленькое";
        expResult = false;

        result = instance.ExecuteRule(sRule1, aAttr);
        assertEquals(expResult, result);

    }

    @org.junit.Test
    public void doSplit () {
        JavaAnimals instance = new JavaAnimals();
        String[] expSubRules = {"маленькое","невысокое"};

        String[] resSubRules = instance.doSplit("маленькое|невысокое");
        assertEquals(expSubRules,resSubRules);
    }
}