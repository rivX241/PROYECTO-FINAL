public class Rey extends Pieza{

    public Rey(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/reyBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/reyNegro");
        }
    }
}
