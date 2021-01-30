#include <bits/stdc++.h>
using namespace std;
using pii = pair<int, int>;

int d[3][205];
int INF = 1e8 + 10;
vector<pii> adj[205];

void dijkstra(int st, int idx){
    priority_queue<pii, vector<pii>, greater<pii>> pq;
    fill(d[idx], d[idx]+201, INF);
    d[idx][st]= 0;
    pq.push({d[idx][st], st});
    while(!pq.empty()){
        auto cur = pq.top(); pq.pop();
        int dist = cur.first, i = cur.second;
        if(d[idx][i] != dist) continue;
        for(auto nxt : adj[i]){
            int cost = nxt.first, ni = nxt.second;
            if(d[idx][ni] > dist+cost){
                d[idx][ni] = dist+cost;
                pq.push({d[idx][ni], ni});
            }
        }
    }
}

int solution(int n, int s, int a, int b, vector<vector<int>> fares) {
    for(auto v : fares){
        adj[v[0]].push_back({v[2], v[1]});
        adj[v[1]].push_back({v[2], v[0]});
    }
    dijkstra(s, 0);
    dijkstra(a, 1);
    dijkstra(b, 2);    
    int ans = INF;
    for(int mid = 1; mid <= n; mid++)
        ans = min(ans,d[0][mid]+d[1][mid]+d[2][mid]);
    return ans;
}
