#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Feb  6 13:04:58 2018

@author: stephenmathew
"""
import numpy as np
import matplotlib.pyplot as plt
import random as rand

normalizedFile = 'normalized.txt'
normalizedFileObject = open(normalizedFile,'r')

outputFile = 'outputGradientDescent.txt'
outputFileObject = open(outputFile,'w')

normalizeParameterFile = 'outputPartTwo.txt'

def main():
    
    alpha = [0.01,0.1,0.3,0.05,0.5]
    iterations = [10,20,30,40,50,60,70,80]
    
    data = setUp()
    x = data[0]
    y = data[1]
    w = data[2]
    m = data[3]
    
    for a in alpha:
        outputFileObject.write('Using alpha = ' + str(a) + '\n')
        lossValues = []
        for i in iterations:
            
            w = resetW(w)
            
            outputFileObject.write('Num iterations: \t' + str(i) + '\n')
            
            w = gradientDescent(w,x,y,m,a,i)
            lossValue = lossFunction(w,x,y,m)
            lossValues.append(lossValue)
            
            outputFileObject.write('Value of Loss function:\t' + str(lossValue) + '\n\n')
        
        plt.plot(iterations,lossValues)
        plt.ylabel('Value of loss function')
        plt.xlabel('Iterations')
        plt.title('Alpha: ' + str(a))
        plt.savefig('plotLossValues'+str(a)+'.png')
        plt.close()
    
    w = resetW(w)
    outputFileObject.write('Using alpha = ' + str(0.3) + 'iterations = ' + str(30) +'\n')
    gradientDescent(w,x,y,m,0.3,30)
    
    testValues = [3150,4]
    normalizeNewObservationResult = normalizeNewObservation(testValues)
    normalizedTestValues = normalizeNewObservationResult[0]
    meanSdY = normalizeNewObservationResult[1]
    estimatedY = predictY(normalizedTestValues,w,meanSdY)
    outputFileObject.write('Predicted normalized price of the house: ' + str(estimatedY) + '\n')
    unscaledY = recoverUnscaledY(estimatedY,meanSdY)
    outputFileObject.write('Predicted unscaled price of the house: ' + str(unscaledY) + '\n\n')
    
    w = resetW(w)
    stochasticGradientDescent(w,x,y,m,0.1)
    
def setUp():
    x0 = np.ones([1,47])
    x1 = np.zeros([1,47])
    x2 = np.zeros([1,47])
    x = [x0,x1,x2]
    
    y = np.zeros([1,47])
    
    w0,w1,w2 = 0,0,0
    w = [w0,w1,w2]
    
    m = getVectors(x,y)
    
    return(x,y,w,m)

def resetW(w):
    w0,w1,w2 = 0,0,0
    w = [w0,w1,w2]
    return(w)
    
def getVectors(x,y):
    m = 0
    x1,x2 = x[1],x[2]
    for line in normalizedFileObject:
        
        data = line.split(',')
        x1[0,m]=data[0]
        x2[0,m]=data[1]
        y[0,m]=data[2]
        m+=1
    return(m)

def derivativeWj(w,x,y,xj,m):
    
    result = np.multiply(w[0],np.vdot(x[0],xj))
    result = np.add(result,np.multiply(w[1],np.vdot(x[1],xj))) 
    result = np.add(result,np.multiply(w[2],np.vdot(x[2],xj)))
    result = np.subtract(result, np.vdot(y,xj))
    result /= (m)
    return(result)
    
def gradientDescent(w,x,y,m,alpha,iterations):
    
    for i in range(iterations):
        for j in range(3):
            wj = w[j]
            xj = x[j]
            w[j] = wj - np.multiply(alpha,(derivativeWj(w,x,y,xj,m)))
    

    return (w)
 
def stochasticGradientDescent(w,x,y,m,alpha):
    order = list(range(0,m))
    x0 = 1
    x1 = x[1]
    x2 = x[2]
    for iteration in range (3):
        for i in order:
            xi1 = np.asscalar(x1[0,i])
            xi2 =np.asscalar(x2[0,i])
            yi = np.asscalar(y[0,i])
            w = gradientDescent(w,[x0,xi1,xi2],[yi],m,alpha,1)
        
        rand.shuffle(order)
        lossValue = lossFunction(w,x,y,m)
        outputFileObject.write('Value of loss function at iteration ' 
                               + str(iteration) + ' = ' + str(lossValue) 
                               +'\n')
    

def lossFunction(w,x,y,m):
    result = np.multiply(w[0],x[0])
    result = np.add(result,np.multiply(w[1],x[1]))
    result = np.add(result,np.multiply(w[2],x[2]))
    result = np.subtract(result,y)
    result = np.vdot(result,result)
    result /= (2*m)
    return result

def model(w,x):
    w0,w1,w2 =  w[0],w[1],w[2]
    x1,x2 = x[1],x[2]
    
    return( w0 + (w1*x1) + (w2*x2))
    
def normalizeNewObservation(values):
    newValues = []
    count = 0
    meanSdY = []
    normalizeParameterFileObject = open(normalizeParameterFile,'r')
    
    for line in normalizeParameterFileObject:
        items = line.split(',')
        mean = float(items[0])
        sd = float(items[1])
        if(count<2):
            newValues.append((values[count] -mean)/sd)
        elif(count == 2):
            meanSdY.append(mean)
            meanSdY.append(sd)
        count += 1
    normalizeParameterFileObject.close()
    
    return(newValues,meanSdY)

def predictY(values,w,meanSdY):
    x1 = values[0]
    x2 = values[1]
    w0,w1,w2 = w[0],w[1],w[2]
    y = (w0 + w1*x1 + w2*x2)
    y = recoverUnscaledY(y,meanSdY)
    return(w0 + w1*x1 + w2*x2)

def recoverUnscaledY(y,meanSdY):
    mean = meanSdY[0]
    sd = meanSdY[1]
    return((y*sd)+mean)
    
main()


outputFileObject.close()    
normalizedFileObject.close()