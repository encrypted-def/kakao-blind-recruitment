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
    pt, at = s2i(play_time), s2i(adv_time)
    # A. 특정 초에 시청중인 사람의 수 계산
    d = [0]*360001
    for l in logs:
        st, en = map(s2i, l.split('-'))
        d[st] += 1
        d[en] -= 1
    for i in range(1, 360001):
        d[i] += d[i-1]
    # B. 누적 재생시간이 가장 많은 곳 계산
    mxval, mxtime = sum(d[:at]), 0
    curval = mxval
    for i in range(1, 360001-at):
        curval = curval - d[i-1] + d[i+at-1]
        if curval > mxval:
            mxval = curval
            mxtime = i
    return i2s(mxtime)
