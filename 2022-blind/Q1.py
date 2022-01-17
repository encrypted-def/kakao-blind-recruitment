def solution(id_list, report, k):
    s2i = {} # 아이디를 0, 1, 2, ..의 인덱스로 변환
    n = len(id_list)
    s = [set() for _ in range(n)] # s[i] : i를 신고한 사람의 집합
    cnt = [0] * n
    
    for i in range(n):
        s2i[id_list[i]] = i
    
    for rep in report:
        id1, id2 = rep.split()
        s[s2i[id2]].add(s2i[id1])
        
    for i in range(n):
        if len(s[i]) < k: continue
        for x in s[i]: cnt[x] += 1
    
    return cnt
