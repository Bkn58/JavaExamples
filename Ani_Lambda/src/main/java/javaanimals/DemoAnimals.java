package javaanimals;

public class DemoAnimals {
    /**
     * @param args the command line arguments
     * args[0] - файл с атрибутами животных (одна строка - одно животное, атрибуты разделяются запятыми)
     * args[1] - файл с правилами (одна строка - одно правило, лексемы внутри строки разделяются запятыми)
     */
    public static void main(String[] args) {
        if(args.length==0) System.out.println ("Задайте имя входного файла и имя файла с правилами");
        else {
            /* подсчет через коллекцию животных используя интерфейс из класса*/
            AnimalsCollection cAnimalColl = new AnimalsCollection ();
            System.out.println(cAnimalColl.ReadAndExecute(args[0],args[1]));

            /* стратегия подсчета напрямую в потоке чтения  используя интерфейс из класса*/
            AnimalsStream  cAmimallstr = new AnimalsStream();
            System.out.println(cAmimallstr.ReadAndExecute(args[0],args[1]));

            /* то же, но с использованием паттерна "стратегия" */
            ContextCalc context = new ContextCalc();

            /* стратегия подсчета через коллекцию животных с использованием паттерна "Стратегия"*/
            context.SetStrategy(new AnimalsCollection());
            System.out.println(context.ExecuteStrategy(args[0],args[1]));

            /* стратегия подсчета напрямую в потоке чтения с использованием паттерна "Стратегия"*/
            context.SetStrategy(new AnimalsStream ());
            System.out.println(context.ExecuteStrategy(args[0],args[1]));

        }
    }

}
