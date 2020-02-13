#include <iostream>
#include <fstream>

std::ifstream in("input.txt");
std::ofstream out("output.txt");

struct Node {
    long long key;
    Node *left;
    Node *right;
};

void nlr(Node *root) {
    if (root != nullptr) {
        Node *cur = root;
        out << cur->key << '\n';
        nlr(cur->left);
        nlr(cur->right);
    }
}

Node *remove(long long x, Node *pos) {
    if (pos == nullptr) {
        return nullptr;
    }
    if (x == pos->key) {
        if ((pos->left == nullptr) && (pos->right == nullptr)) {
            return nullptr;
        }
        if (pos->left == nullptr) {
            return pos->right;
        }
        if (pos->right == nullptr) {
            return pos->left;
        }
        Node *buf = pos->right;
        while (buf->left != nullptr) {
            buf = buf->left;
        }
        pos->key = buf->key;
        pos->right = remove(buf->key, pos->right);
    }
    if (x < pos->key) {
        pos->left = remove(x, pos->left);
    }
    if (x > pos->key) {
        pos->right = remove(x, pos->right);
    }
    return pos;
}

int main() {
    Node *root = nullptr;
    long long x;
    long long del;
    in >> del;
    while (in >> x) {
        if (root == nullptr) {
            root = new Node;
            root->key = x;
            root->left = nullptr;
            root->right = nullptr;
        } else {
            Node *cur = root;
            while (true) {
                if (x == cur->key) {
                    break;
                }
                if (x < cur->key) {
                    if (cur->left == nullptr) {
                        cur->left = new Node;
                        cur->left->left = nullptr;
                        cur->left->right = nullptr;
                        cur->left->key = x;
                        break;
                    }
                    cur = cur->left;
                } else {
                    if (cur->right == nullptr) {
                        cur->right = new Node;
                        cur->right->left = nullptr;
                        cur->right->right = nullptr;
                        cur->right->key = x;
                        break;
                    }
                    cur = cur->right;
                }
            }
        }
    }
    root = remove(del, root);
    nlr(root);
    return 0;
}
