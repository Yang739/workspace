#include<bits/stdc++.h>
using namespace std;
typedef long long ll;
int main()
{
    ll t;
    cin >> t;
    while(t--)
    {
        ll n,l,r;
        cin >> n >> l >> r;
        ll a[n+1];
        for(ll i = 1; i <= n; i++)
        {
            cin >> a[i];
        }
        ll ans = 0;
        ll sum = 0;
        ll L=1, R=0;
        while(L<=n && R<=n)
        {
            if(sum < l)
            {
                R++;
                sum += a[R];
            }
            else if(sum > r)
            {
                sum -= a[L];
                L++;
            }
            else
            {
                ans ++;
                sum = 0;
                L=R+1;
            }
        }
        cout << ans << endl;
    }
    return 0;
}