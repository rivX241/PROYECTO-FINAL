import java.awt.*;

public class Tablero {
    final int MAX_COLUMNAS = 8;
    final int MAX_FILAS = 8;
    public static final int TAMANO_CUADRADO = 100;
    public static final int MITAD_CUADRADO = 50;

    public void draw(Graphics2D g2){

        int color = 0;

        for(int fila = 0; fila < MAX_FILAS; fila++){
            for(int columna = 0; columna < MAX_COLUMNAS; columna++){
                if(color == 0){
                    g2.setColor(new Color(177,228,185));
                    color = 1;
                }else{
                    g2.setColor(new Color(112,162,163));
                    color = 0;
                }
                g2.fillRect(columna*TAMANO_CUADRADO,fila*TAMANO_CUADRADO,TAMANO_CUADRADO,TAMANO_CUADRADO);
            }
            if(color == 1){
                color = 0;
            }else{
                color = 1;
            }
        }
    }
}
