import java.util.*;
import java.lang.*;
class Solution {
    static class pair implements Comparable<pair>{
        int idx, weight;
        
        public pair(int idx, int weight){this.idx = idx; this.weight = weight;}

        @Override
        public int compareTo(pair o) {
            if (weight > o.weight) return 1;
            if (weight == o.weight) return 0;
            return -1;
        }
    }
    static int d[][];
    static int INF = (int)1e8 + 10;
    static List<pair> adj[];
    static void dijkstra(int st, int idx){
        for(int i = 1; i <= 200; i++) d[idx][i] = INF;
        d[idx][st] = 0;
        PriorityQueue<pair> queue = new PriorityQueue<>();
        queue.add(new pair(st, 0));
        while(!queue.isEmpty()){
            pair cur = queue.poll();
            if(d[idx][cur.idx] != cur.weight) continue;
            for(int i = 0; i < adj[cur.idx].size(); i++){
                pair nxt = adj[cur.idx].get(i);
                if(d[idx][nxt.idx] > d[idx][cur.idx] + nxt.weight){
                    d[idx][nxt.idx] = d[idx][cur.idx] + nxt.weight;
                    queue.add(new pair(nxt.idx, d[idx][nxt.idx]));
                }
            }
        }
    }
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        d = new int[3][205];
        adj = new ArrayList[n+1];
        for(int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
        for(int i = 0; i < fares.length; i++){
            adj[fares[i][0]].add(new pair(fares[i][1], fares[i][2]));
            adj[fares[i][1]].add(new pair(fares[i][0], fares[i][2]));
        }
        dijkstra(s, 0);
        dijkstra(a, 1);
        dijkstra(b, 2);            
        int ans = INF;
        for(int mid = 1; mid <= n; mid++)
            ans = Math.min(ans,d[0][mid]+d[1][mid]+d[2][mid]);
        return ans;
    }
}
