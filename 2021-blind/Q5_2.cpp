#include <bits/stdc++.h>
using namespace std;
using pii = pair<int,int>;

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
    vector<pii> event;
    // A. 전처리
    for(auto l : logs){
        int st = s2i(l.substr(0, 8)), en = s2i(l.substr(9, 8));
        event.push_back({st, 1});
        event.push_back({en, -1});                
    }
    event.push_back({0, 0});
    sort(event.begin(), event.end());
    // B. 누적 재생시간이 가장 많은 곳 계산
    // cnt1 : 시작 구간에서의 시청중인 사람의 수
    // cnt2 : 끝 구간에서의 시청중인 사람의 수
    int idx1 = 0, idx2 = 0, cnt1 = 0, cnt2 = 0;
    long long curval = 0, mxval = 0;
    int curtime = 0, mxtime = 0;
    while(idx2 + 1 < event.size() && event[idx2+1].first <= at){
        curval += (event[idx2+1].first-event[idx2].first) * cnt2;
        cnt2 += event[idx2+1].second;
        idx2++;
    }
    curval += (at - event[idx2].first) * cnt2;
    mxval = curval;
    
    while(curtime <= pt-at && idx2 + 1 < event.size()){
        int delta1 = event[idx1+1].first - curtime;
        int delta2 = event[idx2+1].first - (curtime + at);
        if(delta1 <= delta2){ // 시작 구간이 다음 event에 더 가까운 경우
            curval = curval + 1ll * (cnt2 - cnt1) * delta1;
            cnt1 += event[idx1+1].second;
            idx1++;
            curtime += delta1;
        }
        else{
            curval = curval + 1ll * (cnt2 - cnt1) * delta2;
            cnt2 += event[idx2+1].second;
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
