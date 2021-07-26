#include <bits/stdc++.h>

using namespace std;

int dx1[4] = {1, 0, -1, 0};
int dy1[4] = {0, 1, 0, -1};

int dx2[4] = {1, -1, 1, -1};
int dy2[4] = {1, 1, -1, -1};

bool OOB(int x, int y){
    return x<0 || x>4 || y<0 || y>4;
}

int chk(vector<string>& board){
    for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
            if(board[i][j] != 'P') continue;
            for(int dir = 0; dir < 4; dir++){
                int nx = i+dx1[dir];
                int ny = j+dy1[dir];
                if(!OOB(nx,ny) && board[nx][ny]=='P') return 0;
            }
            for(int dir = 0; dir < 4; dir++){
                int nx = i+dx2[dir];
                int ny = j+dy2[dir];
                if(!OOB(nx,ny) && board[nx][ny]=='P'){
                    if(board[i][ny] != 'X' || board[nx][j] != 'X') return 0;
                }
            }
            for(int dir = 0; dir < 4; dir++){
                int nx = i+2*dx1[dir];
                int ny = j+2*dy1[dir];
                if(!OOB(nx,ny) && board[nx][ny]=='P'){
                    if(board[i+dx1[dir]][j+dy1[dir]] != 'X') return 0;
                }
            }
        }
    }
    return 1;
}

vector<int> solution(vector<vector<string>> places) {
    vector<int> answer;
    for(auto& p : places) answer.push_back(chk(p));
    return answer;
}
