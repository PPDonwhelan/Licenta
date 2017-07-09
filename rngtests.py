# -*- coding: utf-8 -*-
"""
Created on Sat May 20 12:00:27 2017

@author: MoldovanM2
"""


from math import sqrt
import random
import chi2stats
import functools


class EmpiricalTest:
    """A single empirical test of a random number generator.
    
    EmpiricalTest( aGenerator, samples ) -- creates the test object
    run() -- generate samples, measure, generate expected, compute result
    report() -- print results on stdout
    final() -- tuple of (testname, result, low, high)
    validate() -- 1 if test produces expected results
    """
    def __init__( self, generator, samples=100 ):
        self.generator= generator; """the sample generator"""
        self.actual= []; """the actual frequencies"""
        self.expected= []; """the expected frequencies"""
        self.range= ( 0, 0 ); """the range of chi-squared values"""
        self.result= None;  """the final metric result"""
        self.titles= ( "", "", "" ); """titles for the metric, low and high"""
        self.testName= "";  """the name of this test"""
        self.nSamples= samples; """number of samples to generate"""
        self.domain= 7; """size of domain for the samples"""
        
    def generate( self ):
        return [ self.generator(self.domain) for i in range(self.nSamples) ]
        #return [2,3,4,1,2,5,6,2,3,4,1,5,2,1,2,4,3,6,5,2,3,1,4,3,2,6,3,4,2,1,4,2,5,6,1,4,2,3,6,4,5,2,6,3,1,6,2,5,1,4]
        #return  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4]
    def run( self ):
        """Run the test."""
        u= self.generate( )
        self.actual= self.testFreq( u, self.domain )
        self.expected= self.expectedFreq( self.nSamples, self.domain )
        self.result= chi2stats.chi2( self.actual, self.expected )
    
    def testFreq( self, u, domain ):
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
    
    def validate( self ):
        """Validate the test algorithm against known results"""
        return 0
    
class FreqTest( EmpiricalTest ):
    """Test frequency distribution."""
    #→frequency test constructor (14) 
    #→frequency test procedure (15) 
    #→frequency test expected values (16) 
    #→frequency test validation (17) 
    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Frequency Test"
        self.range= ( 1.635, 12.59 ) # values taken from Table 1 chi-square v=6
        self.titles= ( "value","actual","expected" )
        self.domain= 7
        
    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        freqTable = d*[0]
        for i in list:
            freqTable[i] += 1
        return freqTable
    
    def expectedFreq( self, nSamples, domain ):
        """Return the expected frequency table for a number of samples over a given domain."""
        return domain*[ nSamples/float(domain) ]
    
    def validate( self ):
        """Validate the test algorithm against known results"""
        samples = [ i for i in range(self.domain) ] * 10
        act= self.testFreq( samples, self.domain )
        exp= self.expectedFreq( len(samples), self.domain )
        return act == exp
    
class SerialTest( EmpiricalTest ):
    """Test serial pair frequency distribution."""
    """→serial test constructor (19) 
    →serial test procedure (20) 
    →serial test expected values (21) 
    →serial test validation (22) """
    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Serial Test"
        self.range= ( 45.74, 82.53 )
        self.titles= ( "pair","actual","expected" )
        self.domain= 7
    
    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        freqTable = (d*d)*[0]
        for i in range(int(len(list)/2)):
            q, r = list[2*i], list[2*i+1]
            freqTable[q*d+r] += 1
        return freqTable
    
    def expectedFreq( self, nSamples, domain ):
        """Return the expected frequency table for a number of samples over a given domain."""
        return (domain**2) * [ nSamples/(2*float(domain)**2) ]
    
    def validate( self ):
       """Validate the test algorithm against known results"""
       samples = [ ]
       for i in range(self.domain):
         for j in range(self.domain):
            samples.append(i)
            samples.append(j)
       samples = samples * 10
       act= self.testFreq( samples, self.domain )
       exp= self.expectedFreq( len(samples), self.domain )
       return act == exp
    
class GapTest( EmpiricalTest ):
    """Test distribution of gap lengths between values that fall in a given range."""
    """→gap test constructor (24) 
    →gap test procedure (25) 
    →gap test expected values (26) 
    →gap test validation (27)"""
    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Gap Test"
        self.range= ( 1.635, 12.59 ) # values taken from Table 1 chi-square v=6
        self.titles= ( "gap size","actual","expected" )
        self.domain= 7
        self.nGaps= int(self.nSamples/10)
    
    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        j, s, count = -1, 0, 8*[0]
        while s != self.nGaps and j != len(list):
            j, r = j+1, 0
            while j != len(list) and list[j] < d/2:
                j, r = j+1, r+1
            count[min(r,7)] += 1
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
        return [ probGap(pd,r) for r in range(7) ] + [probOth(pd,7)]
    
    def validate( self ):
       """Validate the test algorithm against known results"""
       samples = [ ]
       for i in range(10):
         for j in range(i):
            samples.append(self.domain/2-1)   # value out of the range
         samples.append(self.domain/2) # value in the gap range
       samples = samples * 10
       t, self.nGaps = self.nGaps, 100
       act= self.testFreq( samples, self.domain )
       exp= self.expectedFreq( len(samples), self.domain )
       self.nGaps= t
       return (act == [10, 10, 10, 10, 10, 10, 10, 30] 
       and exp == [50.0, 25.0, 12.5, 6.25, 3.125, 1.5625, 0.78125, 0.78125])

class PokerTest( EmpiricalTest ):
    """Test distribution of poker hands in sample groups."""
    """→poker test constructor (29) 
    →poker test procedure (30) 
    →poker test expected values (31) 
    →poker test validation (32) """
    
    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Poker Test"
        self.range= ( 0.7107, 9.488 )# values taken from table 1 v=4
        self.titles= ( "distinct","actual","expected" )
        self.domain= 7
    
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
        freqTable= 5*[0]
        for l in range(int(len(list)/5)):
            hand= list[l*5:l*5+5]
            vUnique= [ v in hand for v in range(d) ]
            distinct= functools.reduce( lambda a,b:a+b, vUnique, 0 )
            freqTable[distinct-1] += 1
        return freqTable
    
        
    def expectedFreq( self, n, d ):
        """Return the expected frequency table for a number of samples over a given domain."""
        k= 5
        exp= 5*[0]
        for r in range(1,6):
            p= 1.0
            for i in range(r):
                p *= d-i
            exp[r-1]= (n/5)*(p/float(d)**k) * chi2stats.Stirling(k, r)
        return exp
    
    def validate( self ):
       """Validate the test algorithm against known results"""
       samples = [ ]
       for i in range(5):
         for j in range(5):
            if i < j: samples.append(0)
            else: samples.append(j)
       samples = samples * 10
       act= self.testFreq( samples, self.domain )
       exp= map( int, self.expectedFreq( len(samples), self.domain ) )
       return (act == [10, 10, 10, 10, 10]
       and exp == [0, 0, 4, 20, 24])
       
class CouponTest( EmpiricalTest ):
    """Test distribution of lengths of runs that contain all values from the domain."""
    """→coupon test constructor (34) 
    →coupon test procedure (35) 
    →coupon test expected values (36) 
    →coupon test validation (37) """
    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Coupon Collector's Test"
        self.range= ( 19.28, 44.99 )
        self.titles= ( "run","actual","expected" )
        self.domain= 8
        self.nSeq= int(self.nSamples/25)
    
    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        j, s, count = -1, 0, 40*[0]
        while s != self.nSeq and j != len(list):
            q, r, occurs = 0, 0, d*[0]
            while j != len(list) and q != d:
                j, r = j+1, r+1
                while j != len(list) and occurs[list[j]]:
                    j, r = j+1, r+1
                occurs[list[j]], q = 1, q+1
            count[min(r,39)] += 1
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
        return d*[0] + [ probRun(d,r) for r in range(d,39)] + [probOth(d,39)]
    
    def validate( self ):
       """Validate the test algorithm against known results"""
       samples = [ ]
       for i in range(45):
           j= 0
           while j < self.domain-1:
              samples.append(j)
              j += 1
           while j < i-1:
              samples.append(0)
              j += 1
           samples.append(self.domain-1)
       samples = samples * 10
       t, self.nSeq = self.nSeq, 450
       act= self.testFreq( samples, self.domain )
       exp= map( int, self.expectedFreq( len(samples), self.domain ) )
       self.nSeq= t
       return ( act == 8*[0] + [90] + 30*[10] + [60]
       and exp == [0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 7, 12, 
           16, 20, 23, 25, 26, 26, 25, 24, 23, 21, 20, 18, 16, 
           15, 13, 12, 10, 9, 8, 7, 6, 5, 5, 4, 4, 3, 3, 22] )
    
class PermutationTest( EmpiricalTest ):
    """Test distribution of permutations in sample groups."""
    """→permutation test constructor (39) 
    →permutation test procedure (40) 
    →permutation test expected values (41) 
    →permutation test validation (42) """
    
    """→next permutation function (74) """

    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Permutation Test"
        self.range= ( 13.09, 35.17 )
        self.titles= ( "ordering","actual","expected" )
        self.domain= 7
        self.seqSize= 4
    
    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        t= self.seqSize
        count = chi2stats.fact(t)*[0]
        for i in range(int(len(list)/t)):
            u = list[t*i:t*i+t]
            c = t*[0]
            r = t
            while r > 0:
                s = 0
                for j in range(r):
                    if u[j] > u[s]: s = j
                c[r-1]= s
                u[r-1], u[s] = u[s], u[r-1]
                r -= 1
            f= 0
            for j in range(t-1):
                f = (f+c[j])*(j+2)
            f += c[t-1]
            count[f] += 1
        return count    
    
    def expectedFreq( self, n, d ):
        """Return the expected frequency table for a number of samples over a given domain."""
        perms= chi2stats.fact(self.seqSize)
        return perms*[ n/float(self.seqSize)/perms ]
    
    def validate( self ):
       """Validate the test algorithm against known results"""
       samples = [ ]
       perm = [ 0, 1, 2, 3 ]
       while perm:
           samples += perm
           perm = nextPerm( perm )
       samples = samples * 10
       act= self.testFreq( samples, self.domain )
       exp= self.expectedFreq( len(samples), self.domain )
       return act == exp
   
class RunsUpDTest( EmpiricalTest ):
    """Runs of ascending values, statistically dependent on each other."""
    """→dependent runs up constructor (44) 
    →dependent runs up procedure (45) 
    →dependent runs up chi-squared test (46) 
    →dependent runs up overrides for run() and report() (47) 
    →dependent runs up validation (48) """
    
    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Runs Up - Dependent"
        self.range= ( 1.145, 11.070 )#values taken from table 1 v=5
        self.domain= 7
    
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
        u= self.generate( )
        self.actual= self.testFreq( u, self.domain )
        self.result= self.expectedChi2( self.actual, self.nSamples )
    def report( self ):
        print (self.testName)

    def validate( self ):
       """Validate the test algorithm against known results"""
       samples = [ ]
       for i in range(10):
         for j in range(i):
            samples.append( j )
       samples = samples * 10
       act= self.testFreq( samples, self.domain )
       return act == [0, 10, 10, 10, 10, 10, 40]

class RunsUpIndep1Test( EmpiricalTest ):
    """Runs of ascending values, statistically independent, with a large domain."""
    """→independent, large domain runs up constructor (50) 
    →independent, large domain runs up procedure (51) 
    →independent, large domain runs up expected values (52) 
    →independent, large domain runs up validation (53) """
    
    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Runs Up - Independent"
        self.range= ( 1.145, 11.070 )#table 1 v=5
        self.domain= 1024
        self.nRuns= 0
    
    def testFreq( self, list, domain ):
        """Return the actual frequency table for a set of samples over a given domain."""
        last= -1
        seqLen = 7*[0]
        for i in list:
            if last != -1:
                if i > last:
                    last, thisSeqLen= i, thisSeqLen+1
                else:
                    seqLen[min(thisSeqLen,6)]+= 1
                    last= -1
            else:
                 last, thisSeqLen= i, 1
        seqLen[min(thisSeqLen,6)]+= 1
        self.nRuns= functools.reduce( lambda a,b: a+b, seqLen, 0 )
        return seqLen
    
    def expectedFreq( self, nSamples, d ):
        """Return the expected frequency table for a number of samples over a given domain."""
        def prob(l,nRuns=self.nRuns):
            return float(nRuns)*(1.0/chi2stats.fact(l)-1.0/chi2stats.fact(l+1))
        return [0] + [prob(l) for l in range(1,6)] + [float(self.nRuns)/chi2stats.fact(6)]
    
    expResult= [0, 500, 333, 125, 33, 6, 1]
    def validate( self ):
       """Validate the test algorithm against known results"""
       samples = [ ]
       for i in range(10):
         for j in range(i):
            samples.append( j )
         samples.append( i-1 )  # skipped to make runs independent
       samples = samples * 10
       act= self.testFreq( samples, self.domain )
       self.nRuns= 100
       exp= map( int, self.expectedFreq( samples, self.domain ) )
       return act == [0, 10, 10, 10, 10, 10, 41] and exp == self.expResult
   
class RunsUpIndep2Test( RunsUpIndep1Test ):
    """Runs of ascending values, statistically independent, with a small domain."""
    """→independent, small domain runs up constructor (55) 
    →independent, small domain runs up procedure (56) 
    →independent, small domain runs up expected results (57) 
    →independent, small domain runs up additional test (58) """
    
    def __init__( self, generator, samples ):
        RunsUpIndep1Test.__init__( self, generator, samples )
        self.testName= "Runs Up - Independent (Ritter)"
        self.domain= 7
    
    def expectedFreq( self, nSamples, d ):
        """Return the expected frequency table for a number of samples over a given domain."""
        def prob(d,l):
            return chi2stats.bico(d,l)/float(d)**l-chi2stats.bico(d,l+1)/float(d)**(l+1)
        def probOth(d,l=6):
            return chi2stats.bico(d,l)/float(d)**float(l)
        return [0] + [self.nRuns*prob(d,l) for l in range(1,6)] + [self.nRuns*probOth(d,6)]
    
    expResult= [0, 531, 332, 108, 23, 3, 0]

    def testRitter():
        """Test the Ritter expected value function."""
        self.nRuns= 1.0
        for r in range(15):
            print (self.expectedFreq(256,r+1))

class MaxOfTTest( EmpiricalTest ):
    """Test the distribution of local maxima of groups of size T."""
    """→max of t test constructor (60) 
    →max of t test procedure (61) 
    →max of t test expected values (62) 
    →max of t test validation (63) """
    
    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Max of T Test"
        self.range= ( 6.570, 23.68 )
        self.titles= ( "value","actual","expected" )
        self.domain= 7
        self.T= 8
    
    def testFreq( self, list, d ):
        """Return the actual frequency table for a set of samples over a given domain."""
        t= self.T
        newList= [ max( list[i*t:i*t+t] ) for i in range( int(len(list)/t )) ]
        freqTable= d*[0]
        for v in newList:
            freqTable[v]+=1
        return freqTable
    
    def expectedFreq( self, nSamples, d ):
      t= float(self.T)
      return [ (nSamples/t)*((l/float(d))**t-((l-1)/float(d))**t) for l in range(1,d+1) ]
  
    def validate( self ):
       """Validate the test algorithm against known results"""
       samples = [ ]
       for i in range(self.domain-self.T+1):
          for j in range(self.T):
              samples.append( j+i )
       samples = samples * 10
       act= self.testFreq( samples, self.domain )
       exp= map( int, self.expectedFreq( len(samples), self.domain ) )
       return (act == [0]*7+[10]*9
       and exp == [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 4, 8, 13, 22, 36])

class SerialCorrelationTest( EmpiricalTest ):
    """Compute the correlation coefficient between subsequences."""
    """→serial correlation test constructor (65) 
    →serial correlation test run and report overrides (66) 
    →serial correlation test validation (67) """

    def __init__( self, generator, samples ):
        EmpiricalTest.__init__( self, generator, samples )
        self.testName= "Serial Correlation"
        self.domain= 7
    
    def run( self ):
        u= self.generate( )
        c= chi2stats.correlate( u[:-1], u[1:] )
        n= float(len(u))
        m= -1/(n-1)
        s= 1/(n-1)*sqrt( (n*(n-3))/(n+1) )
        self.result= c
        self.range= ( m-2*s, m+2*s )
    def report( self ):
        print (self.testName)

    def validate( self ):
      """validate() -> number"""
      s1 = [ i for i in range(self.domain) ]*10
      c1= chi2stats.correlate( s1, s1 )
    
      s2 = [ self.domain-i for i in range(self.domain) ]*10
      c2= chi2stats.correlate( s1, s2 )
          
      s3 = [ i for i in range(self.domain) ]*10
      s3.sort()
      c3= chi2stats.correlate( s1, s3 )
    
      return c1==1 and c2==-1 and abs(c3)<0.1  
  
theTestList = [ FreqTest, SerialTest, GapTest, PokerTest,
    CouponTest, PermutationTest, RunsUpDTest,
    RunsUpIndep1Test, RunsUpIndep2Test,
    MaxOfTTest, SerialCorrelationTest ]

def validate( aTestClass, aGenerator, samples ):
   ti= aTestClass( aGenerator, samples )
   print (ti.testName, ti.validate())
   
def detail( aTestClass, aGenerator, samples ):
   ti= aTestClass( aGenerator, samples )
   ti.run()
   ti.report()
   
   print (ti.final())
   
# def summary( name="builtin", gen= random.randrange, samples=100 ):
#    s= [name]
#    for t in theTestList:
#       ti= t( gen, samples )
#       s.append( ti.final() )
#    return s
#
# def tests( ):
#    r = []
#    for t in theTestList:
#       ti= t( lambda x:0, 0 )
#       r.append( (ti.testName,) + ti.range )
#    return r


def main( gen=random.randrange, samples=49):
   for t in theTestList:
      detail( t, gen, samples )

if __name__ == "__main__":
   main()
