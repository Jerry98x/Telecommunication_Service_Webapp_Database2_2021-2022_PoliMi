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

(function () {
    window.addEventListener("load", () => {
        makeCall("GET", "HomePageLoading", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            let sps = JSON.parse(message);

                            let anchor = document.createDocumentFragment();
                            sps.forEach(sp => showServicePackage(sp, anchor));
                            if(sessionStorage.getItem('loggedUser') != null){
                                let user = JSON.parse(sessionStorage.getItem('loggedUser'));
                                let userInfo = document.createElement("h6");
                                userInfo.innerHTML = "Logged in as <b>" + user.username + "</b>";
                                anchor.appendChild(userInfo);
                                if(sessionStorage.getItem('rejectedOrders') != null){
                                    let roText = document.createElement("h6");
                                    roText.innerHTML = "Rejected orders";
                                    anchor.appendChild(roText)
                                    JSON.parse(sessionStorage.getItem('rejectedOrders')).forEach(ro => showRejectedOrder(ro, anchor));
                                }
                            }

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

})();



function servicePackageRedirect(event, spId){
    event.preventDefault();
    let form = document.createElement("form");
    form.name = "spToBuyForm";
    let input = document.createElement("input");
    input.name = "spIdToBuy"
    input.value = spId;
    form.appendChild(input);
    makeCall("POST", "BuyPageLoading", form,
        async function (req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        let sptb = JSON.parse(message);
                        sessionStorage.setItem('servicePackageToBuy', JSON.stringify(sptb));
                        sessionStorage.setItem('availableOptionalProducts', JSON.stringify(sptb.availableOptionalProducts));
                        sessionStorage.setItem('availableValidityPeriods', JSON.stringify(sptb.availableValidityPeriods));
                        await (sessionStorage.getItem('servicePackageToBuy') != null &&
                            sessionStorage.getItem('availableOptionalProducts') != null &&
                            sessionStorage.getItem('availableValidityPeriods') != null);
                        window.location.href = "BuyPage.html";

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
}

function showServicePackage(servicePackage, anchor) {
    let spDiv = document.createElement("div");
    let spName = document.createElement("a");
    spName.id = servicePackage.servicePackageId;
    spName.innerHTML = servicePackage.name;
    spName.href = "#";
    spName.addEventListener('click', (event) => servicePackageRedirect(event, spName.id));
    spDiv.appendChild(spName);

    let servicesDiv = document.createElement("div");
    servicePackage.servicesDescriptions.forEach(sd => showServiceDescription(sd, servicesDiv));
    spDiv.appendChild(servicesDiv);

    if(servicePackage.availableOptionalProducts != null && servicePackage.availableOptionalProducts[0] != null) {
        let availableOptionalProductsDiv = document.createElement("div");
        let opText = document.createElement("h5");
        opText.innerHTML = "Available optional products";
        availableOptionalProductsDiv.appendChild(opText);
        servicePackage.availableOptionalProducts.forEach(aop => showOptionalProductDescription(aop, availableOptionalProductsDiv));
        spDiv.appendChild(availableOptionalProductsDiv);
    }
    anchor.appendChild(spDiv);
    //content moved into txt file
}


function showServiceDescription(serviceDescription, servicesDiv){
    let service = document.createElement("div");
    service.innerHTML = serviceDescription;
    servicesDiv.appendChild(service);
}

function showOptionalProductDescription(availableOptionalProduct, availableOptionalProductsDiv){
    let optionalProduct = document.createElement("div");
    optionalProduct.innerHTML = availableOptionalProduct.name;
    availableOptionalProductsDiv.appendChild(optionalProduct);
}

function showRejectedOrder(rejectedOrder, anchor){
    let roLink = document.createElement("a");
    roLink.href = "#";
    roLink.innerHTML = "Order nr. " + rejectedOrder.orderId + " of cost " + rejectedOrder.totalCost_euro + "€/month";
    roLink.addEventListener((event) => confirmRejectedOrder(event, rejectedOrder.orderId));
    anchor.appendChild(roLink);
}

function confirmRejectedOrder(event, orderId){
    event.preventDefault();
    //TODO same as in buyPage
}