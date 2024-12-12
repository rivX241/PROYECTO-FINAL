import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Pieza {
    public BufferedImage imagen;
    public int x, y;
    public int columna,fila,columnaPrevia,filaPrevia;
    public int color;

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
