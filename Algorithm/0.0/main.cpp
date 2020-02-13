#include <iostream>
#include <fstream>

struct Node {
    long long key;
    Node *left;
    Node *right;
};

int main() {
    std::ifstream in("input.txt");
    std::ofstream out("output.txt");
    Node *root = nullptr;
    long long ans = 0;
    long long x;
    while (in >> x) {
        if (root == nullptr) {
            root = new Node;
            root->key = x;
            root->left = nullptr;
            root->right = nullptr;
            ans += x;
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
                        ans += x;
                        break;
                    }
                    cur = cur->left;
                } else {
                    if (cur->right == nullptr) {
                        cur->right = new Node;
                        cur->right->left = nullptr;
                        cur->right->right = nullptr;
                        cur->right->key = x;
                        ans += x;
                        break;
                    }
                    cur = cur->right;
                }
            }
        }
    }
    out << ans;
    return 0;
}
