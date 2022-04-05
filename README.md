# Gale-Shapley-Algorithm
This repository includes the Gale-Shapley Algorithm that aids in finding a stable marriage for an equal number of men and women.

### Program Description:
This program implements Gale-Shapley, which means that we want to develop a stable marriage between all males and females. 
We have equal numbers of men and women, thus we need to marry them to each other based on their preference list. 
A man marries a woman he prefers if she is single, however, if she is not single then we compare the man to her husband 
and check which man does this woman prefer more. If she prefers her husband then they remain married and the other man will 
remain single until he is dequeued again. If she prefers the man over her husband then she will divorce her husband and marry 
the other man. Her ex-husband will now be single and enqueued into the single array until he is dequeued later.
The program terminates once all men and women are married to exactly one person and a stable marriage is formed. 
No married man or women should like someone else that also likes them back.
