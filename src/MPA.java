public class MPA {
    private char[] chars;
    private int size;

    public MPA() {
        this.chars = new char[0];
        this.size = 0;
    }

    public void processString(String input) {
        if (input == null || input.isEmpty()) {
            removeLastChar();
        } else {
            addChars(input);
        }
    }

    private void addChars(String input) {
        int newSize = size + input.length();
        char[] newChars = new char[newSize];

        System.arraycopy(chars, 0, newChars, 0, size);

        for (int i = 0; i < input.length(); i++) {
            newChars[size + i] = input.charAt(i);
        }

        chars = newChars;
        size = newSize;
    }

    private void removeLastChar() {
        if (size > 0) {
            char[] newChars = new char[size - 1];
            System.arraycopy(chars, 0, newChars, 0, size - 1);
            chars = newChars;
            size--;
        } else {
            throw new IllegalStateException("Магазин пуст: невозможно удалить последний символ.");
        }
    }

    public Character getLastChar() {
        if (size > 0) {
            return chars[size - 1];
        } else {
            return null;
        }
    }
}
