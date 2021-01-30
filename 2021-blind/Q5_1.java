import java.util.*;
class Solution {
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
        // A. 특정 초에 시청중인 사람의 수 계산
        int d[] = new int[360001];
        for(int i = 0; i < logs.length; i++){
            String l = logs[i];
            int st = s2i(l.substring(0, 8)), en = s2i(l.substring(9, 17));
            d[st]++; d[en]--;
        }
        for(int i = 1; i <= 360000; i++) d[i] += d[i-1];
        // B. 누적 재생시간이 가장 많은 곳 계산
        long mxval = 0, curval = 0;
        int mxtime = 0;
        for(int i = 0; i < at; i++) curval += d[i];
        mxval = curval;
        for(int i = 1; i <= 360000 - at; i++){
            curval = curval - d[i-1] + d[i+at-1];
            if(curval > mxval){
                mxval = curval;
                mxtime = i;
            }
        }
        return i2s(mxtime);
    }
}
