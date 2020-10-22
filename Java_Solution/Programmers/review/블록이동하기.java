import java.util.*;

class Solution {
    static int N, answer;
    static int[][] nboard;
    static Queue<Drone> que;
    static HashSet<Drone> checkSet;
    
    public static void makePadding(int[][] board) {
        for(int i=0; i<N+2; i++) {
            for(int j=0; j<N+2; j++) {
                if(i==0 || j==0 || i==N+1 || j==N+1) {
                    nboard[i][j] = 1;
                } else {
                    nboard[i][j] = board[i-1][j-1];
                }
            }
        }
    }
    
    public static boolean pushDrone(int x1, int y1, int x2, int y2, int time) {
        Drone drone = new Drone(x1, y1, x2, y2, time);
        
        if(checkSet.contains(drone)) return false;
        
        que.offer(drone);
        checkSet.add(drone);
        return true;
    }
    
    public static void findMinTime() {
        pushDrone(1,1,1,2,0);
        
        int[] dx = {0,1,0,-1}, dy = {1,0,-1,0};
        int[] rotate = {-1, 1};
        int x1, y1, x2, y2, time;
        int nx1, ny1, nx2, ny2;
        
        while(!que.isEmpty()) {
            Drone drone = que.poll();
            x1 = drone.x1;
            y1 = drone.y1;
            x2 = drone.x2;
            y2 = drone.y2;
            time = drone.time;
            
            if((x1 == N && y1 == N) || (x2 == N && y2 == N)) {
                answer = time;
                return;
            }
            
            for(int dir=0; dir<4; dir++) {
                nx1 = x1 + dx[dir];
                ny1 = y1 + dy[dir];
                nx2 = x2 + dx[dir];
                ny2 = y2 + dy[dir];
                
                if(nboard[nx1][ny1] == 0 && nboard[nx2][ny2] == 0) {
                    pushDrone(nx1, ny1, nx2, ny2, time+1);
                }
            }
            
            for(int r=0; r<2; r++) {
                nx1 = x1 + rotate[r];
                ny1 = y1 + rotate[r];
                nx2 = x2 + rotate[r];
                ny2 = y2 + rotate[r];
                
                if(x1 == x2) {
                    if(nboard[nx1][y1] == 0 && nboard[nx2][y2] == 0) {
                        pushDrone(nx1, y1, x2, y1, time+1);
                        pushDrone(nx2, y2, x2, y2, time+1);
                    }
                } else if(y1 == y2) {
                    if(nboard[x1][ny1] == 0 && nboard[x2][ny2] == 0) {
                        pushDrone(x1, ny1, x1, y2, time+1);
                        pushDrone(x2, ny2, x2, y2, time+1);
                    }
                }
            }
        }
    }
    
    public static int solution(int[][] board) {
        answer = 0;
        N = board.length;
        nboard = new int[N+2][N+2];
        que = new LinkedList<>();
        checkSet = new HashSet<>();
        
        makePadding(board);
        findMinTime();
        
        return answer;
    }
    
    public static void main(String[] args) {
        int[][] board = {};
        
        solution(board);
    }
}

class Drone {
    int x1, y1;
    int x2, y2;
    int time;
    
    Drone(int x1, int y1, int x2, int y2, int time) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.time = time;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Drone) {
            Drone drone = (Drone)obj;
            if(this.x1 == drone.x1 && this.y1 == drone.y1 && this.x2 == drone.x2 && this.y2 == drone.y2) return true;
            if(this.x1 == drone.x2 && this.y1 == drone.y2 && this.x2 == drone.x1 && this.y2 == drone.y1) return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int prime = 31;
        int hashcode = 0;
        
        hashcode = prime * hashcode + x1;
        hashcode = prime * hashcode + y1;
        hashcode = prime * hashcode + x2;
        hashcode = prime * hashcode + y2;
        
        return hashcode;
    }
}