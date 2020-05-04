package bsu.fpmi.artsiushkevich;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WindowController {
    static DefaultTableModel createModel() {
        Pair<Integer, Integer> pair = input();
        return createModel(pair.first, pair.second);
    }

    static DefaultTableModel createModel(int w, int h) {
        String names[] = new String[w + 1];
        names[0] = "";
        names[1] = "A";
        for (int i = 2; i <= w; i++) {
            StringBuilder builder = new StringBuilder(names[i - 1]);
            int j = builder.length() - 1;
            while ((j >= 0) && (builder.charAt(j) == 'Z')) {
                builder.setCharAt(j, 'A');
                j--;
            }
            if (j >= 0) {
                builder.setCharAt(j, (char) (builder.charAt(j) + 1));
            } else {
                builder.append('A');
            }
            names[i] = builder.toString();
        }
        sheet = new DefaultTableModel(new Object[h][w + 1], new String[w + 1]) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column > 0);
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

        };
        for (int i = 0; i < h; i++) {
            sheet.setValueAt(i + 1, i, 0);
        }
        return sheet;
    }

    private static Pair<Integer, Integer> input() {
        JTextField wField = new JTextField(5);
        JTextField hField = new JTextField(5);
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Width:"));
        inputPanel.add(wField);
        inputPanel.add(Box.createHorizontalStrut(15)); // a spacer
        inputPanel.add(new JLabel("Height:"));
        inputPanel.add(hField);
        int w = 0;
        int h = 0;
        if (JOptionPane.showConfirmDialog(null, inputPanel, "Enter width and height of table", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
            System.exit(0);
        }
        try {
            w = Integer.parseInt(wField.getText());
            h = Integer.parseInt(hField.getText());
            if ((w <= 0) || (h <= 0)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Incorrect input", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(w, h);
        return pair;
    }

    private static DefaultTableModel sheet;
}
