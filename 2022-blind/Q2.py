# n을 k진법으로 나타낸 문자열 반환
def conv(n, k):
    s = ''
    while n:
        s += str(n%k)
        n //= k
    return s[::-1]

# n이 소수인지 판정
def isprime(n):
    if n <= 1: return False
    i = 2
    while i*i <= n:
        if n%i == 0: return False
        i += 1
    return True
    
def solution(n, k):
    s = conv(n,k)
    cnt = 0
    for num in s.split('0'):
        if not num: continue # 빈 문자열에 대한 예외처리
        if isprime(int(num)): cnt += 1
    return cnt
