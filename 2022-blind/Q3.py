def formula(t, fees):
    if t <= fees[0]: return fees[1]
    q = (t - fees[0] + fees[2] - 1) // fees[2]
    return fees[1] + q * fees[3]

def s2t(s):
    return int(s[:2])*60+int(s[3:])

def solution(fees, records):
    cnt = [0]*10000 # cnt[i] : 차 i가 주차한 총 시간
    stored = [-1]*10000 # 직전에 입차한 시각, 입차하지 않았다면 -1
    for r in records:
        s, num, state = r.split()
        t = s2t(s)
        num = int(num)
        if state == "IN": # 입차
            stored[num] = t
        else: # 출차
            cnt[num] += t - stored[num]
            stored[num] = -1
    
    for i in range(10000):
        if stored[i] != -1:
            cnt[i] += s2t('23:59') - stored[i]

    return [formula(x, fees) for x in cnt if x != 0]
