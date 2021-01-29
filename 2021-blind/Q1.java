import java.util.*;

class Solution {
    public String solution(String new_id) {
        String id1 = new_id.toLowerCase();
        
        String step2_filter = "0123456789abcdefghijklmnopqrstuvwxyz-_.";
        String id2 = "";
        for(int i = 0; i < id1.length(); i++){
            if(step2_filter.indexOf(id1.charAt(i)) != -1)
                id2 += id1.charAt(i);
        }
        
        String id3 = "";
        for(int i = 0; i < id2.length(); i++){
            if(id2.charAt(i) != '.')
                id3 += id2.charAt(i);
            else{
                if(id3.length() > 0 && id3.charAt(id3.length() - 1) == '.') continue;
                id3 += id2.charAt(i);
            }
        }
        
        String id4 = "";
        for(int i = 0; i < id3.length(); i++){
            if((i == 0 || i+1 == id3.length()) && id3.charAt(i) == '.') continue;
            id4 += id3.charAt(i);
        }
        
        String id5 = id4;
        if(id5.length() == 0) id5 = "a";
        
        String id6 = id5;
        if(id6.length() > 15)
            id6 = id6.substring(0, 15);
        if(id6.charAt(id6.length() - 1) == '.') id6 = id6.substring(0, id6.length() - 1);
        
        String id7 = id6;
        while(id7.length() < 3) id7 += id7.charAt(id7.length() - 1);
        return id7;
    }
}
