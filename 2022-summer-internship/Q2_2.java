import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int n = queue1.length;
        Deque<Integer> q1 = new ArrayDeque();
        Deque<Integer> q2 = new ArrayDeque();
        long tot1 = 0, tot2 = 0;
        for(int i = 0; i < n; i++){
            tot1 += queue1[i];
            q1.add(queue1[i]);
        }
        for(int i = 0; i < n; i++){
            tot2 += queue2[i];
            q2.add(queue2[i]);
        }
        for(int i = 0; i < 4*n + 1; i++){
            if(tot1 == tot2) return i;
            if(tot1 < tot2){
                int x = q2.getFirst();
                tot1 += x;
                tot2 -= x;
                q1.add(x);
                q2.removeFirst();
            }
            else{
                int x = q1.getFirst();
                tot2 += x;
                tot1 -= x;
                q2.add(x);
                q1.removeFirst();
            }
        }
        return -1;
    }
}
