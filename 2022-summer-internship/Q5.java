import java.util.*;

class Solution {
    public int[][] solution(int[][] rc, String[] operations) {
        int r = rc.length;
        int c = rc[0].length;
        ArrayDeque<Integer> col1 = new ArrayDeque<>();
        ArrayDeque<Integer> col2 = new ArrayDeque<>();
        for(int i = 0; i < r; i++){
            col1.addLast(rc[i][0]);
            col2.addLast(rc[i][c-1]);
        }
        ArrayDeque<Integer>[] rows = new ArrayDeque[r];
        for(int i = 0; i < r; i++){
            rows[i] = new ArrayDeque<>();
            for(int j = 1; j <= c-2; j++)
                rows[i].addLast(rc[i][j]);
        }
        
        int idx = 0;
        for(String op : operations){
            if(op.equals("ShiftRow")){
                col1.addFirst(col1.pollLast());
                col2.addFirst(col2.pollLast());
                idx--;
                if(idx == -1) idx = r-1;
            }
            else{ // Rotate
                rows[idx].addFirst(col1.pollFirst());
                col2.addFirst(rows[idx].pollLast());
                rows[(idx+r-1)%r].addLast(col2.pollLast());
                col1.addLast(rows[(idx+r-1)%r].pollFirst());
            }
        }
        int[][] ret = new int[r][c];
        for(int i = 0; i < r; i++){
            ret[i][0] = col1.pollFirst();
            for(int j = 1; j <= c-2; j++)
                ret[i][j] = rows[(i+idx)%r].pollFirst();
            ret[i][c-1] = col2.pollFirst();
        }
        return ret;
    }
}
