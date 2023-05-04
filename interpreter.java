import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

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
            
            int value = evaluateExpression(expressionString, variablesMap);
            if (value == Integer.MIN_VALUE) {
                System.out.println("error");
                return;
            }
            // commit variable names and their values to the map
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

    private static int evaluateExpression(String expressionString, Map<String, Integer> variablesMap) {
        List<String> tokensList = tokenize(expressionString);
        
        return evaluateExpressionTokens(tokensList, variablesMap);
        // need to evaluate expression tokens and return 
        // create evaluateExpressionTokens function
    }

    private static List<String> tokenize(String expressionString) {
        List<String> tokenList = new ArrayList<String>();
        StringBuilder tokenBuilder = new StringBuilder();

        for (int i = 0; i < expressionString.length(); i++){
            char currentChar = expressionString.charAt(i);
            if (currentChar == '+' || currentChar == '_' || currentChar == '*' || currentChar == '(' || currentChar == ')'){
                if (tokenBuilder.length() !=0){
                    tokenList.add(tokenBuilder.toString());
                    tokenBuilder = new StringBuilder();
                }
                tokenList.add(Character.toString(currentChar));
            } else {
                tokenBuilder.append(currentChar);
            }
        }
        
        if (tokenBuilder.length() != 0) {
            tokenList.add(tokenBuilder.toString());
        }
        return tokenList;
    }

    private static int evaluateExpressionTokens(List<String> tokensList, Map<String, Integer> variablesMap) {

        int result = evaluateTerm(tokensList, variablesMap);
        // create evaluateTerm function
        if (result == Integer.MIN_VALUE) {
            return result;
        }

        while (!tokensList.isEmpty()) {

            String operator = tokensList.remove(0);
            if (!operator.equals("+") && !operator.equals("-")) {
                tokensList.add(0, operator);
                return result;
            }

            int value = evaluateTerm(tokensList, variablesMap);

            if (value == Integer.MIN_VALUE) {
                return value;
            }
            if (operator.equals("+")){
                result += value;

            }
            if (operator.equals("+")){
                result += value;
            } else {
                result -= value;
            }
        }
        return result;
    }

    


}