import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CSP {

    private ArrayList<String> variables;
    private ArrayList<ArrayList<String>> domains;
    private ArrayList<ArrayList<String>> constraints;

    public CSP() {
        variables = new ArrayList<>();
        domains = new ArrayList<>();
        constraints = new ArrayList<>();
    }

    public void addVariable(String var) {
        variables.add(var);
    }

    public void addDomain(ArrayList<String> domain) {
        domains.add(domain);
    }

    public void addConstraint(ArrayList<String> constraint) {
        constraints.add(constraint);
    }


    public String varString() {
        String wartosci = "{";
        int length = variables.size();
        ;
        for (int i = 0; i < length; ++i) {
            wartosci += variables.get(i);
            if (i != length - 1)
                wartosci += ", ";
        }
        wartosci += "}";
        return wartosci;
    }


    public String domString() {
        String wartosci = "";
        int length = domains.size();
        if (length == 0)
            return wartosci;
        wartosci += "D = {";
        for (int i = 0; i < length; ++i) {
            wartosci += "D" + (i + 1);
            if (i != length - 1)
                wartosci += ", ";
        }
        wartosci += "}\n";

        for (int i = 0; i < length; ++i) {
            wartosci += "D" + (i + 1) + " = {";
            int len = domains.get(i).size();
            for (int j = 0; j < len; ++j) {
                wartosci += domains.get(i).get(j);
                if (j != len - 1)
                    wartosci += ", ";
            }
            wartosci += "}\n";
        }
        return wartosci;
    }
}
