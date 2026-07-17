#include <bits/stdc++.h>
using namespace std;

struct Node {
    int val;
    Node *left, *right;
    Node(int v = 0) : val(v), left(nullptr), right(nullptr) {}
};

void insertBST(Node* &root, int x) {
    if (root == nullptr) {
        root = new Node(x);
        return;
    }
    if (x < root->val) {
        insertBST(root->left, x);
    } else {
        insertBST(root->right, x);
    }
}

void printParent(Node *root, Node *parent = nullptr) {
    if (root == nullptr) return;
    if (parent != nullptr) {
        cout << parent->val << " ";
    }
    printParent(root->left, root);
    printParent(root->right, root);
}

int main()
{
    int n;
    cin >> n;
    Node *root = nullptr;
    for (int i = 0; i < n; i++) {
        int x;
        cin >> x;
        insertBST(root, x);
    }

    printParent(root);
    return 0;
}