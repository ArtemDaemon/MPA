import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MPA mpa = new MPA();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите строку");
        String input = scanner.nextLine();

        System.out.println("Входная строка: " + input);

        for(char symbol : input.toCharArray()) {
            processSymbolAndReport(mpa, symbol);
        }
        processSymbolAndReport(mpa, '\0');

        int finalResult = mpa.isCurrentStateAccepted();
        if(finalResult == MPA.REACHED_FINAL_STATE) {
            System.out.println("Строка принята");
        } else {
            System.out.println("Строка не принята");
        }
    }

    public static void processSymbolAndReport(MPA mpa, char symbol) {
        int result = mpa.processSymbol(symbol);

        if(result == MPA.UNKNOWN_TRANSITION) {
            System.out.println("Строка не принята");
            System.exit(0);
        } else if(result == MPA.EMPTY_STACK_ERR) {
            System.out.println("Нельзя удалить элемент из пустого стека");
            System.exit(1);
        }

        System.out.println("Обработан символ: " + symbol);
        System.out.println(mpa);
    }
}