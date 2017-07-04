/**
 * 
 */
package com.rng;


public class RNGException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public RNGException()
	{
		super();
	}

	public RNGException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RNGException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public RNGException(String message)
	{
		super(message);
	}

	public RNGException(Throwable cause)
	{
		super(cause);
	}
	
}
