#include <bits/stdc++.h>
using namespace std;

using ll = long long;

// n을 k진법으로 나타낸 결과 반환
vector<int> conv(int n, int k){
    vector<int> ret;
    while(n){
        ret.push_back(n % k);
        n /= k;
    }
    reverse(ret.begin(), ret.end());
    return ret;
}

int isprime(ll n){
    if(n <= 1) return 0;
    for(ll i = 2; i*i <= n; i++)
        if(n % i == 0) return 0;
    return 1;
}

int solution(int n, int k) {
    vector<int> s = conv(n, k);
    ll cur = 0; // 0이 나오기 전까지 수를 10진수로 읽음
    int ret = 0;
    for(int digit : s){
        if(digit == 0){
            ret += isprime(cur);
            cur = 0;
            continue;
        }
        cur = 10*cur + digit;        
    }
    ret += isprime(cur);
    return ret;
}
