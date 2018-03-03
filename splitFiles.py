# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""


def splitEmails(fileName):
    
    count = 0
    file_object = open(fileName,'r')
    #print (file_object.read())
    trainFile = open("/Users/stephenmathew/Desktop/train.txt",'w')
    validateFile = open("/Users/stephenmathew/Desktop/validate.txt",'w')
    
    #trainFile.write('hallo')
    #validateFile.write('hallo')
    for line in file_object:
        
        
        if(count < 4000):
            if(line.startswith('1') or line.startswith('0')):
                count += 1
                
            trainFile.write(line)
            
        else: validateFile.write(line)
        
                
    
    
    trainFile.close
    validateFile.close
    file_object.close
    
    print(count)
    
splitEmails("/Users/stephenmathew/Desktop/Machine Learning/percept_data/spam_train.txt")
    
