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

    private static int evaluateTerm(List<String> tokensList, Map<String, Integer> variablesMap) {
        
        int result  = evaluateFactor( tokensList, variablesMap);
        //  need to create evaluateFactor function
        if (result == Integer.MIN_VALUE) {
            return result;
        }

        while (!tokensList.isEmpty()) {

            String operator = tokensList.remove(0);
            if (!operator.equals("*")) {
                
                tokensList.add(0, operator);
                return result;
            }
            int value = evaluateFactor(tokensList, variablesMap);
            if (value == Integer.MIN_VALUE) {
                return value;
            }

            result *= value;
        }

        return result;
    }

    private static int evaluateFactor( List<String> tokensList, Map<String, Integer> variablesMap) {
        String nextToken = tokensList.remove(0);
        if (nextToken.equals("(")) {
            int result = evaluateExpressionTokens(tokensList, variablesMap);

            if (result == Integer.MIN_VALUE) {
                return result;
            }
            if (tokensList.size() == 0 || !tokensList.remove(0).equals(")")) {
                return Integer.MIN_VALUE;

            }
            return result;
        } else if (nextToken.equals("+") || nextToken.equals("-")) {
            int value = evaluateFactor(tokensList, variablesMap);
            if (value == Integer.MIN_VALUE) {
                return value;
            }
            if (nextToken.equals("+")) {
                return value;
            } else {
                return -value;
            }
        } else {
            if (isValidIntegerString(nextToken)) {
                // need to create isValidIntegerString
                return Integer.parseInt(nextToken);

            } else if (variablesMap.containsKey(nextToken)) {
                return variablesMap.get(nextToken);

            } else {
                return Integer.MIN_VALUE;
            }
        }
    }

    private static boolean isValidIntegerString(String inputString) {
        if (inputString.charAt(0) == '0' && inputString.length() > 1) {
            return false;
        }

        for (int i =0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (!Character.isDigit(currentChar)) {
                return false;
            }
        }

        return true;
    }



}