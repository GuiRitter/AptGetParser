package io.github.guiritter.apt_get_parser;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import static java.awt.datatransfer.DataFlavor.stringFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("CallToPrintStackTrace")
public class AptGetParser {

    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    private static Transferable contents;

    private static String fieldArray[];

    private static int i;

    private static String input;

    private static String lineArray[];

    private static final String linesL[] = new String[3];

    private static int LxxI;
    private static int LxyI;
    private static int LxzI;
    private static int LyyI;
    private static int LyzI;

    private static final StringBuilder outputBuilder = new StringBuilder();

    private static StringSelection stringSelection;

    static {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("apt-get Parser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        final JTextArea outputArea = new JTextArea(8, 16);
        outputArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane outputPane = new JScrollPane(outputArea);
        outputPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton("do it");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener((ActionEvent e) -> {

            contents = clipboard.getContents(null);
            if ((contents != null) && contents.isDataFlavorSupported(stringFlavor)) {
                try {
                    input = (String) contents.getTransferData(stringFlavor);
                } catch (UnsupportedFlavorException | IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Could not get clipboard data.", "Error", JOptionPane.ERROR_MESSAGE);
                    input = outputArea.getText();
                }
            }
            lineArray = input.split("\n");
            for (String line : lineArray) {
                outputBuilder.append(line.split(" - ")[0]).append(" ");
            }
            stringSelection = new StringSelection(outputBuilder.toString());
            clipboard.setContents(stringSelection, null);
            outputArea.setText(outputBuilder.toString());
        });

        frame.getContentPane().add(button);

        frame.getContentPane().add(outputPane);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
