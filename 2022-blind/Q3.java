import java.util.*;
class Solution {
    
    int formula(int t, int[] fees){
        if(t <= fees[0]) return fees[1];
        int q = (t - fees[0] + fees[2] - 1) / fees[2];
        return fees[1] + q * fees[3];
    }

    int s2t(String s){
        return Integer.parseInt(s.substring(0, 2)) * 60 + Integer.parseInt(s.substring(3, 5));
    }
    
    public int[] solution(int[] fees, String[] records) {
        int[] cnt = new int[10000]; // cnt[i] : 차 i가 주차한 총 시간
        int[] stored = new int[10000]; // 직전에 입차한 시각, 입차하지 않았다면 -1
        Arrays.fill(stored, -1);
        for(String r : records){
            String[] token = r.split(" ");
            int t = s2t(token[0]);
            int num = Integer.parseInt(token[1]);
            if(token[2].equals("IN")) // 입차
                stored[num] = t;
            else{
                cnt[num] += t - stored[num];
                stored[num] = -1;
            }
        }

        for(int i = 0; i < 10000; i++){
            if(stored[i] != -1)
                cnt[i] += s2t("23:59") - stored[i];            
        }

        ArrayList<Integer> ret_list = new ArrayList<>();
        for(int x : cnt){
            if(x == 0) continue;
            ret_list.add(formula(x, fees));
        }
        int[] ret = new int[ret_list.size()];
        for(int i = 0; i < ret_list.size(); i++)
            ret[i] = ret_list.get(i);
        return ret;
    }
}
