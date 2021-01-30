#include <bits/stdc++.h>
using namespace std;
using ll = long long;
vector<int> adj[300005];
vector<int> s;
int par[300005];
ll d[300005][2];
ll INF = 0x7fffffff7fffffff;
void dfs(int cur){
    if(adj[cur].empty()){ // leaf일 경우
        d[cur][0] = s[cur];
        d[cur][1] = 0;
        return;
    }
    ll mingap = INF;
    ll tmp = 0;
    d[cur][0] = s[cur];
    for(auto x : adj[cur]){
        dfs(x);
        d[cur][0] += min(d[x][0],d[x][1]);
        mingap = min(mingap, d[x][0] - d[x][1]);
    }
    if(mingap < 0) mingap = 0;
    d[cur][1] = d[cur][0] + mingap - s[cur];
}

int solution(vector<int> sales, vector<vector<int>> links) {
    s.push_back(0);
    for(auto x : sales) s.push_back(x);
    for(auto x : links){
        adj[x[0]].push_back(x[1]);
        par[x[1]] = x[0];
    }
    dfs(1);
    return min(d[1][0],d[1][1]);
}
