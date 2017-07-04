/**
 * 
 */
package com.rng.site.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rng.site.web.models.Cart;
import com.rng.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rng.catalog.CatalogService;
import com.rng.site.web.models.LineItem;



/**
 * @author Siva
 *
 */
@Controller
public class CartController extends JCartSiteBaseController
{
	@Autowired
	private CatalogService catalogService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Cart";
	}
	
	@RequestMapping(value="/cart", method=RequestMethod.GET)
	public String showCart(HttpServletRequest request, Model model)
	{
		Cart cart = getOrCreateCart(request);
		model.addAttribute("cart", cart);
		return "cart";
	}
	
	@RequestMapping(value="/cart/items/count", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCartItemCount(HttpServletRequest request, Model model)
	{
		Cart cart = getOrCreateCart(request);
		int itemCount = cart.getItemCount();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", itemCount);
		return map;
	}
		
	@RequestMapping(value="/cart/items", method=RequestMethod.POST)
	@ResponseBody
	public void addToCart(@RequestBody Subject subject, HttpServletRequest request)
	{
		Cart cart = getOrCreateCart(request);
		Subject p = catalogService.getProductBySku(subject.getEmail());
		cart.addItem(p);
	}
	
	@RequestMapping(value="/cart/items", method=RequestMethod.PUT)
	@ResponseBody
	public void updateCartItem(@RequestBody LineItem item, HttpServletRequest request, HttpServletResponse response)
	{
		Cart cart = getOrCreateCart(request);
		if(item.getQuantity() <= 0){
			String sku = item.getSubject().getEmail();
			cart.removeItem(sku);
		} else {
			cart.updateItemQuantity(item.getSubject(), item.getQuantity());
		}
	}
	
	@RequestMapping(value="/cart/items/{sku}", method=RequestMethod.DELETE)
	@ResponseBody
	public void removeCartItem(@PathVariable("sku") String sku, HttpServletRequest request)
	{
		Cart cart = getOrCreateCart(request);
		cart.removeItem(sku);
	}
	
	@RequestMapping(value="/cart", method=RequestMethod.DELETE)
	@ResponseBody
	public void clearCart(HttpServletRequest request)
	{
		Cart cart = getOrCreateCart(request);
		cart.setItems(new ArrayList<LineItem>());
	}
}
