/**
 *
 */

(function (){
    if(sessionStorage.getItem("loggedUser") != null) {
        let user = JSON.parse(sessionStorage.getItem("loggedUser"));
        document.getElementById("user_info").innerHTML = "Logged in as <b>" + user.username + "</b>";
        document.getElementById("anchor_logout").hidden = false;
        if (user.insolvent && document.getElementById("rejected_orders").childElementCount === 0) {
            let userIdForm = createUserIdForm(user.userId);
            window.addEventListener("load", () => {
                makeCall("POST", "GetRejectedOrders", userIdForm,
                    async function (req) {
                        if (req.readyState === XMLHttpRequest.DONE) {
                            let message = req.responseText;
                            switch (req.status) {
                                case 200:
                                    let anchor = document.createDocumentFragment();
                                    let rejectedOrders = JSON.parse(message);
                                    let roText = document.createElement("h6");
                                    roText.innerHTML = "Rejected orders";
                                    anchor.appendChild(roText)
                                    rejectedOrders.forEach(ro => showRejectedOrder(ro, anchor));
                                    document.getElementById("rejected_orders").appendChild(anchor);
                                    document.getElementById("rejected_orders").hidden = false;
                                    break;
                                default:
                                    document.getElementById("errormessage").textContent += message;
                                    break;
                            }
                        }
                    }
                );
            });
        }
    }

    if(sessionStorage.getItem("loggedUserId") != null) {
        let user;
        let userIdForm = createUserIdForm(sessionStorage.getItem("loggedUserId"));
        window.addEventListener("load", () => {
            makeCall("POST", "GetLoggedUserInfo", userIdForm,
                async function (req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        let message = req.responseText;
                        switch (req.status) {
                            case 200:
                                user = JSON.parse(message);
                                document.getElementById("user_info").innerHTML = "Logged in as <b>" + user.username + "</b>";
                                document.getElementById("anchor_logout").hidden = false;

                                sessionStorage.setItem("loggedUser", JSON.stringify(user));
                                sessionStorage.removeItem("loggedUserId");
                                await (sessionStorage.getItem("loggedUser") != null);
                                if (user.insolvent && document.getElementById("rejected_orders").childElementCount === 0) {
                                    let userIdForm = createUserIdForm(user.userId);
                                    makeCall("POST", "GetRejectedOrders", userIdForm,
                                        async function (req) {
                                            if (req.readyState === XMLHttpRequest.DONE) {
                                                let message = req.responseText;
                                                switch (req.status) {
                                                    case 200:
                                                        let anchor = document.createDocumentFragment();
                                                        let rejectedOrders = JSON.parse(message);
                                                        let roText = document.createElement("h6");
                                                        roText.innerHTML = "Rejected orders";
                                                        anchor.appendChild(roText)
                                                        rejectedOrders.forEach(ro => showRejectedOrder(ro, anchor));
                                                        document.getElementById("rejected_orders").appendChild(anchor);
                                                        document.getElementById("rejected_orders").hidden = false;
                                                        break;
                                                    default:
                                                        document.getElementById("errormessage").textContent += message;
                                                        break;
                                                }
                                            }
                                        }
                                    );
                                }
                                break;
                            default:
                                document.getElementById("errormessage").textContent += message;
                                break;
                        }
                    }
                }
            );

        })
    }


    document.getElementById("anchor_logout").addEventListener("click", () => {
        makeCall("GET", "Logout", null,
            function (req) {
                if(req.readyState === XMLHttpRequest.DONE) {
                    // let message = req.responseText;
                    if(req.status === 200) {
                        document.getElementById("anchor_logout").hidden = true;
                        sessionStorage.removeItem("loggedUserId");
                        sessionStorage.removeItem("loggedUser");
                        sessionStorage.removeItem("pendingOrder");
                        window.location.href = "LandingPage.html";
                    }
                }
            }
        );
    });

    window.addEventListener("load", () => {
        makeCall("GET", "GetServicePackages", null,
            function (req) {
            if(req.readyState === XMLHttpRequest.DONE) {
                let message = req.responseText;
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
            });
    });

})();

function createUserIdForm(userId){
    let userIdForm = document.createElement("form");
    userIdForm.name = "userIdForm";
    let input = document.createElement("input");
    input.name = "userId"
    input.value = userId;
    userIdForm.appendChild(input);
    return userIdForm;
}

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
    roLink.innerHTML = "Order nr. " + rejectedOrder.orderId + " of cost " + rejectedOrder.totalCost_euro + "€/month\n";
    roLink.addEventListener("click", (event) => confirmRejectedOrder(event, rejectedOrder.orderId));
    anchor.appendChild(roLink);
    anchor.appendChild(document.createElement("br"));
}

function confirmRejectedOrder(event, rejectedOrderId){
    event.preventDefault();
    sessionStorage.setItem("rejectedOrderId", rejectedOrderId);
    window.location.href = "ConfirmationPage.html";
}