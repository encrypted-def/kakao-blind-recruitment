#include <bits/stdc++.h>
using namespace std;

// d[i][j] : 알고력 i, 코딩력 j를 달성하는데 필요한 최소 시간
// 단 d[alp_max][j]는 알고력이 alp_max "이상", 코딩력 j를 달성하는데 필요한 최소 시간, d[i][cop_max]는 알고력 j, 코딩력 cop_max "이상"을 달성하는데 필요한 최소 시간

int d[155][155];

const int MX = 0x7f7f7f7f;

int solution(int alp, int cop, vector<vector<int>> problems) {
    int alp_max = -1, cop_max = -1;
    for(auto& problem : problems){
        alp_max = max(alp_max, problem[0]);
        cop_max = max(cop_max, problem[1]);
    }
    alp = min(alp, alp_max); 
    cop = min(cop, cop_max); // 초기의 알고력(코딩력) 자체가 alp_max(cop_max)보다 높은 경우에 대한 예외처리
    for(int i = 0; i <= alp_max; i++) fill(d[i], d[i]+cop_max+1, MX);
    d[alp][cop] = 0; // dp의 초기값
    for(int i = alp; i <= alp_max; i++){
        for(int j = cop; j <= cop_max; j++){
            d[i+1][j] = min(d[i+1][j], d[i][j]+1);
            d[i][j+1] = min(d[i][j+1], d[i][j]+1);
            for(auto& problem : problems){
                int alp_req, cop_req, alp_rwd, cop_rwd, cost;
                alp_req = problem[0];
                cop_req = problem[1];
                alp_rwd = problem[2];
                cop_rwd = problem[3];
                cost = problem[4];
                if(i < alp_req || j < cop_req) continue;
                int alp_nxt = min(alp_max, i + alp_rwd);
                int cop_nxt = min(cop_max, j + cop_rwd);
                d[alp_nxt][cop_nxt] = min(d[alp_nxt][cop_nxt], cost + d[i][j]);
            }
        }
    }
    return d[alp_max][cop_max];
}
