# d[i][j] : 알고력 i, 코딩력 j를 달성하는데 필요한 최소 시간
# 단 d[alp_max][j]는 알고력이 alp_max "이상", 코딩력 j를 달성하는데 필요한 최소 시간, d[i][cop_max]는 알고력 j, 코딩력 cop_max "이상"을 달성하는데 필요한 최소 시간

MX = 0x7f7f7f7f

def solution(alp, cop, problems):
    d = [[MX]*155 for _ in range(155)]
    alp_max = max(problem[0] for problem in problems)
    cop_max = max(problem[1] for problem in problems)
    alp = min(alp, alp_max)
    cop = min(cop, cop_max) # 초기의 알고력(코딩력) 자체가 alp_max(cop_max)보다 높은 경우에 대한 예외처리
    d[alp][cop] = 0 # dp의 초기값
    for i in range(alp, alp_max+1):
        for j in range(cop, cop_max+1):
            d[i+1][j] = min(d[i+1][j], d[i][j]+1)
            d[i][j+1] = min(d[i][j+1], d[i][j]+1)
            for problem in problems:
                alp_req, cop_req, alp_rwd, cop_rwd, cost = problem
                if i < alp_req or j < cop_req: continue
                alp_nxt = min(alp_max, i + alp_rwd)
                cop_nxt = min(cop_max, j + cop_rwd)
                d[alp_nxt][cop_nxt] = min(d[alp_nxt][cop_nxt], cost + d[i][j])
    return d[alp_max][cop_max]
