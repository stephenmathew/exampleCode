#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Feb  5 18:42:04 2018

@author: stephenmathew
"""
dataFile = 'housing.txt'
dataFileObject = open(dataFile,'r')

normalizedFile = 'normalized.txt'
normalizedFileObject = open(normalizedFile, 'w')

outputFile = 'outputPartTwo.txt'
outputFileObject = open(outputFile,'w')

def main():
    x1,x2,y = getValues()
    
    meanAndSDx1 = meanAndStandardDeviation(x1)
    meanAndSDx2 = meanAndStandardDeviation(x2)
    meanAndSDy = meanAndStandardDeviation(y)
    
    meanX1 = meanAndSDx1[0]
    standardDeviationX1 = meanAndSDx1[1]
    
    meanX2 = meanAndSDx2[0]
    standardDeviationX2 = meanAndSDx2[1]
    
    meanY = meanAndSDy[0]
    standardDeviationY = meanAndSDy[1]
    
    normalizedX1 = normalize(x1,meanX1,standardDeviationX1)
    normalizedX2 = normalize(x2,meanX2,standardDeviationX2)
    normalizedY = normalize(y,meanY,standardDeviationY)
    
    for i in range(len(normalizedX1)):
        normalizedFileObject.write(str(normalizedX1[i]) + ',')
        normalizedFileObject.write(str(normalizedX2[i]) + ',')
        normalizedFileObject.write(str(normalizedY[i]) + '\n')
    
    outputFileObject.write(str(meanX1) + 
                               ',' + str(standardDeviationX1) + '\n')
    outputFileObject.write(str(meanX2) + 
                               ',' + str(standardDeviationX2) + '\n')
    outputFileObject.write(str(meanY) + 
                               ',' + str(standardDeviationY) + '\n')
    
def meanAndStandardDeviation(values):
    total = 0
    N = len(values)
    for i in values:
        total += i
    mean = (total/N)
    
    sigma = 0
    for i in values:
        sigma += (i - mean)**2
    
    var = sigma/N
    StandardDeviation = var**(1/2)
    
    return(mean,StandardDeviation)

def getValues():
    x1,x2,y = [],[],[]
    for line in dataFileObject:
        data = line.split(',')
        x1.append(int(data[0]))
        x2.append(int(data[1]))
        y.append(int(data[2]))
    return(x1,x2,y)
    
    
def normalize(values,mean,standardDeviation):
    newValues = []
    for value in values:
        newValues.append(((value - mean)/standardDeviation))
    return (newValues)

        
main()
dataFileObject.close()
normalizedFileObject.close()     
outputFileObject.close()   
    
    
