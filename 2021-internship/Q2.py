dx1 = [1,0,-1,0]
dy1 = [0,1,0,-1] # 상하좌우

dx2 = [1,-1,1,-1]
dy2 = [1,1,-1,-1] # 대각선

def chk(board):
    for i in range(5):
        for j in range(5):
            if board[i][j] != 'P': continue
            
            # 상하좌우로 거리가 1인 응시자 확인
            for dir in range(4):
                nx, ny = i+dx1[dir], j+dy1[dir]
                if 0 <= nx < 5 and 0 <= ny < 5 and board[nx][ny] == 'P':
                    return 0
            
            # 대각선에 위치한 응시자 확인
            for dir in range(4):
                nx, ny = i+dx2[dir], j+dy2[dir]
                # 대각선에 응시자가 있을 경우 파티션 존재 여부 확인
                if 0 <= nx < 5 and 0 <= ny < 5 and board[nx][ny] == 'P':
                    if board[i][ny] != 'X' or board[nx][j] != 'X':
                        return 0
            
            # 상하좌우로 거리가 2인 응시자 확인
            for dir in range(4):
                nx, ny = i+2*dx1[dir], j+2*dy1[dir]
                # 거리가 2인 곳에 응시자가 있을 경우 파티션 존재 여부 확인
                if 0 <= nx < 5 and 0 <= ny < 5 and board[nx][ny] == 'P':
                    if board[i+dx1[dir]][j+dy1[dir]] != 'X':
                        return 0
                
    return 1

def solution(places):    
    return [chk(place) for place in places]
