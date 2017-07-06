/**
 * 
 */
package com.rng.site.web.controllers;

import org.springframework.stereotype.Controller;


@Controller
//public class OrderController extends SiteBaseController
//{
public class OrderController
{

//	@Autowired private UserService customerService;
//	@Autowired protected OrderService orderService;
//	@Autowired protected EmailService emailService;
//
//	@Override
//	protected String getHeaderTitle()
//	{
//		return "Order";
//	}
//
//	@RequestMapping(value="/orders", method=RequestMethod.POST)
//	public String placeOrder(@Valid @ModelAttribute("order") OrderDTO order,
//			BindingResult result, Model model, HttpServletRequest request)
//	{
//		Cart cart = getOrCreateCart(request);
//		if (result.hasErrors()) {
//			model.addAttribute("cart", cart);
//			return "checkout";
//        }
//
//		Order newOrder = new Order();
//
//		String email = getCurrentUser().getCustomer().getEmail();
//		Customer customer = customerService.getCustomerByEmail(email);
//		newOrder.setCustomer(customer);
//		Address address = new Address();
//		address.setAddressLine1(order.getAddressLine1());
//		address.setAddressLine2(order.getAddressLine2());
//		address.setCity(order.getCity());
//		address.setState(order.getState());
//		address.setZipCode(order.getZipCode());
//		address.setCountry(order.getCountry());
//
//		newOrder.setDeliveryAddress(address);
//
//		Address billingAddress = new Address();
//		billingAddress.setAddressLine1(order.getAddressLine1());
//		billingAddress.setAddressLine2(order.getAddressLine2());
//		billingAddress.setCity(order.getCity());
//		billingAddress.setState(order.getState());
//		billingAddress.setZipCode(order.getZipCode());
//		billingAddress.setCountry(order.getCountry());
//
//		newOrder.setBillingAddress(billingAddress);
//
//		Set<OrderItem> orderItems = new HashSet<OrderItem>();
//		List<LineItem> lineItems = cart.getItems();
//		for (LineItem lineItem : lineItems)
//		{
//			OrderItem item = new OrderItem();
//			item.setSubject(lineItem.getSubject());
//			item.setQuantity(lineItem.getQuantity());
//			item.setPrice(lineItem.getSubject().getPrice());
//			item.setOrder(newOrder);
//			orderItems.add(item);
//		}
//
//		newOrder.setItems(orderItems);
//
//		Payment payment = new Payment();
//		payment.setCcNumber(order.getCcNumber());
//		payment.setCvv(order.getCvv());
//
//		newOrder.setPayment(payment);
//		Order savedOrder = orderService.createOrder(newOrder);
//
//		this.sendOrderConfirmationEmail(savedOrder);
//
//		request.getSession().removeAttribute("CART_KEY");
//		return "redirect:orderconfirmation?orderNumber="+savedOrder.getOrderNumber();
//	}
//
//	protected void sendOrderConfirmationEmail(Order order)
//	{
//		try {
//			emailService.sendEmail(order.getCustomer().getEmail(),
//					"QuilCartCart - Order Confirmation",
//					"Your order has been placed successfully.\n"
//					+ "Order Number : "+order.getOrderNumber());
//		} catch (RNGException e) {
//			logger.error(e);
//		}
//	}
//
//	@RequestMapping(value="/orderconfirmation", method=RequestMethod.GET)
//	public String showOrderConfirmation(@RequestParam(value="orderNumber")String orderNumber, Model model)
//	{
//		Order order = orderService.getOrder(orderNumber);
//		model.addAttribute("order", order);
//		return "orderconfirmation";
//	}
//
//
//	@RequestMapping(value="/orders/{orderNumber}", method=RequestMethod.GET)
//	public String viewOrder(@PathVariable(value="orderNumber")String orderNumber, Model model)
//	{
//		Order order = orderService.getOrder(orderNumber);
//		model.addAttribute("order", order);
//		return "view_order";
//	}
}
