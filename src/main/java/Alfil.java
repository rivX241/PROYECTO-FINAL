public class Alfil extends Pieza{
    
    //Clase que representa la pieza Alfil
    public Alfil(int color,int columna,int fila){
        //Llama al constructor de la clase base Pieza para inicializar el color y la posición
        super(color,columna,fila);
        
        //Asigna el tipo de pieza (Alfil)
        tipoPieza = Tipo.ALFIL;
        //Asigna la imagen del alfil según su color
        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/alfilBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/alfilNegro");
        }
    }
    //Método condicional de movimiento de alfil
    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo) {
        //Verifica que el movimiento esté dentro del tablero y que no sea al mismo cuadrado
        if(dentroTablero(columnaObjetivo, filaObjetivo) && esElMismoCuadrado(columnaObjetivo, filaObjetivo) == false) {
            //Verifica que el movimiento esté dentro del tablero y que no sea al mismo cuadrado
            if(Math.abs(columnaObjetivo - columnaPrevia) == Math.abs(filaObjetivo - filaPrevia)) {
                //Verifica que el cuadrado esté vacío y que no haya piezas bloqueando la diagonal
                if(cuadradoValido(columnaObjetivo, filaObjetivo) && piezaEstaEnDiagonal(columnaObjetivo, filaObjetivo) == false) {
                    return true; //Verifica validez del movimiento
                }
            }
        }
        //Si alguna condición no se cumple, el movimiento no es válido
        return false;
    }
}
