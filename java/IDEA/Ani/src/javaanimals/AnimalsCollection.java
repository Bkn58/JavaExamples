package javaanimals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bkn
 * Реализует одну из стратегий подсчета количества животных - подсчет животных из коллекции
 */

public class AnimalsCollection extends JavaAnimals implements ReadAndExecute{

    ArrayList<cAnimal> cAnimals;           // динамический массив всех животных

    AnimalsCollection (){
        cAnimals = new ArrayList <> ();
    }

    /**
     * читает исходный файл с атрибутами животных и сохраняет его в коллекции cAnimals
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
     * читает файл с правилами и выполняет их по одному (одна строка - одно правило)
     * атрибуты с функцией "и" разделяются запятой
     * несколько атрибутов могут объединяться функцией "или" (|}
     * для отрицания атрибута используется символ "^"
     */
    public String ReadRules (BufferedReader inputVar){
        String txtLine;
        String sOut = "";
        try {

            txtLine = inputVar.readLine();             // читаем очередную строку с правилом
            while(txtLine != null){
//                System.out.println("Правило = " + txtLine);
                sOut = sOut + "Правило: " + txtLine;

                if (checker.IsRuleValid(txtLine)) {

                    int cnt;
                    cnt = Calculate (txtLine);              // подсчет животных с нужными атрибутами

//                    System.out.println ("Количество=" + cnt);
                    sOut = sOut + " Количество: " + cnt + " ";
                }
                else System.out.println("Синтаксическая ОШИБКА правила в позиции: " + checker.GetErrorPosition()+ " "+ checker.GetErrorMessage());
                txtLine = inputVar.readLine();          // читаем очередную строку с правилом
            }
        }
        catch (IOException ex) {
            Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sOut;
    }
    /**
     * подсчитывает количество животных в коллекции, удовлетворяющих правилу из aAttr
     * @param txtRules - нормализованное правило, состоящее из лексем вида:
     * (лексема1,лексема2,лексема3|лексема4)|(лексема5,^лексема6).....
     * лексемы, перечисленные через запятую внутри скобок, являются конъюнкцией
     * знак "^" перед лексемой - отрицание (этого атрибута не должно быть у животного)
     * лексемы внутри скобок - "подправило", скобки между собой являются дизъюнкцией
     * @return - cnt количество животных, удовлетворяющих входному правилу
     */
    int Calculate (String txtRules) {
        int cnt=0;
        ArrayList ArrayRules = doNormalization (txtRules);
        for (cAnimal selectedAnimal : cAnimals) {                       // перебираем всех животных из коллекции
            boolean isAttrExist=false;
            for (int i=0;i<ArrayRules.size();i++) {                     // выбираем нормализованное "подправило" без скобок
                String sRule = (String)ArrayRules.get(i);
                String [] aAttr = sRule.split(",");                   // получаем из "подправила" массив лексем, необходимых для выборки животных
                for (String sAttr : aAttr) {
                    isAttrExist = ExecuteRule(sAttr, selectedAnimal);   // сравнение очередной лексемы с атрибутами животного
                    if (!isAttrExist) break;                            // если лексемы нет среди атрибутов - прекращаем просмотр других лексем этого "подправила"
                }
                if (isAttrExist) {
                    cnt++;
                    break;                                              // совпали все лексемы хотя бы из одного подправила
                }
            }
        }
        return cnt;
    }
    /**
     * Поиск лексемы в атрибутах конкретного животного
     * @param sRule - текущая лексема текущего правила
     * @param selectedAnimal - элемент коллекции (животное)
     * @return tue - если лексема встречается в атрибутах животного,
     *         false - если лексема не встречается ни в одном атрибуте
     */
    boolean ExecuteRule (String sRule, cAnimal selectedAnimal){
        boolean isExist;

        isExist = ExecuteRule (sRule,selectedAnimal.propAni.toArray(new String [0]));

        return  isExist;
    }

    /**
     * Реализация интерфейса паттерна "Стратегия"
     * @param sFileAni - имя файла с атрибутами животных
     * @param sFileRules - имя файла с правилами
     * @return String - выходная строка результата
     */
    @Override
    public String ReadAndExecute(String sFileAni, String sFileRules){
        String sOut = "";
        File fIn = new File(sFileRules);
        try (BufferedReader inputVar = Files.newBufferedReader(fIn.toPath(), Charset.forName("Cp1251"))) {
            try {
                ReadAllAnimals(sFileAni);
                sOut = ReadRules (inputVar);
            } finally {
                inputVar.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sOut;
    }
}
