/**
 * 
 */
package com.rng.site.web.models;

import java.math.BigDecimal;

import com.rng.entities.Subject;

/**
 * @author Siva
 *
 */
public class LineItem
{
	private Subject subject;
	private int quantity;
	
	public LineItem()
	{
	}
	
	public LineItem(Subject subject, int quantity)
	{
		this.subject = subject;
		this.quantity = quantity;
	}

	public Subject getSubject()
	{
		return subject;
	}
	public void setSubject(Subject subject)
	{
		this.subject = subject;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public BigDecimal getSubTotal()
	{
		return subject.getPrice().multiply(new BigDecimal(quantity));
	}
	
	
}
