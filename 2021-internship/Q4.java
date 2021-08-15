import java.util.*;
class Solution {
    
    class tuple implements Comparable<tuple>{
        int val, idx, state;
        
        public tuple(int val, int idx, int state){
            this.val = val; this.idx = idx; this.state = state;
        }

        @Override
        public int compareTo(tuple o) {
            if (val > o.val) return 1;
            if (val == o.val) return 0;
            return -1;
        }
    }
    
    class pair {
        int x, y;
        pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    final int INF = 0x7f7f7f7f;
    int d[][] = new int[1004][1024]; // d[i][state] : (상태 0, start번 노드)에서 (상태 state, i번 노드)로 갈 때의 최단경로
    List<pair> adj[] = new ArrayList[1004]; // 정방향 간선(번호, 시간)
    List<pair> adjrev[] = new ArrayList[1004]; // 역방향 간선(번호, 시간)
    int trapidx[] = new int[1004];
    
    // 상태 state에 i번 비트가 켜져있는지를 반환하는 함수
    boolean bitmask(int state, int idx){
        return ((1 << trapidx[idx]) & state) != 0;
    }
    
    public int solution(int n, int start, int end, int[][] roads, int[] traps) {
        // d값을 INF로 초기화
        for(int i = 1; i <= n; i++){
            for(int j = 0; j < 1024; j++)
                d[i][j] = INF;
        }
        // adj, adjrev에 대한 초기화
        for(int i = 1; i <= n; i++){
            adj[i] = new ArrayList<>();
            adjrev[i] = new ArrayList<>();            
        }
        // trapidx에 대한 초기화
        for(int i = 1; i <= n; i++) trapidx[i] = -1;
        for(int i = 0; i < traps.length; i++)
            trapidx[traps[i]] = i;
        
        // 통로 처리
        for(int i = 0; i < roads.length; i++){
            int u = roads[i][0];
            int v = roads[i][1];
            int val = roads[i][2];
            adj[u].add(new pair(v, val));
            adjrev[v].add(new pair(u, val));            
        }
        
        d[start][0] = 0;
        PriorityQueue<tuple> pq = new PriorityQueue<>();        
        pq.add(new tuple(d[start][0], start, 0));
        while(!pq.isEmpty()){
            tuple cur = pq.poll();
            // pq에서 뽑히는 원소는 가까운 순이라는 점을 이용해서 맨 마지막에 d[..][end]를 for문으로 순회하지 않아도 되게 idx1 == end일 때 바로 답을 반환
            if(cur.idx == end) return cur.val;
            if(d[cur.idx][cur.state] != cur.val) continue;
            for(int i = 0; i < adj[cur.idx].size(); i++){ // 정방향 간선에 대한 확인
                pair nxt = adj[cur.idx].get(i); // 간선으로 연결된 다음 방
                int rev = 0;
                if(trapidx[cur.idx] != -1 && bitmask(cur.state, cur.idx)) rev ^= 1; // 현재 cur.idx번 trap을 밟은 상태라면 rev 상태를 뒤집음
                if(trapidx[nxt.x] != -1 && bitmask(cur.state, nxt.x)) rev ^= 1; // 현재 nxt.x번 trap을 밟은 상태라면 rev 상태를 뒤집음
                if(rev != 0) continue; // 정방향 간선을 보고 있으므로 trap을 1개만 밟은 상황일 경우 넘어감
                int nxt_state = cur.state;
                if(trapidx[nxt.x] != -1) nxt_state ^= (1 << trapidx[nxt.x]);
                if(d[nxt.x][nxt_state] > nxt.y + cur.val){
                    d[nxt.x][nxt_state] = nxt.y + cur.val;
                    pq.add(new tuple(d[nxt.x][nxt_state], nxt.x, nxt_state));
                }               
            }
            
            for(int i = 0; i < adjrev[cur.idx].size(); i++){ // 역방향 간선에 대한 확인
                pair nxt = adjrev[cur.idx].get(i); // 간선으로 연결된 다음 방
                int rev = 0;
                if(trapidx[cur.idx] != -1 && bitmask(cur.state, cur.idx)) rev ^= 1; // 현재 cur.idx번 trap을 밟은 상태라면 rev 상태를 뒤집음
                if(trapidx[nxt.x] != -1 && bitmask(cur.state, nxt.x)) rev ^= 1; // 현재 nxt.x번 trap을 밟은 상태라면 rev 상태를 뒤집음
                if(rev != 1) continue; // 역방향 간선을 보고 있으므로 trap을 0개 or 2개 밟은 상황일 경우 넘어감
                int nxt_state = cur.state;
                if(trapidx[nxt.x] != -1) nxt_state ^= (1 << trapidx[nxt.x]);
                if(d[nxt.x][nxt_state] > nxt.y + cur.val){
                    d[nxt.x][nxt_state] = nxt.y + cur.val;
                    pq.add(new tuple(d[nxt.x][nxt_state], nxt.x, nxt_state));
                }               
            }
        }
        return -1; // unreachable
    }
}
