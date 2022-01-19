from itertools import *

# a가 b보다 더 좋은 배치일 경우 true
def cmp(a, b):
    return a[::-1] > b[::-1]

def solution(n, info):
    # 라이언이 가장 큰 점수 차이로 우승할 수 있는 결과를 저장
    # ret[0..10] : 10-i점에서 라이언이 맞힌 화살의 수, ret[11] : 점수 차이
    ret = [-1] * 12 
    for comb in combinations_with_replacement(range(11), n):
        arrow = [0] * 12
        score = 0
        for x in comb: arrow[x] += 1
        for i in range(11):
            if arrow[i] > info[i]:
                score += (10 - i)
            elif info[i] != 0:
                score -= (10 - i)
        if score <= 0: continue
        arrow[11] = score
        if cmp(arrow, ret):
            ret = arrow[:] # deepcopy를 해야 함에 주의
    if ret[0] == -1: return [-1]
    return ret[:-1]
