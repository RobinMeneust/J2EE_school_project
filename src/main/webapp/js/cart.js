const url = "edit-cart-item-quantity";

function addToCart(button, productId) {
    if(productId != null && !isNaN(productId) && productId>0) {
        const url = "add-to-cart?id="+productId;

        fetch(url, {
            method: 'GET'
        }).then((response) => {
            let errorAlertBox = $("#error-alert-box");
            if(response.ok) {
                response.json().then((data) => {
                    // Disable the button if the product has been added to the cart
                    if(button != null) {
                        button.setAttribute("disabled",true);
                        button.classList.add("btn-success");
                    }
                });
            } else {
                if(errorAlertBox.length) {
                    errorAlertBox.removeClass("invisible").addClass("visible");
                }
            }
        });
    }
}

function changeQty(itemId, variation) {
    let quantity = parseInt($("#qty_"+itemId).text()) + parseInt(variation);
    if(quantity<=0) {
        removeCartItem(itemId);
        return;
    }

    fetch(url+"?id="+itemId+"&quantity="+quantity, {
        method: 'GET'
    }).then((response) => {
        if(response.ok) {
            $("#qty_"+itemId).html(quantity);
        }
    });
}

function removeCartItem(itemId) {
    fetch(url+"?id="+itemId+"&quantity=0", {
        method: 'GET'
    }).then((response) => {
        if(response.ok) {
            $("#item_"+itemId).remove();
        }
    });
}