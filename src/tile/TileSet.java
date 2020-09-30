package tile;

import java.awt.image.BufferedImage;

public class TileSet {
    private Tile[][] tileSet;
    private int tileWidth;
    private int tileHeight;
    private int tilesPerLine;
    private int lines;

    public TileSet(BufferedImage[][] imgs){
        this.tileWidth = imgs[0][0].getWidth();
        this.tileHeight = imgs[0][0].getHeight();

        this.lines = imgs.length;
        this.tilesPerLine = imgs[0].length;

        this.tileSet = new Tile[imgs.length][imgs[0].length];

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < tilesPerLine; j++) {
                tileSet[i][j] = new Tile(imgs[i][j]);
            }
        }
    }

    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= tileSet.length || y >= tileSet[x].length) {
            return new Tile(new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB));
        }

        return tileSet[x][y];
    }

    public Tile[][] getTiles(){
        return tileSet;
    }

    public int getWidth(){
        return tileWidth;
    }

    public int getHeight(){
        return tileHeight;
    }

    public int getLines() {
        return lines;
    }

    public int getTilesPerLine(){
        return tilesPerLine;
    }
}