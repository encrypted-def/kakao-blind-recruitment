#include <bits/stdc++.h>
using namespace std;
list<int> l;
list<int>::iterator its[1000005];
list<int>::iterator cursor; 
vector<pair<int,int>> erased;
string solution(int n, int k, vector<string> cmd) {
    for(int i = 0; i < n; i++)
        l.push_back(i);
    l.push_back(n);
    auto it = l.begin();
    for(int i = 0; i < n+1; i++){
        its[i] = it;
        it++;        
    }
    cursor = its[k];
    for(auto s : cmd){
        if(s[0] == 'U'){
            int num = stoi(s.substr(2,100));
            while(num--) cursor--;
        }
        else if(s[0] == 'D'){
            int num = stoi(s.substr(2,100));
            while(num--) cursor++;
        }
        else if(s[0] == 'C'){
            erased.push_back({*cursor, *(next(cursor))});
            cursor = l.erase(cursor);
            if(*cursor == n) cursor--;
        }
        else{ // Z
            int cur, nxt;
            tie(cur,nxt) = erased.back();
            erased.pop_back();
            its[cur] = l.insert(its[nxt],cur);            
        }        
    }
    string status(n, 'X');
    for(auto x : l)
        if(x != n) status[x] = 'O';

    return status;
}
