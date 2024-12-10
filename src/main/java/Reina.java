public class Reina extends Pieza{

    public Reina(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/reinaBlanca");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/reinaNegra");
        }
    }
}
