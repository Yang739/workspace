#include<bits/stdc++.h>
using namespace std;
int dx[4] = {0, 0, 1, -1};
int dy[4] = {1, -1, 0, 0};
int s[201][201];
int h[201][201];
int R, C;
int dfs(int x,int y)
{
    if(s[x][y])
    {
        return s[x][y];
    }
    s[x][y] = 1;
    for(int i = 0; i < 4; i++)
    {
        int nx = x + dx[i];
        int ny = y + dy[i];
        if(nx >= 1 && nx <= R && ny >= 1 && ny <= C && h[nx][ny] < h[x][y])
        {
            s[x][y] = max(s[x][y], dfs(nx, ny) + 1);
        }
    }
    return s[x][y];
}
int main()
{
    cin >> R >> C;
    for(int i = 1; i <= R; i++)
    {
        for(int j = 1; j <= C; j++)
        {
            cin >> h[i][j];
        }
    }
    int ans = 0;
    for(int i = 1; i <= R; i++)
    {
        for(int j = 1; j <= C; j++)
        {
            ans = max(ans, dfs(i, j));
        }
    }
    cout << ans << endl;
    return 0;
}