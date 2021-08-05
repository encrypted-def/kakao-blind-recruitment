#include <bits/stdc++.h>
using namespace std;

int l[10005]; // 왼쪽 자식 노드 번호
int r[10005]; // 오른쪽 자식 노드 번호
int x[10005]; // 시험장의 응시 인원
int p[10005]; // 부모 노드 번호
int n; // 노드의 수
int root; // 루트

// cur : 현재 보는 노드 번호, lim : 그룹의 최대 인원 수, cnt : 그룹의 수
int dfs(int cur, int lim, int& cnt){
    int lv = 0; // 왼쪽 자식 트리에서 넘어오는 인원 수
    if(l[cur] != -1) lv = dfs(l[cur], lim, cnt);
    int rv = 0; // 오른쪽 자식 트리에서 넘어오는 인원 수
    if(r[cur] != -1) rv = dfs(r[cur], lim, cnt);
    // 1. 왼쪽 자식 트리와 오른쪽 자식 트리에서 넘어오는 인원을 모두 합해도 lim 이하일 경우
    if(x[cur] + lv + rv <= lim)
        return x[cur] + lv + rv; // 위로 떠넘김
    // 2. 왼쪽 자식 트리와 오른쪽 자식 트리에서 넘어오는 인원 중 작은 것을 합해도 lim 이하일 경우
    if(x[cur] + min(lv, rv) <= lim){
        cnt++; // 둘 중 큰 인원은 그룹을 지어버림
        return x[cur] + min(lv, rv);
    }
    // 3. 1, 2 둘 다 아닐 경우
    cnt += 2; // 왼쪽 자식 트리와 오른쪽 자식 트리 각각을 따로 그룹을 만듬
    return x[cur];
}

int solve(int lim){
    int cnt = 0;
    dfs(root, lim, cnt);
    cnt++; // 맨 마지막으로 남은 인원을 그룹을 지어야 함
    return cnt;
}

int solution(int k, vector<int> num, vector<vector<int>> links) {
    n = num.size();
    fill(p,p+n,-1);
    for(int i = 0; i < n; i++){
        l[i] = links[i][0];
        r[i] = links[i][1];
        x[i] = num[i];
        if(l[i] != -1) p[l[i]] = i;
        if(r[i] != -1) p[r[i]] = i;
    }
    root = min_element(p,p+n) - p; // root의 경우 parent가 없어 값이 -1이므로 min_element를 찾으면 그것이 root이다.
    int st = *max_element(x,x+n);
    int en = 1e8;
    while(st < en){
        int mid = (st+en)/2;
        if(solve(mid) <= k) en = mid;
        else st = mid+1;
    }
    return st;
}
