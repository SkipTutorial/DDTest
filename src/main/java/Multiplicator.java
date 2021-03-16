import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Multiplicator {
    private static final Stack<String> stringStack = new Stack<>();
    private static final Stack<String> quantifierStack = new Stack<>();
    private static final StringBuilder output = new StringBuilder();

    public static void main(String[] args) throws IOException {
        System.out.println("Please, provide input data...");
        String input = getInput();
        String[] inputArr = input.split("");
        int i = 0;
        multiply(inputArr, i);
    }

    private static String getInput() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    private static void multiply(String[] inputArr, int i) throws IOException {
        StringBuilder quantifier = new StringBuilder();
        StringBuilder chars = new StringBuilder();
        String temp;

        while (i < inputArr.length) {

            if (inputArr[i].matches("[0-9]")) {
                while (i < inputArr.length && inputArr[i].matches("[0-9]")) {
                    quantifier.append(inputArr[i]);
                    i++;
                }
                if (i < inputArr.length && inputArr[i].equals("[")) {
                    if (quantifierStack.size() != stringStack.size()) stringStack.push("");
                    quantifierStack.push(quantifier.toString());

                } else {
                    System.out.printf("incorrect input at index [%d]. Hit Enter to exit...", i);
                    getInput();
                    return;
                }
                quantifier.delete(0, quantifier.length());
            } else if (inputArr[i].matches("[a-z]|[A-Z]")) {
                while (i < inputArr.length && inputArr[i].matches("[a-z]|[A-Z]")) {
                    chars.append(inputArr[i]);
                    i++;
                }
                if (quantifierStack.empty()) {
                    output.append(chars.toString());
                    if (i < inputArr.length && !inputArr[i].matches("[0-9]")) {
                        System.out.printf("incorrect input %s at index [%d]. Hit Enter to exit...", inputArr[i],i);
                        getInput();
                        return;
                    }
                } else if (i < inputArr.length) {
                    if (quantifierStack.size() != stringStack.size()) {
                        stringStack.push(chars.toString());
                    } else {
                        temp = stringStack.pop() + chars.toString();
                        stringStack.push(temp);
                    }
                } else {
                    System.out.printf("incorrect input at index [%d]. Hit Enter to exit...", i);
                    getInput();
                    return;
                }
                chars.delete(0, chars.length());
            } else if (inputArr[i].equals("[")) {
                i++;
            } else if (inputArr[i].equals("]")) {
                if (stringStack.empty()) {
                    System.out.printf("incorrect input at index [%d]. Hit Enter to exit...", i);
                    getInput();
                    return;
                } else {
                    temp = stringStack.pop().repeat(Integer.parseInt(quantifierStack.pop()));
                    if (stringStack.empty())
                        if(quantifierStack.empty()) output.append(temp);
                        else stringStack.push(temp);
                    else {
                        temp = stringStack.pop() + temp;
                        stringStack.push(temp);
                    }
                }
                i++;
            } else {
                System.out.printf("incorrect input at index [%d]. Hit Enter to exit", i);
                getInput();
                return;
            }
        }
        System.out.println(output.toString());
    }
}
