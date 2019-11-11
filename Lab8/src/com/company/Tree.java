package com.company;

public class Tree<T extends Comparable<T>> {
    class Node {
        Node() {
            left = null;
            right = null;
            k = null;
        }

        Node(T x) {
            left = null;
            right = null;
            k = x;
        }

        Node left;
        Node right;
        T k;
    }

    Tree() {
        root = null;
    }

    Tree(T x) {
        root = new Node(x);
    }

    public void add(T x) {
        root = add(x, root);
    }

    private Node add(T x, Node pos) {
        if (pos == null) {
            return new Node(x);
        }
        if (x.compareTo(pos.k) < 0) {
            pos.left = add(x, pos.left);
        }
        if (x.compareTo(pos.k) > 0) {
            pos.right = add(x, pos.right);
        }
        return pos;
    }

    public void delete(T x) {
        root = delete(x, root);
    }

    private Node delete(T x, Node pos) {
        if (pos == null) {
            return null;
        }
        if (x.compareTo(pos.k) == 0) {
            if ((pos.left == null) && (pos.right == null)) {
                return null;
            }
            if (pos.left == null) {
                return pos.right;
            }
            if (pos.right == null) {
                return pos.left;
            }
            Node buf = pos.right;
            while (buf.left != null) {
                buf = buf.left;
            }
            pos.k = buf.k;
            pos.right = delete(buf.k, pos.right);
        }
        if (x.compareTo(pos.k) < 0) {
            pos.left = delete(x, pos.left);
        }
        if (x.compareTo(pos.k) > 0) {
            pos.right = delete(x, pos.right);
        }
        return pos;
    }

    public boolean find(T x) {
        return find(x, root);
    }

    private boolean find(T x, Node pos) {
        if (pos == null) {
            return false;
        }
        if (x.compareTo(pos.k) == 0) {
            return true;
        }
        return find(x, pos.left) || find(x, pos.right);
    }

    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node pos) {
        if (pos != null) {
            System.out.print(pos.k.toString() + " ");
            preOrder(pos.left);
            preOrder(pos.right);
        }
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node pos) {
        if (pos != null) {
            inOrder(pos.left);
            System.out.print(pos.k.toString() + " ");
            inOrder(pos.right);
        }
    }

    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node pos) {
        if (pos != null) {
            postOrder(pos.left);
            postOrder(pos.right);
            System.out.print(pos.k.toString() + " ");
        }
    }

    private Node root;
}
