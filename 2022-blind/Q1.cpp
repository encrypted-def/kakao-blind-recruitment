#include <bits/stdc++.h>
using namespace std;

vector<int> solution(vector<string> id_list, vector<string> report, int k) {
    map<string,int> s2i; // 아이디를 0, 1, 2, ..의 인덱스로 변환
    int n = id_list.size();
    set<int> s[1004]; // s[i]: i를 신고한 사람의 집합
    vector<int> cnt(n);
  
    for(int i = 0; i < n; i++)
        s2i[id_list[i]] = i;
    
    for(auto& rep : report){
        stringstream st(rep);
        string id1, id2;
        st >> id1 >> id2;
        s[s2i[id2]].insert(s2i[id1]);
    }
    for(int i = 0; i < n; i++){
        if(s[i].size() < k) continue;
        for(auto x : s[i]) cnt[x]++;
    }
    return cnt;    
}
