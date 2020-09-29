package principal;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Painel  extends javax.swing.JPanel implements java.awt.event.MouseListener, java.awt.event.KeyListener{
    private static screen.Menu menu;

    public Painel() {
        this.setPreferredSize(new java.awt.Dimension(800, 800));

        this.setFocusable(true);
        this.requestFocus();

        this.addMouseListener(this);
        this.addKeyListener(this);

        this.menu = new screen.Menu();
    }

    @Override
    public void paint(java.awt.Graphics g) {
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());

        this.menu.update();
        this.menu.draw(g);

        this.repaint();
    }

    public void openTile(){
        menu.openTile();
    }

    public void saveMap(){
        menu.saveMap();
    }

    public void newMap(){
        menu.newMap();
    }

    public void loadMap(){
        menu.loadMap();
    }

    public void exit(){
        menu.exit();
    }

    public void undo() {
        menu.undo();
    }

    public void redo() {
        menu.redo();
    }

    public void keyReleased(KeyEvent e) {
        menu.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S && e.getModifiers() == KeyEvent.CTRL_MASK){
            System.out.println("Salvando...");
            this.saveMap();
        }else{
            menu.keyPressed(e);
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            this.menu.mouseClick(e.getX(), e.getY());
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}