#include <bits/stdc++.h>
using namespace std;
int main()
{
    list<int> q1,q2;
    map<int,bool> mp;
    pair<int,list<int>::iterator> vis[100005];
    int n1,n2;
    cin>>n1>>n2;
    int m;
    cin>>m;
    int v;
    for(int i=1;i<=m;i++)
    {
        cin>>v;
        if(!mp[v])
        {
            mp[v]=true;
            q2.push_front(v);
            vis[v].first=2;
            vis[v].second=q2.begin();
        }
        else
        {
            if(vis[v].first==1)
            {
                q1.erase(vis[v].second);
            }
            else
            {
                q2.erase(vis[v].second);
            }
            q1.push_front(v);
            vis[v].first=1;
            vis[v].second=q1.begin();
        }
        if(q1.size()>n1)
        {
            int x=q1.back();
            q1.pop_back();
            if(q2.size()<n2)
            {
                q2.push_front(x);
                vis[x].first=2;
                vis[x].second=q2.begin();
            }
            else
            {
                mp[x]=false;
            }
        }
        if(q2.size()>n2)
        {
            int y=q2.back();
            q2.pop_back();
            mp[y]=false;
        }
    }
    for(auto it=q1.begin();it!=q1.end();it++)
    {
        cout<<*it<<" ";
    }
    cout<<endl;
    for(auto it=q2.begin();it!=q2.end();it++)
    {
        cout<<*it<<" ";
    }
    return 0;
}