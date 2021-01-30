#include <bits/stdc++.h>
using namespace std;
using pii = pair<int,int>;
#define X first
#define Y second

int dx[4] = {1,-1,0,0};
int dy[4] = {0,0,-1,1};

bool OOB(int x, int y){
    return x < 0 || x > 3 || y < 0 || y > 3;
}

int dist(vector<vector<int>>& board, pii src, pii dst){
    int d[4][4];
    for(int i = 0; i < 4; i++) for(int j = 0; j < 4; j++) d[i][j]=-1;
    d[src.X][src.Y] = 0;
    queue<pii> q;
    q.push(src);
    while(!q.empty()){
        auto cur = q.front(); q.pop();
        for(int dir = 0; dir < 4; dir++){
            int en = 0; // dir 방향으로 진행할 때 카드까지의 거리
            while(true){
                int nx = cur.X+dx[dir]*en;
                int ny = cur.Y+dy[dir]*en;
                if(OOB(nx+dx[dir],ny+dy[dir]) || (en != 0 && board[nx][ny] != 0)) break;
                en++;
            }
            for(int z : {1, en}){
                int nx = cur.X+dx[dir]*z;
                int ny = cur.Y+dy[dir]*z;
                if(OOB(nx,ny)) continue;
                if(d[nx][ny] == -1)
                {
                  d[nx][ny] = d[cur.X][cur.Y]+1;
                  q.push({nx,ny});
                }               
            }
        }
    }    
    return d[dst.X][dst.Y];
}
int solution(vector<vector<int>> board, int r, int c) {
    vector<pii> occur[7];

    for(int i = 0; i < 4; i++) for(int j = 0; j < 4;j++){
        if(board[i][j]==0) continue;
        occur[board[i][j]].push_back({i,j});
    }

    vector<int> brute;
    for(int i = 1; i <= 6; i++){
        if(!occur[i].empty()) brute.push_back(i);
    }
    int n = brute.size(); // 카드 종류의 개수
    int ans = 99999999;
    do{ // 제거할 종류의 순서
        for(int i = 0; i < (1<<n); i++){ // 2장씩 있는 각 종류에 대해 둘 중에서 어느 것을 먼저 제거할지
            vector<vector<int>> myboard = board;
            vector<pii> visOrder;
            visOrder.push_back({r, c});
            for(int j = 0; j < n; j++){
                if(i & (1<<j)){
                    visOrder.push_back(occur[brute[j]][0]);
                    visOrder.push_back(occur[brute[j]][1]);
                }
                else{
                    visOrder.push_back(occur[brute[j]][1]);
                    visOrder.push_back(occur[brute[j]][0]);
                }
            }
            int cur = 2*n; // enter
            for(int j = 1; j < visOrder.size(); j++){
                cur += dist(myboard, visOrder[j-1], visOrder[j]);
                myboard[visOrder[j].X][visOrder[j].Y] = 0;
            }            
            ans = min(ans,cur);
        }
    }while(next_permutation(brute.begin(), brute.end()));
    return ans;
}
