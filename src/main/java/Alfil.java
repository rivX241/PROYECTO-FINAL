public class Alfil extends Pieza{

    public Alfil(int color,int columna,int fila){
        super(color,columna,fila);

        tipoPieza = Tipo.ALFIL;

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/alfilBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/alfilNegro");
        }
    }
    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo) {
        if(dentroTablero(columnaObjetivo, filaObjetivo) && esElMismoCuadrado(columnaObjetivo, filaObjetivo) == false) {
            if(Math.abs(columnaObjetivo - columnaPrevia) == Math.abs(filaObjetivo - filaPrevia)) {
                if(cuadradoValido(columnaObjetivo, filaObjetivo) && piezaEstaEnDiagonal(columnaObjetivo, filaObjetivo) == false) {
                    return true;
                }
            }
        }
        return false;
    }
}
