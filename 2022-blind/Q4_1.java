import java.util.*;

class Solution {    
    // a가 b보다 더 좋은 배치일 경우 true
    public boolean cmp(int[] a, int[] b){
        for(int i = 11; i >= 0; i--)
            if(a[i] != b[i]) return a[i] > b[i];
        return false;
    }
    // 라이언이 가장 큰 점수 차이로 우승할 수 있는 결과를 저장
    // mn[0..10] : 10-i점에서 라이언이 맞힌 화살의 수, mn[11] : 점수 차이
    int[] mn = new int[12];
    // 백트래킹으로 가능한 모든 경우를 따져볼 변수, 저장 형식은 mn과 동일
    int[] arrow = new int[12];
    // idx번째 값을 정함, 현재 화살은 left개 남아있음
    public void bt(int idx, int left, int[] info){
        if(idx == 10){ // 10번째의 값이 정해진 상황이라면
            // 라이언의 점수를 계산
            arrow[idx] = left;
            int score = 0;
            for(int i = 0; i <= 10; i++){
                if(arrow[i] > info[i])
                    score += (10 - i);
                else if(info[i] != 0)
                    score -= (10 - i);
            }
            if(score <= 0) return;
            arrow[11] = score;
            // 지금 결과가 기존의 결과보다 좋다면 ret를 갱신
            if(cmp(arrow, mn)){
                for(int i = 0; i < 12; i++)
                    mn[i] = arrow[i];                
            }
            return;
        }
        for(int i = 0; i <= left; i++){
            arrow[idx] = i; // idx번째 화살의 수를 i개로 정함
            bt(idx+1, left - i, info); // 다음 단계로 들어감
        }
    }
    
    public int[] solution(int n, int[] info) {
        Arrays.fill(mn, -1);
        bt(0, n, info); // backtracking을 통해 ret에 정답을 넣어둠
        if(mn[0] == -1){
            int[] ret_arr = new int[1];
            ret_arr[0] = -1;
            return ret_arr;
        }
        int[] ret = new int[11];
        for(int i = 0; i < 11; i++)
            ret[i] = mn[i];
        return ret;
    }
}
