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

            if (!isValidVariableName(variableName)) {
                System.out.println("error");
                return;
            }
            // then need to evaluate any expressions
            // need to print out evaluated expressions or "answers" when finished
        }
    }

    private static boolean isValidVariableName(String variableName) {
        if (variableName.length() == 0) {
            return false;
        }
        
        if (!Character.isLetter(variableName.charAt(0)) && variableName.charAt(0) != '_'){
            return false;
        }

        for (int i = 1; i < variableName.length(); i++) {
            char currentChar = variableName.charAt(i);
            if (!Character.isLetterOrDigit(currentChar) && currentChar != '_'){
                return false;
            }
        }

        return true;
    }


}