import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GUI extends JPanel implements Runnable{

    public static final int ANCHO = 1100;
    public static final int ALTO = 800;
    final int FPS = 60;
    Thread gameThread;
    Tablero tablero = new Tablero();
    Mouse mouse = new Mouse();
    //piezas
    public static ArrayList<Pieza> piezas = new ArrayList<>();
    public static ArrayList<Pieza> sPiezas = new ArrayList<>();
    Pieza piezaActiva;
    public static Pieza pEnroque;

    //Colores
    public static final int BLANCO = 0;
    public static final int NEGRO = 1;
    int colorActual = BLANCO;

    //posiciones válidas
    boolean moverse;
    boolean esValido;

    public GUI() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.black);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        configurarPiezas();
        copiarPiezas(piezas,sPiezas);
    }
    private void actualizar(){
        if(mouse.presionado){
            if(piezaActiva == null){

                for(Pieza pieza : sPiezas){
                    if(pieza.color == colorActual && pieza.columna == mouse.x/Tablero.TAMANO_CUADRADO &&
                        pieza.fila == mouse.y/Tablero.TAMANO_CUADRADO){
                        piezaActiva = pieza;
                    }
                }
            }else{
                simular();
            }
        }

        if(!mouse.presionado){
            if(piezaActiva != null){
                if(esValido){
                    copiarPiezas(sPiezas,piezas);
                    piezaActiva.actualizarPosicion();
                    if(pEnroque != null){
                        pEnroque.actualizarPosicion();
                    }
                    cambioJugador();
                }else{
                    copiarPiezas(piezas,sPiezas);
                    piezaActiva.reiniciarPosicion();
                    piezaActiva = null;
                }
            }
        }
    }

    private void cambioJugador(){
        if(colorActual == BLANCO){
            colorActual = NEGRO;
            //reiniciar estado de dos pasos
            for(Pieza pieza : piezas){
                if(pieza.color == NEGRO){
                    pieza.dosPasos = false;
                }
            }
        }else{
            colorActual = BLANCO;

            for(Pieza pieza : piezas){
                if(pieza.color == BLANCO){
                    pieza.dosPasos = false;
                }
            }
        }
        piezaActiva = null;
    }
    private void simular(){

         moverse = false;
         esValido = false;

         copiarPiezas(piezas,sPiezas);
         //Resetear la posicion de torres en el momento del enroque

        if(pEnroque != null){
            pEnroque.columna = pEnroque.columnaPrevia;
            pEnroque.x = pEnroque.getColumna(pEnroque.columna);
            pEnroque = null;
        }

        piezaActiva.x = mouse.x - Tablero.MITAD_CUADRADO;
        piezaActiva.y = mouse.y - Tablero.MITAD_CUADRADO;
        piezaActiva.columna = piezaActiva.getColumna(piezaActiva.x);
        piezaActiva.fila = piezaActiva.getFila(piezaActiva.y);

        if(piezaActiva.puedeMoverse(piezaActiva.columna, piezaActiva.fila)){
            moverse = true;

            if(piezaActiva.chocaPieza != null){
                 sPiezas.remove(piezaActiva.chocaPieza.getIndice());
            }

            revisarEnroque();

            esValido = true;
        }
    }

    public void lanzarJuego(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void copiarPiezas(ArrayList<Pieza> recurso,ArrayList<Pieza> objetivo){
        objetivo.clear();

        for (Pieza pieza : recurso) {
            objetivo.add(pieza);
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tablero.draw(g2);
        for(Pieza p : sPiezas){
            p.dibujar(g2);
        }
        if(piezaActiva != null){
            if(moverse){
                g2.setColor(Color.white);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
                g2.fillRect(piezaActiva.columna*Tablero.TAMANO_CUADRADO,
                        piezaActiva.fila*Tablero.TAMANO_CUADRADO
                        ,Tablero.TAMANO_CUADRADO,Tablero.TAMANO_CUADRADO);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            }
            piezaActiva.dibujar(g2);
        }

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial",Font.BOLD,35));
        g2.setColor(Color.white);

        if(colorActual == BLANCO){
            g2.drawString("Turno:Blancas",840,550);
        }else{
            g2.drawString("Turno:Negras",840,250);
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
    private void revisarEnroque(){
        if(pEnroque != null){
            if(pEnroque.columna == 0){
                pEnroque.columna += 3;
            }else if(pEnroque.columna == 7){
                pEnroque.columna -= 2;
            }
            pEnroque.x = pEnroque.posicionX(pEnroque.columna);

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
        //piezas.add(new Alfil(BLANCO,2, 7));
        //piezas.add(new Alfil(BLANCO,5,7));
        //piezas.add(new Caballo(BLANCO,1,7));
        //piezas.add(new Caballo(BLANCO,6,7));
        piezas.add(new Torre(BLANCO,0,7));
        piezas.add(new Torre(BLANCO,7,7));
        piezas.add(new Rey(BLANCO,4,7));
        //piezas.add(new Reina(BLANCO,3,7));


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
