#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Feb  5 13:52:32 2018

@author: stephenmathew
"""

import sympy as sp


dataFile = 'housing.txt'
dataFileObject = open(dataFile,'r')

outputFile = 'output.txt'
outputFileObject = open(outputFile,'w')

def features():
    m = 0
    x1,x2,y = [],[],[]    
    for line in dataFileObject:
        m +=1
        data = line.split(',')
        x1.append(int(data[0]))
        x2.append(int(data[1]))
        y.append(int(data[2]))
    return(m,x1,x2,y)

def costFunction():
    
    w0 = sp.symbols('w_0')
    w1 = sp.Symbol('w_1')
    w2 = sp.Symbol('w_2')
    
    featuresResult = features()
    m = featuresResult[0]
    x1,x2,y = featuresResult[1],featuresResult[2],featuresResult[3]
    
    total = 0
    for i in range(m):
        total += (y[i] - w0 - w1*x1[i] - w2*x2[i])
    
    function = ((1/(2*m)) * (total)**2)
    outputFileObject.write('Cost Function: ' + str(function) + '\n\n')
   
    derivW0 = (sp.diff(function,w0))
    derivW1 = sp.diff(function,w1)
    derivW2 = (sp.diff(function,w2))
    
    return(derivW0,derivW1,derivW2,function)

def firstGradientIteration():
    
    costFunctionResult = costFunction()
    alpha = 0.15
    
    for j in range(3):
        jDerivative  = costFunctionResult[j]
        outputFileObject.write('Derivative with respect to wj: ' + str(jDerivative) + '\n')
        jCoefficients = sp.Poly.coeffs(sp.poly(jDerivative))
        wj = (0 - alpha* (jCoefficients[3]))
        outputFileObject.write('w' + str(j) + ' after first iteration:\t' + str(wj) + '\n\n')
 
firstGradientIteration()

dataFileObject.close()
outputFileObject.close()
