#include <bits/stdc++.h>
using namespace std;

int n, m;
vector<int> a, b;

int main()
{
    cin >> n >> m;
    a.resize(n);
    b.resize(m);
    for (int i = 0; i < n; i++)
    {
        cin >> a[i];
    }
    for (int i = 0; i < m; i++)
    {
        cin >> b[i];
    }

    sort(a.begin(), a.end());

    long long ans = 0;

    for (int i = 0; i < m; i++)
    {
        int res = INT_MAX;
        int x = b[i];
        int l = 0, r = n - 1;
        while (l <= r)
        {
            int mid = (l + r) / 2;
            if (a[mid] < x)
            {
                res = min(res, x - a[mid]);
                l = mid + 1;
            }
            else if (a[mid] == x)
            {
                res = 0;
                break;
            }
            else
            {
                res = min(res, a[mid] - x);
                r = mid - 1;
            }
        }
        ans += res;
    }
    cout << ans << endl;
    return 0;
}