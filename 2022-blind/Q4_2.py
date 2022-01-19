from itertools import *

# a가 b보다 더 좋은 배치일 경우 true
def cmp(a, b):
    return a[::-1] > b[::-1]

def solution(n, info):
    # 라이언이 가장 큰 점수 차이로 우승할 수 있는 결과를 저장
    # ret[0..10] : 10-i점에서 라이언이 맞힌 화살의 수, ret[11] : 점수 차이
    ret = [-1] * 12 
    for brute in range(1024):
        arrow = [0] * 12
        score = 0
        left = n # 남아있는 화살의 수
        for i in range(10):
            if brute & (1 << i): # i번째 비트가 켜져 있는 경우(10-i점에서 승리할 계획)
                score += (10 - i)
                left -= (info[i] + 1)
                arrow[i] = info[i] + 1
            elif info[i] != 0:
                score -= (10 - i)
        # 라이언의 점수가 높지 않거나 화살을 n발 초과로 쏜 경우
        if score <= 0 or left < 0: continue
        arrow[10], arrow[11] = left, score
        # arrow가 기존에 저장된 ret보다 좋을 경우
        if cmp(arrow, ret): ret = arrow[:] # deepcopy를 해야 함에 주의
    if ret[0] == -1: return [-1]
    return ret[:-1]
