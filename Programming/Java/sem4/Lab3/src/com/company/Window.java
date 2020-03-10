package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

public class Window extends JFrame {
    static class Country {
        public Country(String name, ImageIcon flag) {
            this.name = name;
            this.flag = flag;
        }

        @Override
        public String toString() {
            return name;
        }

        public ImageIcon getFlag() {
            return flag;
        }

        public String name;
        public ImageIcon flag;
    }

    class ListCountryRenderer extends JLabel implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Country entry = (Country) value;
            setText(value.toString());
            setIcon(entry.getFlag());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }

    Window() {
        this.setBounds(300, 100, 900, 600);
        this.setTitle("Lab3");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        DefaultListModel<Country> countriesModel = new DefaultListModel<>();
        countriesModel.add(0, new Country("Bhutan", new ImageIcon(Toolkit.getDefaultToolkit().getImage("Flags/flag_bhutan.png"))));
        countriesModel.add(1, new Country("Canada", new ImageIcon(Toolkit.getDefaultToolkit().getImage("Flags/flag_canada.png"))));
        countriesModel.add(2, new Country("Iceland", new ImageIcon(Toolkit.getDefaultToolkit().getImage("Flags/flag_iceland.png"))));
        countriesModel.add(3, new Country("Monaco", new ImageIcon(Toolkit.getDefaultToolkit().getImage("Flags/flag_monaco.png"))));
        countriesModel.add(4, new Country("Switzerland", new ImageIcon(Toolkit.getDefaultToolkit().getImage("Flags/flag_switzerland.png"))));
        countriesModel.add(5, new Country("Tuvalu", new ImageIcon(Toolkit.getDefaultToolkit().getImage("Flags/flag_tuvalu.png"))));
        JList<Country> countries = new JList<>(countriesModel);
        countries.setCellRenderer(new ListCountryRenderer());
        HashMap<String, String> capitalMap = new HashMap<>();
        capitalMap.put("Bhutan", "Thimphu");
        capitalMap.put("Canada", "Ottawa");
        capitalMap.put("Iceland", "Reykjavik");
        capitalMap.put("Monaco", "Monaco-Ville");
        capitalMap.put("Switzerland", "Bern");
        capitalMap.put("Tuvalu", "Funafuti");
        JLabel chosen = new JLabel("");
        countries.addListSelectionListener(e -> {
            chosen.setIcon(countries.getSelectedValue().getFlag());
            chosen.setText(countries.getSelectedValue().toString() + " " + capitalMap.get(countries.getSelectedValue().toString()));
        });
        panel1.add(chosen, BorderLayout.SOUTH);
        panel1.add(countries, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        TreeMap<String, ImageIcon> flagMap = new TreeMap<>();
        File flagFolder = new File("Flags");
        File[] flags = flagFolder.listFiles();
        for (File flag : flags) {
            StringTokenizer nameTokens = new StringTokenizer(flag.getName(), "_.");
            StringBuilder name = new StringBuilder();
            nameTokens.nextToken();
            while (nameTokens.countTokens() != 1) {
                name.append(nameTokens.nextToken());
            }
            name.setCharAt(0, Character.toUpperCase(name.charAt(0)));
            flagMap.put(name.toString(), new ImageIcon(flag.getAbsolutePath()));
        }
        String[] columns = {"Flag", "Info", "Price", "Pick", "Final cost"};
        Object[][] rows = {{flagMap.get("Italy"), "Great fountains of Rome tour", 800, false},
                {flagMap.get("Japan"), "100 views of Fuiji", 950, false},
                {flagMap.get("Australia"), "Australian deserts", 700, false},
                {null, null, null, true, 0}};
        DefaultTableModel tourModel = new DefaultTableModel(rows, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return ImageIcon.class;
                    case 1:
                        return String.class;
                    case 2:
                    case 4:
                        return Integer.class;
                    case 3:
                        return Boolean.class;
                    default:
                        return Object.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                if (row == this.getRowCount() - 1) {
                    return false;
                }
                switch (column) {
                    case 1:
                    case 2:
                    case 3:
                        return true;
                    default:
                        return false;
                }
            }
        };
        JTable tourTable = new JTable(tourModel);
        tourTable.setRowHeight(150);
        tourTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tourModel.addTableModelListener(e -> {
            if ((e.getColumn() == 3) || (e.getColumn() == 2)) {
                int ans = 0;
                for (int i = 0; i < tourModel.getRowCount() - 1; i++) {
                    if ((boolean) tourModel.getValueAt(i, 3)) {
                        ans += (int) tourModel.getValueAt(i, 2);
                    }
                }
                tourModel.setValueAt(ans, tourModel.getRowCount() - 1, 4);
            }
        });
        JButton add = new JButton("Add new tour");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Object country = JOptionPane.showInputDialog(Window.this, "Choose country", "Tour input", JOptionPane.QUESTION_MESSAGE, new ImageIcon(), flagMap.keySet().toArray(), flagMap.keySet().toArray()[0]);
                    if (country != null) {
                        String description = JOptionPane.showInputDialog(Window.this, "Input tour description");
                        int cost = Integer.parseInt(JOptionPane.showInputDialog(Window.this, "Input tour cost"));
                        int saved = (int) tourModel.getValueAt(tourModel.getRowCount() - 1, 4);
                        tourModel.removeRow(tourModel.getRowCount() - 1);
                        Object[] newRow = {flagMap.get(country.toString()), description, cost, false};
                        tourModel.addRow(newRow);
                        newRow = new Object[]{null, null, null, null, saved};
                        tourModel.addRow(newRow);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Window.this, "Cost must be integer", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(tourTable);
        panel2.add(scrollPane, BorderLayout.CENTER);
        panel2.add(add, BorderLayout.SOUTH);
        Container container = this.getContentPane();
        tabbedPane.add(panel1, "Task 1");
        tabbedPane.add(panel2, "Task 2");
        container.add(tabbedPane);
    }
}
