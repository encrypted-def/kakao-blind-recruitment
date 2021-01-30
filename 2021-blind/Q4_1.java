import java.lang.*;
class Solution {
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int d[][] = new int[205][205];
        int INF = (int)1e8 + 10;      
        for(int i = 1; i <= n; i++)
            for(int j = 1; j <= n; j++)
                d[i][j] = INF;
        
        for(int i = 0; i < fares.length; i++){
            d[fares[i][0]][fares[i][1]] = fares[i][2];
            d[fares[i][1]][fares[i][0]] = fares[i][2];
        }
        for(int i = 1; i <= n; i++) d[i][i] = 0;
        for(int k = 1; k <= n; k++)
            for(int i = 1; i <= n; i++)
                for(int j = 1; j <= n; j++)
                    d[i][j] = Math.min(d[i][j], d[i][k]+d[k][j]);
            
        int ans = INF;
        for(int mid = 1; mid <= n; mid++)
            ans = Math.min(ans,d[s][mid]+d[mid][a]+d[mid][b]);
        return ans;
    }
}
