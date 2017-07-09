jQuery(document).ready(function($){

	/*
	 $("#MyButton").bind("click", function() {
		  fHardCodedFunction.apply(this, [someValue]);
	 });
	 */
	//$("#cart-item-count").bind("click", updateCartItemCount);
	updateCartItemCount();
});

	function updateCartItemCount()
	{
		$.ajax ({ 
	        url: '/cart/items/count', 
	        type: "GET", 
	        dataType: "json",
	        contentType: "application/json",
	        complete: function(responseData, status, xhttp){ 
	        	$('#result').text('('+responseData.responseJSON.count+')');
	        }
	    });
	}

	function getTestsResults()
	{
		$.ajax ({
			url: '/result',
			type: "GET",
			dataType: "json",
			contentType: "application/json",
			complete: function(responseData, status, xhttp){
				$('#result').text('('+responseData.responseJSON.count+')');
			}
		});
	}

	function analizeTestResults(name)
	{
		// $.ajax ({
		// 	url: '/result',
		// 	type: "POST",
		// 	dataType: "json",
		// 	contentType: "application/json",
		// 	data : '{"id":"'+ id +'"}"',
		// 	complete: function(responseData, status, xhttp){
		// 		//updateCartItemCount();
		// 		analyzeSample();
		// 	}
		// });
		$.ajax({
			type: "GET",
			url: "~/helloworld.py",
			data: { param: name}
		}).done(function( o ) {
			// do something
		});
	}





function updateCartItemQuantity(sku, quantity)
	{
		$.ajax ({ 
	        url: '/cart/items', 
	        type: "PUT", 
	        dataType: "json",
	        contentType: "application/json",
	        data : '{ "product" :{ "sku":"'+ sku +'"},"quantity":"'+quantity+'"}',
	        complete: function(responseData, status, xhttp){ 
	        	updateCartItemCount();        	
	        	location.href = '/cart' 
	        }
	    });
	}

	function removeItemFromCart(sku)
	{
		$.ajax ({ 
	        url: '/cart/items/'+sku, 
	        type: "DELETE", 
	        dataType: "json",
	        contentType: "application/json",
	        complete: function(responseData, status, xhttp){ 
	        	updateCartItemCount();
	        	location.href = '/cart' 
	        }
	    });
	}

