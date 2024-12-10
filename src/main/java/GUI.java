import java.awt.*;
import javax.swing.*;

public class GUI extends JPanel implements Runnable{

    public static final int ANCHO = 1100;
    public static final int ALTO = 800;
    final int FPS = 60;
    Thread gameThread;
    Tablero tablero = new Tablero();
    public GUI() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.black);
    }
    private void actualizar(){

    }
    public void lanzarJuego(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tablero.draw(g2);
    }

    @Override
    public void run() {
        //loop del juego
        double intervaloDibujo = 1000000000 / FPS;
        double delta = 0;
        long ultimoTiempo = System.nanoTime();
        long tiempoActual;

        while(gameThread !=null){
            tiempoActual = System.nanoTime();

            delta += (tiempoActual - ultimoTiempo)/intervaloDibujo;
            ultimoTiempo = tiempoActual;

            if(delta >= 1){
                actualizar();
                repaint();
                delta--;
            }
        }
    }
}
