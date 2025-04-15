import java.util.*;

class Token {
    int id;
    String value;
    String type;

    public Token(int id, String value, String type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }
}

public class Main {

    // Token table
    static List<Token> tokenTable = new ArrayList<>();

    static {
        tokenTable.add(new Token(1, "int", "reserved"));
        tokenTable.add(new Token(2, "Ab", "identifier"));
        tokenTable.add(new Token(3, "float", "reserved"));
        tokenTable.add(new Token(4, "Exemplo", "identifier"));
        tokenTable.add(new Token(5, "3456,567", "number"));
        tokenTable.add(new Token(6, "while", "reserved"));
        tokenTable.add(new Token(7, "500", "number"));
    }

    public static String analyzeToken(String input) {
        for (Token token : tokenTable) {
            if (token.value.equals(input)) {
                return "<" + token.type + ", " + token.id + ">";
            }
        }
        return "<unknown, -1>";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a line of code:");
        String lineInput = scanner.nextLine();

        String[] tokens = lineInput.split("\\s+");

        System.out.println("Token analysis result:");
        for (String token : tokens) {
            String result = analyzeToken(token);
            System.out.println(token + " -> " + result);
        }
    }
}
