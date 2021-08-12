MX = 1200005 # n + len(cmd) + 5(dummy)
pre = [-1]*MX
nxt = [-1]*MX

def solution(n, k, cmd):
    status = ['O'] * n
    for i in range(n):
        pre[i], nxt[i] = i-1, i+1
    nxt[n-1] = -1
    cursor = k
    erased = []
    for query in cmd:
        parse = query.split()
        if parse[0] == 'U':
            num = int(parse[1])
            for _ in range(num):
                cursor = pre[cursor]
        elif parse[0] == 'D':
            num = int(parse[1])
            for _ in range(num):
                cursor = nxt[cursor]
        elif parse[0] == 'C':
            erased.append((pre[cursor], cursor, nxt[cursor]))
            # pre, nxt를 변경해 제거를 수행
            if pre[cursor] != -1: nxt[pre[cursor]] = nxt[cursor]
            if nxt[cursor] != -1: pre[nxt[cursor]] = pre[cursor]
            status[cursor] = 'X'
            # 커서 이동
            if nxt[cursor] != -1: cursor = nxt[cursor]
            else: cursor = pre[cursor]

        else: # 'Z'
            pp,cc,nn = erased.pop()
            if pp != -1: nxt[pp] = cc
            if nn != -1: pre[nn] = cc
            status[cc] = 'O'
            
    return ''.join(status)
