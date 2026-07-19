#include <bits/stdc++.h>
using namespace std;

int n, r, q;
struct Node {
    int s, w, id;
} a[200005], b[200005], c[200005];

bool cmp(const Node &x, const Node &y) {
    if (x.s == y.s) return x.id < y.id;
    return x.s > y.s;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);

    cin >> n >> r >> q;
    for (int i = 1; i <= n * 2; i++) {
        cin >> a[i].s;
        a[i].id = i;
    }

    for (int i = 1; i <= n * 2; i++) {
        cin >> a[i].w;
    }

    for (sort(a + 1, a + n * 2 + 1, cmp); r--; merge(b + 1, b + n + 1, c + 1, c + n + 1, a + 1, cmp)) {
        for (int i = 1, j=1; i <= n * 2; i+=2, j++) {
            if (a[i].w > a[i + 1].w) {
                a[i].s++;
                b[j] = a[i];
                c[j] = a[i + 1];
            } else {
                a[i + 1].s++;
                b[j] = a[i + 1];
                c[j] = a[i];
            }
        }
    }

    cout << a[q].id << endl;
    return 0;
}