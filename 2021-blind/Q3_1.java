import java.util.*;

class Solution {
    public int[] solution(String[] info, String[] query) {
        String[][] l = {{"-", "cpp", "java", "python"},
                          {"-", "backend", "frontend"},
                          {"-", "junior", "senior"},
                          {"-", "chicken", "pizza"}};
        // A. 전처리
        ArrayList<Integer>[] L = new ArrayList[108];
        for(int i = 0; i < 108; i++)
            L[i] = new ArrayList<Integer>();
        for(int t = 0; t < info.length; t++){
            String s = info[t];
            int v[] = new int[4];
            int idx1 = 0, idx2 = s.indexOf(" ");
            for(int i = 0; i < 4; i++){
                String cond = s.substring(idx1, idx2);
                idx1 = idx2+1;
                idx2 = s.indexOf(" ", idx1);
                List<String> tmp = Arrays.asList(l[i]);
                v[i] = tmp.indexOf(cond);
            }
            int score = Integer.parseInt(s.substring(idx1, s.length()));
            int factor[] = {27, 9, 3, 1};
            for(int i = 0; i < 16; i++){
                int idx = 0;
                for(int j = 0; j < 4; j++){
                    if((i & (1<<j)) != 0) idx += factor[j] * v[j];
                }
                L[idx].add(score);
            }
        }
        for(int i = 0; i < 108; i++) Collections.sort(L[i]);
        // B. 쿼리
        int[] ans = new int[query.length];
        for(int t = 0; t < query.length; t++){
            String q = query[t];
            int v[] = new int[4];
            int idx1 = 0, idx2 = q.indexOf(" ");
            for(int i = 0; i < 4; i++){
                String cond = q.substring(idx1, idx2);
                idx1 = idx2+5;
                idx2 = q.indexOf(" ", idx1);
                List<String> tmp = Arrays.asList(l[i]);
                v[i] = tmp.indexOf(cond);
            }
            int target = Integer.parseInt(q.substring(idx1 - 4, q.length()));
            int idx = v[0]*27 + v[1]*9 + v[2]*3 + v[3];
            int st = 0, en = L[idx].size();
            while(st < en){
                int mid = (st+en) / 2;
                if(L[idx].get(mid) >= target) en = mid;
                else st = mid+1;
            }
            ans[t] = L[idx].size() - st;
        }
        return ans;
    }
}
