import PSOfunction
for i in range(500):
    if(i==0):
        particle=select(10,Ld,Ud,vmax,vmin)
        globalmax,globalposition=globalevaluate(particle)
    else:
        renewparticle(particle,c1,c2,globalposition,weight,vmax,vmin,Ld,Ud)
        evallocal(particle)
        globalmax,globalposition=globalevaluate(particle)
print("最佳位置")
print(globalposition)
print("最佳值")
print(globalmax)