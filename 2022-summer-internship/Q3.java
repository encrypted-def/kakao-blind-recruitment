import java.util.*;

// d[i][j] : 알고력 i, 코딩력 j를 달성하는데 필요한 최소 시간
// 단 d[alp_max][j]는 알고력이 alp_max "이상", 코딩력 j를 달성하는데 필요한 최소 시간, d[i][cop_max]는 알고력 j, 코딩력 cop_max "이상"을 달성하는데 필요한 최소 시간

class Solution {
    public int solution(int alp, int cop, int[][] problems) {
        int[][] d = new int[155][155];
        final int MX = 0x7f7f7f7f;
        
        int alp_max = -1, cop_max = -1;
        for(int i = 0; i < problems.length; i++){
            alp_max = Math.max(alp_max, problems[i][0]);
            cop_max = Math.max(cop_max, problems[i][1]);
        }
        alp = Math.min(alp, alp_max);
        cop = Math.min(cop, cop_max); // 초기의 알고력(코딩력) 자체가 alp_max(cop_max)보다 높은 경우에 대한 예외처리
        for(int i = 0; i <= alp_max; i++)
            for(int j = 0; j <= cop_max; j++)
                d[i][j] = MX;
        d[alp][cop] = 0; // dp의 초기값
        for(int i = alp; i <= alp_max; i++){
            for(int j = cop; j <= cop_max; j++){
                d[i+1][j] = Math.min(d[i+1][j], d[i][j]+1);
                d[i][j+1] = Math.min(d[i][j+1], d[i][j]+1);
                for(int k = 0; k < problems.length; k++){
                    int alp_req, cop_req, alp_rwd, cop_rwd, cost;
                    alp_req = problems[k][0];
                    cop_req = problems[k][1];
                    alp_rwd = problems[k][2];
                    cop_rwd = problems[k][3];
                    cost = problems[k][4];
                    if(i < alp_req || j < cop_req) continue;
                    int alp_nxt = Math.min(alp_max, i + alp_rwd);
                    int cop_nxt = Math.min(cop_max, j + cop_rwd);
                    d[alp_nxt][cop_nxt] = Math.min(d[alp_nxt][cop_nxt], cost + d[i][j]);
                }
            }
        }
        return d[alp_max][cop_max]; 
    }
}
