/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaanimals;

import java.io.*;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 *
 * @author Bkn
 */
public class JavaAnimals {
    public class cAnimal {
     ArrayList <String> propAni = new ArrayList <String> (); // динамический массив свойств одного животного
    } 
    /**
     *  динамический массив всех животных
     */
    ArrayList <cAnimal> cAnimals = new ArrayList <cAnimal> (); // динамический массив всех животных

    /**
     * записываем атрибуты животного из aAttr в динамический массив
     * @param aAttr - массив с атрибутами животного
     */
        void InsertIntoCollection (String [] aAttr){
            
              cAnimal curAni = new cAnimal();
              curAni.propAni.addAll(Arrays.asList(aAttr));
              // сохраняем экземпляр животного в коллекции всех животных
              cAnimals.add(curAni);
            
        }
    
    /**
     * читает исходный файл с атрибутами животных и сохраняет его в коллекции
     * @param sFile - исходный файл
     */
        void ReadAllAnimals (String sFile){
            String txtLine;

        System.out.println(sFile);
        File fIn = new File(sFile);
        try (BufferedReader inputVar = Files.newBufferedReader(fIn.toPath(), Charset.forName("Cp1251"));) 
        {txtLine = inputVar.readLine();
            while(txtLine != null){
              String [] aAttr = txtLine.split(","); // получаем массив атрибутов одного животного
              
              InsertIntoCollection (aAttr);         // записываем атрибуты животного в динамический массив
              txtLine = inputVar.readLine(); 
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Просматривает файл с животными и сразу ведет подсчет на основании файла с правилами
     * @param sFileAni - файл с животными
     * @param sFileRules - файл с правилами
     */
        String ReadAniFromStream (String sFileAni, String sFileRules){
            BufferedReader inputAnimals = null;
            BufferedReader inputRules = null;
            String sOut = "";
        try {
            String txtLineAni;
            String txtLineRule;
            
            File fInRules = new File(sFileRules);
            inputRules = Files.newBufferedReader(fInRules.toPath(), Charset.forName("Cp1251"));
            txtLineRule = inputRules.readLine();                            // читаем из потока Правила
            
            File fInAnmals = new File(sFileAni);
            inputAnimals = Files.newBufferedReader(fInAnmals.toPath(), Charset.forName("Cp1251"));
            while(txtLineRule != null){
                int cnt = 0;  
                String [] sAttrRule  = txtLineRule.split(",");   // получаем массив атрибутов необходимых для выборки животных;
                txtLineAni = inputAnimals.readLine();
                    while(txtLineAni != null){                              // читаем из потока животных
                        String [] sAttrAni  = txtLineAni.split(",");        // получаем массив атрибутов животных
                        boolean isExist = false;
                        for (String strLexema : sAttrRule){                 // перебираем все лексемы
                            isExist = ExecuteRule (strLexema,sAttrAni);
                            if (!isExist) break;                            // лексема не встретилась в атрибутах животного - прерываем цикл просмотра лексем
                        }
                        if (isExist) cnt++;                                 // все лексемы совпали - наращиваем счтчик
                    txtLineAni = inputAnimals.readLine();                   // читаем из потока животных
                    }
                    sOut = sOut +"Правило: " + txtLineRule + " Количество: " + String.valueOf(cnt)+ " ";
                txtLineRule = inputRules.readLine();                        // читаем из потока Правила
                inputAnimals.close();
                inputAnimals = Files.newBufferedReader(fInAnmals.toPath(), Charset.forName("Cp1251"));
            }
        } catch (IOException ex) {
            Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputAnimals.close();
                inputRules.close();
            } catch (IOException ex) {
                Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
          return sOut;
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
//           System.out.println("----------------");
           for (int j=0;j<cAni.propAni.size();j++){
               System.out.print(cAni.propAni.get(j)+" ");
           }
           System.out.println();
    }
    /**
     * подсчитывает количество животных в коллекции, удовлетворяющих правилу из aAttr
     * @param aAttr - правило в виде массива конъюнкций лексем
     * @return - количество животных, удовлетворяющих данному правилу
     */
        int Calculate (String [] aAttr) {
        int cnt=0; // счетчик животных с нужными атрибутами
            for (cAnimal selectedAnimal : cAnimals) {                   // перебор всех животных из коллекции
                boolean isExist=false;
                for (int i=0;i<aAttr.length;i++){
                    isExist = ExecuteRule (aAttr[i],selectedAnimal);   // исполнение лексемы над результирующим массивом
                    if (!isExist) {break;}                             // если лексема есть среди атрибутов - продолжаем цикл по лексемам
                    }
                if (isExist) {
                    cnt++;                                              // если все лексемы присутствуют в атрибутах животного - наращиваем счетчик
//                        Display (selectedAnimal);
                }                                
              }
            return cnt;
    }
    /**
     * читает файл с правилами и выполняет их по одному (одна строка - одно правило)
     * атрибуты с функцией "и" разделяются запятой
     * несколько атрибутов могут объединняться функцией "или" (|} 
     * для отрицания атрибута используется символ "^"
     */
    void ReadRules (String sFile){
            String txtLine;
        System.out.println(sFile);
        try {
            File fIn = new File(sFile);
            BufferedReader inputVar = Files.newBufferedReader(fIn.toPath(), Charset.forName("Cp1251")); 
            
            txtLine = inputVar.readLine();          // читаем очередную строку с правилом
            while(txtLine != null){
              System.out.println("Правило = " + txtLine); 
              String [] aAttr = txtLine.split(","); // получаем массив атрибутов необходимых для выборки животных
              
              int cnt; 
              cnt = Calculate (aAttr);              // подсчет животных с нужными атрибутами
              
              System.out.println ("Количество=" + cnt);
              txtLine = inputVar.readLine();        // читаем очередную строку с правилом 
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /**
     * Поиск лексемы в атрибутах конкретного животного
     * @param sRule - текущая лексема текущего правила
     * @param selectedAnimal - элемент коллекции (животное)
     * @return tue - если лексема встречается в атрибутах животного, 
     *         false - если лексема не встречается ни в одном атрибуте
     */
    boolean ExecuteRule (String sRule, cAnimal selectedAnimal){
        boolean isExist=false;
            for (String propAni : selectedAnimal.propAni) {
                if (sRule.indexOf("^")==0){ //если лексема отрицание
                    String str;
                    str = sRule.substring(1);
                    if (!str.contains(propAni)) isExist=true;
                }
                else if (sRule.contains(propAni)){
                    isExist=true;
                }
        }
        return isExist;
    }
    /**
     * Поиск лексемы в атрибутах конкретного животного
     * @param sRule - текущая лексема текущего правила
     * @param selectedAnimal - массив с атрибутами животного
     * @return  tue - если лексема встречается в атрибутах животного, 
     *          false - если лексема не встречается ни в одном атрибуте
     */
    boolean ExecuteRule (String sRule, String[] selectedAnimal){
        boolean isExist=false;
            for (String propAni : selectedAnimal) {
                if (sRule.indexOf("^")==0){ //если лексема отрицание
                    String str;
                    str = sRule.substring(1);
                    if (!str.contains(propAni)) isExist=true;
                }
                else if (sRule.contains(propAni)){
                    isExist=true;
                }
        }
        return isExist;
    }
    /**
     * @param args the command line arguments
     * args[0] - файл с атрибутами животных (одна строка - одно животное, атрибуты разделяются запятыми)
     * args[1] - файл с правилами (одна строка - одно правило, лексемы внутри строки разделяются запятыми)
     */
    public static void main(String[] args) {
       if(args.length==0) System.out.println ("Задайте имя входного файла и имя файла правила");
       else {
           JavaAnimals cAnimal = new JavaAnimals ();
            cAnimal.ReadAllAnimals (args[0]);   // чтение коллекции животных
            cAnimal.ReadRules (args[1]);        // чтение и исполнение правил
            
            System.out.println (cAnimal.ReadAniFromStream (args[0],args[1])); // чтение из потока животных без записи в коллекцию и подсчет правил "на лету"
       }
    }
    
}
