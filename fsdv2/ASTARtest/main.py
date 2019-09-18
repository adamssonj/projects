import pygame
import math
from Square import square

pygame.init()
scrW,scrH = 1000,1000
window = pygame.display.set_mode((scrW,scrH))
pygame.display.set_caption("A* visualization")
clock = pygame.time.Clock()

cols = 25
rows = 25
grid = [[0 for x in range(cols)] for y in range(rows)]
squareWidth = scrW / cols
squareHeight = scrH / rows
walls = []
cones_visited = []



def initSquares():
    #Fill my array
    window.fill((0,0,0))
    for x in range(cols):
        for y in range(rows):
            grid[x][y] = square(window,x,y,squareWidth,squareHeight)
            grid[x][y].draw((255,255,255),1)

def getPath(start, end):
    path = [end]
    while end.previous:
        if start.getPos() == end.previous.getPos():
            break
        path.append(end.previous)
        end = end.previous
    return path
def drawPath(myList):
    for p in myList:
        p.draw((255,0,255),0)

def heuristic(a,b):
    return math.sqrt((a.x-b.x)**2 + (a.y-b.y)**2)
def distance(a,b):
        return math.sqrt((a[0]-b[0])**2 + (a[1]-b[1])**2)

direction = 1 #Up = 0 Right = 1, Down = 2, left = 3

#def findDest(start):
#    #Okay, depending on the direction we should check for where to go
#    #For the demo, let's just make an easy version
#    pos = start.getPos()
#    global direction
#
#    if direction == 1: #Right
#        for i in range(pos[0],cols):
#            if grid[i][pos[1]].wall:
#                #if grid[i][pos[1]-1].wall or grid[i-1][pos[1]-1].wall: #Is it a wall above?
#                #    direction = 2
#                #else:
#                #    direction = 0
#                direction=0
#                return grid[i-1][pos[1]]
#    elif direction == 2: #Down
#        for i in range(pos[1],rows):
#            if grid[pos[0]][i].wall:
#                #if grid[pos[0]-1][i].wall or grid[pos[0]-1][i-1].wall:
#                #    direction = 1
#                #else:
#                #    direction = 3
#                direction=1
#                return grid[pos[0]][i-1]
#        return grid[pos[0]][pos[1]+4]
#    elif direction == 3: #Left
#        for i in reversed(range(0,pos[0])):
#            if grid[i][pos[1]].wall:
#            #    if grid[i][pos[1]-1].wall or grid[i-1][pos[1]-1].wall: #Is it a wall above?
#            #        direction = 2
#            #    else:
#            #        direction = 0
#                direction = 2
#                return grid[i+1][pos[1]]
#        return grid[pos[0]-4][pos[1]]
#    elif direction == 0: #Up
#        for i in reversed(range(0, pos[1])):
#            if grid[pos[0]][i].wall:
#            #    print("?")
#            #    if grid[pos[0]-1][i].wall or grid[pos[0]-1][i-1].wall: #Is it a wall to the left?
#            #        direction = 1
#            #    else:
#            #        direction = 3
#                direction = 3
#                return grid[pos[0]][i+1]
#        return grid[pos[0]][pos[1]-4]
#    return grid[-1][-1]

def diff(first, second):
    #second = set(second)
    tst = [item for item in first if item not in second]
    return tst

def findDest_cone(position,cone_Above, cone_Below):
    cones_not_visited = diff(walls,cones_visited)
    next_above = cones_not_visited[0]
    next_below = cones_not_visited[-1]

    for i in cones_not_visited:
        if heuristic(i,cone_Above) < heuristic(next_above,i):
            next_above = i
        if heuristic(i,cone_Below)< heuristic(next_below,i):
            next_below = i
    return next_above, next_below

def findCones(position): #Should be just finding the cones with the smallest euclidean distance
    cones_not_visited = diff(walls,cones_visited)
    cone_above = cones_not_visited[0]
    cone_below = cones_not_visited[-1]

    for i in cones_not_visited:
        if i.x < position.x or i.y < position.y:

            if heuristic(i,position) <  distance(cone_above.getPos(),position.getPos()):
                cone_above = i
        else:
            if heuristic(i,position) <  distance(cone_below.getPos(),position.getPos()):
                cone_below = i
    return cone_above, cone_below

def midPoint(pos1,pos2):
    a= (pos1[0]+pos2[0]) / 2
    b = (pos1[1]+pos2[1]) /2
    return (int(a),int(b))

def Astar(pos):
    open_list = []
    closed_list = []
    start = grid[pos[0]][pos[1]]

    cone_above, cone_below = findCones(start)
    cones_visited.append(cone_above)
    cones_visited.append(cone_below)
    nxt_above, nxt_below = findDest_cone(start,cone_above,cone_below)
    end_coord = midPoint(nxt_above.getPos(), nxt_below.getPos())
    end = grid[end_coord[0]][end_coord[1]]


    open_list.append(start)
    prev = start
    while len(open_list) > 0:
        #We need to find the node with the lowest f value and continue from there
        lowestF = 0
        for i in range(len(open_list)):
            if open_list[i].f < open_list[lowestF].f:
                lowestF = i

        current = open_list[lowestF]

        #Are we finished?
        if current == end:
            current.previous = prev
            return getPath(start,current)

        closed_list.append(current) #Add current node to the closed set, to mark it finished
        open_list.pop(lowestF)      #Likewise remove it from the open

        #Find neighbouring nodes, not walls
        neighbours = current.getNeighbours(grid)
        for neighbour in neighbours:
        #Check all neighbours that are not in the closed list
        #if neighbour not in closed_list:
            if neighbour not in closed_list and not neighbour.wall:
                temp_g = current.g + heuristic(neighbour,current) ## Need to check here if it's a diagonal neighbour or not.
                if neighbour not in open_list:
                    open_list.append(neighbour)
                elif temp_g >= neighbour.g:
                     continue
                neighbour.g = temp_g
                neighbour.h = heuristic(neighbour, end)
                neighbour.f = neighbour.g + neighbour.h
                neighbour.previous = current
        prev = current

    return []

def drawCar(prev, new):
    grid[prev[0]][prev[1]].draw((150,0,0),0)
    grid[new[0]][new[1]].draw((255,0,255),0)

def addWall(pos):
    x,y = pos[0], pos[1]
    gridX = int(x / squareWidth)
    gridY = int(y / squareHeight)
    #gridX = x
    #gridY = y
    grid[gridX][gridY].wall = True
    walls.append(grid[gridX][gridY])
    grid[gridX][gridY].draw((0,0,0),0,walls)

def main():
    initSquares()
    pygame.display.update()
    currentPosition = (4,4)
    run = True
    pathlen = 0
    count = 0
    addedWalls = False
    ##Create a track basically
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
    #addWall((4,2))
    #addWall((6,2))
    #addWall((8,2))
    #addWall((10,2))
    #addWall((12,2))
    #addWall((4,6))
    #addWall((6,6))
    #addWall((8,6))
    #addWall((10,6))
    #addWall((12,6))

    ##Begin turn
    #addWall((14,7))
    #addWall((14,3))
    #addWall((16,8))
    #addWall((16,4))
    #addWall((18,9))
    #addWall((18,5))
    #addWall((20,10))
    #addWall((20,6))
    #addWall((22,6))
    #addWall((22,10))
    #addWall((24,10))
    #addWall((24,6))
    while run:
        path = Astar(currentPosition)
        pathlen = len(path)-1
        for i in reversed(path):
            clock.tick(1)
            drawCar(currentPosition,i.getPos())
            currentPosition = i.getPos()
            pygame.display.update()


main()
