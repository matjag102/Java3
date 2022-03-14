import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.*;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {
        try{
            SystemDecyzyjny decSystem = new SystemDecyzyjny("australian");

            System.out.println(decSystem.wyswietlSymbole()+"\n");
            System.out.println(decSystem.wyswietlmaxmin()+"\n");
            System.out.println(decSystem.printodchylenie()+"\n");
            System.out.println(decSystem.printroznezlicz()+"\n");
            System.out.println(decSystem.printroznesets()+"\n");

        }


        catch(java.io.IOException e){
            System.out.println("Blad!");
        }
    }
}

class SystemDecyzyjny{


    String name;
    Map<String, Integer> symbole;
    Map<String, Map<String, Double>> minmaxv;
    Map<String, Integer> roznezlicz;
    Map<String, Set<String>> rozneset;
    Map<String, String> najczesciej;
    Map<String, Double> srednia;
    Map<String, Double> warian;
    Map<String, Map<String, Double>> odchylenie;



    File dataFile;
    Scanner data;
    File dataTypesFile;
    Scanner dataTypes;
    File zmienionyDataFile;
    Scanner zmienionyData;

    public String wyswietlSymbole(){
        
        String wyniki = "Symbole \n";
        
        
        for ( String key : symbole.keySet() ) {
            wyniki += key+"\t\t"+symbole.get(key)+"\n";
        }
        return wyniki;
    }

    public String wyswietlmaxmin(){
        
        String wyniki = "min max\n";
        
        
        for ( String key : minmaxv.keySet() ) {
            wyniki += key+"\t\t\t"+minmaxv.get(key).get("min")+"\t\t\t"+minmaxv.get(key).get("max")+"\n";
        }
        return wyniki;
    }

    public String printroznezlicz(){

        String wyniki = "rozne wartosci\n";


        for ( String key : roznezlicz.keySet() ) {
            wyniki += key+"\t\t\t"+roznezlicz.get(key)+"\n";
        }
        return wyniki;
    }

    public String printroznesets(){

        String wyniki = "rozne wartosci\n";


        for ( String key : rozneset.keySet() ) {
            wyniki += key+"\t\t\t"+rozneset.get(key)+"\n";
        }
        return wyniki;
    }

    public String printodchylenie(){

        String wyniki = "odchylenie\n";


        for ( String key : odchylenie.get("atrybuty").keySet() ) {
            wyniki += key+"\t\t\t"+odchylenie.get("atrybuty").get(key)+"\n";
        }
        wyniki += "odchylenie\n";


        for ( String key : odchylenie.get("symbole").keySet() ) {
            wyniki += key+"\t\t\t"+odchylenie.get("symbole").get(key)+"\n";
        }
        return wyniki;
    }

    private void zliczsymbole() throws FileNotFoundException {


        String[] line;
        String symbol;

        symbole = new HashMap<>();



        while(data.hasNextLine()){
            line = data.nextLine().split("\\s+");
            symbol = line[line.length-1];
            if(!symbole.containsKey(symbol)){
                symbole.put(symbol, 1);
            }
            else{
                symbole.put(symbol, symbole.get(symbol)+1);
            }
        }
        data = new Scanner(dataFile);
    }

    private void cminmax() throws FileNotFoundException {

        String[] dataLine;
        String[] dataTypeLine;
        int i;
        Double parsedAttr;

        minmaxv=new HashMap<>();



        while(data.hasNextLine()){
            dataLine = data.nextLine().split("\\s+");
            i=1;

            for(String value: dataLine){
                if(i >= dataLine.length)
                    break;
                dataTypeLine = dataTypes.nextLine().split("\\s+");

                if(dataTypeLine[1].equals("n")) {
                    parsedAttr = Double.parseDouble(value);
                    if(!minmaxv.containsKey("a"+i)){
                        minmaxv.put("a"+i, new HashMap<>());
                        minmaxv.get("a"+i).put("min", parsedAttr);
                        minmaxv.get("a"+i).put("max", parsedAttr);
                    }
                    else{
                        if(minmaxv.get("a"+i).get("min") > parsedAttr){
                            minmaxv.get("a"+i).put("min", parsedAttr);
                        }
                        else if(minmaxv.get("a"+i).get("max") < parsedAttr){
                            minmaxv.get("a"+i).put("max", parsedAttr);
                        }
                    }
                }
                i++;
            }
            dataTypes = new Scanner(dataTypesFile);
        }
        data = new Scanner(dataFile);
    }

    private void szukajwartosci() throws FileNotFoundException {

        String[] line;
        int i;

        //4a
        Map<String, Map<String, Integer>> valuesCount = new HashMap<>();
        najczesciej = new HashMap<>();

        roznezlicz = new HashMap<>();
        rozneset = new HashMap<>();



        while(data.hasNextLine()){
            line = data.nextLine().split("\\s+");
            i=1;

            for(String value: line){
                if (i >= line.length)
                    break;
                if(!rozneset.containsKey("a"+i)){
                    valuesCount.put("a"+i, new HashMap<>());
                    valuesCount.get("a"+i).put(value, 1);

                    roznezlicz.put("a"+i, 1);
                    rozneset.put("a"+i, new HashSet<>());
                    rozneset.get("a"+i).add(value);
                }
                else{
                    if(!rozneset.get("a"+i).contains(value)){
                        valuesCount.get("a"+i).put(value, 1);

                        roznezlicz.put("a"+i, roznezlicz.get("a"+i)+1);
                        rozneset.get("a"+i).add(value);
                    }
                    else{
                        valuesCount.get("a"+i).put(value, valuesCount.get("a"+i).get(value)+1);
                    }
                }

                i++;
            }


            //4a
            for(String attr: valuesCount.keySet()){

                String mostCommon = null;
                int mostCommonAmount = 0;


                for(String value: valuesCount.get(attr).keySet()){
                    if(valuesCount.get(attr).get(value) > mostCommonAmount){
                        mostCommon = value;
                        mostCommonAmount = valuesCount.get(attr).get(value);
                    }
                }
                najczesciej.put(attr, mostCommon);
            }
        }
        data = new Scanner(dataFile);
    }

    private void obliczstd() throws FileNotFoundException {

        int objNo = 0, i;
        Double parsedAttr;
        String[] dataLine;
        String[] dataTypeLine;
        Map<String, List<Double>> objatrybuty = new HashMap<>();
        srednia = new HashMap<>();
        Map<String, List<Double>> symbolatrybuty = new HashMap<>();
        Map<String, Double>sredniaSymbol = new HashMap<>();
        warian = new HashMap<>();
        Map<String, Double> warianSymbol = new HashMap<>();

        

        while(data.hasNextLine()){
            objNo++;
            dataLine = data.nextLine().split("\\s+");
            i=1;

            for(String value: dataLine){
                if(i >= dataLine.length)
                    break;
                dataTypeLine = dataTypes.nextLine().split("\\s+");

                if(dataTypeLine[1].equals("n")) {
                    parsedAttr = Double.parseDouble(value);
                    if(!objatrybuty.containsKey("a"+i)){
                        objatrybuty.put("a"+i, new ArrayList<>());
                    }
                    if(!symbolatrybuty.containsKey(dataLine[dataLine.length-1])){
                        symbolatrybuty.put(dataLine[dataLine.length-1], new ArrayList<>());
                    }
                    objatrybuty.get("a"+i).add(parsedAttr);
                    symbolatrybuty.get(dataLine[dataLine.length-1]).add(parsedAttr);
                }

                i++;
            }

            dataTypes = new Scanner(dataTypesFile);
        }

        objatrybuty.forEach((k, v)-> srednia.put(k, 0.0));
        for(String key: objatrybuty.keySet()){
            for(Double value: objatrybuty.get(key)){
                srednia.put(key, srednia.get(key)+value);
            }
        }

        symbolatrybuty.forEach((k, v)->sredniaSymbol.put(k, 0.0));
        for(String key: symbolatrybuty.keySet()){
            for(Double value: symbolatrybuty.get(key)){
               sredniaSymbol.put(key,sredniaSymbol.get(key)+value);
            }
        }

        int finalObjNo = objNo;
        srednia.forEach((k, v) -> srednia.put(k, v/finalObjNo));
       sredniaSymbol.forEach((k, v) ->sredniaSymbol.put(k, v/symbolatrybuty.get(k).size()));

        for(String key: srednia.keySet()){
            for(Double value: objatrybuty.get(key)){
                if(warian.containsKey(key))
                    warian.put(key, warian.get(key)+Math.pow(value-srednia.get(key), 2));
                else
                    warian.put(key, Math.pow(value-srednia.get(key), 2));
            }
        }

        for(String key:sredniaSymbol.keySet()){
            for(Double value: symbolatrybuty.get(key)){
                if(warianSymbol.containsKey(key))
                    warianSymbol.put(key, warianSymbol.get(key)+Math.pow(value-sredniaSymbol.get(key), 2));
                else
                    warianSymbol.put(key, Math.pow(value-sredniaSymbol.get(key), 2));
            }
        }

        warian.forEach((k, v) -> warian.put(k, v/finalObjNo));
        warianSymbol.forEach((k, v) -> warianSymbol.put(k, v/symbolatrybuty.get(k).size()));

        odchylenie = new HashMap<>();
        Map<String, Double> odchylenieAttr = new HashMap<>();
        Map<String, Double> odchylenieSymbol = new HashMap<>();

        warian.forEach((k, v) -> odchylenieAttr.put(k, Math.sqrt(v)));
        warianSymbol.forEach((k, v) -> odchylenieSymbol.put(k, Math.sqrt(v)));

        odchylenie.put("atrybuty", odchylenieAttr);
        odchylenie.put("symbole", odchylenieSymbol);

        data = new Scanner(dataFile);
    }
    

    private void stworzbrakujacewartosci() throws java.io.IOException {
        
        String[] dataLine;
        String newContent = "";
        boolean canReplace = true;
        Integer objCount = 0;
        
        
        for(String key: symbole.keySet()){
            objCount += symbole.get(key);
        }

        Integer attrCount = roznezlicz.size();

        Integer brakujacezlicz = (int)((objCount*attrCount)*0.1);
        

        while(canReplace){
            while(data.hasNextLine()){
                dataLine = data.nextLine().split("\\s+");

                for(int i = 0; i < dataLine.length-1; i++) {
                    if (canReplace && !dataLine[i].equals("?") && Math.floor(Math.random() * 100) < 10) {
                        dataLine[i] = "?";
                        brakujacezlicz--;
                        if(brakujacezlicz <= 0) canReplace = false;
                    }
                    newContent += dataLine[i] + " ";
                }
                newContent += dataLine[dataLine.length-1] + "\n";
            }

            data = new Scanner(dataFile);
        }

        zmienionyDataFile = new File("dane\\"+name+"-zmieniony.txt");
        Files.deleteIfExists(zmienionyDataFile.toPath());
        zmienionyDataFile.createNewFile();

        FileWriter writer = new FileWriter("dane\\"+name+"-zmieniony.txt");
        writer.write(newContent);
        writer.close();

        zmienionyData = new Scanner(zmienionyDataFile);
    }

    private void missingv() throws java.io.IOException{

        String[] dataLine;
        String[] attrType;
        String newContent = "";



        while(zmienionyData.hasNextLine()){
            dataLine = zmienionyData.nextLine().split("\\s+");

            for(int i = 0; i < dataLine.length-1; i++) {
                attrType = dataTypes.nextLine().split("\\s+");
                if (dataLine[i].equals("?")) {
                    if (attrType[1].equals("n")){
                        dataLine[i] = String.format("%.2f", srednia.get("a"+(i+1)));
                    }
                    else if (attrType[1].equals("s")){
                        dataLine[i] = najczesciej.get("a"+(i+1));
                    }
                }
                newContent += dataLine[i] + " ";
            }
            newContent += dataLine[dataLine.length-1] + "\n";
            dataTypes = new Scanner(dataTypesFile);
        }

        FileWriter writer = new FileWriter("dane\\"+name+"-zmieniony.txt", false);
        writer.write(newContent);
        writer.close();
    }

    private void normalizuj(int a, int b) throws java.io.IOException {

        double an, bn, cn, dn;
        String[] dataLine;
        String[] attrType;
        String newContent = "";


        while(data.hasNextLine()){
            dataLine = data.nextLine().split("\\s+");

            for(int i = 0; i < dataLine.length-1; i++) {
                attrType = dataTypes.nextLine().split("\\s+");
                if (attrType[1].equals("n")){
                    an = Double.valueOf(dataLine[i]) - minmaxv.get("a"+(i + 1)).get("min");
                    bn = an * (b - a);
                    cn = minmaxv.get("a"+(i + 1)).get("max") - minmaxv.get("a"+(i + 1)).get("min");
                    dn = (bn / cn) + a;

                    dataLine[i] = String.format("%.2f", dn);
                }
                newContent += dataLine[i] + " ";
            }
            newContent += dataLine[dataLine.length-1] + "\n";
            dataTypes = new Scanner(dataTypesFile);
        }
        data = new Scanner(dataFile);

        File normalizujdDataFile = new File("dane\\"+name+"-normalizujd-"+a+"-"+b+".txt");
        Files.deleteIfExists(normalizujdDataFile.toPath());
        normalizujdDataFile.createNewFile();

        FileWriter writer = new FileWriter("dane\\"+name+"-normalizujd-"+a+"-"+b+".txt");
        writer.write(newContent);
        writer.close();
    }

    private void standar() throws java.io.IOException {

        double an, bn;
        String[] dataLine;
        String[] attrType;
        String newContent = "";


        while(data.hasNextLine()){
            dataLine = data.nextLine().split("\\s+");

            for(int i = 0; i < dataLine.length-1; i++) {
                attrType = dataTypes.nextLine().split("\\s+");
                if (attrType[1].equals("n")){
                    an = Double.valueOf(dataLine[i]) - srednia.get("a"+(i + 1));
                    bn = an/warian.get("a"+(i+1));

                    dataLine[i] = String.format("%.2f", bn);
                }
                newContent += dataLine[i] + " ";
            }
            newContent += dataLine[dataLine.length-1] + "\n";
            dataTypes = new Scanner(dataTypesFile);
        }
        data = new Scanner(dataFile);

        File normalizujdDataFile = new File("dane\\"+name+"-standard"+".txt");
        Files.deleteIfExists(normalizujdDataFile.toPath());
        normalizujdDataFile.createNewFile();

        FileWriter writer = new FileWriter("dane\\"+name+"-standard"+".txt");
        writer.write(newContent);
        writer.close();
    }

    SystemDecyzyjny(String name) throws java.io.IOException{
        
        this.name = name;
        dataFile = new File("dane\\"+name+".txt");
        data = new Scanner(dataFile);
        dataTypesFile = new File("dane\\"+name+"-type.txt");
        dataTypes = new Scanner(dataTypesFile);

        // Zadanie 3
        zliczsymbole();
        cminmax();
        szukajwartosci();
        obliczstd();

        // Zadanie 4a
        stworzbrakujacewartosci();
        missingv();

        // Zadanie 4b
        normalizuj(-1, 1);
        normalizuj(0, 1);
        normalizuj(-10, 10);

        // Zadanie 4c
        standar();
    }
}