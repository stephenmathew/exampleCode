Problem One:
The answers for this question are included in the file 'output.txt'
For the cost function I took sigma of all of the data points in the housing.txt file and computed using sympy 
Python code for this part is found in partOne.py

Problem Two:

A: Feature Normalization 
Mean : is given by sigma(xi)/n = xbar
SD is given by sqrt(1/n(sigma(xi-xbar)^2))

Python code to normalize features is found in partTwo.py, the resulting features are found in normalized.txt
When normalizing the features I also inadvertently normalized the Y values. When predicting a new Y value the result has to be appropriately rescaled to account for this in order to interpret the price. This is handled by the function 'recoverUnscaledY' in gradientDescent.py

B: Gradient Descent
The formula for the loss function is given by:
J(w) = (1/2m) Σi [ y(i) – fw(x(i)) ]^2 
(Copied from lecture slides)

The code for the reminder of the exercises is found in gradientDescent.py
output is found in outputGradientDescent.txt
Plots for the J(w) of given alpha are included as .png files as well

