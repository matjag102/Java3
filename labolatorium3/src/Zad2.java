import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Zad2 {

    public static void main(String[] args) {
        System.out.println("Zad2");
        String decsyspath = "D:\\Sztucznaint\\labolatorium3\\class_materials\\SystemDecyzyjny.txt";
        ArrayList<String> rows = zpliku(decsyspath);
        int xSize = rows.get(0).split(" ").length;
        int ySize = rows.size();
        int[][] attributes = new int[ySize][xSize-1];
        int[] decisions = new int[ySize];
        for (int i = 0; i < ySize; ++i) {
            String [] line = rows.get(i).split(" ");
            for (int j = 0; j < xSize-1; ++j)
                attributes[i][j] = Integer.parseInt(line[j]);
            decisions[i] = Integer.parseInt(line[xSize-1]);
        }

        System.out.println("Dec Sys:");
        fun_decsys(attributes, decisions);
        System.out.println("\nRules:");
        fun1(attributes, decisions);
    }



    public static ArrayList<String> zpliku(String path) {
        ArrayList<String> dane = new ArrayList<>();
        try {
            File obj = new File(path);
            Scanner read1 = new Scanner(obj);
            while (read1.hasNextLine()) {
                String data = read1.nextLine();
                dane.add(data);
            }
            read1.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return dane;
    }

    public static void fun_decsys(int [][] attributes, int [] decisions) {
        int xSize = attributes[0].length + 1;
        int ySize = attributes.length;
        System.out.print("   ");
        for(int i = 0; i < xSize-1; ++i) {
            System.out.print("a"+(i+1)+" ");
        }
        System.out.println("d");
        for(int i = 0; i < ySize; ++i) {
            System.out.print("o"+(i+1)+" ");
            for(int j = 0; j < xSize-1; ++j) {
                System.out.print(attributes[i][j]);
                System.out.print("  ");
            }

            System.out.print(decisions[i]);
            System.out.println();
        }
    }

    public static void fun1(int [][] attributes, int [] decisions) {
        int ySize = attributes.length;

        int atrybuty_liczba = attributes[0].length;
        ArrayList<int[]> combinations = new ArrayList<>();
        ArrayList<int[]> ele = new ArrayList<>();
        for(int i = 1; i <= atrybuty_liczba; ++i) {
            ele = generate(atrybuty_liczba, i);
            for(int [] j : ele)
                combinations.add(j);

        }

        ArrayList<Integer> considerations = new ArrayList<>();
        for(int i = 0; i < ySize; ++i)
            considerations.add(i);



        for(int combinationsIterator = 0; combinationsIterator < combinations.size() && considerations.size() > 0; ++combinationsIterator) {
            for(int i = 0; i < considerations.size(); ++i) {
                boolean correct = true;
                int obj = considerations.get(i);
                for(int j = 0; j < ySize; ++j) {
                    boolean sameAttributesValues = true;
                    for(int k : combinations.get(combinationsIterator))
                        if(attributes[obj][k] != attributes[j][k])
                            sameAttributesValues = false;
                    if(sameAttributesValues)
                        if(decisions[obj] != decisions[j])
                            correct = false;
                }


                if(correct) {
                    System.out.print("o"+(obj+1));
                    for(int j = 0; j < combinations.get(combinationsIterator).length; ++j) {
                        int attribute = combinations.get(combinationsIterator)[j];
                        System.out.print(":a"+(attribute+1)+"="+attributes[obj][attribute]);
                    }
                    System.out.print("=>d=");
                    System.out.print(decisions[obj]);
                    ArrayList<Integer> toDeletedFromConsiderations = new ArrayList<>();
                    for(int j = 0; j < ySize; ++j) {
                        boolean sameAttributesValues = true;
                        for(int k : combinations.get(combinationsIterator))
                            if(attributes[obj][k] != attributes[j][k])
                                sameAttributesValues = false;
                        if(sameAttributesValues)
                            toDeletedFromConsiderations.add(j);

                    }


                    for(int j : toDeletedFromConsiderations)
                        System.out.print(" o"+(j+1)+" ");
                    System.out.println();
                    for(int k = toDeletedFromConsiderations.size()-1; k >= 0; --k) {
                        considerations.remove(toDeletedFromConsiderations.get(k));
                    }


                    if(toDeletedFromConsiderations.size() > 0) {
                        i = -1;
                    }
                }
            }
        }
    }



    public static ArrayList<int[]> generate(int n, int r) {
        ArrayList<int[]> combinations = new ArrayList<>();
        fun2(combinations, new int[r], 0, n-1, 0);
        return combinations;
    }




    private static void fun2(ArrayList<int[]> combinations, int [] data, int a, int b, int i) {
        if (i == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (a <= b) {
            data[i] = a;
            fun2(combinations, data, a + 1, b, i + 1);
            fun2(combinations, data, a + 1, b, i);
        }
    }
}

