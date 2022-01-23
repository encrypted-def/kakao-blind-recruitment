import java.util.*;

class Solution {
    
    // 왼쪽 자식, 오른쪽 자식, 양/늑대 값
    int[] l = new int[20];
    int[] r = new int[20];
    int[] val = new int[20];
    int n;
    int ans = 1;
    int[] vis = new int[1<<17]; // vis[x] : 상태 x를 방문했는가?
    
    // 상태에 대한 dfs 함수
    void solve(int state){
        if(vis[state] == 1) return;
        vis[state] = 1;
        // wolf : 늑대의 수, num : 전체 정점의 수
        int wolf = 0, num = 0;
        for(int i = 0; i < n; i++){
            if((state & (1<<i)) != 0){
                num++;
                wolf+=val[i];
            }
        }
        // 만약 늑대가 절반 이상일 경우 방문할 수 없는 상태이니 종료
        if(2*wolf >= num) return;
        // 현재 state의 양의 수가 ans보다 클 경우 ans를 갱신
        ans = Math.max(ans,num-wolf);
        // 이제 다음 상태로 넘어갈 시간
        for(int i = 0; i < n; i++){
            // i번째 비트가 꺼져있는 경우 해당 정점이 없으니 넘어감
            if((state & (1<<i)) == 0) continue;
            // 현재 보고 있는 i번째 정점의 left가 있다면
            if(l[i] != -1)
                solve(state | (1<<l[i]));
            // 현재 보고 있는 i번째 정점의 right가 있다면 
            if(r[i] != -1)
                solve(state | (1<<r[i]));
        }
    }
    
    
    public int solution(int[] info, int[][] edges) {
        n = info.length;
        Arrays.fill(l, -1);
        Arrays.fill(r, -1);
        for(int i = 0; i < n; i++) val[i] = info[i];
        for(int i = 0; i < n-1; i++){
            int a = edges[i][0]; // 부모
            int b = edges[i][1]; // 자식
            if(l[a] == -1) l[a] = b;
            else r[a] = b;
        }
        solve(1); // 0번 노드만 포함된 상태에서 dfs 시작
        return ans;
    }
}
