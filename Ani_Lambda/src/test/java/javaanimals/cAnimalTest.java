package javaanimals;

import org.junit.Test;

import static org.junit.Assert.*;

public class cAnimalTest {

    @Test
    public void executeRule() {
        cAnimal ex = new cAnimal();
        ex.propAni.add("курица");
        ex.propAni.add("травоядное");
        ex.propAni.add("легкое");
        ex.propAni.add("маленькое");
        boolean result = ex.isRuleMatch("травоядное|плотоядное,маленькое");
        boolean expResult = true;
        assertEquals(expResult, result);
    }
}