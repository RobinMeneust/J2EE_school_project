publishableStripeKeyPromise = fetch("get-stripe-publishable-key").then((response) => {
    return response.json();
}).then((data) => {
    if("stripe-publishable-key" in data) {
        return data["stripe-publishable-key"];
    } else {
        return "";
    }
});

let contextPathPromise = fetch("get-context-path").then((response) => {
    return response.json();
}).then((data) => {
    if("path" in data) {
        return data["path"];
    } else {
        return "";
    }
});


let elements;
let globalClientSecret = null;
let stripe = null;

initialize();
checkStatus();

document
    .querySelector("#payment-form")
    .addEventListener("submit", handleSubmit);

// Fetches a payment intent and captures the client secret
async function initialize() {
    stripe = Stripe(await publishableStripeKeyPromise);

    const response = await fetch(`create-payment-intent`);
    const { clientSecret } = await response.json();
    globalClientSecret = clientSecret;

    const appearance = {
        theme: 'stripe',
    };
    elements = stripe.elements({ appearance, clientSecret });

    const paymentElement = elements.create("card", {
        hidePostalCode: true
    });
    paymentElement.mount("#payment-element");
}

async function handleSubmit(e) {
    e.preventDefault();
    setLoading(true);

    contextPath = await contextPathPromise;
    if(contextPath == "") {
        console.error("Website URL could not be fetched")
        return;
    }

    const { paymentIntent, error } = await stripe.confirmCardPayment(globalClientSecret, {
        payment_method: {
            card: elements.getElement('card'),
            billing_details: {
                // TODO fetch from session variable (user)
                name: 'John Doe',
                email: 'john@example.com',
            },
        },
    });


    if (error) {
        console.error(error);
        if (error.type === "card_error" || error.type === "validation_error") {
            showMessage(error.message);
        } else {
            showMessage("An unexpected error occurred.");
        }
    } else if (paymentIntent.status === 'succeeded') {
        window.location.href = `http://localhost:8082${contextPath}/checkout`;
    } else {
        console.log('Payment is not yet confirmed');
    }
    setLoading(false);
}

// Get payment intent status after submit
async function checkStatus() {
    const clientSecret = new URLSearchParams(window.location.search).get(
        "payment_intent_client_secret"
    );

    if (!clientSecret) {
        return;
    }

    const { paymentIntent } = await stripe.retrievePaymentIntent(clientSecret);

    switch (paymentIntent.status) {
        case "succeeded":
            showMessage("Payment succeeded!");
            break;
        case "processing":
            showMessage("Your payment is processing.");
            break;
        case "requires_payment_method":
            showMessage("Your payment was not successful, please try again.");
            break;
        default:
            showMessage("Something went wrong.");
            break;
    }
}

// Display message or spinner

function showMessage(messageText) {
    const messageContainer = document.querySelector("#payment-message");

    messageContainer.classList.remove("visually-hidden");
    messageContainer.textContent = messageText;

    setTimeout(function () {
        messageContainer.classList.add("visually-hidden");
        messageContainer.textContent = "";
    }, 4000);
}

// Show a spinner on payment submission
function setLoading(isLoading) {
    if (isLoading) {
        $(".spinner-pay-btn").each(function() {
            $(this).removeClass("visually-hidden");
        });
        $("#pay-btn").prop("disabled",true);
        $("#pay-btn-text").addClass("visually-hidden");
    } else {
        $(".spinner-pay-btn").each(function() {
            $(this).addClass("visually-hidden");
        });
        $("#pay-btn").prop("disabled",false);
        $("#pay-btn-text").removeClass("visually-hidden");
    }
}