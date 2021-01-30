adj = [[] for i in range(300005)]
INF = 0x7fffffff7fffffff
d = [[0]*2 for i in range(300005)]
s = []

def dfs(cur):
    if not adj[cur]:
        d[cur][0], d[cur][1] = s[cur], 0
        return None
    mingap = INF
    d[cur][0] = s[cur]
    for x in adj[cur]:
        dfs(x)
        d[cur][0] += min(d[x])
        mingap = min(mingap, d[x][0] - d[x][1])
    if mingap < 0: mingap = 0
    d[cur][1] = d[cur][0] + mingap - s[cur]
    
def solution(sales, links):
    global s
    s = [0] + sales
    for x in links:
        adj[x[0]].append(x[1])
    dfs(1)
    return min(d[1])
