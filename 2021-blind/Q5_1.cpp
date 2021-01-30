#include <bits/stdc++.h>
using namespace std;

int s2i(string s){
    return stoi(s.substr(0, 2))*3600 + stoi(s.substr(3, 2))*60 + stoi(s.substr(6, 2));
}

string numzfill(int x){
    if(x < 10) return "0"+to_string(x);
    return to_string(x);
}

string i2s(int t){
    string ret = numzfill(t/3600) + ":";
    t %= 3600;
    ret += numzfill(t/60)+":";
    t %= 60;
    return ret + numzfill(t);
}

string solution(string play_time, string adv_time, vector<string> logs) {
    int pt = s2i(play_time), at = s2i(adv_time);
    // A. 특정 초에 시청중인 사람의 수 계산
    int d[360001] = {};
    for(auto l : logs){
        int st = s2i(l.substr(0, 8)), en = s2i(l.substr(9, 8));
        d[st]++; d[en]--;
    }
    for(int i = 1; i <= 360000; i++) d[i] += d[i-1];
    // B. 누적 재생시간이 가장 많은 곳 계산
    long long mxval = 0, curval = 0;
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
