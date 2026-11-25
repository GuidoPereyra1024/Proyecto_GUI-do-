import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIPincel extends JFrame {

    private MatrizColor matriz;
    private ColorRGB nuevoColor;

    private JButton[][] botonMatriz;
    private JPanel panelMenu;
    private JPanel panelMatriz;

    public GUIPincel(MatrizColor c) {
        super("Pincel de IPOO");

        matriz = c;
        nuevoColor = new ColorRGB(0, 0, 0);

        setSize(700, 600);
        setLayout(new BorderLayout());

        inicializarPanelMatriz();
        inicializarPanelMenu();

        add(panelMatriz, BorderLayout.CENTER);
        add(panelMenu, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarPanelMatriz() {
        panelMatriz = new JPanel();

        panelMatriz.setLayout(new GridLayout(
            matriz.cantFilas(),
            matriz.cantColumnas()
        ));

        botonMatriz = new JButton[
            matriz.cantFilas()
        ][
            matriz.cantColumnas()
        ];

        for (int f = 0; f < matriz.cantFilas(); f++) {
            for (int c = 0; c < matriz.cantColumnas(); c++) {
                JButton boton = new JButton();

                boton.setBackground(Color.WHITE);
                boton.setOpaque(true);
                boton.setBorderPainted(true);
                boton.addActionListener(new OyenteMatriz());

                botonMatriz[f][c] = boton;
                panelMatriz.add(boton);
            }
        }
    }

    private void inicializarPanelMenu() {
        panelMenu = new JPanel();
        panelMenu.add(new JLabel("Panel de control"));
    }

    private class OyenteMatriz implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Por ahora no hace nada.
        }
    }
}