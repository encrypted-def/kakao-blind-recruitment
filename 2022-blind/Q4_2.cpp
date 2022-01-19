#include <bits/stdc++.h>
using namespace std;

// a가 b보다 더 좋은 배치일 경우 true
bool cmp(vector<int>& a, vector<int>& b){
    for(int i = 11; i >= 0; i--)
        if(a[i] != b[i]) return a[i] > b[i];
    return false;
}

vector<int> solution(int n, vector<int> info) {
    // 라이언이 가장 큰 점수 차이로 우승할 수 있는 결과를 저장
    // ret[0..10] : 10-i점에서 라이언이 맞힌 화살의 수, ret[11] : 점수 차이
    vector<int> ret(12, -1);
    // 라이언은 자신이 승리할 점수를 선택. brute = 1100100001(2)이면
    // 10, 5, 2, 1점에서 이길 것임을 의미.
    for(int brute = 0; brute < 1024; brute++){
        vector<int> arrow(12);
        int score = 0;
        int left = n; // 남아있는 화살의 수
        for(int i = 0; i < 10; i++){
            if(brute & (1<<i)){ // i번째 비트가 켜져 있는 경우(10-i점에서 승리할 계획)
                score += (10-i);
                left -= (info[i]+1);
                arrow[i] = info[i]+1;
            }
            else if(info[i] != 0){ // i번째 비트가 꺼져 있는 경우(10-i점에서 패배할 계획), 어피치가 1개라도 맞췄으면 점수에 반영
                score -= (10-i);
            }
        }
        // 라이언의 점수가 높지 않거나 화살을 n발 초과로 쏜 경우
        if(score <= 0 || left < 0) continue;
        arrow[10] = left;
        arrow[11] = score;
        // arrow가 기존에 저장된 ret보다 좋을 경우
        if(cmp(arrow, ret)) ret = arrow;
    }
    if(ret[0] == -1) return vector<int>({-1});
    ret.pop_back();
    return ret;
}
