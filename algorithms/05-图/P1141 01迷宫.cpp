#include <bits/stdc++.h>
using namespace std;

int n, m;
char g[1005][1005];
int a[1005][1005];

vector<int> ans;

struct Node {
    int x, y;
};

void bfs(int startX, int startY, int counter) {
    queue<Node> q;
    q.push({startX, startY});
    a[startX][startY] = counter;
    ans[counter]++;

    while (!q.empty()) {
        Node current = q.front();
        q.pop();

        int dx[] = {0, 0, 1, -1};
        int dy[] = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newX = current.x + dx[i];
            int newY = current.y + dy[i];

            if (newX >= 1 && newX <= n && newY >= 1 && newY <= n &&
                g[newX][newY] != g[current.x][current.y] && a[newX][newY] == 0) {
                a[newX][newY] = counter;
                ans[counter]++;
                q.push({newX, newY});
            }
        }
    }
}

int main() {
    cin >> n >> m;
    ans.assign(n * n + 5, 0);

    int counter = 0;

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            cin >> g[i][j];
        }
    }

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (a[i][j] == 0) {
                counter++;
                bfs(i, j, counter);
            }
        }
    }

    int x, y;
    for (int i = 1; i <= m; i++) {
        cin >> x >> y;
        cout << ans[a[x][y]] << "\n";
    }

    return 0;
}