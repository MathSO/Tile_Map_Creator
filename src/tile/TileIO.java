package tile;

import screen.Map;

import update.ChangeListener;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;

import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TileIO {
    private static JFileChooser fc = new JFileChooser();
    private static File file;
    private static JTextField wt = new JTextField(15);
    private static JTextField ht = new JTextField(15);

    public static Map newMap() {
        file = null;

        int x = 0;
        int y = 0;
        if (JOptionPane.showConfirmDialog(null, createPanel(), "Map size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                x = Integer.parseInt(wt.getText());
                y = Integer.parseInt(ht.getText());

                ChangeListener.clear();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Try again!\nOnly integer numbers alowed!!");
                
                return null;
            }
        } else {
            return null;
        }

        if (x < 0 || y < 0) {
            JOptionPane.showMessageDialog(null, "Try again!\nOnly positive numbers alowed!!");
            return null;
        }

        return new Map(x, y);
    }

    public static Map loadMap() {
        ObjectInputStream sc;
        int x;
        int y;
        int aux[][];

        try {
            boolean right = false;

            do {
                if (fc.showOpenDialog(null) != fc.APPROVE_OPTION) {
                    return null;
                }
                
                file = fc.getSelectedFile();

                String name = file.getName();
                if (name.contains(".map")) {
                    right = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Couldn't read selected file! It must be a .map file");
                }
            } while (!right);

            sc = new ObjectInputStream(new java.io.FileInputStream(file));

            int imgLine = (int)sc.readObject();
            int imgPerLine = (int)sc.readObject();
            int w = (int)sc.readObject();
            int h = (int)sc.readObject();
            BufferedImage[][] tile = new BufferedImage[imgLine][imgPerLine];
            
            for (int i = 0; i < imgLine; i++) {
                for(int j = 0; j < imgPerLine; j++) {
                    tile[i][j] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    tile[i][j].setRGB(0, 0, w, h, (int[])sc.readObject(), 0, w);
                }
            }
            
            int[][] map = (int[][])sc.readObject();


            sc.close();

            ChangeListener.clear();
            return new Map(map, tile);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void saveMap(Map map) {
        if (file == null) {
            do {
                if(fc.showOpenDialog(null) != fc.APPROVE_OPTION) {
                    return;
                }
                
                file = fc.getSelectedFile();
            } while (file.exists() && JOptionPane.showConfirmDialog(null, "File already existis! Do you want to overwrite?", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION);
        }

        if (!file.getName().contains(".map")) {
            file = new File(file.getPath() + ".map");
        }

        try {
            ObjectOutputStream pw = new ObjectOutputStream(new java.io.FileOutputStream(file));
            Tile[][] tiles = map.getTileSet().getTiles();

            pw.writeObject(tiles.length);
            pw.writeObject(tiles[0].length);

            pw.writeObject(tiles[0][0].getWidth());
            pw.writeObject(tiles[0][0].getHeight());
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    pw.writeObject(tiles[i][j].getImage().getRGB(0, 0, tiles[i][j].getWidth(), tiles[i][j].getHeight(), null, 0, tiles[i][j].getWidth()));
                }
            }

            pw.writeObject(map.getMapInt());

            pw.flush();

            ChangeListener.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JPanel createPanel(){
        SpringLayout layout = new SpringLayout();
        JLabel wl = new JLabel("Width:");
        JLabel hl = new JLabel("Height:");
        JPanel painel = new JPanel(layout);

        wt = new JTextField(15);
        ht = new JTextField(15);

        wt.setHorizontalAlignment(JTextField.RIGHT);
        ht.setHorizontalAlignment(JTextField.RIGHT);

        painel.setPreferredSize(new java.awt.Dimension(300, 100));

        painel.add(wl);
        painel.add(wt);

        painel.add(hl);
        painel.add(ht);

        layout.putConstraint(SpringLayout.WEST, wl, 19, SpringLayout.WEST, painel);
        layout.putConstraint(SpringLayout.NORTH, wl, 20, SpringLayout.NORTH, painel);

        layout.putConstraint(SpringLayout.WEST, wt, 25, SpringLayout.EAST, wl);
        layout.putConstraint(SpringLayout.NORTH, wt, 20, SpringLayout.NORTH, painel);

        layout.putConstraint(SpringLayout.WEST, hl, 20, SpringLayout.WEST, painel);
        layout.putConstraint(SpringLayout.NORTH, hl, 20, SpringLayout.SOUTH, wl);

        layout.putConstraint(SpringLayout.WEST, ht, 20, SpringLayout.EAST, hl);
        layout.putConstraint(SpringLayout.NORTH, ht, 20, SpringLayout.SOUTH, wt);

        return painel;
    }
    
    public static BufferedImage[][] readTiles() {
        int tileSizeX = 0;
        int tileSizeY = 0;

        if (JOptionPane.showConfirmDialog(null, createPanel(), "Tile Size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                tileSizeX = Integer.parseInt(wt.getText());
                tileSizeY = Integer.parseInt(ht.getText());
            } catch (Exception e) {

                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Try again!\nOnly integer numbers alowed!!");
                
                return null;
            }
        } else {
            return null;
        }

        if (fc.showOpenDialog(null) != fc.APPROVE_OPTION ) {
            return null;
        }

        BufferedImage img;
        try {
            img = javax.imageio.ImageIO.read(fc.getSelectedFile());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Loading faled! Please try again!");
            return null;
        }

        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage[][] imgs = new BufferedImage[(h / tileSizeY)][(w / tileSizeX)];
        
        for (int i = 0; i < imgs.length; i++) {
            for (int j = 0; j < imgs[0].length; j++) {
                imgs[i][j] = img.getSubimage(j * tileSizeX, i * tileSizeY, tileSizeX, tileSizeY);
            }
        }

        ChangeListener.clear();
        return imgs;
    }
}