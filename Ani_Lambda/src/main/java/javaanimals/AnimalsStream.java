package javaanimals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bkn
 * Реализует одну из стратегий подсчета количества животных - подсчет "на лету" из входного потока
 */
public class AnimalsStream extends JavaAnimals implements ReadAndExecute {
    /**
     * Реализация интерфейса паттерна "Стратегия"
     * Просматривает файл с животными и сразу "на лету" ведет подсчет на основании файла с правилами
     * @param sFileAni - файл с животными
     * @param sFileRules - файл с правилами
     * @return String - выходная строка результата
     */
    @Override
    public String ReadAndExecute(String sFileAni, String sFileRules){
        BufferedReader inputAnimals = null;
        BufferedReader inputRules = null;
        StringBuilder sOut = new StringBuilder();
        try {
            String txtLineAni;
            String txtLineRule;

            File fInRules = new File(sFileRules);
            inputRules = Files.newBufferedReader(fInRules.toPath(), Charset.forName("Cp1251"));
            txtLineRule = inputRules.readLine();                            // читаем из потока Правила

            File fInAnimals = new File(sFileAni);
            inputAnimals = Files.newBufferedReader(fInAnimals.toPath(), Charset.forName("Cp1251"));
            while(txtLineRule != null){
                if (checker.IsRuleValid(txtLineRule)) {
                    ArrayList ArrayRules = doNormalization (txtLineRule);

                    txtLineAni = inputAnimals.readLine();
                    int cnt = 0;
                    while (txtLineAni != null) {                                // читаем из потока животных
                        boolean isExist = false;
                        for (Object arrayRule : ArrayRules) {                 // выбираем нормализованное "подправило" без скобок
                            String sRule = (String) arrayRule;
                            String[] sAttrRule = sRule.split(",");           // получаем массив атрибутов "подправила", необходимых для выборки животных;
                            String[] sAttrAni = txtLineAni.split(",");       // получаем массив атрибутов животных
                            for (String strLexeme : sAttrRule) {                // перебираем все лексемы "подправила"
                                isExist = ExecuteRule(strLexeme, sAttrAni);
                                if (!isExist)
                                    break;                                      // лексема не встретилась в атрибутах животного - прерываем цикл просмотра лексем
                            }
                            if (isExist) {
                                cnt++;                                          // все лексемы "подправила" совпали - наращиваем счетчик
                                break;                                          // нет необходимости дальше смотреть "подправила"
                            }
                        }
                        txtLineAni = inputAnimals.readLine();                   // читаем из потока животных
                    }
                    sOut.append("Правило: ").append(txtLineRule).append(" Количество: ").append(cnt).append(" ");
                }
                txtLineRule = inputRules.readLine();                        // читаем из потока Правила
                inputAnimals.close();
                inputAnimals = Files.newBufferedReader(fInAnimals.toPath(), Charset.forName("Cp1251"));
            }
        } catch (IOException ex) {
            Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputAnimals != null) inputAnimals.close();
                if (inputRules != null)   inputRules.close();
            } catch (IOException ex) {
                Logger.getLogger(JavaAnimals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sOut.toString();
    }

}
