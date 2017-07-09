import sys

def bla(some):
    print some
    print 'Number of arguments:', len(sys.argv), 'arguments.'
    print 'Argument List:', str(sys.argv)
    print str(sys.argv)
if __name__ == "__main__":
    print "something"
    some=sys.argv[1]
    bla(some)