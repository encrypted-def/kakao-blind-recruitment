import java.util.*;

class Solution {
    static int dx1[] = {1, 0, -1, 0};
    static int dy1[] = {0, 1, 0, -1};

    static int dx2[] = {1, -1, 1, -1};
    static int dy2[] = {1, 1, -1, -1};

    public boolean OOB(int x, int y){
        return x<0 || x>4 || y<0 || y>4;
    }
    
    public int chk(String[] board){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board[i].charAt(j) != 'P') continue;
                
                // 상하좌우로 거리가 1인 응시자 확인
                for(int dir = 0; dir < 4; dir++){
                    int nx = i+dx1[dir];
                    int ny = j+dy1[dir];
                    if(!OOB(nx,ny) && board[nx].charAt(ny)=='P') return 0;
                }
                
                // 대각선에 위치한 응시자 확인
                for(int dir = 0; dir < 4; dir++){
                    int nx = i+dx2[dir];
                    int ny = j+dy2[dir];
                    if(!OOB(nx,ny) && board[nx].charAt(ny)=='P'){
                        if(board[i].charAt(ny) != 'X' || board[nx].charAt(j) != 'X') return 0;
                    }
                }
                
                // 상하좌우로 거리가 2인 응시자 확인
                for(int dir = 0; dir < 4; dir++){
                    int nx = i+2*dx1[dir];
                    int ny = j+2*dy1[dir];
                    // 거리가 2인 곳에 응시자가 있을 경우 파티션 존재 여부 확인
                    if(!OOB(nx,ny) && board[nx].charAt(ny)=='P'){
                        if(board[i+dx1[dir]].charAt(j+dy1[dir]) != 'X') return 0;
                    }
                }
            }
        }
        return 1;        
    }
    
    public int[] solution(String[][] places) {
        int[] answer = new int[5];
        for(int i = 0; i < 5; i++) answer[i] = chk(places[i]);
        return answer;
    }
}
