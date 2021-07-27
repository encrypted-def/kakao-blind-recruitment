#include <bits/stdc++.h>
using namespace std;

const int MX = 1000005;
int pre[MX], nxt[MX];
string solution(int n, int k, vector<string> cmd) {
    string status(n, 'O');
    for(int i = 0; i < n; i++){
        pre[i] = i-1;
        nxt[i] = i+1;        
    }
    nxt[n-1] = -1;    
    stack<tuple<int,int,int>> erased;
    int cursor = k;
    for(auto s : cmd){
        if(s[0] == 'U'){
            int num = stoi(s.substr(2,100));
            while(num--) cursor = pre[cursor];
        }
        else if(s[0] == 'D'){
            int num = stoi(s.substr(2,100));
            while(num--) cursor = nxt[cursor];
        }
        else if(s[0] == 'C'){
            erased.push({pre[cursor], cursor, nxt[cursor]});
            // pre, nxt를 변경해 제거를 수행
            if(pre[cursor] != -1) nxt[pre[cursor]] = nxt[cursor];
            if(nxt[cursor] != -1) pre[nxt[cursor]] = pre[cursor];
            status[cursor] = 'X';            
            // 커서 이동
            if(nxt[cursor] != -1) cursor = nxt[cursor];
            else cursor = pre[cursor];
            

        }
        else{ // Z
            int pp, cc, nn;
            tie(pp, cc, nn) = erased.top();
            erased.pop();
            if(pp != -1)
                nxt[pp] = cc;
            if(nn != -1)
                pre[nn] = cc;
            pre[cc] = pp;
            nxt[cc] = nn;
            status[cc] = 'O';
        }        
    }
    return status;
}
