#include<iostream>
using namespace std;
int main()
{
    int n;
    cin >> n;
    int x,y;
    bool b[100][100] = {false};
    double ans = 0;
    for(int i = 0; i < n; i++)
    {
        cin >> x >> y;
        if(!b[x][y])
        {
            b[x][y] = true;
            ans += 2;
            if(b[x-1][y])
            {
                ans -= 0.5;
            }
            if(b[x+1][y])
            {
                ans -= 0.5;
            }
            if(b[x][y-1])
            {
                ans -= 0.5;
            }
            if(b[x][y+1])
            {
                ans -= 0.5;
            }
        }
    }
    cout << ans << endl;
    return 0;
}