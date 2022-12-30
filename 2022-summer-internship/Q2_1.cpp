#include <bits/stdc++.h>
using namespace std;

using ll = long long;

int solution(vector<int> queue1, vector<int> queue2) {
    int n = queue1.size();
    vector<int> a;
    a.insert(a.end(), queue1.begin(), queue1.end());
    a.insert(a.end(), queue2.begin(), queue2.end());
    ll target = 0;
    for(auto x : a)
        target += x;
    if(target % 2 != 0) return -1;
    target /= 2; // 합이 target이 되는 구간을 찾아야 함
    
    int ans = 0x7f7f7f7f; // 일단 매우 큰 값을 넣어놓고 시작
    int en = 0;
    ll tot = a[0]; // 현재 보는 구간의 합 = a[st]+...+a[en], 범위가 양 끝을 포함(inclusive)한다는 점과 en < st일 땐 a[st]+...+a[2*n-1]+a[0]+...+a[en]을 나타냄에 주의
    for(int st = 0; st < 2*n; st++){
        while(tot < target){
            en = (en + 1) % (2*n);
            tot += a[en];
        }
        // while문을 탈출하면 a[st]+...+a[en] 구간의 합이 target 이상임을 의미
        if(tot == target){ // 구간의 합이 정확히 target과 일치할 경우
            int moves; // 작업 횟수
            if(en < n-1) moves = st + n + en; // en이 끝에서 돌아 다시 앞으로 옴
            else moves = st + (en - n + 1);
            ans = min(ans, moves);
        }
        tot -= a[st];
    }
    if(ans == 0x7f7f7f7f) ans = -1;
    return ans;
    
}
