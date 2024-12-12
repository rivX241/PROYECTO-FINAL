import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Pieza {
    public BufferedImage imagen;
    public int x, y;
    public int columna,fila,columnaPrevia,filaPrevia;
    public int color;
    public Pieza chocaPieza;

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

        x= posicionX(columna);
        y= posicionY(fila);
        columnaPrevia = getColumna(x);
        filaPrevia = getFila(y);
    }

    public boolean moverse(int columnaObjetivo, int filaObjetivo){
        return false;
    }

    public boolean dentroTablero(int columnaObjetivo, int filaObjetivo){
        if(columnaObjetivo >= 0 && columnaObjetivo <=7 && filaObjetivo >= 0 && filaObjetivo <=7){
            return true;
        }
        return false;
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
        if(filaObjetivo == columnaPrevia && filaObjetivo == filaPrevia) {
            return true;
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
        if(columnOnj >= 0 && filaObj <= 7 && filaObj >= 0 && filaObj <= 7){
            return true;
        }
        return false;
    }
}
