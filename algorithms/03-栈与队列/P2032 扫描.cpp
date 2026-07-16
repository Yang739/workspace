#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
int main()
{
    ll n, k;
    cin >> n >> k;
    vector<ll> a(n+1);
    vector<ll> b(n+1, 0);
    ll head = 1, tail = 0;
    for(ll i = 1; i <= n; i++)
    {
        cin >> a[i];
        if(i - b[head] >= k)
        {
            head++;
        }
        while(tail >= head && a[b[tail]] <= a[i])
        {
            tail--;
        }
        b[++tail] = i;
        if(i >= k)
        {
            cout << a[b[head]] << "\n";
        }
    }
    return 0;
}