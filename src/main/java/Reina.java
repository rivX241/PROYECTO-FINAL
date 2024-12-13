public class Reina extends Pieza{

    public Reina(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/reinaBlanca");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/reinaNegra");
        }
    }
    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo) {
        if(dentroTablero(columnaObjetivo, filaObjetivo) && !esElMismoCuadrado(columnaObjetivo, filaObjetivo)) {

            //Vertical y horizontal}
            if(columnaObjetivo == columnaPrevia || filaObjetivo == filaPrevia) {
                if(cuadradoValido(columnaObjetivo, filaObjetivo) && !piezaEstaEnRecta(columnaObjetivo, filaObjetivo)) {
                    return true;
                }
            }
            

            //Diagonal
            if(Math.abs(columnaObjetivo - columnaPrevia) == Math.abs(filaObjetivo - filaPrevia)) {
                if(cuadradoValido(columnaObjetivo, filaObjetivo) && !piezaEstaEnDiagonal(columnaObjetivo, filaObjetivo)) {
                    return true;
                }
            }
        }
        return false;
    }
}
