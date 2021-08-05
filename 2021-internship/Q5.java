import java.util.*;

class Solution {
    int l[] = new int[10005]; // 왼쪽 자식 노드 번호
    int r[] = new int[10005]; // 오른쪽 자식 노드 번호
    int x[] = new int[10005]; // 시험장의 응시 인원
    int p[] = new int[10005]; // 부모 노드 번호
    int n; // 노드의 수
    int root; // 루트
    
    int cnt = 0;
    public int dfs(int cur, int lim){
        int lv = 0; // 왼쪽 자식 트리에서 넘어오는 인원 수
        if(l[cur] != -1) lv = dfs(l[cur], lim);
        int rv = 0; // 오른쪽 자식 트리에서 넘어오는 인원 수
        if(r[cur] != -1) rv = dfs(r[cur], lim);
        // 1. 왼쪽 자식 트리와 오른쪽 자식 트리에서 넘어오는 인원을 모두 합해도 lim 이하일 경우
        if(x[cur] + lv + rv <= lim)
            return x[cur] + lv + rv; // 위로 떠넘김
        // 2. 왼쪽 자식 트리와 오른쪽 자식 트리에서 넘어오는 인원 중 작은 것을 합해도 lim 이하일 경우
        if(x[cur] + Math.min(lv, rv) <= lim){
            cnt++; // 둘 중 큰 인원은 그룹을 지어버림
            return x[cur] + Math.min(lv, rv);
        }
        // 3. 1, 2 둘 다 아닐 경우
        cnt += 2; // 왼쪽 자식 트리와 오른쪽 자식 트리 각각을 따로 그룹을 만듬
        return x[cur];
    }
    
    public int solve(int lim){
        cnt = 0;
        dfs(root, lim);
        cnt++; // 맨 마지막으로 남은 인원을 그룹을 지어야 함
        return cnt;
    }
    
    public int solution(int k, int[] num, int[][] links) {
        n = num.length;
        for(int i = 0; i < n; i++) p[i] = -1;
        for(int i = 0; i < n; i++){
            l[i] = links[i][0];
            r[i] = links[i][1];
            x[i] = num[i];
            if(l[i] != -1) p[l[i]] = i;
            if(r[i] != -1) p[r[i]] = i;
        }
        for(int i = 0; i < n; i++){
            if(p[i] == -1){
                root = i;
                break;
            }
        }        
        int st = x[0];
        for(int i = 0; i < n; i++) st = Math.max(st, x[i]);
        int en = (int)1e8;
        while(st < en){
            int mid = (st+en)/2;
            if(solve(mid) <= k) en = mid;
            else st = mid+1;
        }
        return st;
    }
}
