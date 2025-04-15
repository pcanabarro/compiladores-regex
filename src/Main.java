import java.util.*;
import java.util.regex.*;

class Token {
    int id;
    String value;
    String type;

    public Token(int id, String value, String type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        if (type.equals("símbolo"))
            return "<" + value + ",>";
        else
            return "<" + type + "," + id + ">";
    }
}

public class Main {
    static List<String> reservedWords = Arrays.asList("int", "double", "char", "float", "if", "while", "for");
    static Set<String> symbols = new HashSet<>(Arrays.asList("(", ")", ";", "=", "<", ">", "{", "}", "+", "-", "*", "/"));
    static Map<String, Token> symbolTable = new LinkedHashMap<>();
    static int currentId = 1;

    static Pattern identifierPattern = Pattern.compile("^[A-Z][a-zA-Z]*$");
    static Pattern floatNumberPattern = Pattern.compile("^\\d+,\\d+$");
    static Pattern integerPattern = Pattern.compile("^\\d+$");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o código (finalize com linha vazia):");

        List<List<String>> tokenLines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;

            line = line.replaceAll("#.*$", "");

            for (String sym : symbols) {
                line = line.replaceAll(Pattern.quote(sym), " " + sym + " ");
            }

            String[] parts = line.trim().split("\\s+");

            List<String> tokensForLine = new ArrayList<>();

            for (String part : parts) {
                if (part.isEmpty()) continue;

                if (symbols.contains(part)) {
                    tokensForLine.add("<" + part + ",>");
                } else if (reservedWords.contains(part)) {
                    tokensForLine.add(getOrCreateToken(part, "reservada").toString());
                } else if (identifierPattern.matcher(part).matches()) {
                    tokensForLine.add(getOrCreateToken(part, "identificador").toString());
                } else if (floatNumberPattern.matcher(part).matches()) {
                    tokensForLine.add(getOrCreateToken(part, "número").toString());
                } else if (integerPattern.matcher(part).matches()) {
                    tokensForLine.add(getOrCreateToken(part, "número").toString());
                }
            }

            if (!tokensForLine.isEmpty()) {
                tokenLines.add(tokensForLine);
            }
        }

        System.out.println("\nSaída dos tokens por linha:");
        for (List<String> tokenLine : tokenLines) {
            for (String token : tokenLine) {
                System.out.print(token + " ");
            }
            System.out.println();
        }

//        System.out.println("\nTabela de símbolos:");
//        for (Token token : symbolTable.values()) {
//            System.out.println("<" + token.type + ", " + token.id + "> → " + token.value);
//        }
    }

    private static Token getOrCreateToken(String value, String type) {
        if (symbolTable.containsKey(value)) {
            return symbolTable.get(value);
        } else {
            Token token = new Token(currentId++, value, type);
            symbolTable.put(value, token);
            return token;
        }
    }
}
