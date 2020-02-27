#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <algorithm>

struct Node {
    long long key;
    Node *left;
    Node *right;
    Node *anc;
};

Node *to_remove = nullptr;
bool flag = false;

int child_counter(Node *pos) {

    if (pos != nullptr) {
        int cnt = 0;
        if (pos->left != nullptr) {
            cnt += 1;
        }
        if (pos->right != nullptr) {
            cnt += 2;
        }
        return cnt;
    }
    return -1;
}

Node *add(Node *pos, long long x) {
    if (pos != nullptr) {
        if (x < pos->key) {
            pos->left = add(pos->left, x);
            pos->left->anc = pos;
        }
        if (x > pos->key) {
            pos->right = add(pos->right, x);
            pos->right->anc = pos;
        }
    } else {
        pos = new Node();
        pos->anc = nullptr;
        pos->left = nullptr;
        pos->right = nullptr;
        pos->key = x;
    }
    return pos;
}

void nlr(Node *pos1, Node *pos2) {
    if ((pos1 != nullptr) || (pos2 != nullptr)) {
        int cnt1 = child_counter(pos1);
        int cnt2 = child_counter(pos2);
        if (cnt1 != cnt2) {
            if ((cnt1 == 0) || (cnt2 == 3)) {
                if (pos1 == nullptr) {
                    flag = true;
                } else {
                    nlr(pos1->left, pos2->left);
                    nlr(pos1->right, pos2->right);
                }
            } else {
                if (to_remove != nullptr) {
                    flag = true;
                }
                to_remove = pos2;
                if ((pos1 != nullptr) && (pos2 != nullptr)) {
                    if (pos2->left != nullptr) {
                        nlr(pos1, pos2->left);
                    } else {
                        nlr(pos1, pos2->right);
                    }
                }
            }
        } else {
            if ((pos1 != nullptr) && (pos2 != nullptr)) {
                nlr(pos1->left, pos2->left);
                nlr(pos1->right, pos2->right);
            }
        }
    }
}

int main() {
    std::ifstream in("tst.in");
    std::ofstream out("tst.out");
    Node *root1 = nullptr, *root2 = nullptr;
    long long size1 = 0, size2 = 0;
    std::string s;
    in >> s;
    while (s != "*") {
        root1 = add(root1, std::strtol(s.c_str(), nullptr, 10));
        in >> s;
        size1++;
    }
    long long x;
    while (in >> x) {
        root2 = add(root2, x);
        size2++;
    }
    if (std::abs(size1 - size2) != 1) {
        out << "NO\n";
        return 0;
    }
    if (size1 > size2) {
        std::swap(root1, root2);
        std::swap(size1, size2);
    }
    if (size1 == 0) {
        out << "YES\n" << root2->key << '\n';
        return 0;
    }
    nlr(root1, root2);
    if (flag) {
        out << "NO\n";
        return 0;
    }
    out << "YES\n";
    if (root2 == to_remove) {
        out << to_remove->key << '\n';
        return 0;
    }
    int counter = 0;
    bool ch = (to_remove->right != nullptr);
    if ((to_remove != root2) && (to_remove->anc->left == to_remove)) {
        while ((to_remove != root2) && (to_remove->anc->left == to_remove) &&
               (child_counter(to_remove) != 3)) {
            to_remove = to_remove->anc;
            counter++;
        }
    }
    if (((child_counter(to_remove) == 3) && (to_remove->left->right != nullptr)) || ((counter == 1) && (ch))) {
        to_remove = to_remove->left;
    }
    while (to_remove->right == nullptr) {
        Node *buf = to_remove;
        while ((buf != root2) && (buf->anc->right == buf)) {
            buf = buf->anc;
        }
        if ((buf->anc != nullptr) && (buf->anc->left == buf)) {
            buf = buf->anc;
            if (buf->right != nullptr) {
                to_remove = buf;
            } else {
                break;
            }
        } else {
            break;
        }
    }

    out << to_remove->key << '\n';
    return 0;
}