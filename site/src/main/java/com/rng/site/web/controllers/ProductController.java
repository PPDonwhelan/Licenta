/**
 * 
 */
package com.rng.site.web.controllers;

import com.rng.catalog.CatalogService;
import com.rng.entities.Subject;
import com.rng.site.web.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Siva
 *
 */
@Controller
public class ProductController extends JCartSiteBaseController
{	
	@Autowired
	private CatalogService catalogService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Subject";
	}	
	
//	@RequestMapping("/products/{sku}")
//	public String product(@PathVariable String sku, Model model)
//	{
//		Subject subject = catalogService.getProductBySku(sku);
//		model.addAttribute("subject", subject);
//		return "subject";
//	}
	
	@RequestMapping("/products")
	public String searchProducts(@RequestParam(name="q", defaultValue="") String query, Model model)
	{
		List<Subject> subjects = catalogService.searchProducts(query);
		model.addAttribute("subjects", subjects);
		return "subjects";
	}

	@RequestMapping(value="/products/images/{productId}", method=RequestMethod.GET)
	public void showProductImage(@PathVariable String productId, HttpServletRequest request, HttpServletResponse response) {
		try {
			FileSystemResource file = new FileSystemResource(WebUtils.IMAGES_DIR +productId+".jpg");
			response.setContentType("image/jpg");
			org.apache.commons.io.IOUtils.copy(file.getInputStream(), response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}	      
	}
}
