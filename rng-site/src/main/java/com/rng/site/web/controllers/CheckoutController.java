/**
 * 
 */
package com.rng.site.web.controllers;

import javax.servlet.http.HttpServletRequest;

import com.rng.site.web.models.Cart;
import com.rng.site.web.models.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Siva
 *
 */
@Controller
public class CheckoutController extends SiteBaseController
{

	@Override
	protected String getHeaderTitle()
	{
		return "Checkout";
	}

	@RequestMapping(value="/checkout", method=RequestMethod.GET)
	public String checkout(HttpServletRequest request, Model model)
	{
		OrderDTO order = new OrderDTO();
		model.addAttribute("order", order);
		Cart cart = getOrCreateCart(request);
		model.addAttribute("cart", cart);
		return "checkout";
	}
}
