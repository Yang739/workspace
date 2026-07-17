#include <bits/stdc++.h>
using namespace std;

int t, n;
vector<vector<int>> a;
int size[100005], depth[100005];

void dfs(int u, int fa)
{
    size[u] = 1;
    depth[u] = depth[fa] + 1;
    for(int v : a[u])
    {
        if(v == fa) continue;
        dfs(v, u);
        size[u] += size[v];
    }
    return;
}

int main()
{
    cin >> t;
    while(t--)
    {
        cin >> n;
        a.assign(n + 1, vector<int>());
        for(int i=1, u, v; i<n; i++)
        {
            cin >> u >> v;
            a[u].push_back(v);
            a[v].push_back(u);
        }
        dfs(1, 0);
        for(int i=1; i<=n; i++)
        {
            cout << depth[i] << " " << n - size[i] + 1 << "\n";
        }
    }
    return 0;
}