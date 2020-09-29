package tile;

import update.ChangeListener;

import screen.Menu;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;

import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TileIO {
    private static JFileChooser fc = new JFileChooser();
    private static File file;
    private static JTextField wt = new JTextField(15), ht = new JTextField(15);

    public static void newMap(Menu m){
        file = null;

        int x = 0, y = 0;
        if (JOptionPane.showConfirmDialog(null, createPanel(), "Map size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                x = Integer.parseInt(wt.getText());
                y = Integer.parseInt(ht.getText());
            } catch (Exception e) {

                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Try again!\nOnly integer numbers alowed!!");
                
                return;
            }
        }

        ChangeListener.clear();
        m.setMap(new int[x][y]);
    }

    public static void loadMap(Menu m){
        ObjectInputStream sc;
        int n, x, y, aux[][];
        Tile[] tile;

        try {
            boolean right = false;

            do {
                if (fc.showOpenDialog(null) != fc.APPROVE_OPTION) {
                    return;
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

            n = (int)sc.readObject();
            int w = (int)sc.readObject(), h = (int)sc.readObject();

            tile = new Tile[n];
            for (int i = 0; i < n; i++){
                BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                img.setRGB(0, 0, w, h, (int[])sc.readObject(), 0, w);

                tile[i] = new Tile(img);
            }
            
            aux = (int[][])sc.readObject();


            sc.close();
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }
        
        ChangeListener.clear();
        m.setMap(aux);
        m.setTiles(tile);
    }

    public static void saveMap(int map[][], Tile[] tiles) {
        if (file == null) {
            do {
                if(fc.showOpenDialog(null) != fc.APPROVE_OPTION)
                    return;
                
                file = fc.getSelectedFile();
            } while (file.exists() && JOptionPane.showConfirmDialog(null, "File already existis! Do you want to overwrite?", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION);
        }

        if (!file.getName().contains(".map")) {
            file = new File(file.getPath() + ".map");
        }

        try {
            ObjectOutputStream pw = new ObjectOutputStream(new java.io.FileOutputStream(file));

            pw.writeObject(tiles.length);

            pw.writeObject(tiles[0].getWidth());
            pw.writeObject(tiles[0].getHeight());
            for (int i = 0; i < tiles.length; i++) {
                pw.writeObject(tiles[i].getImage().getRGB(0, 0, tiles[i].getWidth(), tiles[i].getHeight(), null, 0, tiles[i].getWidth()));
            }

            pw.writeObject(map);

            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ChangeListener.clear();
        }
    }

    private static JPanel createPanel(){
        SpringLayout layout = new SpringLayout();
        JLabel wl = new JLabel("Width:"), hl = new JLabel("Height:");
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
    
    public static Tile[] readTiles(Tile[] aux) {
        int tileSizeX = 80, tileSizeY = 80;

        if (JOptionPane.showConfirmDialog(null, createPanel(), "Tile Size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                tileSizeX = Integer.parseInt(wt.getText());
                tileSizeY = Integer.parseInt(ht.getText());
            } catch (Exception e) {

                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Try again!\nOnly integer numbers alowed!!");
                
                return aux;
            }
        } else {
            return aux;
        }

        if (fc.showOpenDialog(null) != fc.APPROVE_OPTION ) {
            return aux;
        }

        BufferedImage img;
        try {
            img = javax.imageio.ImageIO.read(fc.getSelectedFile());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Loading faled! Please try again!");
            return aux;
        }

        int w = img.getWidth(), h = img.getHeight(), tm = w / tileSizeX;
        aux = new tile.Tile[(w / tileSizeX) * (h / tileSizeY) + 1];
        aux[0] = new Tile(new BufferedImage(tileSizeX, tileSizeY, BufferedImage.TYPE_INT_ARGB));
        
        for (int i = 0; i < aux.length - 1; i++) {
            aux[i + 1] = new Tile(img.getSubimage((i % tm) * tileSizeX, (i / tm) * tileSizeY, tileSizeX, tileSizeY));
        }

        ChangeListener.clear();
        return aux;
    }
}