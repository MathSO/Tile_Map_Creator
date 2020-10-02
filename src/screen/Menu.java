package screen;

import tile.TileIO;
import tile.TileSet;

import update.ChangeListener;

import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

public class Menu {
    private BufferedImage imgButton;
    private Map map;

    private int init;
    private int scale;
    private int selected;
    private int mapOffsetX;
    private int mapOffsetY;
    
    private boolean leftKey;
    private boolean rightKey;
    private boolean upKey;
    private boolean downKey;

    public Menu() {
        try {
            this.imgButton = javax.imageio.ImageIO.read(new java.io.File("res/Buttons.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        this.selected = 1;
        this.init = -20;
        this.scale = 80;

        map = new Map(0, 0);

        this.mapOffsetX = this.mapOffsetY = 0;

        this.leftKey = this.rightKey = this.upKey = this.downKey = false;
    }

    public void update() {
        if (leftKey && mapOffsetX > 0) {
            mapOffsetX--;
        }

        if (rightKey && mapOffsetX < map.getWidth()) {
            mapOffsetX++;
        }
        
        if (upKey && mapOffsetY > 0) {
            mapOffsetY--;
        }
        
        if (downKey && mapOffsetY < map.getHeight()) {
            mapOffsetY++;
        }
    }

    public void draw(java.awt.Graphics g) {
        if (map != null) {
            map.drawMap(g, mapOffsetX, mapOffsetY);
        }
        
        g.setColor(java.awt.Color.GRAY);
        g.fill3DRect(0, 0, (int)g.getClipBounds().getWidth(), 200, false);
        g.drawImage(imgButton, 0, 0, null);

        TileSet ts = map.getTileSet();
        for (int i = 0; i < ts.getLines(); i++) {
            for (int j = 0; j < ts.getTilesPerLine(); j++) {
                ts.getTile(i, j).draw(g, -init + (10 + this.scale) * (j + i * ts.getTilesPerLine()), 70, this.scale, this.scale);

                g.setColor(java.awt.Color.WHITE);
                g.drawRect(-init + (10 + this.scale) * (j + i * ts.getTilesPerLine()), 70, this.scale - 1, this.scale - 1);
            }
        }
    }

    public void newMap() {
        Map aux = TileIO.newMap();
        if (aux != null) {
            aux.setTileSet(map.getTileSet());
            map = aux;
            
            mapOffsetX = mapOffsetY = 0;
        }
    }

    public void saveMap() {
        if (map != null) {
            TileIO.saveMap(map);
        } else {
            JOptionPane.showMessageDialog(null, "There is no open map in the editor!");
        }
    }

    public void loadMap() {
        Map aux = TileIO.loadMap();
        if (aux != null) {
            map = aux;
        }
    }

    public void openTile() {
        map.setTileSet(TileIO.readTiles());
        this.init = -20;
    }

    public void exit() {
        if (ChangeListener.hasChange()) {
            switch (JOptionPane.showConfirmDialog(null, "You are about to close the window! Would like to save your progress?", "Confirmation!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE)) {
                case JOptionPane.YES_OPTION:
                    this.saveMap();
                    // fall through
                case JOptionPane.NO_OPTION:
                    System.exit(0);

                case JOptionPane.CANCEL_OPTION:
                default:
            }
        } else {
            System.exit(0);
        }
    }

    public void undo() {
        map.undo();
    }

    public void redo() {
        map.redo();
    }

    public void mouseClick(int x, int y) {
        if ( y < 50 && map.getTileSet().getLines() != 0) {
            if (x < 50) {
                init -= 200;

                if (init < -20) {
                    init = -20;
                }
            } else {
                if (x < 100) {
                    if (init + 200 < (map.getTileSet().getLines() * map.getTileSet().getTilesPerLine()) * (scale + 10)) {
                        init += 200;
                    }
                }
            }
        } else {
            if (y > 200) {
                x += mapOffsetX;
                y += mapOffsetY - 200;

                x /= map.getTileSet().getWidth();
                y /= map.getTileSet().getHeight();

                map.setTile(y, x, selected);
            } else {
                if (y > 70 && y < 70 + this.scale) {
                    int s = (init + x) / (scale + 10);
                    selected = s;
                }
            }
        }
    }

    public void keyPressed(KeyEvent k) {
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
                    this.undo();
                } else {
                    if (k.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK)) {
                        this.redo();
                    }
                }
                break;
        }
    }

    public void keyReleased(KeyEvent k) {
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
}