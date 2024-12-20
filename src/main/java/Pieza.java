import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Pieza {
    public Tipo tipoPieza;
    public BufferedImage imagen;
    public int x, y;
    public int columna,fila,columnaPrevia,filaPrevia;
    public int color;
    public Pieza chocaPieza;
    public boolean mover,dosPasos;

    public Pieza(int color, int columna,int fila) {
        this.color = color;
        this.columna = columna;
        this.fila = fila;
        x = posicionX(columna);
        y = posicionY(fila);
        columnaPrevia = columna;
        filaPrevia = fila;
    }
    public BufferedImage getImagen(String rutaImagen) {
        BufferedImage imagen = null;

        try{
            imagen = ImageIO.read(getClass().getResourceAsStream(rutaImagen + ".png"));
        }catch(IOException e){
            e.getMessage();
        }
        return imagen;
    }
    public int posicionX(int columna){
        return columna * Tablero.TAMANO_CUADRADO;
    }
    public int posicionY(int fila){
        return fila * Tablero.TAMANO_CUADRADO;
    }

    public int getColumna(int x){
        return (x + Tablero.MITAD_CUADRADO)/Tablero.TAMANO_CUADRADO;
    }
    public int getFila(int y){
        return (y + Tablero.MITAD_CUADRADO)/Tablero.TAMANO_CUADRADO;
    }
    public int getIndice(){
        for(int indice = 0; indice < GUI.sPiezas.size(); indice++){
            if(GUI.sPiezas.get(indice) == this){
                return indice;
            }
        }
        return 0;
    }
    public void actualizarPosicion(){

        //revisar captura al paso
        if(tipoPieza == Tipo.PEON){
            if(Math.abs(fila-filaPrevia) == 2){
                dosPasos = true;
            }
        }
        x= posicionX(columna);
        y= posicionY(fila);
        columnaPrevia = getColumna(x);
        filaPrevia = getFila(y);
        mover = true;
    }

    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo){
        return false;
    }

    public boolean dentroTablero(int columnaObjetivo, int filaObjetivo){
        return columnaObjetivo >= 0 && columnaObjetivo <= 7 && filaObjetivo >= 0 && filaObjetivo <= 7;
    }

    public void reiniciarPosicion(){
        columna = columnaPrevia;
        fila = filaPrevia;
        x = posicionX(columna);
        y = posicionY(fila);
    }

    public Pieza getChocaPieza(int columnaObjetivo, int filaObjetivo){
        for(Pieza pieza : GUI.sPiezas){
            if(pieza.columna == columnaObjetivo && pieza.fila == filaObjetivo && pieza != this){
                return pieza;
            }
        }
        return null;
    }

    public boolean esElMismoCuadrado(int columnaObjetivo, int filaObjetivo) {
        return columnaObjetivo == columnaPrevia && filaObjetivo == filaPrevia;
    }

    public boolean piezaEstaEnRecta(int columnaObjetivo, int filaObjetivo) {
        //Cuando esta pieza se mueve a la izquierda
        for(int c = columnaPrevia - 1; c > columnaObjetivo; c--) {
            for(Pieza pieza : GUI.sPiezas) {
                if(pieza.columna == c && pieza.fila == filaObjetivo) {
                    chocaPieza = pieza;
                    return true;
                }
            }
        }

        //Cuando esta pieza se mueve a la derecha
        for(int c = columnaPrevia + 1; c < columnaObjetivo; c++) {
            for(Pieza pieza : GUI.sPiezas) {
                if(pieza.columna == c && pieza.fila == filaObjetivo) {
                    chocaPieza = pieza;
                    return true;
                }
            }
        }
        //Cuando esta pieza se mueve a la arriba
        for(int r = filaPrevia - 1; r > filaObjetivo; r--) {
            for(Pieza pieza : GUI.sPiezas) {
                if(pieza.columna == columnaObjetivo && pieza.fila == r) {
                    chocaPieza = pieza;
                    return true;
                }
            }
        }

        //Cuando esta pieza se mueve a la arriba
        for(int r = filaPrevia + 1; r < filaObjetivo; r++) {
            for(Pieza pieza : GUI.sPiezas) {
                if(pieza.columna == columnaObjetivo && pieza.fila == r) {
                    chocaPieza = pieza;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean piezaEstaEnDiagonal(int columnaObjetivo, int filaObjetivo) {

        if(filaObjetivo < filaPrevia) {

            //Arriba izquierda
            for(int c = columnaPrevia - 1; c > columnaObjetivo; c--) {
                int diff = Math.abs(c - columnaPrevia);
                for(Pieza pieza : GUI.sPiezas) {
                    if(pieza.columna == c && pieza.fila == filaPrevia - diff){
                        chocaPieza = pieza;
                        return true;
                    }
                }
            }

            //Arriba derecha
            for(int c = columnaPrevia + 1; c < columnaObjetivo; c++) {
                int diff = Math.abs(c - columnaPrevia);
                for(Pieza pieza : GUI.sPiezas) {
                    if(pieza.columna == c && pieza.fila == filaPrevia - diff){
                        chocaPieza = pieza;
                        return true;
                    }
                }
            }
        }
        if(filaObjetivo > filaPrevia){
            //Abajo izquierda
            for(int c = columnaPrevia - 1; c > columnaObjetivo; c--) {
                int diff = Math.abs(c - columnaPrevia);
                for(Pieza pieza : GUI.sPiezas) {
                    if(pieza.columna == c && pieza.fila == filaPrevia + diff){
                        chocaPieza = pieza;
                        return true;
                    }
                }
            }
            //Abajo derecha
            for(int c = columnaPrevia + 1; c < columnaObjetivo; c++) {
                int diff = Math.abs(c - columnaPrevia);
                for(Pieza pieza : GUI.sPiezas) {
                    if(pieza.columna == c && pieza.fila == filaPrevia + diff){
                        chocaPieza = pieza;
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean cuadradoValido(int columnaObjetivo, int filaObjetivo){
        chocaPieza = getChocaPieza(columnaObjetivo, filaObjetivo);
        if(chocaPieza == null){
            return true;
        }else{
            if(chocaPieza.color != this.color){
                return true;
            }else{
                chocaPieza =null;
            }
        }
        return false;
    }

    public void dibujar(Graphics2D g2){
        g2.drawImage(imagen,x,y,Tablero.TAMANO_CUADRADO,Tablero.TAMANO_CUADRADO,null);
    }
    /*Verificar si la pieza esta en la tabla */
    public boolean enLaTabla(int columnOnj, int filaObj){
        return columnOnj >= 0 && filaObj <= 7 && filaObj >= 0;
    }
}
