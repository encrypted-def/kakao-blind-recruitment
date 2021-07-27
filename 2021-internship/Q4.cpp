#include <bits/stdc++.h>
using namespace std;

int d[1004][1024]; // d[i][state] : (상태 0, start번 노드)에서 (상태 state, i번 노드)로 갈 때의 최단경로 
vector<pair<int,int>> adj[1004]; // 정방향 간선(번호, 시간)
vector<pair<int,int>> adjrev[1004]; // 역방향 간선(번호, 시간)
int trapidx[1004]; // trapidx[i] : i번 노드의 함정 번호. 함정은 0번부터 차례로 번호가 부여되어 있으며 i번 노드가 함정이 아닐 경우 -1

// 상태 state에 i번 비트가 켜져있는지를 반환하는 함수
bool bitmask(int state, int idx){
    return (1 << trapidx[idx]) & state;
}

int solution(int n, int start, int end, vector<vector<int>> roads, vector<int> traps) {
    for(auto road : roads){
        int u = road[0];
        int v = road[1];
        int val = road[2];
        adj[u].push_back({v,val});
        adjrev[v].push_back({u,val});        
    }
    // trapidx 값 지정
    fill(trapidx+1,trapidx+n+1,-1);
    for(int i = 0; i < traps.size(); i++) trapidx[traps[i]] = i;
    for(int i = 1; i <= n; i++) fill(d[i], d[i]+1024,0x7f7f7f7f);
    d[start][0] = 0;
    // priority queue를 작은 원소가 높은 우선순위를 만들게끔 하기 위한 선언
    priority_queue<tuple<int,int,int>, vector<tuple<int,int,int>>, greater<tuple<int,int,int>>> pq;
    pq.push({d[start][0], start, 0});
    while(!pq.empty()){
        int val, idx, state;
        tie(val, idx, state) = pq.top();
        pq.pop();
        // pq에서 뽑히는 원소는 가까운 순이라는 점을 이용해서 맨 마지막에 d[..][end]를 for문으로 순회하지 않아도 되게 idx1 == end일 때 바로 답을 반환
        if(idx == end) return val;
        if(d[idx][state] != val) continue;
        for(auto p : adj[idx]){ // 정방향 간선에 대한 확인
            int nxt = p.first; // 간선으로 연결된 다음 방
            int dist = p.second;
            int rev = 0;
            if(trapidx[idx] != -1 && bitmask(state, idx)) rev ^= 1; // 현재 idx번 trap을 밟은 상태라면 rev 상태를 뒤집음
            if(trapidx[nxt] != -1 && bitmask(state, nxt)) rev ^= 1; // 현재 nxt번 trap을 밟은 상태라면 rev 상태를 뒤집음
            if(rev) continue; // 정방향 간선을 보고 있으므로 trap을 1개만 밟은 상황일 경우 넘어감
            int nxt_state = state;
            if(trapidx[nxt] != -1) nxt_state ^= (1 << trapidx[nxt]);
            if(d[nxt][nxt_state] > dist + val){
                d[nxt][nxt_state] = dist + val;
                pq.push({d[nxt][nxt_state],nxt,nxt_state});
            }            
        }  

        for(auto p : adjrev[idx]){ // 역방향 간선에 대한 확인
            int nxt = p.first; // 간선으로 연결된 다음 방
            int dist = p.second;
            int rev = 0;
            if(trapidx[idx] != -1 && bitmask(state, idx)) rev ^= 1; // 현재 idx번 trap을 밟은 상태라면 rev 상태를 뒤집음
            if(trapidx[nxt] != -1 && bitmask(state, nxt)) rev ^= 1; // 현재 nxt번 trap을 밟은 상태라면 rev 상태를 뒤집음
            if(!rev) continue; // 역방향 간선을 보고 있으므로 trap을 0개 or 2개 밟은 상황일 경우 넘어감
            int nxt_state = state;
            if(trapidx[nxt] != -1) nxt_state ^= (1 << trapidx[nxt]);
            if(d[nxt][nxt_state] > dist + val){
                d[nxt][nxt_state] = dist + val;
                pq.push({d[nxt][nxt_state],nxt,nxt_state});
            }            
        }
    }
    return -1; // unreachable
}
