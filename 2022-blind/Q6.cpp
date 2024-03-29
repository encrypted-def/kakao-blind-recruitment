#include <bits/stdc++.h>
using namespace std;

int d[1003][1003];

int solution(vector<vector<int>> board, vector<vector<int>> skill) {
    int n = board.size();
    int m = board[0].size();
    // 변화량 테이블에 스킬 기록
    for(auto& v : skill){
        int type = v[0], r1 = v[1], c1 = v[2],
              r2 = v[3], c2 = v[4], degree = v[5];
        if(type == 1) degree = -degree;
        d[r1][c1] += degree;
        d[r1][c2+1] -= degree;
        d[r2+1][c1] -= degree;
        d[r2+1][c2+1] += degree;
    }
    // 누적합 처리
    for(int i = 1; i < n; i++)
        for(int j = 0; j < m; j++)
            d[i][j] += d[i-1][j];
    
    for(int i = 0; i < n; i++)
        for(int j = 1; j < m; j++)
            d[i][j] += d[i][j-1];
    
    int ans = 0;
    for(int i = 0; i < n; i++)
        for(int j = 0; j < m; j++)
            if(d[i][j]+board[i][j] > 0) ans++;
    return ans;
}
