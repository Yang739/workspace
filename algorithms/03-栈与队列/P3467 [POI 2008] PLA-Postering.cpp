#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
int main()
{
    ll n;
    cin >> n;
    stack<ll> s;
    ll d, v;
    ll ans = n;
    for(ll i = 0; i < n; i++)
    {
        cin >> d >> v;
        while (!s.empty() && s.top() > v)
            s.pop();
        if (!s.empty() && s.top() == v)
            ans--;
        s.push(v);
    }
    cout << ans << endl;
    return 0;
}