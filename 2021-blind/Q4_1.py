def solution(n, s, a, b, fares):
    INF = 10**8 + 10
    d = [[INF]*205 for i in range(205)]
    for v in fares:
        d[v[0]][v[1]] = v[2]
        d[v[1]][v[0]] = v[2]
    for i in range(1, n+1): d[i][i] = 0
    for k in range(1, n+1):
        for i in range(1, n+1):
            for j in range(1, n+1):
                if d[i][j] > d[i][k] + d[k][j]:
                    d[i][j] = d[i][k] + d[k][j]
    
    return min(d[s][mid]+d[mid][a]+d[mid][b] for mid in range(1, n+1))
