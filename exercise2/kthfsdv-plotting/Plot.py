import numpy as np
import matplotlib.pyplot as plt
import csv
import Options


class Plot():
    def __init__(self,root,function,xStart,xLim):
        #Initalize plot
        self.funct = function
        self.xPoints, self.yPoints, self.yMax, self.yMin= self.setPoints(xStart,xLim)
        self.axis =plt.subplot()
        self.axis.set_xlim(xmin=xStart,xmax=xLim)
        self.axis.set_ylim(ymin=self.yMin,ymax=self.yMax)
        self.axis.plot(self.xPoints, self.yPoints)

        #
        self.lastXmin = xStart
        self.lastXmax = xLim

        self.rxMin = xStart
        self.rxMax = xLim
        self.scale = 0
        #Options
        self.master=root
        self.opt = Options.Options(root,self)



    def setPoints(self,xStart,xLim):
        x = np.arange(xStart,xLim, (xLim-xStart)/1000) #1000points per update
        y,ymax,ymin = self.funct(x)
        return x.tolist(),y,ymax,ymin

#    def updatePoints(self,xStart,xLim): #We only want to update the points that we do not have.A bit tricky when I am using two slides, something to fix
#        if xStart < self.lastXmin:
#            from_ = self.lastXmin
#            to = xStart
#        if xLim > self.lastXmax:
#            from_ = self.lastXmax
#            to = xLim
#        x = np.arange(from_,to,(to-from_)/1000)
#        y,ymax,ymin = self.funct(x)
#        if ymax < self.yMax:
#            ymax=self.yMax
#        if ymin >self.yMin:
#            ymin = self.yMin
#        self.lastXmin = xStart
#        self.lastXmax = xLim
#        return x.tolist(),y,ymax,ymin

    def updateAxis(self,up, scale =0):
        if scale:
            scale = int(scale)
            scaleConst = 1
            xmin, xmax, ymin, ymax = plt.axis() #Stored as numpy float64
            if self.scale <= scale:
                if up:
                    xmax = xmax + scale*scaleConst
                else:
                    xmin = xmin - scale*scaleConst

            #self.axis.set_xlim(xmin=xmin, xmax= xmax)
            self.xPoints, self.yPoints,self.yMax, self.yMin = self.setPoints(xmin,xmax)
            #self.axis.set_ylim(ymin= self.yMin, ymax= self.yMax)
            self.axis.set(xlim=(xmin,xmax), ylim= (self.yMin,self.yMax))
            self.axis.autoscale()
            self.scale = scale
            plt.draw()

        self.axis.plot(self.xPoints,self.yPoints,'b')

    def resetAxis(self):
        plt.clf()
        self.axis =plt.subplot()
        self.axis.set_xlim(xmin=self.rxMin, xmax= self.rxMax)
        self.xPoints, self.yPoints,self.yMax,self.yMin = self.setPoints(self.rxMin,self.rxMax)
        self.axis.set_ylim(ymin= self.yMin, ymax= self.yMax)
        self.axis.plot(self.xPoints,self.yPoints)
        plt.draw()
        self.opt.resetSlides()

    def saveData(self,name):
        dataName = name.get()+'.csv'
        with open(dataName,'w',newline='') as f:
            writer = csv.writer(f)
            writer.writerow(['x', 'y']) #Header
            for i in range(len(self.xPoints)):
                writer.writerow([self.xPoints[i],self.yPoints[i]])

    def savePlot(self,plotName):
        plt.savefig(fname=plotName.get())
    def plotShow(self):
        plt.show()
        self.master.mainloop()
