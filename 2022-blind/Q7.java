import java.util.*;

class Solution {
    int[] dx = {0,0,-1,1};
    int[] dy = {-1,1,0,0};

    int n, m;

    public boolean OOB(int x, int y){
        return x<0 || x>=n || y<0 || y>=m;
    }

    boolean[][] vis = new boolean[5][5]; // 방문 여부(0일 경우 해당 칸이 아직 남아있음을 의미)
    int[][] block = new int[5][5];

    // 현재 상태에서 둘 다 최적의 플레이를 할 때 남은 이동 횟수
    // 반환 값이 짝수 : 플레이어가 패배함을 의미, 홀수 : 플레이어가 승리함을 의미
    // curx, cury : 현재 플레이어의 좌표, opx, opy : 상대 플레이어의 좌표
    public int solve(int curx, int cury, int opx, int opy){
        // 플레이어가 밟고 있는 발판이 사라졌다면
        if(vis[curx][cury]) return 0;    
        int ret = 0;
        // 플레이어를 네 방향으로 이동시켜 다음 단계로 진행할 예정
        for(int dir = 0; dir < 4; dir++){
            int nx = curx + dx[dir];
            int ny = cury + dy[dir];
            if(OOB(nx,ny) || vis[nx][ny] || block[nx][ny] == 0) continue;
            vis[curx][cury] = true; // 플레이어가 직전에 있던 곳에 방문 표시를 남김

            // 플레이어를 dir 방향으로 이동시켰을 때 턴의 수
            // 다음 함수를 호출할 때 opx, opy, nx, ny 순으로 호출해야 함에 주의
            int val = solve(opx, opy, nx, ny)+1;      

            // 방문 표시 해제
            vis[curx][cury] = false;        

            // 1. 현재 저장된 턴은 패배인데 새로 계산된 턴은 승리인 경우
            if(ret % 2 == 0 && val % 2 == 1) ret = val; // 바로 갱신
            // 2. 현재 저장된 턴과 새로 계산된 턴이 모두 패배인 경우
            else if(ret % 2 == 0 && val % 2 == 0) ret = Math.max(ret, val); // 최대한 늦게 지는걸 선택
            // 3. 현재 저장된 턴과 새로 계산된 턴이 모두 승리인 경우
            else if(ret % 2 == 1 && val % 2 == 1) ret = Math.min(ret, val); // 최대한 빨리 이기는걸 선택

        }
        return ret;
    }
    
    public int solution(int[][] board, int[] aloc, int[] bloc) {
        n = board.length;
        m = board[0].length;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                block[i][j] = board[i][j];
        return solve(aloc[0], aloc[1], bloc[0], bloc[1]);
    }
}
