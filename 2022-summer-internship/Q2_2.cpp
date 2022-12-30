#include <bits/stdc++.h>

using namespace std;

int solution(vector<int> queue1, vector<int> queue2) {
    queue<int> q1, q2;
    long long tot1 = 0, tot2 = 0;
    for(auto x : queue1){
        tot1 += x;
        q1.push(x);
    }
    for(auto x : queue2){
        tot2 += x;
        q2.push(x);
    }
    for(int i = 0; i < 4 * queue1.size(); i++){
        if(tot1 == tot2) return i;
        if(tot1 < tot2){
            int x = q2.front();
            tot1 += x;
            tot2 -= x;
            q1.push(x);
            q2.pop();
        }
        else{
            int x = q1.front();
            tot2 += x;
            tot1 -= x;
            q2.push(x);
            q1.pop();            
        }
    }
    return -1;
}
