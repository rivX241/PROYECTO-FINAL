public class Alfil extends Pieza{

    public Alfil(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/alfilBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/alfilNegro");
        }
    }
}
