import tkinter as tk
import numpy as np
import Plot


#This is the main class of the plotting program, it handles input from the user.
def plotFunction(t):
    retList = []
    if not (isinstance(t,int) or isinstance(t,float)):
        for x in t:
            retList.append(3*np.pi*np.exp(-1*lambd(x)))
    else:
        return 3*np.pi*np.exp(-lambd(t))
    return retList,max(retList), min(retList)

def lambd(t):
    return (5*(np.sin(2*t*np.pi)))

xStart = 0
xLim = 10
root = tk.Tk()
p = Plot.Plot(root,plotFunction,xStart,xLim)

p.plotShow()
