import java.util.*;
import java.lang.*;

class Solution {
    public String[] solution(String[] orders, int[] course) {
        ArrayList<HashMap<String, Integer>> freq = new ArrayList<HashMap<String, Integer>>();
        
        for(int i = 0; i < 11; i++){
            HashMap<String, Integer> tmp = new HashMap<String, Integer>();
            freq.add(tmp);
        }

        // A. 코스 메뉴 조합 추출        
        for(int t = 0; t < orders.length; t++){
            char order[] = orders[t].toCharArray();
            Arrays.sort(order);
            for(int i = 1; i < (1 << order.length); i++){
                String menu = "";
                /*for(int j = 0; j < order.length; j++){
                    if((i & (1 << j)) != 0) menu += order[j];
                }*/
                int tmp = i;
                for(int j = 0; j < order.length; j++){
                    if(tmp % 2 == 1) menu += order[j];
                    tmp /= 2;
                }
                int cnt = freq.get(menu.length()).getOrDefault(menu, 0);
                freq.get(menu.length()).put(menu, cnt + 1);
            }
        }
        
        // B. 코스요리 메뉴 계산
        ArrayList<String> ans = new ArrayList<String>();
        for(int i = 0; i < course.length; i++){
            int mxfreq = 0;
            
            for(Map.Entry mapElem : freq.get(course[i]).entrySet()){
                mxfreq = Math.max(mxfreq, (int)mapElem.getValue());
            }

            if(mxfreq < 2) continue;
            for(Map.Entry mapElem : freq.get(course[i]).entrySet())
                if((int)mapElem.getValue() == mxfreq) ans.add((String)mapElem.getKey());
        }
        
        Collections.sort(ans);
        return ans.toArray(new String[ans.size()]);
    }
}
