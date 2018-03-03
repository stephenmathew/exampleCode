#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Jan 25 12:21:11 2018

@author: stephenmathew
"""
#import numpy as np
import pickle 

sourceFile = '/Users/stephenmathew/Desktop/Machine Learning/train.txt'
vocabFile = '/Users/stephenmathew/Desktop/Machine Learning/vocab.pickle'
sourceFileCombined = '/Users/stephenmathew/Desktop/Machine Learning/percept_data/spam_train.txt'

class vocabGenerator():
    
    
        
    uniqueWords = dict()
    refinedVocab = dict()
    
    #N = number of emails to read
    def createVocab(self, N):
        
        count = 0
        file_object = open(sourceFile, 'r')
        self.uniqueWords= dict()
        
        for line in file_object:
            
            if(count < N):
                count += 1 
                email_vocab = dict()
                
                email_words = line.split()
                for x in range(len(email_words)):
                    if(email_vocab.get(email_words[x], 0) > 0):
                        email_vocab[email_words[x]] += 1
                    else:
                        email_vocab[email_words[x]] = 1
                 
                for key in email_vocab.keys():
                    if(self.uniqueWords.get(key , 0) > 0):
                        self.uniqueWords[key] += 1
                    else:
                        self.uniqueWords[key] = 1
             
        file_object.close()      
    
    def refineVocab(self,X):
        self.refinedVocab = dict()
        
        for key in self.uniqueWords.keys():
            if (self.uniqueWords[key] >= X):
                self.refinedVocab[key] = self.uniqueWords.get(key)
    
        count = 0
        
        for key in self.refinedVocab.keys():
            self.refinedVocab[key] = count
            count +=1
    
        with open(vocabFile , 'wb' ) as handle:
            pickle.dump(self.refinedVocab, handle, protocol=pickle.HIGHEST_PROTOCOL )
            
        
    def createCombinedVocab(self):
         
         fileObject = open(sourceFileCombined,'r')
         
         for line in fileObject:
            
            email_vocab = dict()
            
            email_words = line.split()
            for x in range(len(email_words)):
                if(email_vocab.get(email_words[x], 0) > 0):
                    email_vocab[email_words[x]] += 1
                else:
                    email_vocab[email_words[x]] = 1
             
            for key in email_vocab.keys():
                if(self.uniqueWords.get(key , 0) > 0):
                    self.uniqueWords[key] += 1
                else:
                    self.uniqueWords[key] = 1
             
         fileObject.close()      
        
        

