import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Ajedrez");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GUI gp = new GUI();
        window.add(gp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.lanzarJuego();
    }
}
