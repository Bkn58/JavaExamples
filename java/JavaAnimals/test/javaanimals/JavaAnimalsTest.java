/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaanimals;

import java.util.ArrayList;
import java.util.Arrays;
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
public class JavaAnimalsTest {
    
    public JavaAnimalsTest() {
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
     * Test of ReadAllAnimals method, of class JavaAnimals.
     */
    @Test
    public void testReadAllAnimals() {
        System.out.println("ReadAllAnimals");
        String sFile = "src\\javaanimals\\animals.txt";
        JavaAnimals instance = new JavaAnimals();
        instance.ReadAllAnimals(sFile);
        // TODO review the generated test code and remove the default call to fail.
    //    fail("The test case is a prototype.");
    }

    @Test
    public void testReadRules() {
        System.out.println("ReadRules");
        String sFile1 = "src\\javaanimals\\animals.txt";
        String sFile = "src\\javaanimals\\rules.txt";
        JavaAnimals instance = new JavaAnimals();
        instance.ReadAllAnimals(sFile1);
        instance.ReadRules(sFile);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testCalculate () {
        System.out.println("Calculate");
        JavaAnimals instance = new JavaAnimals();
        int result;
        int expResult;
             
        String [] sRule = {"травоядное|плотоядное","маленькое"};
              // записываем атрибуты животных в динамический массив
        String [] aAttr = {"курица", "травоядное", "маленькое", "легкое"}; 
        instance.InsertIntoCollection(aAttr);
        String [] aAttr1 = {"слон","тяжелое","высокое","травоядное"}; 
        instance.InsertIntoCollection(aAttr1);
        String [] aAttr2 = {"коза","травоядное","невысокое","среднее"}; 
        instance.InsertIntoCollection(aAttr2);
        String [] aAttr3 = {"тигр","плотоядное","невысокое","тяжелое","злое"}; 
        instance.InsertIntoCollection(aAttr3);
        String [] aAttr4 = {"лиса","плотоядное","маленькое","среднее"}; 
        instance.InsertIntoCollection(aAttr4);
        String [] aAttr5 = {"свинья","всеядное","невысокое","среднее"}; 
        instance.InsertIntoCollection(aAttr5);
        String [] aAttr6 = {"еж","всеядное","маленькое","легкоe"}; 
        instance.InsertIntoCollection(aAttr6);
        String [] aAttr7 = {"корова","травоядное","невысокое","тяжелое","желтое"}; 
        instance.InsertIntoCollection(aAttr7);
        
        instance.DisplayAll(instance.cAnimals);
        
        expResult = 2;
        result = instance.Calculate(sRule);
        assertEquals(expResult, result);

        String [] sRule1 = {"травоядное"};
        expResult = 4;
        result = instance.Calculate(sRule1);
        assertEquals(expResult, result);

        String [] sRule2 = {"всеядное","^высокое"};
        expResult = 2;
        result = instance.Calculate(sRule2);
        assertEquals(expResult, result);
        
    }
    /**
     * Test of ExecuteRule method, of class JavaAnimals.
     */
    @Test
    public void testExecuteRule() {
        System.out.println("ExecuteRule");
        boolean result=true;
        boolean expResult;
        String [] aAttr = {"курица", "травоядное", "маленькое", "легкое"}; 
        JavaAnimals instance = new JavaAnimals();
             
        String sRule = "маленькое";
        instance.InsertIntoCollection(aAttr);
        instance.DisplayAll(instance.cAnimals);
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
        sRule = "^высокое";
        instance.InsertIntoCollection(aAttr);
        expResult = true;
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

    }
    @Test
    public void ReadAniFromStream() {
        System.out.println("ReadAniFromStream");
        boolean result=true;
        boolean expResult;
        JavaAnimals instance = new JavaAnimals();
        String sEtalon = "Правило: травоядное|плотоядное,маленькое Количество: 2 Правило: травоядное Количество: 4 Правило: всеядное,^высокое Количество: 2 Правило: злое Количество: 1 Правило: травоядное,желтое Количество: 1";
        String sResult = instance.ReadAniFromStream("src\\javaanimals\\animals.txt","src\\javaanimals\\rules.txt");
        System.out.println (sResult);
        assertEquals (sEtalon,sResult.trim());
    }

    /**
     * Test of main method, of class JavaAnimals.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = {"src\\javaanimals\\animals.txt","src\\javaanimals\\rules.txt"};
        JavaAnimals.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
