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

(function (){
    if(sessionStorage.getItem("loggedUser") != null) {
        let user = JSON.parse(sessionStorage.getItem("loggedUser"));
        let userInfo = document.createElement("h6");
        userInfo.innerHTML = "Logged in as <b>" + user.username + "</b>";
        document.getElementById("user_login").appendChild(userInfo);

        if(user.insolvent) {//TODO test
            let userIdForm = document.createElement("form");
            userIdForm.name = "userIdForm";
            let input = document.createElement("input");
            input.name = "userId"
            input.value = user.userId;
            userIdForm.appendChild(input);
            window.addEventListener("load", () => {
                makeCall("POST", "GetRejectedOrders", userIdForm,
                    function (req) {
                        if (req.readyState === XMLHttpRequest.DONE) {
                            var message = req.responseText;
                            switch (req.status) {
                                case 200:
                                    let anchor = document.createDocumentFragment();
                                    let rejectedOrders = JSON.parse(message);
                                    let roText = document.createElement("h6");
                                    roText.innerHTML = "Rejected orders";
                                    anchor.appendChild(roText)
                                    rejectedOrders.forEach(ro => showRejectedOrder(ro, anchor));
                                    document.getElementById("sp_column").appendChild(anchor);
                                    break;
                                default:
                                    document.getElementById("errormessage").textContent += message;
                                    break;
                            }
                        }
                    })
            })
        }
    }

    window.addEventListener("load", () => {
        makeCall("GET", "GetServicePackages", null,
            function (req) {
            if(req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        let anchor = document.createDocumentFragment();
                        let sps = JSON.parse(message);
                        anchor.appendChild(document.createElement("br"));
                        sps.forEach(sp => showServicePackage(sp, anchor));
                        document.getElementById("sp_column").appendChild(anchor);
                        break;
                    default:
                        document.getElementById("errormessage").textContent += message;
                        break;
                }
            }
            })
    })

}) ();


function servicePackageRedirect(event, spId){
    event.preventDefault();
    sessionStorage.setItem("servicePackageIdToBuy", spId);
    window.location.href = "BuyPage.html";
}

function showServicePackage(servicePackage, anchor) {
    let spDiv = document.createElement("div");
    spDiv.classList.add("border-bottom");
    let spHeader = document.createElement("h4");
    let spName = document.createElement("a");
    spName.id = servicePackage.servicePackageId;
    spName.innerHTML = servicePackage.name;
    spName.href = "#";
    spName.addEventListener("click", (event) => servicePackageRedirect(event, spName.id));
    spHeader.appendChild(spName);
    spDiv.appendChild(spHeader);

    let servicesDiv = document.createElement("div");
    servicePackage.servicesDescriptions.forEach(sd => showServiceDescription(sd, servicesDiv));
    spDiv.appendChild(servicesDiv);
    spDiv.appendChild(document.createElement("br"));

    if(servicePackage.availableOptionalProducts != null && servicePackage.availableOptionalProducts[0] != null) {
        let availableOptionalProductsDiv = document.createElement("div");
        availableOptionalProductsDiv.classList.add("border");
        let opText = document.createElement("h5");
        opText.innerHTML = "Available optional products";
        availableOptionalProductsDiv.appendChild(opText);
        servicePackage.availableOptionalProducts.forEach(aop => showOptionalProductDescription(aop, availableOptionalProductsDiv));
        spDiv.appendChild(availableOptionalProductsDiv);
    }
    spDiv.appendChild(document.createElement("br"));
    anchor.appendChild(spDiv);
    anchor.appendChild(document.createElement("br"));
    //content moved into txt file
}


function showServiceDescription(serviceDescription, servicesDiv){
    let service = document.createElement("div");
    //service.classList.add("border");
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

function confirmRejectedOrder(event, rejectedOrderId){
    event.preventDefault();
    let rejectedOrderForm = document.createElement("form");
    rejectedOrderForm.name = "roForm";
    let input = document.createElement("input");
    input.name = "rejectedOrderId";
    input.value = rejectedOrderId;
    rejectedOrderForm.appendChild(input);
    makeCall("POST", "GetRejectedOrderToComplete", rejectedOrderForm,
        async function (req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        //TODO move returned rejected order values into "pendingOrder" and redirect to ConfirmationPage.html
                        break;
                    default:
                        document.getElementById("errormessage").textContent = message;
                }
            }
        }
    );
}