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

#TODO: Change such that only points not already calculated are added to the list instead of recalculating every point when using the sliders!
#TODO: Create sepe

    def updateAxis(self,up, scale =0):
        if scale:
            scale = int(scale)
            scaleConst = 1
            xmin, xmax, ymin, ymax = plt.axis()
            if self.scale <= scale:
                if up:
                    xmax = xmax + scale*scaleConst
                else:
                    xmin = xmin - scale*scaleConst

            self.xPoints, self.yPoints,self.yMax, self.yMin = self.setPoints(xmin,xmax)
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
        dataName = 'Data/'+name.get()+'.csv'
        with open(dataName,'w',newline='') as f:
            writer = csv.writer(f)
            writer.writerow(['x', 'y']) #Header
            for i in range(len(self.xPoints)):
                writer.writerow([self.xPoints[i],self.yPoints[i]])

    def savePlot(self,plotName):
        name = 'Data/'+plotName.get()
        plt.savefig(fname=name)
    def plotShow(self):
        plt.show()
        self.master.mainloop()
