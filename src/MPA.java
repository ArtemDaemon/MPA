import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class MPA {
    private enum STATES {
        q0, q1, q2
    }

    private static final STATES[] ACCEPTED_STATES = {
            STATES.q2
    };

    public static final int EMPTY_STACK_ERR = -2;
    public static final int UNKNOWN_TRANSITION = -1;
    public static final int NOT_REACHED_FINAL_STATE = 0;
    public static final int REACHED_FINAL_STATE = 1;

    private static class Transition {
        final STATES newState;
        final String stackAction;

        public Transition(STATES newState, String stackAction) {
            this.newState = newState;
            this.stackAction = stackAction;
        }
    }

    private static class TransitionKey {
        final STATES currentState;
        final char inputSymbol;
        final char stackTop;

        public TransitionKey(STATES currentState, char inputSymbol, char stackTop) {
            this.currentState = currentState;
            this.inputSymbol = inputSymbol;
            this.stackTop = stackTop;
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            TransitionKey that = (TransitionKey) o;
            return inputSymbol == that.inputSymbol &&
                    stackTop == that.stackTop &&
                    currentState == that.currentState;
        }

        @Override
        public int hashCode() {
            return Objects.hash(currentState, inputSymbol, stackTop);
        }
    }

    private final Map<TransitionKey, Transition> transitionTable = new HashMap<>();
    private final Stack<Character> inputStack = new Stack<>();
    private STATES currentState;

    public MPA() {
        this.currentState = STATES.q0;
        setTransitionTable();
    }

    private void setTransitionTable() {
        transitionTable.put(new TransitionKey(STATES.q0, 'a', 'a'),
                new Transition(STATES.q0, "push:aaa"));
        transitionTable.put(new TransitionKey(STATES.q0, 'a', '\0'),
                new Transition(STATES.q0, "push:aaa"));
        transitionTable.put(new TransitionKey(STATES.q0, 'b', 'a'),
                new Transition(STATES.q1, "pop"));
        transitionTable.put(new TransitionKey(STATES.q0, '\0', '\0'),
                new Transition(STATES.q2, "none"));
        transitionTable.put(new TransitionKey(STATES.q1, 'b', 'a'),
                new Transition(STATES.q1, "pop"));
        transitionTable.put(new TransitionKey(STATES.q1, '\0', '\0'),
                new Transition(STATES.q2, "none"));
        transitionTable.put(new TransitionKey(STATES.q1, '\0', 'a'),
                new Transition(STATES.q2, "none"));
    }

    public int processSymbol(char currentSymbol) {
        char stackTop = inputStack.isEmpty() ? '\0' : inputStack.peek();
        TransitionKey key = new TransitionKey(currentState, currentSymbol, stackTop);
        Transition transition = transitionTable.get(key);

        if(transition == null) return UNKNOWN_TRANSITION;

        currentState = transition.newState;

        if(transition.stackAction.startsWith("push:")) {
            String toPush = transition.stackAction.substring(5);
            for(char c : toPush.toCharArray()) {
                inputStack.push(c);
            }
        } else if(transition.stackAction.equals("pop")) {
            if(inputStack.isEmpty()) {
                return EMPTY_STACK_ERR;
            }
            inputStack.pop();
        }

        return isCurrentStateAccepted();
    }

    public int isCurrentStateAccepted() {
        for(STATES acceptedState : ACCEPTED_STATES) {
            if(currentState == acceptedState)
                return REACHED_FINAL_STATE;
        }
        return NOT_REACHED_FINAL_STATE;
    }

    public STATES getCurrentState() {
        return currentState;
    }

    @Override
    public String toString() {
        return "Текущее состояние = " + currentState +
                "; Содержимое стека: " + inputStack;
    }
}