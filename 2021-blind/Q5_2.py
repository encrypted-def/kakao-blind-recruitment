def s2i(s):
    z = s.split(':')
    return int(z[0])*3600+int(z[1])*60+int(z[2])

def i2s(t):
    ret = ''
    ret += str(t//3600).zfill(2)+':'
    t %= 3600
    ret += str(t//60).zfill(2)+':'
    t %= 60
    ret += str(t).zfill(2)
    return ret

def solution(play_time, adv_time, logs):
    event = []
    pt, at = s2i(play_time), s2i(adv_time)
    # A. 전처리
    for l in logs:
        st,en = map(s2i, l.split('-'))
        event.append((st,1))
        event.append((en,-1))
    event.append((0, 0));
    event.sort()
    # B. 누적 재생시간이 가장 많은 곳 계산
    # cnt1 : 시작 구간에서의 시청중인 사람의 수
    # cnt2 : 끝 구간에서의 시청중인 사람의 수
    idx1, idx2, cnt1, cnt2 = 0, 0, 0, 0
    curtime, curval = 0, 0
    while idx2 != len(event) - 1 and event[idx2+1][0] <= at:
        curval += (event[idx2+1][0]-event[idx2][0]) * cnt2
        cnt2 += event[idx2+1][1]
        idx2 += 1
    curval += (at - event[idx2][0]) * cnt2
    mxval = curval
    mxtime = 0
    while curtime <= pt-at and idx2 < len(event) - 1:
        delta1 = event[idx1+1][0] - curtime
        delta2 = event[idx2+1][0] - (curtime + at)
        if delta1 <= delta2: # 시작 구간이 다음 event에 더 가까운 경우
            curval = curval + (cnt2 - cnt1) * delta1
            cnt1 += event[idx1+1][1]
            idx1 += 1
            curtime += delta1
        else:
            curval = curval + (cnt2 - cnt1) * delta2
            cnt2 += event[idx2+1][1]
            idx2 += 1
            curtime += delta2
        if curval > mxval:
            mxval, mxtime = curval, curtime
    return i2s(mxtime)
