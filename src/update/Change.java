package update;

public class Change {
    private int x, y, atual, last;

    public Change(int x, int y, int a, int l) {
        this.x = x;
        this.y = y;
        this.atual = a;
        this.last = l;
    }

    public void undo(int[][] mapa) {
        mapa[x][y] = this.last;
    }

    public void redo(int[][] mapa) {
        mapa[x][y] = this.atual;
    }
}