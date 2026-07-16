#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
int main()
{
    ll n;
    cin >> n;
    stack<ll> s;
    ll ans = 0;
    for(ll i = 0; i < n; i++)
    {
        ll x;
        cin >> x;
        while (!s.empty() && s.top() <= x)
            s.pop();
        ans += s.size();
        s.push(x);
    }
    cout << ans << endl;
    return 0;
}