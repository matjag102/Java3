import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;

public class Zad1 {
    public static void main(String[] args) {
        System.out.println("Zadanie 1\n");
        CSP csp;
        csp = new CSP();
        csp.addVariable("X1");
        csp.addVariable("X2");
        csp.addVariable("X3");
        System.out.println("X = " + csp.varString());
        ArrayList<String> d1 = new ArrayList<>();
        d1.add("R");
        d1.add("B");
        d1.add("G");
        ArrayList<String> d2 = new ArrayList<>();
        d2.add("R");
        ArrayList<String> d3 = new ArrayList<>();
        d3.add("G");
        csp.addDomain(d1);
        csp.addDomain(d2);
        csp.addDomain(d3);
        System.out.println();
        System.out.print(csp.domString());
    }
}


