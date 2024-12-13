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
    ArrayList<Pieza> piezasPromociones = new ArrayList<>();

    Pieza piezaActiva,pJaque;
    public static Pieza pEnroque;

    //Colores
    public static final int BLANCO = 0;
    public static final int NEGRO = 1;
    int colorActual = BLANCO;

    //posiciones válidas
    boolean moverse;
    boolean esValido;
    boolean promover;
    boolean tablas;
    boolean juegoTerminado;


    public GUI() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.black);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        configurarPiezas();
        copiarPiezas(piezas,sPiezas);
    }
    private void actualizar(){

        if(promover){
            promocion();
        }else if(!juegoTerminado && !tablas){
            //Selección de la pieza activa según el clic del ratón
            if(mouse.presionado){
                if(piezaActiva == null){

                    for(Pieza pieza : sPiezas){
                        if(pieza.color == colorActual && pieza.columna == mouse.x/Tablero.TAMANO_CUADRADO &&
                                pieza.fila == mouse.y/Tablero.TAMANO_CUADRADO){
                            piezaActiva = pieza;//Activar pieza seleccionada
                        }
                    }
                }else{
                    //Simula el movimiento de la pieza activa
                    simular();
                }
            }
            //Si el movimiento es válido, actualiza la posición de las piezas
            if(!mouse.presionado){
                if(piezaActiva != null){
                    if(esValido){
                        copiarPiezas(sPiezas,piezas);
                        piezaActiva.actualizarPosicion();
                        if(pEnroque != null){
                            pEnroque.actualizarPosicion();
                        }
                        if(reyEnJaque() && esJaqueMate()){
                            juegoTerminado = true;
                        }else if(tablas && !reyEnJaque()){
                            tablas = true;
                        }else{
                            if(promoverPieza()){
                                promover = true;
                            }else{
                                cambioJugador();
                            }
                        }

                    }else{
                        //Si el movimiento no es válido, restablece la posición original
                        copiarPiezas(piezas,sPiezas);
                        piezaActiva.reiniciarPosicion();
                        piezaActiva = null;
                    }
                }
            }
        }

    }

    private void cambioJugador(){
        if(colorActual == BLANCO){
            colorActual = NEGRO; //Cambia al turno de las piezas negras
             //Reinicia el estado de las piezas blancas
            for(Pieza pieza : piezas){
                if(pieza.color == NEGRO){
                    pieza.dosPasos = false; //Restablece el flag de movimiento de dos pasos para las piezas negras
                }
            }
        }else{
            colorActual = BLANCO;//Cambia al turno de las piezas blancas

            for(Pieza pieza : piezas){
                if(pieza.color == BLANCO){
                    pieza.dosPasos = false; // Restablece el flag de movimiento de dos pasos para las piezas blancas
                }
            }
        }
        piezaActiva = null;//Resetea la pieza activa
    }
    private void simular(){

         moverse = false;
         esValido = false;

         copiarPiezas(piezas,sPiezas);//Copia el estado actual de las piezas
         //Resetear la posicion de torres en el momento del enroque

        if(pEnroque != null){
            pEnroque.columna = pEnroque.columnaPrevia;
            pEnroque.x = pEnroque.getColumna(pEnroque.columna);
            pEnroque = null;//Termina el enroque
        }
        //Actualiza la posición de la pieza activa a la nueva posición del ratón
        piezaActiva.x = mouse.x - Tablero.MITAD_CUADRADO;
        piezaActiva.y = mouse.y - Tablero.MITAD_CUADRADO;
        piezaActiva.columna = piezaActiva.getColumna(piezaActiva.x);
        piezaActiva.fila = piezaActiva.getFila(piezaActiva.y);
        
        //Verifica si el movimiento es válido
        if(piezaActiva.puedeMoverse(piezaActiva.columna, piezaActiva.fila)){
            moverse = true;

            if(piezaActiva.chocaPieza != null){
                 sPiezas.remove(piezaActiva.chocaPieza.getIndice());    //Elimina la pieza capturada
            }

            revisarEnroque();   //Verifica si se puede hacer enroque
            if(!esIlegal(piezaActiva) && !oponentePuedeCapturarRey()){  //Verifica si el rey hace un movimiento ilegal
                esValido = true;   //El movimiento es válido
            }
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

    private boolean esJaqueMate(){

        Pieza rey = getRey(true);
        if(reyPuedeMoverse(rey)){
            return false;
        }else{
            int columnaDiferencia = Math.abs(pJaque.columna - rey.columna);
            int filaDiferencia = Math.abs(pJaque.fila - rey.fila);

            if(columnaDiferencia == 0){
                if(pJaque.fila < rey.fila){
                    for(int fila = pJaque.fila;fila < rey.fila;fila++){
                        for(Pieza pieza : sPiezas){
                            if(pieza != rey && pieza.color != colorActual && pieza.puedeMoverse(pJaque.columna,fila)){
                                return false;
                            }
                        }
                    }
                }
                if(pJaque.fila > rey.fila){
                    for(int fila = pJaque.fila;fila > rey.fila;fila--){
                        for(Pieza pieza : sPiezas){
                            if(pieza != rey && pieza.color != colorActual && pieza.puedeMoverse(pJaque.columna,fila)){
                                return false;
                            }
                        }
                    }
                }
            } else if (filaDiferencia == 0) {
                if(pJaque.columna < rey.columna){
                    for(int columna = pJaque.columna;columna < rey.columna;columna++){
                        for(Pieza pieza : sPiezas){
                            if(pieza != rey && pieza.color != colorActual && pieza.puedeMoverse(columna,pJaque.fila)){
                                return false;
                            }
                        }
                    }
                }if(pJaque.columna > rey.columna){
                    for(int columna = pJaque.columna;columna > rey.columna;columna--){
                        for(Pieza pieza : sPiezas){
                            if(pieza != rey && pieza.color != colorActual && pieza.puedeMoverse(columna,pJaque.fila)){
                                return false;
                            }
                        }
                    }
                }
            } else if (columnaDiferencia == filaDiferencia) {
                if(pJaque.fila < rey.fila){
                    if(pJaque.columna < rey.columna){
                        for(int columna = pJaque.columna, fila = pJaque.fila;columna < rey.columna;columna++,fila++){
                            for(Pieza pieza : sPiezas){
                                if(pieza != rey && pieza.color != colorActual && pieza.puedeMoverse(columna,fila)){
                                    return false;
                                }
                            }
                        }
                    }
                    if(pJaque.columna > rey.columna){
                        for(int columna = pJaque.columna, fila = pJaque.fila;columna > rey.columna;columna--,fila++){
                            for(Pieza pieza : sPiezas){
                                if(pieza != rey && pieza.color != colorActual && pieza.puedeMoverse(columna,fila)){
                                    return false;
                                }
                            }
                        }
                    }
                }
                if(pJaque.fila > rey.fila){
                    if(pJaque.columna < rey.columna){
                        for(int columna = pJaque.columna, fila = pJaque.fila;columna < rey.columna;columna++,fila--){
                            for(Pieza pieza : sPiezas){
                                if(pieza != rey && pieza.color != colorActual && pieza.puedeMoverse(columna,fila)){
                                    return false;
                                }
                            }
                        }
                    }
                    if(pJaque.columna > rey.columna){
                        for(int columna = pJaque.columna, fila = pJaque.fila;columna > rey.columna;columna--,fila--){
                            for(Pieza pieza : sPiezas){
                                if(pieza != rey && pieza.color != colorActual && pieza.puedeMoverse(columna,fila)){
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private boolean reyPuedeMoverse(Pieza rey){
        if(esMovimientoValido(rey,-1,-1)||esMovimientoValido(rey,0,-1)||
                esMovimientoValido(rey,1,-1)||esMovimientoValido(rey,-1,0)
                ||esMovimientoValido(rey,1,0)||esMovimientoValido(rey,-1,1)||
                esMovimientoValido(rey,0,1)||esMovimientoValido(rey,1,1)){
            return true;
        }
        return false;
    }
    private boolean esMovimientoValido(Pieza rey,int columnaExtra,int filaExtra){
        boolean esMovimientoValido = false;

        //Actualiza un segundo la posicion del rey
        rey.columna += columnaExtra;
        rey.fila += filaExtra;

        if(rey.puedeMoverse(rey.columna,rey.fila)){
            if(rey.chocaPieza != null){
                sPiezas.remove(rey.chocaPieza.getIndice());
            }
            if(!esIlegal(rey)){
                esMovimientoValido = true;
            }
        }

        rey.reiniciarPosicion();
        copiarPiezas(piezas,sPiezas);

        return esMovimientoValido;
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
                if(esIlegal(piezaActiva) || oponentePuedeCapturarRey()){
                    g2.setColor(Color.gray);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
                    g2.fillRect(piezaActiva.columna*Tablero.TAMANO_CUADRADO,
                            piezaActiva.fila*Tablero.TAMANO_CUADRADO
                            ,Tablero.TAMANO_CUADRADO,Tablero.TAMANO_CUADRADO);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }else{
                    g2.setColor(Color.white);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
                    g2.fillRect(piezaActiva.columna*Tablero.TAMANO_CUADRADO,
                            piezaActiva.fila*Tablero.TAMANO_CUADRADO
                            ,Tablero.TAMANO_CUADRADO,Tablero.TAMANO_CUADRADO);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }


            }
            piezaActiva.dibujar(g2);
        }

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial",Font.BOLD,35));
        g2.setColor(Color.white);

        if(promover){
            g2.drawString("Promover a:", 840,150);
            for(Pieza pieza : piezasPromociones){
                g2.drawImage(pieza.imagen,pieza.posicionX(pieza.columna),pieza.posicionY(pieza.fila),
                        Tablero.TAMANO_CUADRADO,Tablero.TAMANO_CUADRADO,null);
            }
        }else{
            if(colorActual == BLANCO){
                g2.drawString("Turno:Blancas",840,550);
                if(pJaque != null && pJaque.color == NEGRO){
                    g2.setColor(Color.red);
                    g2.drawString("Rey",840,640);
                    g2.drawString("en Jaque",840,700);
                }
            }else{
                g2.drawString("Turno:Negras",840,250);
                if(pJaque != null && pJaque.color == BLANCO){
                    g2.setColor(Color.red);
                    g2.drawString("Rey",840,110);
                    g2.drawString("en Jaque",840,160);
                }
            }
        }
        if(juegoTerminado){
            String s = "";
            if(colorActual == BLANCO){
                s = "Blancas ganan";
            }else{
                s = "Negras ganan";
            }
            g2.setFont(new Font("Arial",Font.BOLD,100));
            g2.setColor(Color.blue);

            g2.drawString(s,200,430);
        }
        if (tablas) {
            g2.setFont(new Font("Arial",Font.BOLD,100));
            g2.setColor(Color.lightGray);
            g2.drawString("Tablas",200,430);
        }
    }

    private void promocion(){
        if(mouse.presionado){
            for(Pieza pieza : piezasPromociones){
                if(pieza.columna == mouse.x/Tablero.TAMANO_CUADRADO && pieza.fila == mouse.y/Tablero.TAMANO_CUADRADO){
                    switch (pieza.tipoPieza){
                        case TORRE: sPiezas.add(new Torre(colorActual,piezaActiva.columna,piezaActiva.fila)); break;
                        case CABALLO: sPiezas.add(new Caballo(colorActual,piezaActiva.columna,piezaActiva.fila)); break;
                        case ALFIL: sPiezas.add(new Alfil(colorActual,piezaActiva.columna,piezaActiva.fila)); break;
                         case REINA: sPiezas.add(new Reina(colorActual,piezaActiva.columna,piezaActiva.fila)); break;
                        default: break;
                    }
                    sPiezas.remove(piezaActiva.getIndice());
                    copiarPiezas(sPiezas,piezas);
                    piezaActiva = null;
                    promover = false;
                    cambioJugador();
                }
            }
        }
    }

    private boolean esIlegal(Pieza rey){
        if(rey.tipoPieza == Tipo.REY){
            for(Pieza pieza : sPiezas){
                if(pieza != rey && pieza.color != rey.color && pieza.puedeMoverse(rey.columna,rey.fila)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean oponentePuedeCapturarRey(){

        Pieza rey = getRey(false);

        for(Pieza pieza : sPiezas){
            if(pieza.color != rey.color && pieza.puedeMoverse(rey.columna,rey.fila)){
                return true;
            }
        }
        return false;
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

    private boolean promoverPieza(){
        if(piezaActiva.tipoPieza == Tipo.PEON){
            if(colorActual == BLANCO && piezaActiva.fila == 0 || colorActual == NEGRO && piezaActiva.fila == 7){
                piezasPromociones.clear();
                piezasPromociones.add(new Torre(colorActual,9,2));
                piezasPromociones.add(new Caballo(colorActual,9,3));
                piezasPromociones.add(new Alfil(colorActual,9,4));
                piezasPromociones.add(new Reina(colorActual,9,5));
                return true;
            }
        }
        return false;
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

    private boolean reyEnJaque(){
        Pieza rey = getRey(true);

        if(piezaActiva.puedeMoverse(rey.columna,rey.fila)){
            pJaque = piezaActiva;
            return true;
        }else{
            pJaque = null;
        }

        return false;
    }

    private Pieza getRey(boolean rival){
        Pieza rey = null;

        for(Pieza pieza :sPiezas){
            if(rival){
                if(pieza.tipoPieza == Tipo.REY && pieza.color != colorActual){
                    rey = pieza;
                }
            }else{
                if(pieza.tipoPieza == Tipo.REY && pieza.color == colorActual){
                    rey = pieza;
                }
            }
        }
        return rey;
    }

    private boolean tablas(){
        int count = 0;

        for(Pieza pieza : sPiezas){
            if(pieza.color != colorActual){
                count++;
            }
        }

        if(count == 1){
            if(!reyPuedeMoverse(getRey(true))){
                return true;
            }
        }
        return false;
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
        piezas.add(new Rey(BLANCO,4,7));
        piezas.add(new Reina(BLANCO,3,7));


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
