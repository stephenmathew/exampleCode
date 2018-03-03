#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Jan 28 16:51:43 2018

@author: stephenmathew
"""

import pickle
import numpy as np
import matplotlib.pyplot as plt


vocabFile = '/Users/stephenmathew/Desktop/Machine Learning/vocab.pickle'
trainFile = '/Users/stephenmathew/Desktop/Machine Learning/train.txt'
validateFile = '/Users/stephenmathew/Desktop/Machine Learning/validate.txt'
sourceFileCombined = '/Users/stephenmathew/Desktop/Machine Learning/percept_data/spam_train.txt'
testFile = '/Users/stephenmathew/Desktop/Machine Learning/percept_data/spam_test.txt'
outputFile = '/Users/stephenmathew/Desktop/output.txt'
outputFileObject = open(outputFile,'w')

partOne = '/Users/stephenmathew/Desktop/Machine Learning/assignmentOne/partOne.py'
from partOne import vocabGenerator

def main():
    
    outputFileObject.write('Part Three: \n')
    test()
    
    outputFileObject.write('Parts Five and Six:\n')
    N = [100,500,2000,4000]
    validateErrors = []
    iterations = []
    for n in N:
        points = testN(n)
        validateErrors.append(points[1])
        iterations.append(points[0])
    
    plt.plot(N,validateErrors)
    plt.ylabel('Validate Errors')
    plt.savefig('/Users/stephenmathew/Desktop/plotValidateError.png')
    plt.close()
    
    plt.plot(N,iterations)
    plt.ylabel('Perceptron Iterations')
    plt.savefig('/Users/stephenmathew/Desktop/plotIterations.png')
    plt.close()
    
    outputFileObject.write('Part Seven:\n')
    for X in [20,25,30]:
        outputFileObject.write('Testing using X = ' + str(X) + ':\n')
        
        for maxIterations in [8,10,12,14]:
            testMaxIterations(4000,maxIterations,X)
    
    outputFileObject.write('Part Eight:\n')
    testCombined(30,13)
    
    outputFileObject.write('\nPart Nine: \n')
    testCombined(1200,500)

#vocab list was created in file 'partOne.py as a numPy array
#unpickling it here 
#N = number emails to use for vocab generation
#X = Number emails required for a word to be included in vocabulary
def getVocabList(N,X):

    vocabCreator = vocabGenerator()
    
    vocabCreator.createVocab(N)
    vocabCreator.refineVocab(X)
    with open(vocabFile, 'rb') as handle:
        vocabDict = pickle.load(handle)
        
    return vocabDict
    


#email parameter is a python list of words in the email 
def featureVector(email,vocabDict,lengthOfFeatureVector):
    
    #new feature vector numpy array with length of vocabDict
    #default = 0 
    emailFeatureVector = np.zeros([1,lengthOfFeatureVector])
    
    #iterating words in the email 
    for x in range(1,len(email)):
        
        word = email[x]
        
        #if the word is the jth term in the vocab list
        #the jth term of the email feature vector = 1
        if(vocabDict.get(word,-1) >= 0 ):
            
            emailFeatureVector[0,vocabDict[word]] = 1
    
    return emailFeatureVector

#returns tuple of Y values and a matrix of feature Vectors
def getFeatureVectors(N,vocabDict,lengthOfFeatureVector,inputFile):
    
    inputFileObject = open(inputFile,'r')
    Y = np.ones([N,1])
    featureVectorMatrix = np.zeros([N,lengthOfFeatureVector])
    count = 0
    
    
    for email in inputFileObject:
        
        #Only train using first N emails
        if(count < N):
            words = email.split()
            if(words[0] == '0'): 
                Y[count,0] = -1
                
            featureVectorMatrix[count,] = featureVector(words,vocabDict,lengthOfFeatureVector)
        
            count += 1
            
        else: break
    
    inputFileObject.close()
    
    return(Y,featureVectorMatrix)
    

def perceptron_train(data,lengthOfFeatureVector):
    
    w = np.zeros([1,lengthOfFeatureVector])
    wCopy = np.ones([1,lengthOfFeatureVector])
    
    trainY = data[0]
    trainFeatureVectors = data[1]
    
    iterCount = 1
    k = 0
    while(np.array_equal(w,wCopy) == False):
        
        wCopy = np.copy(w)
        
        for i in range(np.shape(trainY)[0]):
            
            yi = trainY[i,0]
           
            xi = trainFeatureVectors[i,]
            
            result = (yi * np.vdot(xi,w))
            
            if(np.sign(result) > 0):
                w = w
                
            else:
                k += 1
                w = w + np.multiply(yi,xi)
        
        iterCount += 1
    
    return(w,k,iterCount)

#Modified perceptron algortihim with an argument to limit number of iterations
def perceptron_train_MaxIter(data,lengthOfFeatureVector,maxIterations):
    
    w = np.zeros([1,lengthOfFeatureVector])
    wCopy = np.ones([1,lengthOfFeatureVector])
    
    trainY = data[0]
    trainFeatureVectors = data[1]
    
    iterCount = 1
    k = 0
    while(np.array_equal(w,wCopy) == False):
        
        wCopy = np.copy(w)
        
        for i in range(np.shape(trainY)[0]):
            
            yi = trainY[i,0]
           
            xi = trainFeatureVectors[i,]
            
            result = (yi * np.vdot(xi,w))
            
            if(np.sign(result) > 0):
                w = w
                
            else:
                k += 1
                w = w + np.multiply(yi,xi)
        
        iterCount += 1
        if(iterCount >= maxIterations):
            break 
    
    return(w,k,iterCount)

def perceptron_error(w,data):
    
    countMistakes = 0
    y = data[0]
    featureVectors = data[1]
    
    numEmails = np.shape(featureVectors)[0]
    
    for i in range(numEmails):
        
        xi = featureVectors[i,]
        yi = y[i,0]
        
        if(yi * np.vdot(xi,w) < 0):
            countMistakes += 1
    
    return((countMistakes/numEmails)*100)

#prints most positive and most negative weighted words to an output file
def positiveNegativeWeights(w,vocabDict,lengthOfFeatureVector):
    
    #copy w so we don't modify it in place
    copy = np.copy(w)
    
    ascending = np.sort(copy)
    bottomEight = []
    
    descending = np.fliplr(ascending)
    topEight = []
    
    negativeSuspects = []
    positiveSuspects = []
    
    invertedVocabDict = dict((y,x) for x,y in vocabDict.items())
    
    for i in range(8):
        bottomEight.append(ascending[0,i])
        topEight.append(descending[0,i])
        
    for i in range(lengthOfFeatureVector):
        if(np.isin(copy[0,i],bottomEight)):
           negativeSuspects.append(i)
            
        elif(np.isin(copy[0,i],topEight)):
            positiveSuspects.append(i)
    
    outputFileObject.write('Negative weighted (non spam) words: \n')
    for i in range(len(negativeSuspects)):
        index = negativeSuspects[i]
        weight = w[0,index]
        word = invertedVocabDict[index]
        outputFileObject.write('word: ' + str(word) + '\t weight: ' + str(weight) + '\n')
    
    outputFileObject.write('\n \nPositive weighted (spam) words: \n')
    for i in range(len(positiveSuspects)):
        index = positiveSuspects[i]
        weight = w[0,index]
        word = invertedVocabDict[index]
        outputFileObject.write('word: ' + str(word) + '\t weight: ' + str(weight) + '\n')
    
    outputFileObject.write('\n')
    
def test():
        N = 4000
        outputFileObject.write('N = ' + str(N) + '\n')
        vocabDict = getVocabList(N,25)
        lengthOfFeatureVector = len(vocabDict.keys())
        outputFileObject.write('Length of dictionary: ' + str(lengthOfFeatureVector) + '\n')
        
        trainVectors = getFeatureVectors(N,vocabDict,lengthOfFeatureVector,trainFile)
        trainData = (trainVectors[0],trainVectors[1])
        validateVectors = getFeatureVectors(1000,vocabDict,lengthOfFeatureVector,validateFile)
        validateData = (validateVectors[0],validateVectors[1])
        
        result = perceptron_train(trainData,lengthOfFeatureVector)
        
        outputFileObject.write('Corrections made: \t' + str(result[1]) + '\n')
        outputFileObject.write('Perceptron iterations: \t' + str(result[2]) + '\n')
        outputFileObject.write('Train Error Rate: \t' + str(perceptron_error(result[0],trainData)) + '\n')
        outputFileObject.write('Validate Error Rate: \t' + str(perceptron_error(result[0],validateData)) + '\n\n')
        outputFileObject.write('Part four:\n')
        positiveNegativeWeights(result[0],vocabDict,lengthOfFeatureVector)
        
def testN(N):
        
        outputFileObject.write('N = ' + str(N) + '\n')
        vocabDict = getVocabList(N,25)
        lengthOfFeatureVector = len(vocabDict.keys())
        outputFileObject.write('Length of dictionary: ' + str(lengthOfFeatureVector) + '\n')
        
        trainVectors = getFeatureVectors(N,vocabDict,lengthOfFeatureVector,trainFile)
        trainData = (trainVectors[0],trainVectors[1])
        validateVectors = getFeatureVectors(1000,vocabDict,lengthOfFeatureVector,validateFile)
        validateData = (validateVectors[0],validateVectors[1])
        
        result = perceptron_train(trainData,lengthOfFeatureVector)
        
        outputFileObject.write('Corrections made: \t' + str(result[1]) + '\n')
        outputFileObject.write('Perceptron iterations: \t' + str(result[2]) + '\n')
        outputFileObject.write('Train Error Rate: \t' + str(perceptron_error(result[0],trainData)) + '\n')
        validateError = perceptron_error(result[0],validateData)
        outputFileObject.write('Validate Error Rate: \t' + str(validateError) + '\n\n')
        
        return(result[2],validateError)
        
#for testing with multiple values of N, maxIterations or X 
def testMaxIterations(N,maxIterations,X):
    
    vocabDict = getVocabList(N,X)
    lengthOfFeatureVector = len(vocabDict.keys())
    
    vectors = getFeatureVectors(N,vocabDict,lengthOfFeatureVector,trainFile)
    trainData = (vectors[0],vectors[1])
    validateVectors = getFeatureVectors(1000,vocabDict,lengthOfFeatureVector,validateFile)
    validateData = (validateVectors[0],validateVectors[1])
    
    result = perceptron_train_MaxIter(trainData,lengthOfFeatureVector, maxIterations)
    outputFileObject.write('Max Iterations = ' + str(maxIterations) +'\n')
    outputFileObject.write('Length of dictionary: ' + str(lengthOfFeatureVector) + '\n')
    outputFileObject.write('Perceptron iterations: \t' + str(result[2]) + '\n')
    outputFileObject.write('Validate Error Rate: \t' + str(perceptron_error(result[0],validateData)) + '\n\n')

#testing perceptron implementation on the combined training data and spam_test.txt
def testCombined(X,maxIter):
    
    vocabCreator = vocabGenerator()
    
    vocabCreator.createCombinedVocab()
    vocabCreator.refineVocab(X)
    with open(vocabFile, 'rb') as handle:
        vocabDict = pickle.load(handle)
    
    lengthOfFeatureVector = len(vocabDict.keys())
    trainVectors = getFeatureVectors(5000,vocabDict,lengthOfFeatureVector,sourceFileCombined)
    trainData = (trainVectors[0],trainVectors[1])
    
    testFileObject = open(testFile,'r')
    lengthOfTestFile = sum(1 for line in testFileObject)
    testFileObject.close()
    testVectors = getFeatureVectors(lengthOfTestFile,vocabDict,lengthOfFeatureVector,testFile)
    testData = (testVectors[0],testVectors[1])
    
    outputFileObject.write('Testing on Test Data: \n')
    outputFileObject.write('Using X = ' + str(X) + '\n')
    outputFileObject.write('Number of words in dictionary = ' + str(lengthOfFeatureVector) + '\n')
    result = perceptron_train_MaxIter(trainData,lengthOfFeatureVector,maxIter)
    outputFileObject.write('Max iterations: \t' + str(maxIter) + '\n')
    outputFileObject.write('Perceptron Iterations = ' + str(result[2]) + '\n')
    outputFileObject.write('Test error rate = ' + str(perceptron_error(result[0],testData)) + '\n')
    
    
    
main()        
outputFileObject.close()        
