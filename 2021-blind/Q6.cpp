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

    vector<int> p;
    for(int i = 1; i <= 6; i++){
        if(!occur[i].empty()) p.push_back(i);
    }
    int n = p.size(); // 카드 종류의 개수
    int ans = 99999999;
    do{ // 제거할 종류의 순서에 대한 permutation
        vector<vector<int>> myboard = board;
        int d[6][2];
        d[0][0] = dist(myboard, {r, c}, occur[p[0]][0]) + dist(myboard, occur[p[0]][0], occur[p[0]][1]);
        d[0][1] = dist(myboard, {r, c}, occur[p[0]][1]) + dist(myboard, occur[p[0]][1], occur[p[0]][0]);
        myboard[occur[p[0]][0].X][occur[p[0]][0].Y] = 0;
        myboard[occur[p[0]][1].X][occur[p[0]][1].Y] = 0;
        for(int i = 1; i < n; i++){
             d[i][0] = min(d[i-1][0] + dist(myboard, occur[p[i-1]][1], occur[p[i]][0]), d[i-1][1] + dist(myboard, occur[p[i-1]][0], occur[p[i]][0])) + dist(myboard, occur[p[i]][0], occur[p[i]][1]);
            d[i][1] = min(d[i-1][0] + dist(myboard, occur[p[i-1]][1], occur[p[i]][1]), d[i-1][1] + dist(myboard, occur[p[i-1]][0], occur[p[i]][1])) + dist(myboard, occur[p[i]][1], occur[p[i]][0]);
            myboard[occur[p[i]][0].X][occur[p[i]][0].Y] = 0;
            myboard[occur[p[i]][1].X][occur[p[i]][1].Y] = 0;        
        }
        ans = min({ans, d[n-1][0], d[n-1][1]});        
    }while(next_permutation(p.begin(), p.end()));
    return ans + 2*n;
}
