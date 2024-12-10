public class Caballo extends Pieza{

    public Caballo(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/caballoBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/caballoNegro");
        }
    }
}
