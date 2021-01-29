import bisect

l1 = ['-', 'cpp', 'java', 'python']
l2 = ['-', 'backend', 'frontend']
l3 = ['-', 'junior', 'senior']
l4 = ['-', 'chicken', 'pizza']

def solution(info, query):
    # A. 전처리
    L = [[] for i in range(108)]
    for s in info:
        z = s.split(' ')
        v1, v2, v3, v4 = l1.index(z[0]), l2.index(z[1]), l3.index(z[2]), l4.index(z[3])
        v5 = int(z[4])
        for c1 in [0, v1]:
            for c2 in [0, v2]:
                for c3 in [0, v3]:
                    for c4 in [0, v4]:
                        idx = c1*27 + c2*9 + c3*3 + c4
                        L[idx].append(v5)

    for i in range(108):
        L[i].sort()
    #B. 쿼리
    ret = []
    for q in query:
        z = q.split(' ')
        v1, v2, v3, v4 = l1.index(z[0]), l2.index(z[2]), l3.index(z[4]), l4.index(z[6])
        target = int(z[7])
        idx = v1*27 + v2*9 + v3*3 + v4
        ret.append(len(L[idx]) - bisect.bisect_left(L[idx], target))
    return ret
