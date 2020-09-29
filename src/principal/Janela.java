package principal;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Janela extends JFrame implements java.awt.event.WindowListener{
    private static Painel painel = new Painel();
    public Janela(String s){
        super(s);

        this.addWindowListener(this);
    }

    public void windowClosing(WindowEvent e){
        painel.exit();
    }

    public void windowClosed(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowOpened(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowIconified(WindowEvent e){}

    public static void main(String args[]) {
        Janela janela = new Janela("Editor!");
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

        newMap.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                painel.newMap();
            }
        });

        saveMap.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                painel.saveMap();
            }
        });

        loadMap.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                painel.loadMap();
            }
        });

        addTile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                painel.openTile();
            }
        });

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                painel.exit();
            }
        });

        undo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                painel.undo();
            }
        });

        redo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                painel.redo();
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

        janela.setJMenuBar(menuBar);
        janela.add(painel);

        janela.pack();

        janela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}