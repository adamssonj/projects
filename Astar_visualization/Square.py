import math
import pygame

class square:
    def __init__(self,window,x,y,w,h):
        self.x = x
        self.y = y
        self.squareWidth = w
        self.squareHeight = h
        self.f = 0
        self.g = 0
        self.h = 0

        self.window = window

        self.neighbours = []
        self.previous = None
        self.wall = False

    def draw(self,color,outline, walls = [],lastSquare = None):
        if self.wall:
            color = (255,255,255)
            outline = 0
            for wall in walls:
                self.drawLine(color,wall)
        if lastSquare:
                self.drawLine(color,lastSquare)
        pygame.draw.ellipse(self.window,color,(self.x*self.squareWidth,self.y*self.squareHeight,self.squareWidth,self.squareHeight),outline)
    def drawLine(self,color,object):
        diffX = math.fabs(self.x-object.x)
        diffY = math.fabs(self.y-object.y)
        if diffX+diffY ==1 or not object.wall: #If they are beside each other, draw line
            w = self.squareWidth/2
            start = ((self.x*self.squareWidth-1)+w,(self.y*self.squareWidth-1)+w) ##Need the -1 here because of the outline
            stop = ((object.x*self.squareWidth-1)+w,(object.y*self.squareWidth-1)+w)
            pygame.draw.line(self.window,color,start,stop,int(self.squareWidth))
    def getSquare(self,i,j,grid):
        #   squareWidth = screenWidth / #cols <=> #cols = screenWidth / squareWidth
        if i < 0 or j < 0 or i >= self.window.get_width()/self.squareWidth or j >= self.window.get_height()/self.squareHeight:
            return None
        return grid[i][j]
    def getNeighbours(self,grid):
        if len(self.neighbours) == 0:
            self.addNeighbours(grid)
        return self.neighbours
    def addNeighbours(self,grid):
        self.walls = []
        moveArray = [[-1,0],[0,-1],[1,0],[0,1]]
        for i in range(4):
            self.square = self.getSquare(self.x+moveArray[i][0],self.y+moveArray[i][1],grid)
            if self.square != None:
                if not self.square.wall:
                    self.neighbours.append(self.square)
                else:
                    self.walls.append(self.square)
        ##Diagonals
        diagonalMoves = [[-1,-1],[1,-1],[1,1],[-1,1]]
        for i in range(4):
            self.square = self.getSquare(self.x+diagonalMoves[i][0],self.y+diagonalMoves[i][1],grid)
            if self.square != None:
                if not self.square.wall:
                    ##These if statements checks to make sure we didn't jump inbetween two walls.
                    if i == 2 and not (self.getSquare(self.square.x-1,self.square.y,grid) in self.walls and (self.getSquare(self.square.x,self.square.y-1,grid) in self.walls)):
                        self.neighbours.append(self.square)
                    if i == 0 and not (self.getSquare(self.square.x+1,self.square.y,grid) in self.walls and (self.getSquare(self.square.x,self.square.y+1,grid) in self.walls)):
                        self.neighbours.append(self.square)
                    if i == 1 and not (self.getSquare(self.square.x-1,self.square.y,grid) in self.walls and (self.getSquare(self.square.x,self.square.y+1,grid) in self.walls)):
                        self.neighbours.append(self.square)
                    if i == 3 and not (self.getSquare(self.square.x+1,self.square.y,grid) in self.walls and (self.getSquare(self.square.x,self.square.y-1,grid) in self.walls)):
                        self.neighbours.append(self.square)
