MX = 1200005 # n + len(cmd) + 5(dummy)
dat = [-1]*MX
pre = [-1]*MX
nxt = [-1]*MX
unused = 1

num2idx = [-1]*1000005

# 새로 num을 삽입한 위치를 반환
def insert(addr, num):
    global unused
    dat[unused] = num
    pre[unused] = addr
    nxt[unused] = nxt[addr]
    if nxt[addr] != -1:
        pre[nxt[addr]] = unused
    nxt[addr] = unused
    unused = unused + 1
    return unused - 1
    
# addr번지의 원소를 제거, 제거한 다음 원소를 반환. 만약 삭제된 원소가 가장 마지막 원소였을 경우 이전 원소를 반환(문제의 상황과 동일)
def erase(addr):
    global unused
    nxt[pre[addr]] = nxt[addr]
    if nxt[addr] != -1:
      pre[nxt[addr]] = pre[addr]
      return nxt[addr]
    return pre[addr]
    
def solution(n, k, cmd):
    for i in range(n):
        num2idx[i] = insert(i, i)
    cursor = num2idx[k]
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
            erased.append((dat[pre[cursor]], dat[cursor])) # pre value, cur value
            cursor = erase(cursor)
        else: # 'Z'
            preval, curval = erased.pop()
            if preval == -1: preidx = 0
            else: preidx = num2idx[preval]
            num2idx[curval] = insert(preidx, curval)
            
    status = ['X'] * n
    cur = nxt[0]
    while cur != -1:
        status[dat[cur]] = 'O'
        cur = nxt[cur]
    return ''.join(status)
