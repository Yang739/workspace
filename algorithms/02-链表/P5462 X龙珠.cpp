#include<bits/stdc++.h>
using namespace std;

int main()
{
    int n;
    cin>>n;
    vector<int> a(n+1);
    for(int i=1;i<=n;i++)
    {
        cin>>a[i];
    }
    vector<int> nxt(n+1,0),prv(n+1,0);
    int head=1;
    int tail=n;
    for(int i=1;i<=n;i++)
    {
        nxt[i]=i+1;
        prv[i]=i-1;
    }
    nxt[tail]=0;
    prv[head]=0;
    priority_queue<pair<int,int>> q;
    for(int i=1;i<=n;i++)
    {
        q.push({a[i],i});
    }
    vector<int> ans;
    vector<bool> vis(n+1,false);
    int rem=n;
    while(rem>1)
    {
        while(!q.empty())
        {
            auto p = q.top();
            int val = p.first;
            int idx = p.second;
            if(vis[idx] || vis[nxt[idx]] || nxt[idx]==0)
            {
                q.pop();
                continue;
            }
            int k = nxt[idx];
            int f = prv[idx];
            int c = nxt[k];
            if (f != 0) nxt[f] = c;
            else head = c;
            if (c != 0) prv[c] = f;
            else tail = f;
            vis[idx] = true;
            vis[k] = true;
            ans.push_back(val);
            ans.push_back(a[k]);
            rem -= 2;
            q.pop();
            break;
        }
    }
    if(rem==1)
    {
        for(int i=1;i<=n;i++)
        {
            if(!vis[i])
            {
                ans.push_back(a[i]);
                break;
            }
        }
    }
    for(int i=0;i<ans.size();i++)
    {
        cout<<ans[i]<<" ";
    }
    return 0;
}