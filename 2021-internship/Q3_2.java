import java.util.*;

class Solution {
    final int MX = 1000005; // n + len(cmd) + 5(dummy)
    int pre[] = new int[MX];
    int nxt[] = new int[MX];

    public String solution(int n, int k, String[] cmd) {        
        for(int i = 0; i < n; i++){
            pre[i] = i-1;
            nxt[i] = i+1;
        }
        nxt[n-1] = -1;
        int cursor = k; // cursor를 원소 k에 둠 
        Stack<tuple> erased = new Stack<>(); // 삭제되는 원소들에 대해 (이전 원소의 값, 현재 원소의 값, 다음 원소의 값)을 저장
        StringBuilder status = new StringBuilder("O".repeat(n));
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
                erased.push(new tuple(pre[cursor], cursor, nxt[cursor]));
                // pre, nxt를 변경해 제거를 수행
                if(pre[cursor] != -1) nxt[pre[cursor]] = nxt[cursor];
                if(nxt[cursor] != -1) pre[nxt[cursor]] = pre[cursor];
                status.setCharAt(cursor, 'X');
                // 커서 이동
                if(nxt[cursor] != -1) cursor = nxt[cursor];
                else cursor = pre[cursor];
            }
            else{ // Z
                int pp = erased.peek().x; // 복구된 원소의 이전 원소
                int cc = erased.peek().y; // 복구된 원소
                int nn = erased.peek().z; // 복구된 원소의 다음 원소
                erased.pop();
                if(pp != -1) nxt[pp] = cc;
                if(nn != -1) pre[nn] = cc;
                status.setCharAt(cc, 'O');
            }
        }
        return status.toString();
        
    }
    class tuple {
        int x, y, z;
        tuple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
