import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Interpreter {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        String[] inputLines = inputString.split(";");
        Map<String, Integer> variablesMap = new HashMap<String, Integer>();

        for (String inputLine : inputLines) {

            String[] assignmentParts = inputLine.split("=");

            if (assignmentParts.length != 2) {
                System.out.println("error");
                return;
            }

            String variableName = assignmentParts[0].trim();
            String expressionString = assignmentParts[1].trim();

            // need to check for valid variable names
            // then need to evaluate any expressions
        }
    }



}