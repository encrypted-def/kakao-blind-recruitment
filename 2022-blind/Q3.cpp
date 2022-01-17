#include <bits/stdc++.h>
using namespace std;

int formula(int t, vector<int>& fees){
    if(t <= fees[0]) return fees[1];
    int q = (t - fees[0] + fees[2] - 1) / fees[2];
    return fees[1] + q * fees[3];
}

int s2t(string s){
    return stoi(s.substr(0, 2)) * 60 + stoi(s.substr(3, 2));
}

vector<int> solution(vector<int> fees, vector<string> records) {
    int cnt[10000] = {}; // cnt[i] : 차 i가 주차한 총 시간
    int stored[10000]; // 직전에 입차한 시각, 입차하지 않았다면 -1
    fill(stored, stored+10000, -1);
    for(string& r : records){
        stringstream st(r);
        string s, num_str, state;
        st >> s >> num_str >> state;
        int t = s2t(s);
        int num = stoi(num_str);
        if(state == "IN") // 입차
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
    
    vector<int> ret;
    for(int x : cnt){
        if(x == 0) continue;
        ret.push_back(formula(x, fees));
    }
    return ret;
}
