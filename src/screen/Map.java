package screen;

import tile.TileSet;

import update.ChangeListener;

import java.awt.image.BufferedImage;

public class Map {
    private int[][] mapInt;
    private int mapWidth;
    private int mapHeight;
    private TileSet ts;

    public Map(int mw, int mh) {
        this.mapWidth = mw;
        this.mapHeight = mh;

        this.mapInt = new int[mw][mh];

        for (int i = 0; i < mw; i++) {
            for (int j = 0; j < mh; j++) {
                mapInt[i][j] = -1;
            }
        }

        this.ts = new TileSet(new BufferedImage[][]{{new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB)}});
    }

    public Map(int[][] map, BufferedImage[][] t) {
        this.mapInt = map;

        this.mapWidth = map[0].length;
        this.mapHeight = map.length;

        this.ts = new TileSet(t);
    }

    public void drawMap(java.awt.Graphics g, int xOffSet, int yOffSet) {
        g.setColor(java.awt.Color.WHITE);

        for (int i = yOffSet / ts.getHeight(); 
            i < mapInt.length && i * ts.getHeight() <= yOffSet + g.getClipBounds().getHeight(); 
            i++){

            for (int j = xOffSet / ts.getWidth(); 
                j < mapInt[i].length && j * ts.getWidth() <= xOffSet + g.getClipBounds().getWidth(); 
                j++) {

                g.drawRect(-xOffSet + j * ts.getWidth(), -yOffSet + 200 + i * ts.getHeight(), ts.getWidth() - 1, ts.getHeight() - 1);
                
                int tx = mapInt[i][j] / ts.getTilesPerLine();
                int ty = mapInt[i][j] % ts.getTilesPerLine();
                ts.getTile(tx, ty).draw(g, -xOffSet +  j * ts.getWidth(), -yOffSet + 200 + i * ts.getHeight());
            }
        }
    }

    public void setTileSet(TileSet t){
        this.ts = t;
    }

    public void setTileSet(BufferedImage[][] t){
        this.ts = new TileSet(t);
    }

    public void setTile(int x, int y, int sel){
        if (x >= mapInt.length || y >= mapInt[x].length || x < 0 || y < 0) {
            return;
        }

        ChangeListener.addChange(x, y, sel, mapInt[x][y]);
        mapInt[x][y] = sel;
    }

    public TileSet getTileSet(){
        return ts;
    }

    public void undo() {
        ChangeListener.undo(mapInt);
    }

    public void redo() {
        ChangeListener.redo(mapInt);
    }

    public int getWidth() {
        return mapInt.length * ts.getWidth();
    }

    public int getHeight() {
        return mapInt[0].length * ts.getHeight();
    }

    public int[][] getMapInt(){
        return mapInt;
    }
}