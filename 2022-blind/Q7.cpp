#include <bits/stdc++.h>
using namespace std;

int dx[4] = {0,0,-1,1};
int dy[4] = {-1,1,0,0};

int n, m;

bool OOB(int x, int y){
    return x<0 || x>=n || y<0 || y>=m;
}

bool vis[5][5]; // 방문 여부(0일 경우 해당 칸이 아직 남아있음을 의미)
vector<vector<int>> block;

// 현재 상태에서 둘 다 최적의 플레이를 할 때 남은 이동 횟수
// 반환 값이 짝수 : 플레이어가 패배함을 의미, 홀수 : 플레이어가 승리함을 의미
// curx, cury : 현재 플레이어의 좌표, opx, opy : 상대 플레이어의 좌표
int solve(int curx, int cury, int opx, int opy){
    // 플레이어가 밟고 있는 발판이 사라졌다면
    if(vis[curx][cury]) return 0;    
    int ret = 0;
    // 플레이어를 네 방향으로 이동시켜 다음 단계로 진행할 예정
    for(int dir = 0; dir < 4; dir++){
        int nx = curx + dx[dir];
        int ny = cury + dy[dir];
        if(OOB(nx,ny) || vis[nx][ny] || !block[nx][ny]) continue;
        vis[curx][cury] = 1; // 플레이어가 직전에 있던 곳에 방문 표시를 남김
        
        // 플레이어를 dir 방향으로 이동시켰을 때 턴의 수
        // 다음 함수를 호출할 때 opx, opy, nx, ny 순으로 호출해야 함에 주의
        int val = solve(opx, opy, nx, ny)+1;      
        
        // 방문 표시 해제
        vis[curx][cury] = 0;        
        
        // 1. 현재 저장된 턴은 패배인데 새로 계산된 턴은 승리인 경우
        if(ret % 2 == 0 && val % 2 == 1) ret = val; // 바로 갱신
        // 2. 현재 저장된 턴과 새로 계산된 턴이 모두 패배인 경우
        else if(ret % 2 == 0 && val % 2 == 0) ret = max(ret, val); // 최대한 늦게 지는걸 선택
        // 3. 현재 저장된 턴과 새로 계산된 턴이 모두 승리인 경우
        else if(ret % 2 == 1 && val % 2 == 1) ret = min(ret, val); // 최대한 빨리 이기는걸 선택
        
    }
    return ret;
}

int solution(vector<vector<int>> board, vector<int> aloc, vector<int> bloc) {
    n = board.size();
    m = board[0].size();
    block = board;
    return solve(aloc[0], aloc[1], bloc[0], bloc[1]);
}
