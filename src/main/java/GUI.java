import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GUI extends JPanel implements Runnable{

    public static final int ANCHO = 1100;
    public static final int ALTO = 800;
    final int FPS = 60;
    Thread gameThread;
    Tablero tablero = new Tablero();

    //piezas
    public static ArrayList<Pieza> piezas = new ArrayList<>();
    public static ArrayList<Pieza> sPiezas = new ArrayList<>();

    //Colores
    public static final int BLANCO = 0;
    public static final int NEGRO = 1;
    int colorActual = BLANCO;


    public GUI() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.black);

        configurarPiezas();
        copiarPiezas(piezas,sPiezas);
    }
    private void actualizar(){

    }
    public void lanzarJuego(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    private void copiarPiezas(ArrayList<Pieza> recurso,ArrayList<Pieza> objetivo){
        objetivo.clear();

        for(int i = 0; i < recurso.size(); i++){
            objetivo.add(recurso.get(i));
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tablero.draw(g2);
        for(Pieza p : sPiezas){
            p.dibujar(g2);
        }
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
    public void configurarPiezas() {
        piezas.add(new Peon(BLANCO, 0, 6));
        piezas.add(new Peon(BLANCO, 1, 6));
        piezas.add(new Peon(BLANCO, 2, 6));
        piezas.add(new Peon(BLANCO, 3, 6));
        piezas.add(new Peon(BLANCO, 4, 6));
        piezas.add(new Peon(BLANCO, 5, 6));
        piezas.add(new Peon(BLANCO, 6, 6));
        piezas.add(new Peon(BLANCO, 7, 6));
        piezas.add(new Alfil(BLANCO,2, 7));
        piezas.add(new Alfil(BLANCO,5,7));
        piezas.add(new Caballo(BLANCO,1,7));
        piezas.add(new Caballo(BLANCO,6,7));
        piezas.add(new Torre(BLANCO,0,7));
        piezas.add(new Torre(BLANCO,7,7));
        piezas.add(new Rey(BLANCO,3,7));
        piezas.add(new Reina(BLANCO,4,7));


        piezas.add(new Peon(NEGRO,0,1));
        piezas.add(new Peon(NEGRO,1,1));
        piezas.add(new Peon(NEGRO,2,1));
        piezas.add(new Peon(NEGRO,3,1));
        piezas.add(new Peon(NEGRO,4,1));
        piezas.add(new Peon(NEGRO,5,1));
        piezas.add(new Peon(NEGRO,6,1));
        piezas.add(new Peon(NEGRO,7,1));
        piezas.add(new Alfil(NEGRO,2,0));
        piezas.add(new Alfil(NEGRO,5,0));
        piezas.add(new Caballo(NEGRO,1,0));
        piezas.add(new Caballo(NEGRO,6,0));
        piezas.add(new Torre(NEGRO,0,0));
        piezas.add(new Torre(NEGRO,7,0));
        piezas.add(new Rey(NEGRO,4,0));
        piezas.add(new Reina(NEGRO,3,0));
    }
}
