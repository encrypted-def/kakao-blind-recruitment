#include <bits/stdc++.h>
using namespace std;

string solution(int n, int k, vector<string> cmd) {
    set<int> s;
    vector<int> erased;
    for(int i = 0; i < n; i++)
        s.insert(i);
    auto it = s.find(k);
    for(auto c : cmd){
        if(c[0] == 'U'){
            int num = stoi(c.substr(2,100));
            while(num--) it--;
        }
        else if(c[0] == 'D'){
            int num = stoi(c.substr(2,100));
            while(num--) it++;
        }
        else if(c[0] == 'C'){
            erased.push_back(*it);
            it = s.erase(it);
            if(it == s.end()) it--;
        }
        else{ // Z
            s.insert(erased.back());
            erased.pop_back();
        }
    }
    string status(n,'X');
    for(auto x : s) status[x] = 'O';
    return status;
}
