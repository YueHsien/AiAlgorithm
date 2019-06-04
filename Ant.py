tsp=[
     [0,91.8,105.2,89.9,189.9,76.2,278.3,54.4],
     [91.8,0,187.2,38.9,271.3,162.9,363.3,88.4],
     [105.2,187.2,0,194.1,182.3,31.4,176.1,153.8],
     [89.9,38.9,194.1,0,249.4,166.1,368.3,63.6],
     [189.9,271.3,182.3,249.4,0,168.0,243.0,185.9],
     [76.2,162.9,31.4,166.1,168.0,0,202.2,122.8],
     [278.3,363.3,176.1,368.3,243.0,202.2,0,320.0],
     [54.4,88.4,153.8,63.6,185.9,122.8,320.0,0]
     ]
degree=[
     [0,0.0001,0.0001,0.0001,0.0001,0.0001,0.0001,0.0001],
     [0.0001,0,0.0001,0.0001,0.0001,0.0001,0.0001,0.0001],
     [0.0001,0.0001,0,0.0001,0.0001,0.0001,0.0001,0.0001],
     [0.0001,0.0001,0.0001,0,0.0001,0.0001,0.0001,0.0001],
     [0.0001,0.0001,0.0001,0.0001,0,0.0001,0.0001,0.0001],
     [0.0001,0.0001,0.0001,0.0001,0.0001,0,0.0001,0.0001],
     [0.0001,0.0001,0.0001,0.0001,0.0001,0.0001,0,0.0001],
     [0.0001,0.0001,0.0001,0.0001,0.0001,0.0001,0.0001,0]
    ]
import random
import itertools
import math
import copy
ant=[]
set=[1,2,3,4,5,6,7,8]
ad=1#濃度參數
bd=0#距離參數
Q=100 #濃度強度
nums = list(itertools.permutations([1,2,3,4,5,6,7]))
#a=random.choice(list(itertools.combinations(x0,2))) #隨機挑選2個節點
def selectant(n):
    i=0
    k=[]
    for x in nums: #挑n個
        if(i==n):
            break;
        else:
            k.append([])
            randselect=random.randint(0,3000)
            k[i].append(nums[randselect][0])
            i+=1
    return k
def handlej(fitemp,valuesforkey,temp,ant,k,degree):
    dice=random.random()
    for i in range(len(fitemp)): #跟GA一樣的輪盤概念
         if(dice<fitemp[i]):
              valuesforkey=fitemp[i]
              break;
         elif(i==0):
              continue
    for i,j in temp.items():
              if(valuesforkey==j[1]):
                 ant[k].append(i)
                 temp.pop(i)
                 partrenew(ant[k],degree)
                 break;
    return
def handleacs(temp,ant,k,degree):
    #select=
    s=[temp[i][2] for i in temp.keys()]
    s=max(s)
    for i,j in temp.items():
        if(j[2]==s):
            ant[k].append(i)
            temp.pop(i)
            partrenew(ant[k],degree)
            break
    return
def selectnode(ant,set,ad,bd,degree):
    k=0
    #print(ant)
    q0=0.5 #決定用哪種轉換機率 小於則用acs，否則用原本
    while 1:
        temp={} #儲存尚未拜訪的點以及機率及機率總和(輪盤用)還有轉換率的分子用於找max用。
        for i in set:
            if(i not in ant[k]):
                temp.setdefault(i,[0,0,0]) #從左依序是，每個點的機率、每個點相加的機率、max用的分子
        while temp:
            index=len(ant[k])-1
            sump=0 #轉換機率的分母總和
            sump1=0#加總所有的機率值
            for i,j in temp.items():
                v1=ant[k][index]
                sump+=math.pow(degree[v1-1][i-1],ad)*math.pow((1/tsp[v1-1][i-1]),bd) 
                #計算尚未拜訪節點的濃度和長度倒數的分母
            for i,j in temp.items():
                v1=ant[k][index]
                vary=math.pow(degree[v1-1][i-1],ad)*math.pow((1/tsp[v1-1][i-1]),bd)
                temp[i][2]=vary #將分子的值紀錄進去
                vary=vary/sump
                #上面是計算該點到尚未拜訪各點的分子並計算機率
                temp[i][0]=vary
                sump1+=vary#將機率做加總
                temp[i][1]=sump1
            #print("temp=")
            #print(temp)
            #print()
            #for i in degree:
             #   print(i)
              #  print()
            #print()
            fitemp=[]
            for i,j in temp.items():
                fitemp.append(j[1])
                valuesforkey=0
           # print("fitemp=")
            #print(fitemp)
            #print()
            #if(zz==0):
            q=random.random()
            if(q<=q0):
                handleacs(temp,ant,k,degree)
            else:
                handlej(fitemp,valuesforkey,temp,ant,k,degree)  
        k+=1
        if(k==len(ant)):
           #print(ant)
           break
    return ant

def length(k):
    distance=tsp[k[len(k)-1]-1][k[0]-1]
    for i in range(len(tsp[0])):
        if(i==len(tsp)-1):
            break
        else:
            distance+=tsp[k[i]-1][k[i+1]-1]
    return distance
def partrenew(ant,degree):
    v1=ant[len(ant)-2]-1
    v2=ant[len(ant)-1]-1
    degree[v1][v2]=((1-0.95)*degree[v1][v2])+(0.95*0.0001)
    if(len(ant)==8):
        degree[ant[8-1]-1][ant[0]-1]=((1-0.95)*degree[ant[len(ant)-1]-1][ant[0]-1])+(0.95*0.0001)
    return
def alldegree(mint,Q):
    L=length(mint) #總長度
    v1=mint[len(mint)-1]-1
    v2=mint[0]-1
    degree[v1][v2]+=Q/L
    #print(degree)
    ll=[]
    for i in range(len(mint)):
        if(i==0):
            continue
        else:
            v1=mint[i-1]-1
            v2=mint[i]-1
            degree[v1][v2]=(1-0.95)*degree[v1][v2]+0.95*(Q/L)
    #print(degree)
    return
def optstep2initial(l,t):
    a=list(itertools.combinations(t,2))
    minl=length(t)
    mint=copy.deepcopy(t)
    tmax=0
    for i in a:
        if(i[0]==start or i[1]==start):
           continue
        elif(tmax==10):
            break
        else:
            tarray=copy.deepcopy(t)
            a0=tarray.index(i[0])
            a1=tarray.index(i[1])
            temp=tarray[a0]
            tarray[a0]=tarray[a1]
            tarray[a1]=temp
            #print(tarray)
            templ=length(tarray) #計算長度
            tmax+=1
            if(templ<minl):
                minl=templ
                mint=tarray
    return minl,mint
def optstep1(ant,minl,mint):
    l=0 #長度
    t=[] #路徑
    al=0
    at=[]
    tem=[]
    tl=[]
    for i in ant:
        l=length(i)
        t=i
        al,at=optstep2(l,t,minl,mint)
        tl.append(al)
        tem.append(at)
    #print(tl)
    #print(tem)
    index=min(tl)
    #print(index)
    index=tl.index(index)
    #print(index)
    index2=tem[index]
    return index2
def optstep2(l,t,minl,mint):
    a=list(itertools.combinations(t,2))
    tmax=0
    minll=length(t)
    mintt=copy.deepcopy(t)
    global start
    for i in a:
        if(i[0]==start or i[1]==start):
           continue
        elif(tmax==10):
            break
        else:
            tarray=copy.deepcopy(t)
            a0=tarray.index(i[0])
            a1=tarray.index(i[1])
            temp=tarray[a0]
            tarray[a0]=tarray[a1]
            tarray[a1]=temp
            #print(tarray)
            templ=length(tarray) #計算長度
            tmax+=1
            if(templ<minll):
                minll=templ
                mintt=tarray
                #先算出這一批區域解的最好，再去給表現最好的螞蟻的線段且包含在最佳解內加強濃度。
    return minll,mintt
def optstep3(mint): #加強最佳路徑的濃度
    L=length(mint)
    v1=mint[len(mint)-1]-1
    v2=mint[0]-1
    degree[v1][v2]+=(1-0.95)*degree[v1][v2]+0.95*(100/L)
    for i in range(len(mint)):
        if(i==len(mint)-1):
            break
        else:
            v1=mint[i]-1
            v2=mint[i+1]-1
            degree[v1][v2]+=(1-0.95)*degree[v1][v2]+0.95*(100/L)
    return
def optstepinitial(ant):
    l=0 #長度
    t=[] #路徑
    minl=0
    mint=[]
    for i in ant:
        l=length(i)
        t=i
        minl,mint=optstep2initial(l,t)
    return minl,mint
minl=0
mint=[]
deb=0
start=8 #s

for i in range(50):
        ant=[[start],[start],[start],[start],[start]]
        #ant=selectant(5)
        ant=selectnode(ant,set,ad,bd,degree)
        #for i in ant:
         #   print(i)
        #    print
        mint=optstep1(ant,minl,mint)
        optstep3(mint)

print("最低路徑")
print(mint)
print()
print("最短長度=")
print(length(mint))
