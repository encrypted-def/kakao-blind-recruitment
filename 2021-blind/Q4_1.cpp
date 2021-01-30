#include <bits/stdc++.h>
using namespace std;

int d[205][205];
int INF = 1e8+10;
int solution(int n, int s, int a, int b, vector<vector<int>> fares) {
    for(int i = 1; i <= n; i++) fill(d[i], d[i]+1+n, INF);
    for(auto v : fares){
        d[v[0]][v[1]] = v[2];
        d[v[1]][v[0]] = v[2];
    }
    for(int i = 1; i <= n; i++) d[i][i] = 0;
    for(int k = 1; k <= n; k++)
        for(int i = 1; i <= n; i++)
            for(int j = 1; j <= n; j++)
                d[i][j] = min(d[i][j], d[i][k]+d[k][j]);

    int ans = INF;
    for(int mid = 1; mid <= n; mid++)
        ans = min(ans,d[s][mid]+d[mid][a]+d[mid][b]);
    return ans;
}
