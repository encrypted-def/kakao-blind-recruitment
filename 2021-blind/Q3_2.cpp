#include <bits/stdc++.h>
using namespace std;

string l[4][4] = {{"-", "cpp", "java", "python"},
                  {"-", "backend", "frontend"},
                  {"-", "junior", "senior"},
                  {"-", "chicken", "pizza"}};

vector<int> solution(vector<string> info, vector<string> query) {
    // A. 전처리
    vector<vector<int>> L(108, vector<int>(100001));
    for(auto s : info){
        int v[4];
        int idx1 = 0, idx2 = s.find(" ", idx1);
        for(int i = 0; i < 4; i++){
            string cond = s.substr(idx1, idx2 - idx1);
            idx1 = idx2+1;
            idx2 = s.find(" ", idx1);
            v[i] = find(l[i], l[i]+4, cond) - l[i];
        }
        int score = stoi(s.substr(idx1, 10));
        for(int c1 : {0, v[0]}){
            for(int c2 : {0, v[1]}){
                for(int c3 : {0, v[2]}){
                    for(int c4 : {0, v[3]}){
                        int idx = c1*27 + c2*9 + c3*3 + c4;
                        L[idx][score]++;
                    }
                }
            }
        }
    }
    for(int i = 0; i < 108; i++){
        for(int j = 1; j <= 100000; j++)
            L[i][j] += L[i][j-1];
    }
    // B. 쿼리
    vector<int> answer;
    for(auto q : query){
        int v[4];
        int idx1 = 0, idx2 = q.find(" and ", idx1);
        for(int i = 0; i < 4; i++){
            string cond = q.substr(idx1, idx2 - idx1);
            idx1 = idx2+5;
            idx2 = q.find(" ", idx1);
            v[i] = find(l[i], l[i]+4, cond) - l[i];
        }
        int target = stoi(q.substr(idx1 - 4, 10));
        int idx = v[0]*27 + v[1]*9 + v[2]*3 + v[3];
        answer.push_back(L[idx][100000] - L[idx][target-1]);
    }
    return answer;
}
