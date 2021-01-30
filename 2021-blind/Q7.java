import java.util.*;
class Solution {
    static ArrayList<Integer>[] adj = new ArrayList[300005];
    static ArrayList<Integer> s = new ArrayList<Integer>();
    static long INF = 0x7fffffff7fffffffL;
    static long d[][] = new long[300005][2];
    static void dfs(int cur){
        if(adj[cur].isEmpty()){
            d[cur][0] = s.get(cur);
            d[cur][1] = 0;
            return;
        }
        long mingap = INF;
        d[cur][0] = s.get(cur);
        for(Integer x : adj[cur]){
            dfs(x);
            d[cur][0] += Math.min(d[x][0], d[x][1]);
            mingap = Math.min(mingap, d[x][0] - d[x][1]);
        }
        if(mingap < 0) mingap = 0;
        d[cur][1] = d[cur][0] + mingap - s.get(cur);
    }
    public int solution(int[] sales, int[][] links) {
        int n = sales.length;
        for(int i = 1; i <= n; i++) adj[i] = new ArrayList<Integer>();
        s.add(0);
        for(int i = 0; i < n; i++) s.add(sales[i]);
        for(int i = 0; i < n-1; i++)
            adj[links[i][0]].add(links[i][1]);            
        dfs(1);
        return (int)Math.min(d[1][0], d[1][1]);
    }
}
