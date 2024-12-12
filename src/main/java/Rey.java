public class Rey extends Pieza{

    public Rey(int color,int columna,int fila){
        super(color,columna,fila);

        if(color == GUI.BLANCO){
            imagen = getImagen("/sprites/reyBlanco");
        } else {
            imagen = getImagen("/sprites/reyNegro");
        }
    }

    /*Movimiento en vertical y horizontal */
    public boolean puedeMoverse(int columnOnj, int filaObj) {
        if(enLaTabla(columnOnj, filaObj)){
            if(Math.abs(columnOnj - columnaPrevia) + Math.abs(filaObj - filaPrevia) == 1) {
                return true;
            }
        }
        return false;
    }
}
