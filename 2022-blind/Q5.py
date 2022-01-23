l = [-1] * 20 # 왼쪽 자식
r = [-1] * 20 # 오른쪽 자식
val = [] # 양/늑대 값
n = 0
ans = 1
vis = [0] * (1 << 17) # vis[x] : 상태 x를 방문했는가?

def solve(state):
    global ans
    if vis[state]: return None
    vis[state] = 1
    # wolf : 늑대의 수, num : 전체 정점의 수
    wolf, num = 0, 0
    for i in range(n):
        if state & (1 << i):
            num += 1
            wolf += val[i]
    # 만약 늑대가 절반 이상일 경우 방문할 수 없는 상태이니 종료
    if 2*wolf >= num: return None
    # 현재 state의 양의 수가 ans보다 클 경우 ans를 갱신
    ans = max(ans, num - wolf)
    
    # 이제 다음 상태로 넘어갈 시간
    for i in range(n):
        if not state & (1<<i):
            continue
        # 현재 보고 있는 i번째 정점의 left가 있다면
        if l[i] != -1:
            solve(state | (1<<l[i]))
        if r[i] != -1:
            solve(state | (1<<r[i]))
    

def solution(info, edges):
    global n, val
    n = len(info)
    val = info[:]
    for u, v in edges:
        if l[u] == -1: l[u] = v
        else: r[u] = v
    
    solve(1)
    return ans
