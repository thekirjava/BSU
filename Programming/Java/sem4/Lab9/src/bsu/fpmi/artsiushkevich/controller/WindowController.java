package bsu.fpmi.artsiushkevich.controller;

import bsu.fpmi.artsiushkevich.exception.CyclicLinkException;
import bsu.fpmi.artsiushkevich.exception.DateFormatException;
import bsu.fpmi.artsiushkevich.exception.SheetLinkException;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WindowController {
    public static DefaultTableModel createModel() {
        Pair<Integer, Integer> pair = input();
        return createModel(pair.first, pair.second);
    }

    private static DefaultTableModel createModel(int w, int h) {
        realValue = new String[w][h];
        String[] names = new String[w + 1];
        names[0] = " ";
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
        sheet = new DefaultTableModel(new Object[h][w + 1], names) {
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
        sheet.addTableModelListener(formula);
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
        return new Pair<>(w, h);
    }

    private static final TableModelListener formula = new TableModelListener() {

        @Override
        public void tableChanged(TableModelEvent e) {
            if (visited == null) {
                try {
                    realValue[e.getFirstRow()][e.getColumn() - 1] = sheet.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                    visited = new boolean[sheet.getRowCount()][sheet.getColumnCount()];
                    for (int i = 0; i < sheet.getRowCount(); i++) {
                        for (int j = 1; j < sheet.getColumnCount(); j++) {
                            if ((sheet.getValueAt(i, j) == null) || (sheet.getValueAt(i, j).toString().equals(""))) {
                                continue;
                            }

                            getDate(i, j);
                        }
                    }
                } catch (SheetLinkException | DateFormatException | CyclicLinkException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    visited = null;
                }
            }
        }


        private void constSum(int i, int j) {
            StringBuilder builder = new StringBuilder(realValue[i][j - 1]);
            builder.deleteCharAt(0);
            int dot_counter = 0;
            int day = 0;
            int month = 0;
            int year = 0;
            int x = 0;
            int sign = 1;
            for (int k = 0; k < builder.length(); k++) {
                if ((builder.charAt(k) == '+') || (builder.charAt(k) == '-')) {
                    if (builder.charAt(k) == '-') {
                        sign = -1;
                    }
                    dot_counter++;
                    continue;
                }
                if ((builder.charAt(k) == '.') || (builder.charAt(k) == '/')) {
                    dot_counter++;
                } else {
                    switch (dot_counter) {
                        case 0:
                            day *= 10;
                            day += Integer.parseInt(String.valueOf(builder.charAt(k)));
                            break;
                        case 1:
                            month *= 10;
                            month += Integer.parseInt(String.valueOf(builder.charAt(k)));
                            break;
                        case 2:
                            year *= 10;
                            year += Integer.parseInt(String.valueOf(builder.charAt(k)));
                            break;
                        default:
                            x *= 10;
                            x += Integer.parseInt(String.valueOf(builder.charAt(k)));
                    }
                }
            }
            x *= sign;

            GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
            calendar.add(Calendar.DAY_OF_MONTH, x);
            System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println(calendar.get(Calendar.MONTH));
            System.out.println(calendar.get(Calendar.YEAR));
            sheet.setValueAt(dateFormat.format(calendar.getTime()), i, j);
        }

        private void fieldSum(int i, int j) throws SheetLinkException, DateFormatException, CyclicLinkException {
            StringBuilder builder = new StringBuilder(realValue[i][j - 1]);
            builder.deleteCharAt(0);
            int sign = 1;
            int x = 0;
            int k = 0;
            while ((builder.charAt(k) != '-') && (builder.charAt(k) != '+')) {
                k++;
            }
            Pair<Integer, Integer> pair = stringToPos(builder.substring(0, k));
            if (builder.charAt(k) == '-') {
                sign = -1;
            }
            k++;
            while (k < builder.length()) {
                x *= 10;
                x += builder.charAt(k) - '0';
                k++;
            }
            x *= sign;
            GregorianCalendar calendar = stringToDate(getDate(pair.first, pair.second));
            calendar.add(Calendar.DAY_OF_MONTH, x);
            sheet.setValueAt(dateFormat.format(calendar.getTime()), i, j);
        }

        private void minMax(int i, int j) throws SheetLinkException, DateFormatException, CyclicLinkException {
            StringBuilder builder = new StringBuilder(realValue[i][j - 1]);
            builder.deleteCharAt(0);
            boolean isMin = builder.charAt(2) == 'N';
            GregorianCalendar ans = null;
            builder.delete(0, 4);
            builder.deleteCharAt(builder.length() - 1);
            StringTokenizer tokenizer = new StringTokenizer(builder.toString(), ", ");
            while (tokenizer.hasMoreTokens()) {
                String s = tokenizer.nextToken();
                Matcher dateMatcher = DATE.matcher(s);
                GregorianCalendar buf;
                if (dateMatcher.matches()) {
                    buf = stringToDate(s);
                } else {
                    Pair<Integer, Integer> pair = stringToPos(s);
                    buf = stringToDate(getDate(pair.first, pair.second));
                }
                if ((ans == null) || ((ans.compareTo(buf) > 0) == isMin)) {
                    ans = buf;
                }
            }
            sheet.setValueAt(dateFormat.format(ans.getTime()), i, j);

        }

        private String getDate(int i, int j) throws SheetLinkException, DateFormatException, CyclicLinkException {
            if (visited[i][j]) {
                sheet.setValueAt("", i, j);
                throw new CyclicLinkException();
            }
            visited[i][j] = true;

            if ((i >= sheet.getRowCount()) || (j >= sheet.getRowCount())) {
                visited[i][j] = false;
                sheet.setValueAt("", i, j);
                throw new SheetLinkException();
            }
            Matcher dateMatcher = DATE.matcher(realValue[i][j - 1]);
            if (dateMatcher.matches()) {
                visited[i][j] = false;
                return sheet.getValueAt(i, j).toString();
            }
            Matcher sumMatcher = SUM_CONST.matcher(realValue[i][j - 1]);
            if (sumMatcher.matches()) {
                constSum(i, j);
                visited[i][j] = false;
                return sheet.getValueAt(i, j).toString();
            }
            Matcher sumFieldMatcher = SUM_FIELD.matcher(realValue[i][j - 1]);
            if (sumFieldMatcher.matches()) {
                fieldSum(i, j);
                visited[i][j] = false;
                return sheet.getValueAt(i, j).toString();
            }
            Matcher minMaxMatcher = MIN_MAX.matcher(realValue[i][j - 1]);
            if (minMaxMatcher.matches()) {
                minMax(i, j);
                visited[i][j] = false;
                return sheet.getValueAt(i, j).toString();
            }
            sheet.setValueAt("", i, j);
            visited[i][j] = false;
            throw new DateFormatException();
        }

        private GregorianCalendar stringToDate(String s) {
            int month = 0, day = 0, year = 0, dot = 0;
            for (int i = 0; i < s.length(); i++) {
                if ((s.charAt(i) == '.') || (s.charAt(i) == '/')) {
                    dot++;
                    continue;
                }
                switch (dot) {
                    case 0:
                        day *= 10;
                        day += s.charAt(i) - '0';
                        break;
                    case 1:
                        month *= 10;
                        month += s.charAt(i) - '0';
                        break;
                    case 2:
                        year *= 10;
                        year += s.charAt(i) - '0';
                }
            }
            return new GregorianCalendar(year, month - 1, day);
        }

        private Pair<Integer, Integer> stringToPos(String s) {
            StringBuilder builder = new StringBuilder(s);
            int i = 0, j = 0, k = 0;
            while (Character.isAlphabetic(builder.charAt(k))) {
                i *= 26;
                i += builder.charAt(k) - 'A';
                k++;
            }
            while (k < builder.length()) {
                j *= 10;
                j += builder.charAt(k) - '0';
                k++;
            }
            return new Pair<>(j - 1, i + 1);
        }

        private final Pattern DATE = Pattern.compile("(((0?[1-9]|[1-2][0-9]|3[0-1])[/.](1|3|5|7|8|10|12|01|03|05|07|08)|(0?[1-9]|[1-2][0-9]|30)[/.](4|6|9|11|04|06|09))|(0?[1-9]|1[0-9]|2[0-8])[/.](2|02))[/.][0-9]+[+-]?[0-9]+");
        private final Pattern SUM_CONST = Pattern.compile("=(((0?[1-9]|[1-2][0-9]|3[0-1])[/.](1|3|5|7|8|10|12|01|03|05|07|08)|(0?[1-9]|[1-2][0-9]|30)[/.](4|6|9|11|04|06|09))|(0?[1-9]|1[0-9]|2[0-8])[/.](2|02))[/.][0-9]+[+-]?[0-9]+");
        private final Pattern SUM_FIELD = Pattern.compile("=[A-Z]+[0-9]+[+-]?[0-9]+");
        private final Pattern MIN_MAX = Pattern.compile("=(MIN|MAX)\\(((((0?[1-9]|[1-2][0-9]|3[0-1])[/.](1|3|5|7|8|10|12|01|03|05|07|08)|(0?[1-9]|[1-2][0-9]|30)[/.](4|6|9|11|04|06|09))|(0?[1-9]|1[0-9]|2[0-8])[/.](2|02))[/.][0-9]+[+-]?[0-9]+|[A-Z]+[0-9]+)(,[ ]?((((0?[1-9]|[1-2][0-9]|3[0-1])[/.](1|3|5|7|8|10|12|01|03|05|07|08)|(0?[1-9]|[1-2][0-9]|30)[/.](4|6|9|11|04|06|09))|(0?[1-9]|1[0-9]|2[0-8])[/.](2|02))[/.][0-9]+[+-]?[0-9]+|[A-Z]+[0-9]+))+\\)");
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    };

    public static void setMouseListener(JTable t) {
        table = t;
        t.addMouseListener(tableMouseListener);
    }

    private static final MouseAdapter tableMouseListener = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            visited = new boolean[sheet.getRowCount()][sheet.getColumnCount()];
            int i = table.getSelectedRow();
            int j = table.getSelectedColumn();
            if (j > 0) {
                sheet.setValueAt(realValue[i][j - 1], i, j);
            }
            visited = null;
        }
    };
    private static JTable table;
    private static DefaultTableModel sheet;
    private static String[][] realValue;
    private static boolean[][] visited;
}
