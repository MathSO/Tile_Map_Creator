package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public static final int BLOCKED = 1;
    private BufferedImage img;

    public Tile(BufferedImage i, int rx, int ry) {
        this.img = new BufferedImage(rx, ry, BufferedImage.TYPE_INT_ARGB);
        this.img.getGraphics().drawImage(i, 0, 0, rx, ry, null);
    }

    public Tile(BufferedImage i) {
        this.img = i;
    }

    public void draw(java.awt.Graphics g, int x, int y, int rx, int ry) {
        g.drawImage(img, x, y, rx, ry, null);
    }

    public BufferedImage getImage(){
        return img;
    }

    public int getWidth(){
        return img.getWidth();
    }

    public int getHeight(){
        return img.getHeight();
    }

    public void draw(java.awt.Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
    }
}