import tkinter as tk


class Options():
    def __init__(self,root,parent):
        width = 400
        height = 200
        settings = str(width)+'x'+str(height)+"+1500+100"
        root.geometry(settings)
        self.parent = parent
        self.clearOnce = False
        self.master = root

        #Initalize fields, buttons and sliders
        plotName = tk.Entry(root,width = 11)
        plotName.pack()
        plotName.insert(0,"Plot Name")
        plotName.place(x=0,y=0)
        plotName.bind("<FocusIn>",self.clearBox)

        dataName = tk.Entry(root,width = 11)
        dataName.pack()
        dataName.insert(0,'Data Name')
        dataName.place(x=0,y=30)
        dataName.bind('<FocusIn>',self.clearBox)

        savePlotButton = tk.Button(self.master,text="Save Plot", command = lambda name = plotName:parent.savePlot(name))
        savePlotButton.pack()
        savePlotButton.place(x=300,y=30)

        saveDataButton = tk.Button(self.master,text="Save Data", command = lambda name= dataName:parent.saveData(name))
        saveDataButton.pack()
        saveDataButton.place(x=300, y=60)


        resetAxis = tk.Button(self.master,text="Reset Axis", command = parent.resetAxis)
        resetAxis.pack()
        resetAxis.place(x= 300, y=0)

        self.slideUp = tk.Scale(self.master, from_= 0, to = 100, orient = tk.HORIZONTAL,command = lambda scale,up=True: parent.updateAxis(up,scale))
        self.slideUp.pack()
        self.slideUp.place(x = 120, y= 0)

        self.slideDown = tk.Scale(self.master,from_= 0,to = 100,orient=tk.HORIZONTAL, command = lambda scale,up=False: parent.updateAxis(up,scale))
        self.slideDown.pack()
        self.slideDown.place(x=120,y=40)

    def resetSlides(self):
        self.slideUp.set(0)
        self.slideDown.set(0)
    def clearBox(self,event):
        if not self.clearOnce:
            event.widget.delete(0,"end")
        self.clearOnce = True
    def getScale(self,que=0):
        return que
