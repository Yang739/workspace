#include <bits/stdc++.h>
using namespace std;

int L, N, K;
vector<int> sit;

bool check(int x)
{
    int y = K;
    int size = 0;
    for (int i = 0; i < N; )
    {
        if(y < 0) break;
        if (sit[i] - size <= x)
        {
            size = sit[i];
            i++;
        } else {
            size += x;
            y--;
        }
    }
    if(y < 0) return false;
    return true;
}

int main()
{
    cin >> L >> N >> K;
    sit.resize(N);
    for (int i = 0; i < N; i++)
    {
        cin >> sit[i];
    }
    int l = 0, r = L;
    int ans;
    while (l <= r)
    {
        int mid = (l + r) / 2;
        if (check(mid))
        {
            ans = mid;
            r = mid - 1;
        }
        else
        {
            l = mid + 1;
        }
    }
    cout << ans << endl;
    return 0;
}