import java.util.*;
import java.lang.*;

class Solution {
    
    static class pair implements Comparable<pair>{
        int x, y;
        
        public pair(int x, int y){this.x = x; this.y = y;}

        @Override
        public int compareTo(pair o) {
            if (x > o.x) return 1;
            if (x < o.x) return -1;
            if(y > o.y) return 1;
            if(y == o.y) return 0;
            return -1;
        }
    }
    
    static int s2i(String s){
        return Integer.parseInt(s.substring(0, 2))*3600 + Integer.parseInt(s.substring(3, 5))*60 + Integer.parseInt(s.substring(6, 8));
    }
    
    static String numzfill(int x){
        if(x < 10) return "0" + String.valueOf(x);
        return String.valueOf(x);
    }
    
    static String i2s(int t){
        String ret = numzfill(t/3600) + ":";
        t %= 3600;
        ret += numzfill(t/60) + ":";
        t %= 60;
        return ret + numzfill(t);
    }
        
    public String solution(String play_time, String adv_time, String[] logs) {
        int pt = s2i(play_time), at = s2i(adv_time);
        // A. 전처리
        ArrayList<pair> event = new ArrayList<>();
        for(int i = 0; i < logs.length; i++){
            String l = logs[i];
            int st = s2i(l.substring(0, 8)), en = s2i(l.substring(9, 17));
            event.add(new pair(st, 1));
            event.add(new pair(en, -1));            
        }
        event.add(new pair(0, 0));
        Collections.sort(event);
        // B. 누적 재생시간이 가장 많은 곳 계산
        // cnt1 : 시작 구간에서의 시청중인 사람의 수
        // cnt2 : 끝 구간에서의 시청중인 사람의 수
        int idx1 = 0, idx2 = 0, cnt1 = 0, cnt2 = 0;
        long curval = 0, mxval = 0;
        int curtime = 0, mxtime = 0;
        while(idx2 + 1 < event.size() && event.get(idx2+1).x <= at){
            curval += (event.get(idx2+1).x-event.get(idx2).x) * cnt2;
            cnt2 += event.get(idx2+1).y;
            idx2++;
        }
        curval += (at - event.get(idx2).x) * cnt2;
        mxval = curval;

        while(curtime <= pt-at && idx2 + 1 < event.size()){
            int delta1 = event.get(idx1+1).x - curtime;
            int delta2 = event.get(idx2+1).x - (curtime + at);
            if(delta1 <= delta2){ // 시작 구간이 다음 event에 더 가까운 경우
                curval = curval + (long)(cnt2 - cnt1) * delta1;
                cnt1 += event.get(idx1+1).y;
                idx1++;
                curtime += delta1;
            }
            else{
                curval = curval + (long)(cnt2 - cnt1) * delta2;
                cnt2 += event.get(idx2+1).y;
                idx2++;
                curtime += delta2;
            }
            if(curval > mxval){
                mxval = curval;
                mxtime = curtime;
            }
        }
        return i2s(mxtime);
    }
}
