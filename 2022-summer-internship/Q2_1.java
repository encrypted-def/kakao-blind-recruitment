import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int n = queue1.length;
        int[] a = new int[2*n];
        for(int i = 0; i < n; i++){
            a[i] = queue1[i];
            a[n+i] = queue2[i];
        }
        long target = 0;
        for(int i = 0; i < 2*n; i++)
            target += a[i];
        if(target % 2 != 0) return -1;
        target /= 2; // 합이 target이 되는 구간을 찾아야 함
        
        int ans = 0x7f7f7f7f; // 일단 매우 큰 값을 넣어놓고 시작
        int en = 0;
        long tot = a[0]; // 현재 보는 구간의 합 = a[st]+...+a[en], 범위가 양 끝을 포함(inclusive)한다는 점과 en < st일 땐 a[st]+...+a[2*n-1]+a[0]+...+a[en]을 나타냄에 주의
        for(int st = 0; st < 2*n; st++){
            while(tot < target){
                en = (en + 1) % (2*n);
                tot += a[en];
            }
            // while문을 탈출하면 a[st]+...+a[en] 구간의 합이 target 이상임을 의미
            if(tot == target){ // 구간의 합이 정확히 target과 일치할 경우
                int moves; // 작업 횟수
                if(en < n-1) moves = 3*n + 1 + st + en; // 3n+1번의 연산을 통해 큐1에 첫 번째 원소만 남길 수 있고, 이후 st+en 번의 추가적인 연산을 거쳐 a[st] .. a[en]을 큐1에 둘 수 있음
                else moves = st + (en - n + 1);
                ans = Math.min(ans, moves);
            }
            tot -= a[st];
        }
        if(ans == 0x7f7f7f7f) ans = -1;
        return ans;
    }
}
