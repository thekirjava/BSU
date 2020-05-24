package bsu.fpmi.artsiushkevich.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DOMParser {
    public static DefaultMutableTreeNode parseDOM(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(file);
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("libraryCard");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName());
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            ArrayList<DefaultMutableTreeNode> treeNode = dfs(n);
            if (treeNode != null) {
                for (DefaultMutableTreeNode node : treeNode) {
                    root.add(node);
                }
            }
        }
        return root;
    }

    private static ArrayList<DefaultMutableTreeNode> dfs(Node pos) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(pos.getNodeName());
        if (pos.getNodeName().equals("#text")) {
            StringTokenizer tokenizer = new StringTokenizer(pos.getNodeValue(), "\n\t");
            if (!tokenizer.hasMoreTokens()) {
                return null;
            }
            ArrayList<DefaultMutableTreeNode> ans = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                ans.add(new DefaultMutableTreeNode(tokenizer.nextToken()));
            }
            return ans;
            //StringBuilder builder = new StringBuilder();

        }
        NodeList list = pos.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            ArrayList<DefaultMutableTreeNode> newNode = dfs(n);
            if (newNode != null) {
                for (DefaultMutableTreeNode node : newNode) {
                    treeNode.add(node);
                }
            }
        }
        NamedNodeMap map = pos.getAttributes();
        if (map != null) {
            for (int i = 0; i < map.getLength(); i++) {
                Node n = map.item(i);
                treeNode.add(new DefaultMutableTreeNode(n.getNodeName() + "=" + n.getNodeValue()));
            }
        }
        ArrayList<DefaultMutableTreeNode> ans = new ArrayList<>();
        ans.add(treeNode);
        return ans;
    }
}
