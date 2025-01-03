# 파일 합치기
# Knuth's Optimization - O(N^3)

import sys
import pprint

sys.stdin = open("input.txt")

T = int(input())

for _ in range(T):
    K = int(input())
    fileSize = list(map(int, input().split()))
    # fileSize.sort(reverse = True)
    dpMap = [[0] * K for _ in range(K)]
    priceSum = [0] * K
    for i in range(K):
        if i == 0:
            priceSum[i] = fileSize[i]
        else:
            priceSum[i] = priceSum[i-1] + fileSize[i]

    # i는 구간의 길이
    for i in range(1, K):
        # j는 구간이 시작하는 index
        for j in range(0, K):
            if i+j == K: break
            dpMap[j][j+i] = sys.maxsize

            # k는 구간을 둘로 나누는 역할
            for k in range(j, j+i):
                if j == 0:
                    dpMap[j][j+i] = min(dpMap[j][j+i], dpMap[j][k] + dpMap[k+1][j+i] + priceSum[i+j])
                else:
                    dpMap[j][j+i] = min(dpMap[j][j+i], dpMap[j][k] + dpMap[k+1][i+j] + priceSum[i+j] - priceSum[j-1])
                
    
    print(dpMap[0][K-1])