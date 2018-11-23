# COMP251_A5
_**McGill University**_

Comp 251: Algorithms & Datastructures

Gabriel Negash

## Balloons
Questions for TA:
```
1. Can each arrow be fired from a different height, or do they all need to be fired from the same height?
    A: Yes
2. Are balloons in this context point particles? e.g. only one specific grid coordinate, or are they volumetric?
    A: Yes
3. When it says 
"Each line contains mi integers hj separated by a space, representing respective height of the jth balloon from left to right"
 Does it mean baloon 1 is closer to the shooter than balloon 2? e.g. if B1 and B2 both have H=3;
    A:Left to right 

Ex: Balloon Heights: 2 1 2 3
Where Heights are y axis, and distances are x axis
4
3      x
2 x   x  
1   x
  1 2 3 4       


```
Summary:
```
Per problem
M balloons 
Each balloon has its own height
Each balloon is in it's own vertical column
```

## Mancala Leapfrog

Summary:
```
Only single line
can be Empty Full Full, or backwards: full full empty
    I.e. move A->C or C->A, both cases delete b.
```
### ***ISSUE***
Doesn't Seem possible with the given sample solution. For problem #6 it claims to  have 2 remaining but it must be 3

## Island Discovery

Summary: 
```
Not diagonal, unless they have something in common on lat/vert axis.
```