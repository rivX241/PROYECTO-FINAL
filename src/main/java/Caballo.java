public class Caballo extends Pieza{

    public Caballo(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/caballoBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/caballoNegro");
        }
    }

    public boolean moverse(int columnaObjetivo,int filaObjetivo){
        if(dentroTablero(columnaObjetivo,filaObjetivo)){
            if(Math.abs(columnaObjetivo-columnaPrevia)*Math.abs(filaObjetivo-filaPrevia) == 2){
                if(cuadradoValido(columnaObjetivo,filaObjetivo)){
                    return true;
                }
            }
        }
        return false;
    }
}
