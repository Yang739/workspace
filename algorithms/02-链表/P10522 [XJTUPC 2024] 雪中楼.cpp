#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<int> a(n + 1);
    for (int i = 1; i <= n; ++i) {
        cin >> a[i];
    }

    vector<int> nxt(n + 1, 0), prv(n + 1, 0);
    int head = 0;

    for (int i = 1; i <= n; ++i) {
        if (a[i] == 0) {
            prv[i] = 0;
            nxt[i] = head;
            if (head) {
                prv[head] = i;
            }
            head = i;
        } else {
            int p = a[i];
            int q = nxt[p];
            prv[i] = p;
            nxt[i] = q;
            nxt[p] = i;
            if (q) {
                prv[q] = i;
            }
        }
    }

    for (int cur = head; cur; cur = nxt[cur]) {
        cout << cur << ' ';
    }
    return 0;
}