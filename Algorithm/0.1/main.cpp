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

int main() {
    Node *root = nullptr;
    long long x;
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
    nlr(root);
    return 0;
}
