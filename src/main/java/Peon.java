public class Peon extends Pieza{

    public Peon(int color, int columna, int fila){
        super(color, columna, fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/peonBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/peonNegro");
        }
    }
}
