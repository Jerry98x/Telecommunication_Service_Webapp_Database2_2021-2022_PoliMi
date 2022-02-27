/**
 *
 */
function makeCall(method, url, formElement, cback, reset = true) {
    var req = new XMLHttpRequest(); // visible by closure
    req.onreadystatechange = function() {
        cback(req)
    }; // closure
    req.open(method, url);
    if (formElement == null) {
        req.send();
    } else {
        req.send(new FormData(formElement));
    }
    if (formElement !== null && reset === true) {
        formElement.reset();
    }
}

function showService(service, anchor){//TODO in employee app check when creating service that unlimited true iff min!=0/null
    let serviceDiv = document.createElement("div");
    serviceDiv.innerHTML = service;
    anchor.appendChild(serviceDiv);
}

function showOptionalProduct(optionalProduct, anchor){
    let optionalProductDiv = document.createElement("input");
    optionalProductDiv.type = "checkbox";
    optionalProductDiv.innerHTML = optionalProduct;
    anchor.appendChild(optionalProductDiv);
}

(function () {
    window.addEventListener("load", () => {

        let sptb = sessionStorage.getItem('servicePackageToBuy');

        var anchor = document.createDocumentFragment();
        let title = document.createElement("div");
        title.innerHTML = sptb.name;
        anchor.appendChild(title);
        sptb.servicesDescriptions.forEach(service => showService(service, anchor));
        sptb.availableOptionalProducts.forEach(product => showOptionalProduct(product, anchor));
        //TODO show validity periods

        document.getElementById("main").appendChild(anchor);
        /*
        let form = document.createElement("form");
        form.name = "spToBuyForm";
        let input = document.createElement("input");
        input.name = "spIdToBuy"
        input.value = sessionStorage.getItem('servicePackageIdToBuy');
        form.appendChild(input);
        makeCall("POST", "BuyPageLoading", form,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            let sptb = sessionStorage.getItem('servicePackageToBuy');

                            var anchor = document.createDocumentFragment();
                            let title = document.createElement("div");
                            title.innerHTML = sptb.name;
                            anchor.appendChild(title);
                            sptb.servicesDescriptions.forEach(service => showService(service, anchor));
                            sptb.availableOptionalProducts.forEach(product => showOptionalProduct(product, anchor));
                            //TODO show validity periods

                            document.getElementById("main").appendChild(anchor);
                            break;
                        case 400: // bad request
                            document.getElementById("errormessage").textContent = message;
                            break;
                        case 401: // unauthorized
                            document.getElementById("errormessage").textContent = message;
                            break;
                        case 500: // server error
                            document.getElementById("errormessage").textContent = message;
                            break;
                    }
                }
            }
        );
    });

         */
    });

})();

