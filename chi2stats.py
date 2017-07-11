# -*- coding: utf-8 -*-
"""
Created on Sat May 20 16:28:12 2017

@author: MoldovanM2
"""

"""Chi-Squared Stats Library.

Functions for handling chi-squared statistics:

chi2(actual,expected)      compute the chi-squared metric for two 
                           distributions.
chi2P(value,degF)          probability that this observed 
                           chi-squared metric value indicates a 
                           difference between actual and 
                           expected
chi2Val(degF,prob)         the chi-squared metric value for the 
                           given degrees of freedom and 
                           probability

Function for computing correlation coefficient:

correlate(u,v)             compute the correlation coefficient
                           for two sample sequences
                           
Special functions:

fact(n) -> number          factorial: permutations of of n things
bico(n,r) -> number        binomial coefficient: combinations n things taken r at a time
Stirling(n,k) -> number    Stirling number: k-sized subsets of n elements
Bernoulli(p) -> number     Bernoulli number p
gammln(x) -> number        log of gamma function for x
gammp(a,x) -> number       incomplete gamma function P(a,x)

Root-finding functions:

bracket(f,g) -> (low,high) bracket a zero of the function near g
bisect(f,r,EPS) -> number  find a zero of a function, f, in the range, r, with tolerance of EPS
"""

import math
# import functools

def chi2( act, exp ):
    """Return chi-squared metric between actual and expected observations."""
    v= 0
    for a,e in zip(act,exp):
        if e > 0:
            v += (a-e)**2/e
    return v

# def chi2P( chi2V, degF ):
#    """Return P(value|degF) = P(degF/2,value/2)."""
#    return gammp( degF/2.0, chi2V/2.0 )
#
#
# def chi2Val( degF, prob ):
#    """Return the chi-squared value that corresponds with given degrees of freedom and probability."""
#    theFunction= lambda x,d=degF,p=prob: chi2P(x,d)-p
#    return bisect( theFunction, bracket( theFunction, 1 ) )
#
# from math import sqrt
# def correlate( u, v ):
#     """Return correlation coefficient between sequences u and v."""
#     """→sum of a sequence definition (84)
#     →mul of two sequences definition (85) """
#     n = float(len(u))
#     top = n * sum( mul(u,v) ) - sum(u)*sum(v)
#     bottom = sqrt( ( n*sum( mul(u,u) ) - sum(u)**2 )*( n*sum( mul(v,v) ) - sum(v)**2 ) )
#     return top/bottom
#
# def sum( v ):
#     return float(functools.reduce( lambda a,b:a+b, v, int(0) ))

def mul( u, v ):
    return [ u[i]*v[i] for i in range(len(u)) ]

cache_fact = { 0:int(1) }
def fact( nn ):
    """Return the number of permutations of nn things."""
    n= int(nn)
    if n < 0: raise Exception("invalid argument %s to fact()"%nn)
    if n in cache_fact: return cache_fact[n]
    return cache_fact.setdefault(n,n*fact(n-1))

def bico( n, r ):
    """Return the number of combinations of n things taken r at a time."""
    if n < 0 or r < 0: raise Exception("invalid arguments %s, %s to bico()"%(n,r))
    return fact(n)/(fact(r)*fact(n-r))

cache_stirl = { }

def Stirling(n,k):
    """Return the Stirling number of the second kind
    for a set with n elements partitioned into k 
    disjoint non-empty sets."""

    def stirlf(n,k):
        """Actual Stirling computation, not using cache"""
        if k == n: return int(1)
        if k == 1: return int(1)
        return int(Stirling(n-1,k-1) + k*Stirling(n-1,k))

    if (n,k) in  cache_stirl: return cache_stirl[ (n,k) ]
    return cache_stirl.setdefault( (n,k), stirlf(n,k) )

# cache_bern = { 0:1.0 }
# def Bernoulli(p):
#     """Return the Bernoulli number p."""
#
#     def bernf(p):
#         """Actual Bernoulli computation, not using cache"""
#         sum= 0.0
#         for k in range(p):
#          sum += bico(p+1,k)*Bernoulli(k)
#         return -(1.0/(p+1))*sum
#
#     if cache_bern.has_key( p ): return cache_bern[p]
#     return cache_bern.setdefault( p, bernf(p) )
#
# def gammln1( xx ):
#    """Return the log of the gamma function at xx>0."""
#    if xx < 7.0:
#       # compute the product for the equivalence
#       f= xx
#       while xx < 7.0:
#         xx = xx + 1.0
#         f = f * xx
#       return lgm1( xx+1.0 ) - math.log(f)
#    else:
#       return lgm1( xx )
#
# def lgm1( x ):
#    """Return Stirling approximation of log of gamma at x>7.0."""
#    A=   0.918938533204673  # log(sqrt(2*pi))
#    c0= -0.000595238095238
#    c1=  0.000793650793651
#    c2= -0.002777777777778
#    c3=  0.083333333333333
#    z= 1.0/x**2
#    return (x-0.5)*math.log(x) - x + A + (((c0*z+c1)*z+c2)*z+c3)/x
#
# def gammln2( xx ):
#    """Return the log of the gamma function at xx>0."""
#    if xx < 7.0:
#       f= xx
#       while xx < 7.0:
#         xx = xx + 1.0
#         f = f * xx
#       return lgm2(xx+1.0) - math.log(f)
#    else:
#       return lgm2(xx)
#
#
# def lgm2( w ):
#    """Return Stirling approximation of log of gamma at x>7.0."""
#    A=   0.918938533204673  # log(sqrt(2*pi))
#    # c[i] = Bernoulli(2*i)/(2*i*(2*i-1))
#    c = [ 0, 1./12., -1./360.,
#       1./1260.,   -1./1680.,
#       1./1188., -691./360360.,
#       1./156., -3617./122400.,
#       43867./244188., -174611./125400.,
#       77683./5796., -236364091./1506960.,
#       657931./300., -3392780147./93960. ]
#    den= float(w)
#    w2 = float(w*w)
#    presum= (w-0.5)*math.log(w)-w+A
#    for ci in c[1:]:
#       sum = presum + ci/den
#       if sum == presum: break
#       den = den * w2
#       presum = sum
#    return sum
#
# def gammln(x,method=gammln1):
#    """Return the log of the gamma function at xx>0."""
#    return method(x)
#
# def gammp( a, x ):
#    """Return the incomplete gamma function, P, at a from 0 to x."""
#    # Choose the series or continued fraction approximations.
#    if x < 0 or a <= 0:
#       raise Exception("invalid arguments %s,%s to gammp()"%a,x)
#    if x == 0: return 0.0
#    if x < a+1.0:
#       gamser, gln = gser2( a, x )
#       return gamser
#    else:
#       gammcf, gln = gcf2( a, x )
#       return 1.0 - gammcf
#
#
# def gser2( a, x ):
#    """Return the incomplete gamma function at a from 0 to x where x<a+1."""
#    # series approximation for partial gamma when x < a+1.0
#    gln= gammln(a)
#    r= math.exp( a*math.log(x) - x - gln )
#    a1 = a + 1.0
#    t= x / a1
#    sum= t
#    while t > 1.0E-15:
#       a1 = a1 + 1.0
#       t = t * (x/a1)
#       sum = sum + t
#    ans = (r/a)*(1.0+sum)
#    return ( ans, gln )
#
# def gcf2( a, x ):
#    """Return the incomplete gamma function at a from x to infinity where x>=a+1."""
#    # continued fraction approximation for partial gamma when x >= a+1.0
#    gln= gammln2(a)
#    r= math.exp( a*math.log(x) - x - gln )
#    a2nm1 = 1.0
#    a2n = 1.0
#    b2nm1 = x
#    b2n = x + (1.0 - a)
#    c = 1.0
#    for i in range(100):
#       a2nm1 = x*a2n + c*a2nm1
#       b2nm1 = x*b2n + c*b2nm1
#       am0 = a2nm1/b2nm1
#       c = c + 1.0
#       cma = c - a
#       a2n = a2nm1 + cma*a2n
#       b2n = b2nm1 + cma*b2n
#       an0 = a2n/b2n
#       if abs(an0-am0) < 1.0E-015*an0: break
#    ans= r*an0
#    return ( ans, gln )
#
# def bracket( function, guess ):
#    """Return a range which brackets a zero of function around the initial guess."""
#    # locate a point where f(c1) < 0
#    tries= 144
#    c1= guess
#    while function(c1) > 0 and tries > 0:
#       c1=c1/2.0
#       tries -= 1
#    if tries == 0: return None
#    # locate a point where f(c2) > 0
#    tries= 144
#    c2= guess
#    while function(c2) < 0 and tries > 0:
#       c2=c2*2.0
#       tries -= 1
#    if tries == 0: return None
#    return ( c1, c2 )
#
# def bisect( func, range_, EPS=0.000001 ):
#    """Bisect the range (a tuple of low and high) to find a zero of func
#    until the range is narrower then some small number, EPS."""
#    tries= 144
#    c1, c2 = range_
#    if c1 == c2: raise Exception("invalid range (%d,%d) in bisect()"%range_)
#    while abs( (c2-c1)/c2 ) > EPS and tries > 0:
#       mid= (c1+c2)/2
#       f_atmid= func(mid)
#       if f_atmid < 0:
#          c1= mid
#       elif f_atmid > 0:
#          c2= mid
#       else:
#          return mid
#       tries -= 1
#    return c1
#
# def _test1():
#    print ("test gammln() function")
#    for n in range(10):
#       f= fact(n)
#       f1= math.exp( gammln1(n+1) )
#       f2= math.exp( gammln2(n+1) )
#       print ("%2d %10d %10.0f %10.0f" % (n, f, f1, f2))
#    for n in range(1,21):
#       x= n/2.0
#       print ("%4.1f %10f %10f" % ( x, gammln1(x), gammln2(x) ))
#
#
# def _test2():
#    print ("test gammp() function")
#    for a in [ 0.5, 1.0, 3.0, 10.0 ]:
#       print ("a=",a)
#       for x in [ .25, .5, 1, 2, 4, 6, 8, 10, 12, 14 ]:
#          print (x,gammp(a,x))
#
#
# def _test3():
#    print ("test chi2P() function")
#    print ("%.2f %.2f" % (chi2P( 0.114831620490197,3),0.01))
#    print ("%.2f %.2f" % (chi2P( 0.351845959260105,3),0.05))
#    print ("%.2f %.2f" % (chi2P( 0.584375459244772,3),0.10))
#    print ("%.2f %.2f" % (chi2P( 2.3659727453222,  3),0.50))
#    print ("%.2f %.2f" % (chi2P( 6.2513944516967,  3),0.90))
#    print ("%.2f %.2f" % (chi2P( 7.8147247029009,  3),0.95))
#    print ("%.2f %.2f" % (chi2P(11.344882119471,   3),0.99))
#
def _test4():
   print ("test chi2() function")
   probSet= [ 0.01, 0.05, 0.1, 0.5, 0.9, 0.95, 0.99 ]
   print ("DegF"," ".join( map(lambda x:'%-9g'%x,probSet) ))
   for f in range(1,11):
      print ("%4d" % f)
      for p in probSet:
         v= "%.4g" % chi2(f, p)
         print ("%-9s" % v)
      print()


# def _test5():
#    actual= [ 1.0, -1/2.0, 1/6.0, 0, -1/30.0, 0, 1/42.0, 0, -1/30.0, 0, 5/66.0,
#          0, -691/2730., 0, 7/6.0, 0, -3617/510.0, 0, 43867/798.0, 0, -174611/330.0]
#    print ("actual Bernoulli numbers, computed Bernoulli numbers")
#    for p in range(len(actual)):
#       print ("%2d %12.7f %12.7f" % (p,Bernoulli(p),actual[p]))
#    print ("Bernoulli numbers used in log-gamma approximation")
#    for i in range(1,21):
#       print ("%2d %2d %24.8f" % (i,2*i,Bernoulli(2*i)/(2*i*(2*i-1))))
#
if __name__ == "__main__":
    _test4()
   # _test3()
   # _test4()
   # _test5()
   
