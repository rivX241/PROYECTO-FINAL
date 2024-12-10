public class Torre extends Pieza{

    public Torre(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/torreBlanca");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/torreNegra");
        }
    }
}
