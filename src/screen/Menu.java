package screen;

import tile.Tile;
import tile.TileIO;

import update.ChangeListener;

import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

public class Menu {
    private BufferedImage imgButton;
    private Tile[] tiles;
    private int init, scale, selected, mapOffsetX, mapOffsetY;
    private int[][] map;
    private boolean leftKey, rightKey, upKey, downKey;

    public Menu() {
        try {
            this.imgButton = javax.imageio.ImageIO.read(new java.io.File("resorces/Buttons.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        this.selected = 1;
        this.init = -20;
        this.scale = 80;

        this.tiles = new Tile[1];

        tiles[0] = new Tile(new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB));

        this.map = new int[0][0];
        this.mapOffsetX = 0;
        this.mapOffsetY = 0;

        this.leftKey = this.rightKey = this.upKey = this.downKey = false;
    }

    public void update() {
        if (leftKey) {
            mapOffsetX--;
        }

        if (rightKey) {
            mapOffsetX++;
        }
        
        if (upKey) {
            mapOffsetY--;
        }
        
        if (downKey) {
            mapOffsetY++;
        }
        
        if (mapOffsetX < 0) {
            mapOffsetX = 0;
        }
        
        if (mapOffsetY < 0) {
            mapOffsetY = 0;
        }
        
        if (mapOffsetX > map.length * tiles[0].getWidth()) {
            mapOffsetX = map.length * tiles[0].getWidth();
        }
        
        if (mapOffsetY > map.length * tiles[0].getHeight()) {
            mapOffsetY = map.length * tiles[0].getHeight();
        }
    }

    public void draw(java.awt.Graphics g) {
        g.setColor(java.awt.Color.WHITE);
        for (int i = mapOffsetY / tiles[0].getHeight(); i < map.length && i * tiles[0].getHeight() <= mapOffsetY + g.getClipBounds().getHeight(); i++){
            for (int j = mapOffsetX / tiles[0].getWidth(); j < map[i].length && j * tiles[0].getWidth() <= mapOffsetX + g.getClipBounds().getWidth(); j++) {
                g.drawRect(-mapOffsetX + j * tiles[0].getWidth(), -mapOffsetY + 200 + i * tiles[0].getHeight(), tiles[0].getWidth() - 1, tiles[0].getHeight() - 1);
                
                if (map[i][j] < tiles.length){
                    tiles[map[i][j]].draw(g, -mapOffsetX +  j * tiles[0].getWidth(), -mapOffsetY + 200 + i * tiles[0].getHeight());
                }
            }
        }
        
        g.setColor(java.awt.Color.GRAY);
        g.fill3DRect(0, 0, (int)g.getClipBounds().getWidth(), 200, false);
        g.drawImage(imgButton, 0, 0, null);

        for (int i = 0; i < tiles.length; i++) {
            tiles[i].draw(g, -init + (10 + this.scale) * i, 70, this.scale, this.scale);

            g.setColor(java.awt.Color.WHITE);
            g.drawRect(-init + (10 + this.scale) * i, 70, this.scale - 1, this.scale - 1);
        }
    }

    public void newMap() {
        TileIO.newMap(this);
        mapOffsetX = mapOffsetY = 0;
    }

    public void saveMap() {
        if (map != null && map.length > 0) {
            TileIO.saveMap(map, tiles);
        } else {
            JOptionPane.showMessageDialog(null, "There is no open map in the editor!");
        }
    }

    public void loadMap() {
        TileIO.loadMap(this);
    }

    public void openTile() {
        System.out.println("Add...");
        this.tiles = TileIO.readTiles(this.tiles);
        init = -20;
    }

    public void exit() {
        if (ChangeListener.hasChange()) {
            switch (JOptionPane.showConfirmDialog(null, "You are about to close the window! Would like to save your progress?", "Confirmation!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE)) {
                case JOptionPane.YES_OPTION:
                    this.saveMap();

                case JOptionPane.NO_OPTION:
                    System.exit(0);
                
                case JOptionPane.CANCEL_OPTION:
                default:
            }
        } else {
            System.exit(0);
        }
    }

    public void setTileToSeleceted(int x, int y) {
        if (x < map.length && y < map[x].length && map[x][y] != selected) {
            ChangeListener.addChange(x, y, selected, map[x][y]);
            map[x][y] = selected;
        }
    }

    public void undo() {
        ChangeListener.undo(map);
    }

    public void redo() {
        ChangeListener.redo(map);
    }

    public void mouseClick(int x, int y) {
        if ( y < 50 && tiles.length != 1) {
            if (x < 50) {
                init -= 200;

                if (init < -20) {
                    init = -20;
                }
            } else {
                if (x < 100) {
                    if (init + 200 < (tiles.length - 1) * (scale + 10)) {
                        init += 200;
                    }
                }
            }
        } else {
            if (y > 200) {
                x += mapOffsetX;
                y += mapOffsetY - 200;

                x /= tiles[0].getWidth();
                y /= tiles[0].getHeight();

                setTileToSeleceted(y, x);
            } else {
                if (y > 70 && y < 70 + this.scale) {
                    int s = (init + x) / (scale + 10);
                    if (tiles.length > s){
                        selected = s;
                    }
                }
            }
        }
    }

    public void keyPressed(KeyEvent k){
        switch (k.getKeyCode()) {
            case KeyEvent.VK_W:
                upKey = true;
            break;

            case KeyEvent.VK_S:
                downKey = true;
            break;

            case KeyEvent.VK_A:
                leftKey = true;
            break;

            case KeyEvent.VK_D:
                rightKey = true;
            break;

            case KeyEvent.VK_Z:
                if (k.getModifiers() == KeyEvent.CTRL_MASK) {
                    undo();
                } else {
                    if (k.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK)) {
                        redo();
                    }
                }
            break;
        }
    }

    public void keyReleased(KeyEvent k){
        switch (k.getKeyCode()) {
            case KeyEvent.VK_W:
                upKey = false;
            break;

            case KeyEvent.VK_S:
                downKey = false;
            break;

            case KeyEvent.VK_A:
                leftKey = false;
            break;

            case KeyEvent.VK_D:
                rightKey = false;
            break;
        }
    }

    public void setMap(int map[][]) {
        this.map = map;
    }

    public void setTiles(Tile[] t){
        tiles = t;
    }

    public Tile[] getTiles(){
        return tiles;
    }
}