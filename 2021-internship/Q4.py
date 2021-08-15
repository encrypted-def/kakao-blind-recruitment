import heapq

INF = 10**8 + 10
d = [[INF] * 1024 for _ in range(1004)] # d[i][state] : (상태 0, start번 노드)에서 (상태 state, i번 노드)로 갈 때의 최단경로
adj = [[] for _ in range(1004)] # 정방향 간선(번호, 시간)
adjrev = [[] for _ in range(1004)] # 역방향 간선(번호, 시간)
trapidx = [-1] * 1004 # trapidx[i] : i번 노드의 함정 번호. 함정은 0번부터 차례로 번호가 부여되어 있으며 i번 노드가 함정이 아닐 경우 -1

# 상태 state에 i번 비트가 켜져있는지를 반환하는 함수
def bitmask(state, idx):
    return (1 << trapidx[idx]) & state

def solution(n, start, end, roads, traps):
    for u, v, val in roads:
        adj[u].append((v,val))
        adjrev[v].append((u,val))
    
    # trapidx 값 지정
    for i in range(len(traps)):
        trapidx[traps[i]] = i
    
    # dijkstra 진행
    heap = []
    d[start][0] = 0
    heapq.heappush(heap, (d[start][0], start, 0))
    while heap:
        val, idx, state = heapq.heappop(heap)
        # pq에서 뽑히는 원소는 가까운 순이라는 점을 이용해서 맨 마지막에 d[..][end]를 for문으로 순회하지 않아도 되게 idx == end일 때 바로 답을 반환
        if idx == end: return val
        if d[idx][state] != val: continue
        for nxt, dist in adj[idx]: # 정방향 간선에 대한 확인
            rev = 0
            if trapidx[idx] != -1 and bitmask(state, idx): rev ^= 1 # 현재 idx번 trap을 밟은 상태라면 rev 상태를 뒤집음
            if trapidx[nxt] != -1 and bitmask(state, nxt): rev ^= 1 # 현재 nxt번 trap을 밟은 상태라면 rev 상태를 뒤집음
            if rev: continue # 정방향 간선을 보고 있으므로 trap을 1개만 밟은 상황일 경우 넘어감
            nxt_state = state
            if trapidx[nxt] != -1: nxt_state ^= (1 << trapidx[nxt])
            if d[nxt][nxt_state] > dist + val:
                d[nxt][nxt_state] = dist + val
                heapq.heappush(heap, (d[nxt][nxt_state], nxt, nxt_state))
        
        for nxt, dist in adjrev[idx]: # 역방향 간선에 대한 확인
            rev = 0
            if trapidx[idx] != -1 and bitmask(state, idx): rev ^= 1 # 현재 idx번 trap을 밟은 상태라면 rev 상태를 뒤집음
            if trapidx[nxt] != -1 and bitmask(state, nxt): rev ^= 1 # 현재 nxt번 trap을 밟은 상태라면 rev 상태를 뒤집음
            if not rev: continue # 역방향 간선을 보고 있으므로 trap을 0개 or 2개 밟은 상황일 경우 넘어감
            nxt_state = state
            if trapidx[nxt] != -1: nxt_state ^= (1 << trapidx[nxt])
            if d[nxt][nxt_state] > dist + val:
                d[nxt][nxt_state] = dist + val
                heapq.heappush(heap, (d[nxt][nxt_state], nxt, nxt_state))
    
    return -1 # Unreachable
