package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static class Error {
        String type;
        int position;

        Error(String type, int position) {
            this.type = type;
            this.position = position;
        }
    }

    public static void main(String[] args) {
        String sourceCode = "if (a + 25 <= b) { print(\"hello world\"); }";

        String[] tokens = tokenize(sourceCode);

        for (String token : tokens) {
            System.out.println(token);
        }
    }


    public static String[] tokenize(String sourceCode) {
        String identifierRegex = "[a-zA-Z][a-zA-Z0-9]{0,14}";
        String integerConstantRegex = "0|100|[1-9]\\d?";
        String operatorRegex = "[+\\-*/]|:=|>=|<=|<>|[{}\\[\\]();,]";
        String stringRegex = "\"[^\"]*\"";
        String keywordRegex = "if|else|for|print|int|[bfhjk]+";

        String regex = String.format("(%s)|(%s)|(%s)|(%s)|(%s)", identifierRegex, integerConstantRegex, operatorRegex, stringRegex, keywordRegex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sourceCode);

        StringBuilder tokenizedCode = new StringBuilder();
        List<Error> errors = new ArrayList<>();

        while (matcher.find()) {
            String token = matcher.group().trim();
            tokenizedCode.append(token).append("\n");

            // Verificar si el token es un error
            if (!token.matches(identifierRegex) &&
                    !token.matches(integerConstantRegex) &&
                    !token.matches(operatorRegex) &&
                    !token.matches(stringRegex) &&
                    !token.matches(keywordRegex)) {
                errors.add(new Error("Invalid token", matcher.start()));
            }
        }

        // Mostrar errores
        if (!errors.isEmpty()) {
            System.out.println("\nErrores encontrados:");
            System.out.println("Tipo de error\t\tPosición");
            for (Error error : errors) {
                System.out.printf("%s\t\t\t%d%n", error.type, error.position);
            }
        }

        return tokenizedCode.toString().split("\n");
    }
}
