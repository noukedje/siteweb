     
	 function removeItemFromCart(id)
	    {
		 $.ajax ({ 
	        url: '/cart/delete/'+id, 
	        type: "DELETE", 
	        dataType: "json",
	        contentType: "application/json",
	        complete: function(responseData, status, xhttp){ 
	        	updateCartItemCount();
	        	location.href = '/cart' 
	        }
	      });
	    }
	    function updateCartItemQuantity(id, qte)
	    { 
	    	$.ajax ({ 
	        url: '/cart/update/'+id+'/'+qte, 
	        type: "POST" 
	        //dataType: "json",
	        //contentType: "application/json",
	        //data : '{ "product" :{ "id":"'+ id +'"},"quantity":"'+quantity+'"}',
	       // complete: function(responseData, status, xhttp){ 
	        	//updateCartItemCount();        	
	        	
	        //}
	        });
	     }
	     function updateCartItemCount()
	     {
		  $.ajax ({ 
	        url: '/cart/items/count', 
	        type: "GET", 
	        dataType: "json",
	        contentType: "application/json",
	        complete: function(responseData, status, xhttp){ 
	        	$('#cart-item-count').text(responseData.responseJSON.count);
	        }
	      });
	     }

	

	


