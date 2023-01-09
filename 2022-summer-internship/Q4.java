import java.util.*;

class Solution {
    class pair implements Comparable<pair>{
        int val, idx;
        
        public pair(int val, int idx){
            this.val = val; this.idx = idx;
        }

        @Override
        public int compareTo(pair o) {
            if (val > o.val) return 1;
            if (val == o.val) return 0;
            return -1;
        }
    }
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        int d[] = new int[50005]; // d[i] : i번 지점까지의 intensity
        List<pair> adj[] = new ArrayList[50005]; // 간선(비용, 번호)
        for(int i = 1; i <= n; i++)
            adj[i] = new ArrayList<>();
        final int MX = 0x7f7f7f7f;
        boolean issummit[] = new boolean[50005]; // issummit[i] : i번 지점이 summit인지 여부
        
        for(int i = 0; i < summits.length; i++)
            issummit[summits[i]] = true;
        for(int i = 0; i < paths.length; i++){
            int u = paths[i][0];
            int v = paths[i][1];
            int w = paths[i][2];
            adj[u].add(new pair(w, v));
            adj[v].add(new pair(w, u));            
        }
        for(int i = 1; i <= n; i++)
            d[i] = MX;
        PriorityQueue<pair> pq = new PriorityQueue<>();
        for(int i = 0; i < gates.length; i++){
            d[gates[i]] = 0;
            pq.add(new pair(d[gates[i]], gates[i])); // 우선순위 큐에 (0, 출입구) 추가
        }
        while(!pq.isEmpty()){
            pair cur = pq.poll();
            if(d[cur.idx] != cur.val) continue;
            for(int i = 0; i < adj[cur.idx].size(); i++){
                pair nxt = adj[cur.idx].get(i);
                if(d[nxt.idx] <= Math.max(d[cur.idx], nxt.val)) continue;
                d[nxt.idx] = Math.max(d[cur.idx], nxt.val);
                if(!issummit[nxt.idx]) // nxt.Y가 산봉우리일 경우 pq에 넣지 않아 산봉우리를 타고 다른 산봉우리로 가는 것을 막음. 
                    pq.add(new pair(d[nxt.idx], nxt.idx));
            }
        }
        int ans = summits[0];
        for(int i = 0; i < summits.length; i++){
            if(d[summits[i]] < d[ans]) ans = summits[i];
            else if(d[summits[i]] == d[ans] && summits[i] < ans) ans = summits[i];
        }
        int ret[] = new int[2];
        ret[0] = ans;
        ret[1] = d[ans];
        return ret;
    }
}
