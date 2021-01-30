import collections, itertools, copy

dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]

def OOB(x, y):
    return x < 0 or x > 3 or y < 0 or y > 3

def dist(board, src, dst):
    d = [[-1]*4 for i in range(4)]
    d[src[0]][src[1]] = 0
    q = collections.deque()
    q.append(src)
    while q:
        cur = q.popleft()
        for dir in range(4):
            en = 0
            while True:
                nx = cur[0] + dx[dir] * en
                ny = cur[1] + dy[dir] * en
                if OOB(nx+dx[dir], ny+dy[dir]) or (en != 0 and board[nx][ny]): break
                en += 1
            for z in (1, en):
                nx = cur[0] + dx[dir] * z
                ny = cur[1] + dy[dir] * z
                if OOB(nx, ny): continue
                if d[nx][ny] == -1:
                    d[nx][ny] = d[cur[0]][cur[1]] + 1
                    q.append((nx,ny))
    return d[dst[0]][dst[1]]
    
def solution(board, r, c):
    occur = [[] for i in range(7)]
    brute = []
    for i in range(4):
        for j in range(4):
            if board[i][j] == 0: continue
            if occur[board[i][j]]: brute.append(board[i][j])
            occur[board[i][j]].append((i,j))
    
    n = len(brute)
    ans = 99999999
    for p in itertools.permutations(brute):        
        myboard = copy.deepcopy(board)
        d = [[0]*2 for i in range(n)]
        d[0][0] = dist(myboard, (r, c), occur[p[0]][0]) + dist(myboard, occur[p[0]][0], occur[p[0]][1])
        d[0][1] = dist(myboard, (r, c), occur[p[0]][1]) + dist(myboard, occur[p[0]][1], occur[p[0]][0])
        myboard[occur[p[0]][0][0]][occur[p[0]][0][1]] = 0
        myboard[occur[p[0]][1][0]][occur[p[0]][1][1]] = 0        
        for i in range(1, n):
            myDist = dist(myboard, occur[p[i]][0], occur[p[i]][1])
            d[i][0] = min(d[i-1][0] + dist(myboard, occur[p[i-1]][1], occur[p[i]][0]), d[i-1][1] + dist(myboard, occur[p[i-1]][0], occur[p[i]][0])) + dist(myboard, occur[p[i]][0], occur[p[i]][1])
            d[i][1] = min(d[i-1][0] + dist(myboard, occur[p[i-1]][1], occur[p[i]][1]), d[i-1][1] + dist(myboard, occur[p[i-1]][0], occur[p[i]][1])) + dist(myboard, occur[p[i]][1], occur[p[i]][0])
            myboard[occur[p[i]][0][0]][occur[p[i]][0][1]] = 0
            myboard[occur[p[i]][1][0]][occur[p[i]][1][1]] = 0            
        ans = min(ans, d[n-1][0], d[n-1][1])
        
    return ans + 2 * n;
