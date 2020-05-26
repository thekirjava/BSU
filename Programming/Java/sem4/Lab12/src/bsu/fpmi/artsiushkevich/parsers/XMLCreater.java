package bsu.fpmi.artsiushkevich.parsers;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class XMLCreater {
    public static void createXML(File file, DefaultTreeModel model) throws FileNotFoundException {
        PrintStream printStream = new PrintStream(file);
        printStream.println("<?xml version=\"1.0\"  encoding=\"UTF-8\" standalone=\"yes\"?>");
        printStream.println("<data>");
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        dfs(root, new boolean[root.getChildCount()], printStream, 1);
        printStream.println("</data>");
    }

    static void printTabs(int level, PrintStream printStream) {
        for (int i = 0; i < level; i++) {
            printStream.print("\t");
        }
    }

    static void dfs(DefaultMutableTreeNode parent, boolean[] used, PrintStream printStream, int level) {
        if (parent.getChildCount() == 0) {
            printTabs(level, printStream);
            printStream.println(parent.toString());
            return;
        }
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (used[i]) {
                continue;
            }
            DefaultMutableTreeNode pos = (DefaultMutableTreeNode) parent.getChildAt(i);
            printTabs(level, printStream);
            if (pos.getChildCount() == 0) {
                printStream.println("<book>");
                printTabs(level + 1, printStream);
                printStream.println(pos.toString());
                printTabs(level, printStream);
                printStream.println("</book>");
                continue;
            }
            StringTokenizer tokenizer = new StringTokenizer(pos.toString());
            StringBuilder pos_name = new StringBuilder();
            while (tokenizer.hasMoreTokens()) {
                pos_name.append(tokenizer.nextToken());
            }
            printStream.print("<" + pos_name.toString());
            boolean[] pos_used = new boolean[pos.getChildCount()];
            for (int j = 0; j < pos.getChildCount(); j++) {
                tokenizer = new StringTokenizer(pos.getChildAt(j).toString(), "=");
                if (tokenizer.countTokens() >= 2) {
                    printStream.print(" " + tokenizer.nextToken() + "=\"");
                    while (tokenizer.hasMoreTokens()) {
                        printStream.print(tokenizer.nextToken());
                        if (tokenizer.hasMoreTokens()) {
                            printStream.print("=");
                        }
                    }
                    printStream.print("\"");
                    pos_used[j] = true;
                } else {
                    pos_used[j] = false;
                }
            }
            printStream.println(">");
            dfs(pos, pos_used, printStream, level + 1);
            printTabs(level, printStream);
            printStream.println("</" + pos_name.toString() + ">");
        }
    }
}
