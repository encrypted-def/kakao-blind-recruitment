import java.util.*;
class Solution {
    static class pair{
        public int x, y;
        pair(int x, int y){this.x = x; this.y = y;}
    }
    
    static int dx[] = {1,-1,0,0};
    static int dy[] = {0,0,1,-1};
    
    static boolean OOB(int x, int y){
        return x < 0 || x > 3 || y < 0 || y > 3;
    }
    
    static int dist(int[][] board, pair src, pair dst){
        int d[][] = new int[4][4];
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                d[i][j] = -1;
        d[src.x][src.y] = 0;
        Queue<pair> q = new LinkedList<>();
        q.add(new pair(src.x, src.y));
        while(!q.isEmpty()){
            pair cur = q.poll();
            for(int dir = 0; dir < 4; dir++){
                int en = 0; // dir 방향으로 진행할 때 카드 혹은 마지막 칸까지의 거리
                while(true){
                    int nx = cur.x + dx[dir] * en;
                    int ny = cur.y + dy[dir] * en;
                    if(OOB(nx+dx[dir], ny+dy[dir]) || (en != 0 && board[nx][ny] != 0)) break;
                    en++;
                }
                for(int i = 0; i < 2; i++){
                    int z = 1;
                    if(i == 1) z = en;
                    int nx = cur.x + dx[dir] * z;
                    int ny = cur.y + dy[dir] * z;
                    if(OOB(nx, ny)) continue;
                    if(d[nx][ny] == -1){
                        d[nx][ny] = d[cur.x][cur.y] + 1;
                        q.add(new pair(nx, ny));
                    }
                }                
            }
        }
        return d[dst.x][dst.y];
    }
    
    static int p[] = new int[6];
    static boolean isused[] = new boolean[7];
    static int n = 0;
    static int ans = 9999999;
    static pair occur[][] = new pair[7][2];
    static int[][] bboard = new int[4][4];
    static int rr, cc;
    static void solve(int idx){
        if(idx == n){ // p[0], p[1], ... , p[n-1]에 permutation이 다 채워짐
            int myboard[][] = new int[4][4];
            for(int i = 0; i < 4; i++)
                for(int j = 0; j < 4; j++)
                    myboard[i][j] = bboard[i][j];
            int d[][] = new int[6][2];
            
            d[0][0] = dist(myboard, new pair(rr, cc), occur[p[0]][0]) + dist(myboard, occur[p[0]][0], occur[p[0]][1]);
            d[0][1] = dist(myboard, new pair(rr, cc), occur[p[0]][1]) + dist(myboard, occur[p[0]][1], occur[p[0]][0]);
            myboard[occur[p[0]][0].x][occur[p[0]][0].y] = 0;
            myboard[occur[p[0]][1].x][occur[p[0]][1].y] = 0;
            for(int i = 1; i < n; i++){
                d[i][0] = Math.min(d[i-1][0] + dist(myboard, occur[p[i-1]][1], occur[p[i]][0]), d[i-1][1] + dist(myboard, occur[p[i-1]][0], occur[p[i]][0])) + dist(myboard, occur[p[i]][0], occur[p[i]][1]);
                d[i][1] = Math.min(d[i-1][0] + dist(myboard, occur[p[i-1]][1], occur[p[i]][1]), d[i-1][1] + dist(myboard, occur[p[i-1]][0], occur[p[i]][1])) + dist(myboard, occur[p[i]][1], occur[p[i]][0]);
                myboard[occur[p[i]][0].x][occur[p[i]][0].y] = 0;
                myboard[occur[p[i]][1].x][occur[p[i]][1].y] = 0;    
            }
            ans = Math.min(Math.min(ans, d[n-1][0]), d[n-1][1]);
        }
        for(int i = 1; i <= 6; i++){
            if(occur[i][0].x == -1 || isused[i]) continue;
            p[idx] = i;
            isused[i] = true;
            solve(idx+1);
            isused[i] = false;            
        }
    }
    
    public int solution(int[][] board, int r, int c) {
        rr = r; cc = c;
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                bboard[i][j] = board[i][j];
        for(int i = 0; i < 7; i++){
            occur[i][0] = new pair(-1, -1);
            occur[i][1] = new pair(-1, -1);      
        }
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(board[i][j] == 0) continue;
                if(occur[board[i][j]][0].x == -1){
                    occur[board[i][j]][0] = new pair(i, j);
                    n++;
                }
                else
                    occur[board[i][j]][1] = new pair(i, j);
            }
        }
        solve(0);
        return ans + 2*n;
    }
}
