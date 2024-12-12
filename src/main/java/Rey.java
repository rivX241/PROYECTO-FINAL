public class Rey extends Pieza{

    public Rey(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/reyBlanco");
        }else if(color == GUI.NEGRO){
            imagen = getImagen("/sprites/reyNegro");
        }
    }

    @Override
    public boolean moverse(int columnaObjetivo, int filaObjetivo) {
        if(dentroTablero(columnaObjetivo,filaObjetivo)){
            if(Math.abs(columnaObjetivo-columnaPrevia)+ Math.abs(filaObjetivo-filaPrevia) == 1
                || Math.abs(columnaObjetivo-columnaPrevia) * Math.abs(filaObjetivo-filaPrevia) == 1){
                if(cuadradoValido(columnaObjetivo,filaObjetivo)){
                    return true;
                }
            }
        }
        return false;
    }
}
