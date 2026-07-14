#include<bits/stdc++.h>
using namespace std;
typedef long long ll;
int main()
{
    ll t;
    cin >> t;
    while(t--)
    {
        ll n,m;
        cin >> n >> m;
        string a,b;
        cin >> a >> b;
        ll ans = 0;
        ll j = 0;
        for(ll i = 0; i < n; i++)
        {
            while(j < m && a[i] != b[j])
            {
                j++;
            }
            if(j < m)
            {
                ans = i + 1;
                j++;
            }
            else
            {
                break;
            }
        }
        cout << ans << endl;
    }
    return 0;
}