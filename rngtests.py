# -*- coding: utf-8 -*-
"""
Created on Sat May 20 12:00:27 2017

@author: MoldovanM2
"""


from math import sqrt

import sys

import chi2stats


class EmpiricalTest:
    """A single empirical test of a random number generator.
    
    EmpiricalTest( aGenerator, samples ) -- creates the test object
    run() -- generate samples, measure, generate expected, compute result
    report() -- print results on stdout
    final() -- tuple of (testname, result, low, high)
    validate() -- 1 if test produces expected results
    """
    def __init__( self,  sample=[],domain=6,range=(0,0) ):
        self.actual= []; """the actual frequencies"""
        self.expected= []; """the expected frequencies"""
        self.range= range; """the range of chi-squared values"""
        self.result= None;  """the final metric result"""
        self.titles= ( "", "", "" ); """titles for the metric, low and high"""
        self.testName= "";  """the name of this test"""
        self.nSamples= len(sample); """number of samples to generate"""
        self.domain= domain; """size of domain for the samples"""
        self.sample=sample; """the sample list"""
        


    def run( self ):
        """Run the test."""
        self.actual= self.testFreq( self.sample, self.domain )
        self.expected= self.expectedFreq( self.nSamples, self.domain )
        print self.actual
        print self.expected
        self.result= chi2stats.chi2( self.actual, self.expected )
    
    def testFreq( self, sample, domain ):
        """Return the actual frequency table for a set of samples over a given domain."""
        return []
    
    def expectedFreq( self, nSamples, domain ):
        """Return the expected frequency table for a number of samples over a given domain."""
        return []
    
    def report( self ):
        """Print the details of the test."""
        print (self.testName)
        print ("%s\t%s\t%s" % self.titles)
        for l in range(len(self.expected)):
            a,e= self.actual[l], self.expected[l]
            print ("%d\t%8.1f\t%8.1f" % (l,a,e))
    
    def final( self ):
        """Return a tuple of (name,metric,low,high)"""
        if self.result == None:
            self.run()
        return ( self.testName, self.result ) + self.range
    
    # def validate( self ):
    #     """Validate the test algorithm against known results"""
    #     return 0
    
class FreqTest( EmpiricalTest ):
    """Test frequency distribution."""
    #→frequency test constructor (14) 
    #→frequency test procedure (15) 
    #→frequency test expected values (16) 
    #→frequency test validation (17) 
    def __init__( self, sample,domain,range ):
        EmpiricalTest.__init__( self, sample )
        self.testName= "Frequency Test"
        self.range= range # values taken from Table 1 chi-square v=6
        self.titles= ( "value","actual","expected" )
        self.domain= domain
        
    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        freqTable = d*[0]
        for i in list:
            freqTable[i-1] += 1
        return freqTable
    
    def expectedFreq( self, nSamples, domain ):
        """Return the expected frequency table for a number of samples over a given domain."""
        return domain*[ nSamples/float(domain) ]
    
    # def validate( self ):
    #     """Validate the test algorithm against known results"""
    #     samples = [ i for i in range(self.domain) ] * 10
    #     act= self.testFreq( samples, self.domain )
    #     exp= self.expectedFreq( len(samples), self.domain )
    #
    #
    #     return act == exp

class GapTest( EmpiricalTest ):
    """Test distribution of gap lengths between values that fall in a given range."""
    """→gap test constructor (24)
    →gap test procedure (25)
    →gap test expected values (26)
    →gap test validation (27)"""
    def __init__(self, sample, domain, range):
        EmpiricalTest.__init__( self, sample,domain )
        self.testName= "Gap Test"
        self.range= range # values taken from Table 1 chi-square v=6
        self.titles= ( "gap size","actual","expected" )
        self.domain= domain
        self.nGaps= int(self.nSamples/10)

    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        j, s, count = -1, 0, d*[0]
        while s != self.nGaps and j != len(list):
            j, r = j+1, 0
            while j != len(list) and list[j] < d/2:
                j, r = j+1, r+1
            count[min(r,d-1)] += 1
            s += 1
        print (s)
        print(self.nGaps)
        if j == len(list):
            raise "exhausted the sequence found %d out of %d gaps" % ( s, self.nGaps )
        return count

    def expectedFreq(self,nSamples,domain):
        """Return the expected frequency table for a number of samples over a given domain."""
        pd= 0.5
        def probGap( pd, r, nGaps=self.nGaps ):
           return nGaps*pd*(1.0-pd)**r
        def probOth( pd, r, nGaps=self.nGaps ):
           return nGaps*(1.0-pd)**r
        return [ probGap(pd,r) for r in range(domain-1) ] + [probOth(pd,domain-1)]

    # def validate( self ):
    #    """Validate the test algorithm against known results"""
    #    samples = [ ]
    #    for i in range(10):
    #      for j in range(i):
    #         samples.append(self.domain/2-1)   # value out of the range
    #      samples.append(self.domain/2) # value in the gap range
    #    samples = samples * 10
    #    t, self.nGaps = self.nGaps, 100
    #    act= self.testFreq( samples, self.domain )
    #    exp= self.expectedFreq( len(samples), self.domain )
    #    self.nGaps= t
    #    return (act == [10, 10, 10, 10, 10, 10, 10, 30]
    #    and exp == [50.0, 25.0, 12.5, 6.25, 3.125, 1.5625, 0.78125, 0.78125])

class PokerTest( EmpiricalTest ):
    """Test distribution of poker hands in sample groups."""
    """→poker test constructor (29)
    →poker test procedure (30)
    →poker test expected values (31)
    →poker test validation (32) """

    def __init__(self, sample, domain, range):
        EmpiricalTest.__init__( self, sample,domain )
        self.testName= "Poker Test"
        self.range= range
        self.titles= ( "distinct","actual","expected" )
        self.domain= domain

    """def reduce(function, iterable, initializer=None):
        it = iter(iterable)
        if initializer is None:
            try:
                initializer = next(it)
            except StopIteration:
                raise TypeError('reduce() of empty sequence with no initial value')
        accum_value = initializer
        for x in it:
            accum_value = function(accum_value, x)
        return accum_value"""

    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        freqTable= (d-1)*[0]
        for l in range(len(list)/d-1):
            a=l*(d-1)
            b=l*(d-1)+(d-1)
            hand=(b-a)*[0]
            for i in range(a,b):
                hand[i-a]= list[i]
            vUnique= [ v in hand for v in range(1,d) ]
            print 'hand: '+str(hand)
            print 'vUnique: '+str(vUnique)
            distinct= reduce( lambda a,b:a+b, vUnique, 0 )
            print 'distinct: '+str(distinct)
            freqTable[distinct-1] += 1
        return freqTable


    def expectedFreq( self, n, d ):
        """Return the expected frequency table for a number of samples over a given domain."""
        k= d-1
        exp= (d-1)*[0]
        for r in range(1,d):
            p= 1.0
            for i in range(r):
                p *= d-i
            exp[r-1]= (n/d-1)*(p/float(d)**k)*chi2stats.Stirling(k,r)
        return exp


    # def validate( self ):
    #    """Validate the test algorithm against known results"""
    #    samples = [ ]
    #    for i in range(5):
    #      for j in range(5):
    #         if i < j: samples.append(0)
    #         else: samples.append(j)
    #    samples = samples * 10
    #    act= self.testFreq( samples, self.domain )
    #    exp= map( int, self.expectedFreq( len(samples), self.domain ) )
    #    return (act == [10, 10, 10, 10, 10]
    #    and exp == [0, 0, 4, 20, 24])

class CouponTest( EmpiricalTest ):
    """Test distribution of lengths of runs that contain all values from the domain."""
    """→coupon test constructor (34)
    →coupon test procedure (35)
    →coupon test expected values (36)
    →coupon test validation (37) """
    def __init__(self, sample, domain, range):
        EmpiricalTest.__init__( self, sample,domain )
        self.testName= "Coupon Collector's Test"
        self.range= range
        self.titles= ( "run","actual","expected" )
        self.domain= domain
        self.nSeq= int(self.nSamples/8)

    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        j, s, count = -1, 0, (15*d)*[0]
        while s != self.nSeq and j != len(list)-1:
            q, r, occurs = 0, 0, (d)*[0]
            while j != len(list)-1 and q != d:
                j, r = j+1, r+1
                while j != len(list)-1 and occurs[list[j]-1]:
                    j, r = j+1, r+1
                occurs[list[j]-1], q = 1, q+1
            count[min(r,self.nSamples-1)] += 1
            s += 1
        if j == len(list):
            raise "exhausted the sequence"
        return count

    def expectedFreq( self, n, d ):
        """Return the expected frequency table for a number of samples over a given domain."""
        def probRun( d, r, nSeq=self.nSeq ):
            return nSeq*(chi2stats.fact(d)/float(d)**r)*chi2stats.Stirling(r-1,d-1)
        def probOth( d, r, nSeq=self.nSeq ):
            return nSeq*(1.0 - (chi2stats.fact(d)/float(d)**(r-1))*chi2stats.Stirling(r-1,d))
        return d*[0] + [ probRun(d,r) for r in range(d,(d*5-1))] + [probOth(d,(d*5-1))]


    # def validate( self ):
    #    """Validate the test algorithm against known results"""
    #    samples = [ ]
    #    for i in range(45):
    #        j= 0
    #        while j < self.domain-1:
    #           samples.append(j)
    #           j += 1
    #        while j < i-1:
    #           samples.append(0)
    #           j += 1
    #        samples.append(self.domain-1)
    #    samples = samples * 10
    #    t, self.nSeq = self.nSeq, 450
    #    act= self.testFreq( samples, self.domain )
    #    exp= map( int, self.expectedFreq( len(samples), self.domain ) )
    #    self.nSeq= t
    #    return ( act == 8*[0] + [90] + 30*[10] + [60]
    #    and exp == [0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 7, 12,
    #        16, 20, 23, 25, 26, 26, 25, 24, 23, 21, 20, 18, 16,
    #        15, 13, 12, 10, 9, 8, 7, 6, 5, 5, 4, 4, 3, 3, 22] )

class RunsUpDTest( EmpiricalTest ):
    """Runs of ascending values, statistically dependent on each other."""
    """→dependent runs up constructor (44)
    →dependent runs up procedure (45)
    →dependent runs up chi-squared test (46)
    →dependent runs up overrides for run() and report() (47)
    →dependent runs up validation (48) """

    def __init__(self, sample, domain, range):
        EmpiricalTest.__init__( self, sample,domain )
        self.testName= "Runs Up - Dependent"
        self.range= ( 1.145, 11.070 )#values taken from table 1 v=5
        self.domain= domain

    def testFreq( self, list, domain ):
        """Return the actual frequency table for a set of samples over a given domain."""
        last, thisSeqLen= list[0], 1
        seqLen = 7*[0]
        for i in list[1:]:
            if i > last:
                last, thisSeqLen= i, thisSeqLen+1
            else:
                seqLen[min(thisSeqLen,6)] += 1
                last, thisSeqLen= i, 1
            # sum(thisSeqLen) == index in list
        seqLen[min(thisSeqLen,6)] += 1
        return seqLen

    def expectedChi2( self, actual, n ):
        """Return chi-squared metric for runs-up frequencies."""
        a= [ [ 4529.4,  9044.9, 13568.0, 18091.0, 22615.0, 27892.0],
         [ 9044.9, 18097.0, 27139.0, 36187.0, 45234.0, 55789.0],
         [13568.0, 27139.0, 40721.0, 54281.0, 67852.0, 83685.0],
         [18091.0, 36187.0, 54281.0, 72414.0, 90470.0,111580.0],
         [22615.0, 45234.0, 67852.0, 90470.0,113262.0,139476.0],
         [27892.0, 55789.0, 83685.0,111580.0,139476.0,172860.0] ]
        b= [ 1.0/6.0, 5.0/24.0, 11.0/120.0, 19.0/720.0, 29.0/5040.0, 1.0/840.0 ]
        V= 0.0
        for i in range(6):
            for j in range(6):
                V += (actual[i+1] - n*b[i])*(actual[j+1] - n*b[j])*a[i][j]
        return V/n

    def run( self ):
        #u= self.generate( )
        self.actual= self.testFreq( self.sample, self.domain )
        self.result= self.expectedChi2( self.actual, self.nSamples )
    def report( self ):
        print (self.testName)
        print "actual: "+str(self.actual)

    # def validate( self ):
    #    """Validate the test algorithm against known results"""
    #    samples = [ ]
    #    for i in range(10):
    #      for j in range(i):
    #         samples.append( j )
    #    samples = samples * 10
    #    act= self.testFreq( samples, self.domain )
    #    return act == [0, 10, 10, 10, 10, 10, 40]

class MaxOfTTest( EmpiricalTest ):
    """Test the distribution of local maxima of groups of size T."""
    """→max of t test constructor (60)
    →max of t test procedure (61)
    →max of t test expected values (62)
    →max of t test validation (63) """

    def __init__(self, sample, domain, range):
        EmpiricalTest.__init__( self, sample,domain )
        self.testName= "Max of T Test"
        self.range= range
        self.titles= ( "value","actual","expected" )
        self.domain= domain
        self.T= 3

    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        t= self.T
        newList=int(len(list)/t )*[0]
        maxi=0;
        for i in range( int(len(list)/t )):
            maxi=0
            for j in range(i*t,i*t+t):
                if(maxi<list[j]):
                    maxi=list[j]
            newList[i]=maxi

        freqTable= d*[0]
        for v in newList:
            freqTable[v]+=1
        return freqTable

    def expectedFreq( self, nSamples, d ):
      t= float(self.T)
      return [ (nSamples/t)*((l/float(d))**t-((l-1)/float(d))**t) for l in range(1,d+1) ]

    # def validate( self ):
    #    """Validate the test algorithm against known results"""
    #    samples = [ ]
    #    for i in range(self.domain-self.T+1):
    #       for j in range(self.T):
    #           samples.append( j+i )
    #    samples = samples * 10
    #    act= self.testFreq( samples, self.domain )
    #    exp= map( int, self.expectedFreq( len(samples), self.domain ) )
    #    return (act == [0]*7+[10]*9
    #    and exp == [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 4, 8, 13, 22, 36])

# class SerialCorrelationTest( EmpiricalTest ):
#     """Compute the correlation coefficient between subsequences."""
#     """→serial correlation test constructor (65)
#     →serial correlation test run and report overrides (66)
#     →serial correlation test validation (67) """
#
#     def __init__(self, sample, domain, range):
#         EmpiricalTest.__init__( self, sample,domain )
#         self.testName= "Serial Correlation"
#         self.domain= domain
#
#     def run( self ):
#         u= self.sample
#         c= chi2stats.correlate( u[:-1], u[1:] )
#         n= float(len(u))
#         m= -1/(n-1)
#         s= 1/(n-1)*sqrt( (n*(n-3))/(n+1) )
#         self.result= c
#         self.range= ( m-2*s, m+2*s )
#     def report( self ):
#         print (self.testName)
#
#     # def validate( self ):
#     #   """validate() -> number"""
#     #   s1 = [ i for i in range(self.domain) ]*10
#     #   c1= chi2stats.correlate( s1, s1 )
#     #
#     #   s2 = [ self.domain-i for i in range(self.domain) ]*10
#     #   c2= chi2stats.correlate( s1, s2 )
#     #
#     #   s3 = [ i for i in range(self.domain) ]*10
#     #   s3.sort()
#     #   c3= chi2stats.correlate( s1, s3 )
#     #
#     #   return c1==1 and c2==-1 and abs(c3)<0.1


# def validate( aTestClass, sample,domain,range ):
#    ti= aTestClass( sample,domain,range )
#    print (ti.testName, ti.validate())
   
def detail( aTestClass, sample,domain=0,range=(0, 0) ):
    if aTestClass=="FreqTest":
        aTestClass=FreqTest
        domain= max(sample)
        if domain<10:
            range=(1.635,12.59)
        else :
            range=(3.325,16.92)
    if aTestClass=="GapTest":
        aTestClass=GapTest
        domain= max(sample)
        if domain<10:
            range=(1.635,12.59)
        else :
            range=(3.325,16.92)
    if aTestClass=="PokerTest":
        aTestClass=PokerTest
        domain= max(sample)
        range=(0.7107, 9.488)
    if aTestClass=="CouponTest":
        aTestClass=CouponTest
        domain= max(sample)
        if domain<10:
            range=(13.091,35.172)
        else :
            range=(26.509,55.758)
    if aTestClass=="MaxOfTTest":
        aTestClass=MaxOfTTest
        domain= max(sample)+1
        if domain<10:
            range=(0.7107,9.488)
        else :
            range=(2.167,14.07)
    ti= aTestClass( sample,domain,range )
    ti.run()
    # ti.report()
    return (ti.final())



theTestList = [ FreqTest, GapTest, PokerTest,
                CouponTest, RunsUpDTest, MaxOfTTest]

def main(sample,domain,test_name,range=(1.635,12.59) ):
    #sample=[2,3,4,1,5,6,6,2,3,4,1,5,2,1,2,4,3,6,5,2,3,1,4,3,2,6,3,4,2,1,4,2,5,6,1,4,2,3,6,4,5,2,6,3,1,6,2,5,1,4]
   #  sample=[2,3,4,1,2,5,6,2,3,4,1,5,2,1,2,4,3,6,5,2,3,1,4,2,6,3,4,2,1,4]
   #  #sample=[2,3,4,1,2,0,6,2,3,4,1,5,2,1,2,4,3,0,5,2,3,1,4,3,2,6,3,4,2,1,4,0,5,6,1,4,2,0,6,4,5,2,6,3,1,6,2,5,1,4]
   #  #sample=[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
   # #for t in theTestList:
   #  detail( FreqTest, sample,6,(1.635,12.59))
   #  detail( GapTest, sample,6,(1.635,12.59))
   #   detail( PokerTest, sample,6,( 0.7107, 9.488 ))
   #  detail( CouponTest, sample,6,( 13.091,35.172))
    #detail( RunsUpDTest, sample,7,( 1.145, 11.070 ))
   #  detail( MaxOfTTest, sample,7,( 1.1455,11.070 ))
   #  detail( SerialCorrelationTest, sample,7)
   #  # validate( SerialTest, sample,7,( 45.74, 82.53 ))

    detail(test_name,sample,domain,range)
if __name__ == "__main__":

    test_names=sys.argv[1]
    sample=sys.argv[2]
    domain=sys.argv[3]
    range=sys.argv[4]
    main(sample,domain,test_names,range)
