import java.util.*;

class Solution {
    // n을 k진법으로 나타낸 결과 반환
    ArrayList<Integer> conv(int n, int k){
        ArrayList<Integer> ret = new ArrayList();
        while(n > 0){
            ret.add(n % k);
            n /= k;
        }
        Collections.reverse(ret);
        return ret;
    }

    int isprime(long n){
        if(n <= 1) return 0;
        for(long i = 2; i*i <= n; i++)
            if(n % i == 0) return 0;
        return 1;
    }
    
    public int solution(int n, int k) {
        ArrayList<Integer> s = conv(n, k);
        long cur = 0; // 0이 나오기 전까지 수를 10진수로 읽음
        int ret = 0;
        for(int digit : s){
            if(digit == 0){
                ret += isprime(cur);
                cur = 0;
                continue;
            }
            cur = 10*cur + digit;        
        }
        ret += isprime(cur);
        return ret;
    }
}
