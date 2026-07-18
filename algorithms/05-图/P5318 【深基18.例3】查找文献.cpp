#include <bits/stdc++.h>
using namespace std;

vector<int> p[100005];
queue<int> q;
vector<int> visited(100005, 0);
int counter = 0;
int n, m;

void dfs(int current,int counter) {
    visited[current] = 1;

    cout << current << " ";

    if(counter == n) {
        return;
    }

    for (int neighbor : p[current]) {
        if (!visited[neighbor]) {
            dfs(neighbor, counter + 1);
        }
    }
}

void bfs(int start) {
    memset(visited.data(), 0, visited.size() * sizeof(int));
    q.push(start);
    visited[start] = 1;

    while (!q.empty()) {
        int current = q.front();
        q.pop();
        cout << current << " ";

        for (int neighbor : p[current]) {
            if (!visited[neighbor]) {
                visited[neighbor] = 1;
                q.push(neighbor);
            }
        }
    }
}



int main() {
    cin >> n >> m;

    for (int i = 0; i < m; i++) {
        int u, v;
        cin >> u >> v;
        p[u].push_back(v);
    }

    for (int i = 1; i <= n; i++) {
        sort(p[i].begin(), p[i].end());
    }

    dfs(1, 0);

    cout << endl;

    bfs(1);

    return 0;
}