package update;

public class ChangeListener {
    private static final java.util.Stack<Change> changed = new java.util.Stack<Change>();
    private static final java.util.Stack<Change> unchanged = new java.util.Stack<Change>();

    public static void addChange(int x, int y, int a, int l) {
        unchanged.removeAllElements();
        changed.push(new Change(x, y, a, l));
    }

    public static void clear(){
        changed.removeAllElements();
        unchanged.removeAllElements();
    }

    public static void undo(int[][] map) {
        if (changed.empty()) {
            return;
        }

        changed.peek().undo(map);

        unchanged.push(changed.peek());
        changed.pop();
    }

    public static void redo(int[][] map){
        if (unchanged.empty()) {
            return;
        }

        unchanged.peek().redo(map);

        changed.push(unchanged.peek());
        unchanged.pop();
    }

    public static boolean hasChange(){
        return !changed.empty();
    }
}