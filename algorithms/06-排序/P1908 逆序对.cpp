#include <bits/stdc++.h>
using namespace std;
typedef long long ll;

vector<ll> a, c;
ll n, ans;

void merge(int l, int r) {
    if (l >= r) return;
    int mid = (l + r) / 2;
    merge(l, mid);
    merge(mid + 1, r);
    int i = l, j = mid + 1, k = l;
    while (i <= mid && j <= r) {
        if (a[i] <= a[j]) c[k++] = a[i++];
        else {
            c[k++] = a[j++];
            ans += mid - i + 1;
        }
    }
    while (i <= mid) c[k++] = a[i++];
    while (j <= r) c[k++] = a[j++];
    for (int p = l; p <= r; p++) a[p] = c[p];
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);

    cin >> n;
    a.resize(n);
    c.resize(n);
    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }
    ans = 0;
    merge(0, n - 1);
    cout << ans << endl;

    return 0;
}