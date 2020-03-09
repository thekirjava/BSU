package com.company;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.StringTokenizer;

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
        // this.setResizable(false);
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
        panel1.add(chosen, BorderLayout.EAST);
        panel1.add(countries, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        HashMap<String, ImageIcon> flagMap = new HashMap<>();
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
        String[] columns = {"Flag", "Picture", "Info", "Price", "Pick", "Final cost"};
        Object[][] rows = {{flagMap.get("Italy"), new ImageIcon("trevi.jpg"), "Great fountains of Rome tour", 800, false},
                {flagMap.get("Japan"), new ImageIcon("fuji.jpg"), "Journey through Hokusai works", 950, false},
                {flagMap.get("Australia"), new ImageIcon("uluru.jpg"), "Australian deserts", 700, false},
                {null, null, null, null, true, 0}};
        DefaultTableModel tourModel = new DefaultTableModel(rows, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 1:
                        return ImageIcon.class;
                    case 2:
                        return String.class;
                    case 3:
                    case 5:
                        return Integer.class;
                    case 4:
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
                    case 2:
                    case 3:
                    case 4:
                        return true;
                    default:
                        return false;
                }
            }
        };
        JTable tourTable = new JTable(tourModel);
        tourTable.setRowHeight(150);
        tourTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tourModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if ((e.getColumn() == 4) || (e.getColumn() == 3)) {
                    int ans = 0;
                    for (int i = 0; i < tourModel.getRowCount() - 1; i++) {
                        if ((boolean) tourModel.getValueAt(i, 4)) {
                            ans += (int) tourModel.getValueAt(i, 3);
                        }
                    }
                    tourModel.setValueAt(ans, tourModel.getRowCount() - 1, 5);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(tourTable);
        panel2.add(scrollPane, BorderLayout.WEST);
        Container container = this.getContentPane();
        tabbedPane.add(panel1, "Task 1");
        tabbedPane.add(panel2, "Task 2");
        container.add(tabbedPane);
    }
}
