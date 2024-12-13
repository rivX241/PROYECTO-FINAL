//Clase hija Caballo que hereda métodos o atributos de la superclase Pieza
public class Caballo extends Pieza{
    
    //Constructor de la clase Caballo. Inicializa la pieza con el color, columna y fila en el tablero.
    public Caballo(int color,int columna,int fila){
        //Llama al constructor de la clase base Pieza para inicializar el color y la posició
        super(color,columna,fila);
        
        //Asigna pieza caballo
        tipoPieza = Tipo.CABALLO;
        
        //Da la imagen según su color
        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/caballoBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/caballoNegro");
        }
    }
    
    //Condicional para el movimiento del caballo
    public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo){
        //Movimiento dentro del tablero
        if(dentroTablero(columnaObjetivo,filaObjetivo)){
            // El caballo se mueve en forma de "L", es decir, dos cuadrados en una dirección y uno en la otra
            // Esto se verifica multiplicando las diferencias entre columna y fila
            if(Math.abs(columnaObjetivo-columnaPrevia)*Math.abs(filaObjetivo-filaPrevia) == 2){
                //Verifica que el cuadrado de destino sea válido
                if(cuadradoValido(columnaObjetivo,filaObjetivo)){
                    return true; //Valida movimiento
                }
            }
        }
        return false;//No hace que el caballo se mueva por falta de condiciones
    }
}
