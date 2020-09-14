/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaanimals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class ValidatorTest {
    
    public ValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    /**
     * Test of IsRuleValid method, of class Validator.
     */
    @Test
    public void testIsRuleValid() {
        System.out.println("IsRuleValid");
        Validator instance = new Validator();
        instance.AddCondition(0, '(', 1, -1, false, false, "Ожидается открывающая скобка '('");
        instance.AddCondition(1, (char)0x16, 2, 6, false, true, "");
        instance.AddCondition(2, (char)0x16, 2, 3, false, true, "");
        instance.AddCondition(3, ')', 0, 4, false, true, "");
        instance.AddCondition(4, '|', 1, 5, false, true, "");
        instance.AddCondition(5, ',', 1, -1, false, false, "Ожидается закрывающая скобка ')',или '|', или буква или цифра");
        instance.AddCondition(6, '^', 1, -1, false, false, "Ожидается буква или цифра или знак отрицания '^");
        
        String strRule = "(a,bc)";
        boolean result = instance.IsRuleValid(strRule);
        boolean expResult = true;
        assertEquals(expResult, result);
        
        strRule = "(дикое,низкое)";
        result = instance.IsRuleValid(strRule);
        expResult = true;
        assertEquals(expResult, result);
        
        strRule = "(высокое|среднее,легкое,травоядное)(дикое,низкое)";
        result = instance.IsRuleValid(strRule);
        expResult = true;
        assertEquals(expResult, result);

        strRule = "(высокое||среднее,легкое,травоядное)(дикое,низкое)";
        result = instance.IsRuleValid(strRule);
        expResult = false;
        assertEquals(expResult, result);

        strRule = "((дикое,низкое)";
        result = instance.IsRuleValid(strRule);
        expResult = false;
        assertEquals(expResult, result);

        strRule = "()дикое,низкое)";
        result = instance.IsRuleValid(strRule);
        expResult = false;
        assertEquals(expResult, result);

        strRule = "(дикое,низкое))";
        result = instance.IsRuleValid(strRule);
        expResult = false;
        assertEquals(expResult, result);

        strRule = "(дикое,низкое,)";
        result = instance.IsRuleValid(strRule);
        expResult = false;
        assertEquals(expResult, result);

        strRule = "(дикое1,низкое2)";
        result = instance.IsRuleValid(strRule);
        expResult = true;
        assertEquals(expResult, result);

        strRule = "(^высокое)";
        result = instance.IsRuleValid(strRule);
        expResult = true;
        assertEquals(expResult, result);

    }

    
}
