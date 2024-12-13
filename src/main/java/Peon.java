//Clase peon hija que hereda métodos o atributos de la superclase Pieza
public class Peon extends Pieza{
// Constructor de la clase Peón. Inicializa la pieza con el color, columna y fila en el tablero.
    public Peon(int color, int columna, int fila){
        
        super(color, columna, fila); //Llama al constructor de la superclase Pieza para inicializar los atributos comunes.


        tipoPieza = Tipo.PEON; //Selecciona pieza peón
        
        //Da imagen según su color
        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/peonBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/peonNegro");
        }
    }
    //Método que determina si el peón puede moverse a una casilla específica (columnaObjetivo, filaObjetivo)
    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo) {
        //Verifica si la casilla objetivo está dentro del tablero y no es la casilla en la que ya está el peón.
        if(dentroTablero(columnaObjetivo, filaObjetivo) && !esElMismoCuadrado(columnaObjetivo, filaObjetivo)) {
            //Definir el valor del movimiento en función de su color
            int movimientoValor;
            if(color == GUI.BLANCO){
                movimientoValor = -1;// Peón blanco se mueve hacia arriba (fila menor)
            } else {
                movimientoValor = 1;// Peón negro se mueve hacia abajo (fila menor)
            }
            
            //Verificar el choque de piezas
            chocaPieza = getChocaPieza(columnaObjetivo, filaObjetivo);

            //1 cuadrado de movimiento
            if(columnaObjetivo == columnaPrevia && filaObjetivo == filaPrevia + movimientoValor && chocaPieza == null) {
                return true;
            }

            //2 cuadrados de movimiento
            if(columnaObjetivo == columnaPrevia && filaObjetivo == filaPrevia + movimientoValor * 2 && chocaPieza == null &&
                    !mover && !piezaEstaEnRecta(columnaObjetivo, filaObjetivo)) {
                    return true;
                }
            //Movimiento diagonal y matar (si una pieza esta en un cuadrado diagonal al peon)
            if(Math.abs(columnaObjetivo - columnaPrevia) == 1 && filaObjetivo == filaPrevia + movimientoValor && chocaPieza != null &&
                chocaPieza.color != color) {
                    return true; 
            }
            //Captura al paso
            if(Math.abs(columnaObjetivo-columnaPrevia) == 1 && filaObjetivo == filaPrevia + movimientoValor){
                //Recorre todas las piezas en el tablero para ver si un peón enemigo acaba de mover 2 casillas
                for(Pieza pieza:GUI.sPiezas){
                    if(pieza.columna == columnaObjetivo && pieza.fila == filaPrevia && pieza.dosPasos){
                        chocaPieza = pieza;// Asigna la pieza enemiga que será capturada
                        return true;
                    }
                }
            }
        }
        return false; //Si ninguna de las condiciones anteriores se cumple, el movimiento no es válido
    
    }
}
