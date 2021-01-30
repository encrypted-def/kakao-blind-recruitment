import heapq

INF = 10**8 + 10
d = [[INF] * 205 for i in range(3)]
adj = [[] for i in range(205)]

def dijkstra(st, idx):
    heap = []
    d[idx][st] = 0
    heapq.heappush(heap, (-d[idx][st], st))
    while heap:
        cur = heapq.heappop(heap)
        dist, i = -cur[0], cur[1]
        if d[idx][i] != dist: continue
        for nxt in adj[i]:
            cost, ni = nxt
            if d[idx][ni] > dist + cost:
                d[idx][ni] = dist + cost
                heapq.heappush(heap, (-d[idx][ni], ni))

def solution(n, s, a, b, fares):
    for v in fares:
        adj[v[0]].append((v[2], v[1]))
        adj[v[1]].append((v[2], v[0]))                     

    dijkstra(s, 0)
    dijkstra(a, 1)
    dijkstra(b, 2)
                            
    return min(d[0][mid] + d[1][mid] + d[2][mid] for mid in range(1, n+1))
