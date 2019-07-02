import pygame
import math
from Square import square

#pygame inititalizations
pygame.init()
scrW,scrH = 1000,1000
window = pygame.display.set_mode((scrW,scrH))
pygame.display.set_caption("A* visualization")
clock = pygame.time.Clock()


#Create my grid of squares
cols = 50
rows = 50
grid = [[0 for x in range(cols)] for y in range(rows)]
squareWidth = scrW / cols
squareHeight = scrH / rows
path = []
black = (0,0,0)
white = (255,255,255)
pink = (255,0,255)
green=(0,255,0)
red= (255,0,0)
blue = (0,0,255)

def initSquares():
    #Fill my array
    window.fill(black)
    for x in range(cols):
        for y in range(rows):
            grid[x][y] = square(window,x,y,squareWidth,squareHeight)
    #for i in range(cols):
    #    for j in range(rows):
    #        grid[i][j].draw(white,1)

def heuristic(a,b):
    return math.sqrt((a.x-b.x)**2 + (a.y-b.y)**2)

def mainAlgorithm():
    while len(openSet) != 0:
        lowestIndex = 0
        for i in range(len(openSet)):
            if openSet[i].f < openSet[lowestIndex].f:
                lowestIndex = i

        current = openSet[lowestIndex]

        if current == end:
            #print("Algorithm Finished")
            path.insert(0,end)
            return True,path
        closedSet.append(current)
        openSet.pop(lowestIndex)

        neighbours = current.getNeighbours(grid)
        for neighbour in neighbours:
            if neighbour not in closedSet and not neighbour.wall:
                temp_g = current.g + heuristic(neighbour,current) ## Need to check here if it's a diagonal neighbour or not.

                if neighbour not in openSet:
                    openSet.append(neighbour)
                elif temp_g >= neighbour.g:
                     continue
                neighbour.g = temp_g
                neighbour.h = heuristic(neighbour, end)
                neighbour.f = neighbour.g + neighbour.h
                neighbour.previous = current

        ##Draw open and closed set
        for i in range(len(openSet)):
            openSet[i].draw(green,0)
        for j in range(len(closedSet)):
            closedSet[j].draw(red,0)
        path.clear()
        path.append(current)
        while current.previous:
            path.append(current.previous)
            current = current.previous
        return False,path
    return True, path

def addWall(pos):
    x,y = pos[0], pos[1]
    gridX = int(x / squareWidth)
    gridY = int(y / squareHeight)
    checkSquare = grid[gridX][gridY]
    if checkSquare != start and checkSquare != end:
        grid[gridX][gridY].wall = True
        walls.append(checkSquare)
        grid[gridX][gridY].draw(black,0,walls)

initSquares()


#A star set init
walls = []
openSet = []
closedSet = []
start = grid[0][0]
end = grid[-1][-1]

openSet.append(start)
def main(addedWalls):
    run = True
    ok = False
    while not addedWalls:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                return False,False
            if pygame.mouse.get_pressed()[0]:
                pos =  pygame.mouse.get_pos()
                addWall(pos)
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_RETURN:
                    addedWalls = True
                    break
        pygame.display.update()
    while run and not ok:
        start.draw(pink,0)
        end.draw(pink,0)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                return False,False
        ok,path = mainAlgorithm()
        if ok and path[-1] == start:
            last = path[0]
            for i in range(1,len(path)):
                path[i].draw(pink,0,[],last)
                last = path[i]

        pygame.display.update()
    return (False, True) if ok else (True, True)

runGame= True
runMain = True
addedWalls = False
while runGame:
    clock.tick(60)
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            runGame = False
            break
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_ESCAPE:
                #If we want to go again we can press escape
                initSquares()
                openSet.clear()
                closedSet.clear()
                walls.clear()
                start = grid[0][0]
                end = grid[-1][-1]
                addedWalls = False
                runMain = True
                openSet.append(start)
                pygame.display.update()
    if runMain:
        runMain,runGame = main(addedWalls)
        addedWalls = True
