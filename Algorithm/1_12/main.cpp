#include <iostream>
#include <fstream>
#include <deque>

std::ifstream in("tst.in");
std::ofstream out("tst.out");

long long max_length = 0;
struct Node {
    Node *left;
    Node *right;
    long long key;
    long long height;
    long long length;
};

struct path {
    Node *left;
    Node *root;
    Node *right;
    long long length;
};

path max_path;
std::deque<path> path_deque;

Node *add(Node *pos, long long x) {
    if (pos == nullptr) {
        pos = new Node;
        pos->key = x;
        pos->left = nullptr;
        pos->right = nullptr;
        pos->height = 0;
        pos->length = 0;
    } else {
        if (x < pos->key) {
            pos->left = add(pos->left, x);
            pos->height = std::max(pos->height, pos->left->height + 1);
        } else {
            pos->right = add(pos->right, x);
            pos->height = std::max(pos->height, pos->right->height + 1);
        }
    }
    if ((pos->left == nullptr) && (pos->right == nullptr)) {
        pos->length = 0;
    } else {
        if (pos->left == nullptr) {
            pos->length = pos->right->height + 1;
        } else {
            if (pos->right == nullptr) {
                pos->length = pos->left->height + 1;
            } else {
                pos->length = pos->left->height + pos->right->height + 2;
            }
        }
    }
    max_length = std::max(pos->length, max_length);
    return pos;
}

void search(Node *pos, std::pair<Node *, Node *> &ans) {
    if ((pos->left == nullptr) && (pos->right == nullptr)) {
        if ((ans.first == nullptr) || (ans.first->key > pos->key)) {
            ans.first = pos;
        }
    } else {
        if (pos->left == nullptr) {
            if ((pos->right->left == nullptr) && (pos->right->right == nullptr)) {
                if ((ans.second == nullptr) || (ans.second->key > pos->key)) {
                    ans.second = pos;
                }
            }
            search(pos->right, ans);
        } else {
            if (pos->right == nullptr) {
                if ((pos->left->left == nullptr) && (pos->left->right == nullptr)) {
                    if ((ans.second == nullptr) || (ans.second->key > pos->key)) {
                        ans.second = pos;
                    }
                }
                search(pos->left, ans);
            } else {
                if (pos->left->height <= pos->right->height) {
                    if ((pos->right->left == nullptr) && (pos->right->right == nullptr)) {
                        if ((ans.second == nullptr) || (ans.second->key > pos->key)) {
                            ans.second = pos;
                        }
                    }
                    search(pos->right, ans);
                }
                if (pos->left->height >= pos->right->height) {
                    if ((pos->left->left == nullptr) && (pos->left->right == nullptr)) {
                        if ((ans.second == nullptr) || (ans.second->key > pos->key)) {
                            ans.second = pos;
                        }
                    }
                    search(pos->left, ans);
                }
            }
        }
    }
}

void nlr_search(Node *pos) {
    if (pos != nullptr) {
        if (pos->length >= max_length - 1) {
            std::pair<Node *, Node *> left_nodes = std::make_pair(nullptr, nullptr);
            std::pair<Node *, Node *> right_nodes = std::make_pair(nullptr, nullptr);
            if (pos->left != nullptr) {
                search(pos->left, left_nodes);
                if (left_nodes.second == nullptr) {
                    left_nodes.second = pos;
                }
            } else {
                left_nodes = std::make_pair(pos, pos->right);
            }
            if (pos->right != nullptr) {
                search(pos->right, right_nodes);
                if (right_nodes.second == nullptr) {
                    right_nodes.second = pos;
                }
            } else {
                right_nodes = std::make_pair(pos, pos->left);
            }

            max_path.root = pos;
            max_path.length = pos->length;
            max_path.left = left_nodes.first;
            max_path.right = right_nodes.first;
            bool flag = true;
            if ((max_path.left->left == nullptr) && (max_path.left->right == nullptr) &&
                (max_path.right->left == nullptr) && (max_path.right->right == nullptr)) {
                flag = false;
            }
            if (flag) {
                if (path_deque.empty()) {
                    path_deque.push_back(max_path);
                } else {
                    path buf = path_deque.front();
                    if (max_path.length > buf.length) {
                        path_deque.clear();
                        path_deque.push_back(max_path);
                    } else {
                        if (max_path.length == buf.length) {
                            if (max_path.left->key + max_path.right->key <= buf.left->key + buf.right->key) {
                                if (max_path.left->key + max_path.right->key == buf.left->key + buf.right->key) {
                                    if (max_path.root <= buf.root) {
                                        if (max_path.root != buf.root) {
                                            path_deque.clear();
                                        }
                                        path_deque.push_back(max_path);
                                    }
                                } else {
                                    path_deque.clear();
                                    path_deque.push_back(max_path);
                                }
                            }
                        }
                    }
                }
            } else {
                if (left_nodes.first->key + right_nodes.second->key <=
                    left_nodes.second->key + right_nodes.first->key) {
                    if (path_deque.empty()) {
                        max_path.left = left_nodes.first;
                        max_path.right = right_nodes.second;
                        if (right_nodes.first == pos) {
                            max_path.root = right_nodes.second;
                            max_path.length = max_path.root->length;
                        } else {
                            max_path.root = pos;
                            max_path.length = max_path.root->length - 1;
                        }
                        path_deque.push_back(max_path);
                    } else {
                        max_path = path_deque.front();
                        if (max_path.length <= pos->length - 1) {
                            if (max_path.left->key + max_path.right->key >=
                                left_nodes.first->key + right_nodes.second->key) {
                                if (max_path.left->key + max_path.right->key ==
                                    left_nodes.first->key + right_nodes.second->key) {
                                    if (pos->key <= max_path.root->key) {
                                        max_path.left = left_nodes.first;
                                        max_path.right = right_nodes.second;
                                        if (right_nodes.first == pos) {
                                            max_path.root = right_nodes.second;
                                            max_path.length = max_path.root->length;
                                        } else {
                                            max_path.root = pos;
                                            max_path.length = max_path.root->length - 1;
                                        }
                                        if (pos->key != max_path.root->key) {
                                            path_deque.clear();
                                        }
                                        path_deque.push_back(max_path);
                                    }
                                } else {
                                    max_path.left = left_nodes.first;
                                    max_path.right = right_nodes.second;
                                    if (right_nodes.first == pos) {
                                        max_path.root = right_nodes.second;
                                        max_path.length = max_path.root->length;
                                    } else {
                                        max_path.root = pos;
                                        max_path.length = max_path.root->length - 1;
                                    }
                                    path_deque.clear();
                                    path_deque.push_back(max_path);
                                }
                            }
                        }
                    }
                }
                if (left_nodes.first->key + right_nodes.second->key >=
                    left_nodes.second->key + right_nodes.first->key) {
                    if (path_deque.empty()) {
                        max_path.left = left_nodes.second;
                        max_path.right = right_nodes.first;
                        if (pos == left_nodes.first) {
                            max_path.root = left_nodes.second;
                            max_path.length = max_path.root->length;
                        } else {
                            max_path.root = pos;
                            max_path.length = max_path.root->length - 1;
                        }
                        path_deque.push_back(max_path);
                    } else {
                        max_path = path_deque.front();
                        if (max_path.left->key + max_path.right->key >=
                            left_nodes.second->key + right_nodes.first->key) {
                            if (max_path.left->key + max_path.right->key ==
                                left_nodes.second->key + right_nodes.first->key) {
                                if (pos->key <= max_path.root->key) {
                                    max_path.left = left_nodes.second;
                                    max_path.right = right_nodes.first;
                                    if (pos == left_nodes.first) {
                                        max_path.root = left_nodes.second;
                                        max_path.length = max_path.root->length;
                                    } else {
                                        max_path.root = pos;
                                        max_path.length = max_path.root->length - 1;
                                    }
                                    if (pos->key != max_path.root->key) {
                                        path_deque.clear();
                                    }
                                    path_deque.push_back(max_path);
                                }
                            } else {
                                max_path.left = left_nodes.second;
                                max_path.right = right_nodes.first;
                                if (pos == left_nodes.first) {
                                    max_path.root = left_nodes.second;
                                    max_path.length = max_path.root->length;
                                } else {
                                    max_path.root = pos;
                                    max_path.length = max_path.root->length - 1;
                                }
                                path_deque.clear();
                                path_deque.push_back(max_path);
                            }
                        }
                    }
                }
            }
        }
        nlr_search(pos->left);
        nlr_search(pos->right);
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

void nlr_print(Node *pos) {
    if (pos != nullptr) {
        out << pos->key << '\n';
        nlr_print(pos->left);
        nlr_print(pos->right);
    }
}

void lnr_left(Node *pos, long long &ans, long long &cnt) {
    if (pos != nullptr) {
        if (max_path.left->key < pos->key) {
            lnr_left(pos->left, ans, cnt);
        }
        if (cnt == 0) {
            ans = pos->key;
        }
        cnt--;
        if (max_path.left->key > pos->key) {
            lnr_left(pos->right, ans, cnt);
        }
    }
}

void lnr_right(Node *pos, long long &ans, long long &cnt) {
    if (pos != nullptr) {
        if (max_path.right->key < pos->key) {
            lnr_right(pos->left, ans, cnt);
        }
        if (cnt == 0) {
            ans = pos->key;
        }
        cnt--;
        if (max_path.right->key > pos->key) {
            lnr_right(pos->right, ans, cnt);
        }
    }
}

int main() {
    long long x;
    Node *root = nullptr;
    while (in >> x) {
        root = add(root, x);
    }
    if (max_length > 1) {
        max_path.root = nullptr;
        max_path.left = nullptr;
        max_path.right = nullptr;
        nlr_search(root);
        max_path = path_deque.front();
        if (max_path.length % 2 == 0) {
            bool check = false;
            long long ans = -1;
            while (!path_deque.empty()) {
                max_path = path_deque.front();
                long long mid_key = 0;
                long long cnt = max_path.length / 2;
                lnr_left(max_path.root, mid_key, cnt);
                if (cnt >= 0) {
                    cnt++;
                    lnr_right(max_path.root, mid_key, cnt);
                }
                if ((ans != mid_key) && (check)) {
                    check = false;
                    break;
                } else {
                    ans = mid_key;
                    check = true;
                }
                path_deque.pop_front();
            }
            if (check) {
                root = remove(ans, root);
            }
        }
    }
    nlr_print(root);
    return 0;
}
