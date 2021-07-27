import java.util.*;

class Solution {
    final int MX = 1200005; // n + len(cmd) + 5(dummy)
    int dat[] = new int[MX];
    int pre[] = new int[MX];
    int nxt[] = new int[MX];
    int unused = 1;
    int num2idx[] = new int[1000005]; // num2idx[i] : 수 i가 저장된 주소
    
    // addr번지 뒤에 num을 삽입, num을 삽입한 위치를 반환
    public int insert(int addr, int num){
        dat[unused] = num;
        pre[unused] = addr;
        nxt[unused] = nxt[addr];
        if(nxt[addr] != -1) pre[nxt[addr]] = unused;
        nxt[addr] = unused;
        return unused++;
    }
    
    // addr번지의 원소를 제거, 제거한 다음 원소를 반환. 만약 삭제된 원소가 가장 마지막 원소였을 경우 이전 원소를 반환(문제의 상황과 동일)
    public int erase(int addr){
        nxt[pre[addr]] = nxt[addr];
        if(nxt[addr] != -1){
            pre[nxt[addr]] = pre[addr];
            return nxt[addr];
        }
        return pre[addr];
    }
    public String solution(int n, int k, String[] cmd) {
        dat[0] = nxt[0] = -1;
        for(int i = 0; i < n; i++)
            num2idx[i] = insert(i, i);
        int cursor = num2idx[k]; // cursor를 원소 k에 둠 
        Stack<pair> erased = new Stack<>(); // 삭제되는 원소들에 대해 (이전 원소의 값, 현재 원소의 값)을 저장
        for(int i = 0; i < cmd.length; i++){
            if(cmd[i].charAt(0) == 'U'){
                int num = Integer.parseInt(cmd[i].substring(2, cmd[i].length()));
                while(num-- > 0) cursor = pre[cursor];
            }
            else if(cmd[i].charAt(0) == 'D'){
                int num = Integer.parseInt(cmd[i].substring(2, cmd[i].length()));
                while(num-- > 0) cursor = nxt[cursor];
            }
            else if(cmd[i].charAt(0) == 'C'){                
                erased.push(new pair(dat[pre[cursor]], dat[cursor]));
                cursor = erase(cursor);
            }
            else{ // Z
                int preval = erased.peek().x;
                int curval = erased.peek().y;
                erased.pop();
                int preidx; // 값이 preval인 곳의 주소
                if(preval == -1) preidx = 0; // 맨 앞의 원소인 경우 preidx = 0
                else preidx = num2idx[preval];
                num2idx[curval] = insert(preidx, curval);
            }
        }        
        StringBuilder status = new StringBuilder("X".repeat(n));
        // 0번지부터 끝까지 순회
        int cur = nxt[0];
        while(cur != -1){
            status.setCharAt(dat[cur], 'O');
            cur = nxt[cur];
        }
        return status.toString();
        
    }
    class pair {
        int x, y;
        pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
