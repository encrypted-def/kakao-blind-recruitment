def solution(queue1, queue2):
    n = len(queue1)
    a = queue1 + queue2
    target = sum(a)
    if target % 2 != 0:
        return -1
    target //= 2 # 합이 target이 되는 구간을 찾아야 함
    
    ans = 0x7f7f7f7f # 일단 매우 큰 값을 넣어놓고 시작
    en = 0
    tot = a[0] # 현재 보는 구간의 합 = a[st]+...+a[en], 범위가 양 끝을 포함(inclusive)한다는 점과 en < st일 땐 a[st]+...+a[2*n-1]+a[0]+...+a[en]을 나타냄에 주의
    for st in range(2*n):
        while tot < target:
            en = (en + 1) % (2*n)
            tot += a[en]
        
        # while문을 탈출하면 a[st]+...+a[en] 구간의 합이 target 이상임을 의미
        if tot == target: # 구간의 합이 정확히 target과 일치할 경우
            moves = 0 # 작업 횟수
            if en < n-1: moves = 3*n + 1 + st + en # 3n+1번의 연산을 통해 큐1에 첫 번째 원소만 남길 수 있고, 이후 st+en 번의 추가적인 연산을 거쳐 a[st] .. a[en]을 큐1에 둘 수 있음
            else: moves = st + (en - n + 1)
            ans = min(ans, moves)
        
        tot -= a[st]
    
    if ans == 0x7f7f7f7f: ans = -1
    return ans
