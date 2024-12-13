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
        public boolean puedeMoverse(int columnaObjetivo, int filaObjetivo) {
            if(dentroTablero(columnaObjetivo,filaObjetivo)){
                if(Math.abs(columnaObjetivo-columnaPrevia)+ Math.abs(filaObjetivo-filaPrevia) == 1
                    || Math.abs(columnaObjetivo-columnaPrevia) * Math.abs(filaObjetivo-filaPrevia) == 1){
                    if(cuadradoValido(columnaObjetivo,filaObjetivo)){
                        return true;
                    }
                }

                if(!mover){
                    //Enroque derecho
                    if(columnaObjetivo ==columnaPrevia+2 && filaObjetivo == filaPrevia && !piezaEstaEnRecta(columnaObjetivo, filaObjetivo)){
                        for(Pieza pieza: GUI.sPiezas){
                            if(pieza.columna == columnaPrevia+3 && pieza.fila == filaPrevia && !pieza.mover){
                                GUI.pEnroque = pieza;
                                return true;
                            }
                        }
                    }
                    //Enroque izquierdo
                    if(columnaObjetivo ==columnaPrevia-2 && filaObjetivo == filaPrevia && !piezaEstaEnRecta(columnaObjetivo, filaObjetivo)){
                        Pieza[] p = new Pieza[2];
                        for(Pieza pieza: GUI.sPiezas){
                            if(pieza.columna == columnaPrevia-3 && pieza.fila == filaPrevia){
                                p[0] = pieza;
                            }
                            if(pieza.columna == columnaPrevia-4 && pieza.fila == filaPrevia){
                                p[1] = pieza;
                            }
                            if(p[0] == null && p[1] != null && !p[1].mover){
                                GUI.pEnroque = p[1];
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
