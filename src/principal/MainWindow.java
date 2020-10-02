package principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame implements java.awt.event.WindowListener {
    private static Panel panel = new Panel();

    public MainWindow(String title) {
        super(title);

        this.addWindowListener(this);
    }

    @Override public void windowClosing(WindowEvent e) {
        panel.exit();
    }

    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}

    public static void main(String[] args) {
        MainWindow frame = new MainWindow("Tile Editor - Vs. Alpha 0.2");
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        
        JMenuItem newMap = new JMenuItem("New map");
        JMenuItem saveMap = new JMenuItem("Save map");
        JMenuItem loadMap = new JMenuItem("Load map");
        JMenuItem addTile = new JMenuItem("Load tile set");
        JMenuItem exit = new JMenuItem("Exit");

        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");

        newMap.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                panel.newMap();
            }
        });

        saveMap.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                panel.saveMap();
            }
        });

        loadMap.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                panel.loadMap();
            }
        });

        addTile.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                panel.openTile();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                panel.exit();
            }
        });

        undo.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                panel.undo();
            }
        });

        redo.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                panel.redo();
            }
        });

        file.add(newMap);
        file.add(saveMap);
        file.add(loadMap);
        file.addSeparator();
        file.add(addTile);
        file.addSeparator();
        file.add(exit);

        edit.add(undo);
        edit.add(redo);

        menuBar.add(file);
        menuBar.add(edit);

        frame.setJMenuBar(menuBar);
        frame.add(panel);

        frame.pack();

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}