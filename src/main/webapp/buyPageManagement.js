/**
 *
 */

(function () {
    let sptb;
    let aops;
    let avps;
    window.addEventListener("load", () => {
        if(sessionStorage.getItem("loggedUser") != null) { // && sessionStorage.getItem("servicePackageIdToBuy") != null
            let user = JSON.parse(sessionStorage.getItem("loggedUser"));
            let userInfo = document.createElement("h6");
            userInfo.innerHTML = "Logged in as <b>" + user.username + "</b>";
            document.getElementById("user_login").appendChild(userInfo);

            document.getElementById("anchor_logout").hidden = false;
        }

        let servicePackageToBuyForm = document.createElement("form");
        servicePackageToBuyForm.name = "spToBuyForm";
        let input = document.createElement("input");
        input.name = "spIdToBuy"
        input.value = sessionStorage.getItem("servicePackageIdToBuy");
        servicePackageToBuyForm.appendChild(input);
        makeCall("POST", "GetServicePackageToBuy", servicePackageToBuyForm,
            async function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            sptb = JSON.parse(message);
                            aops = sptb.availableOptionalProducts;
                            avps = sptb.availableValidityPeriods;
                            await (aops != null && avps != null);
                            buildPage(sptb, aops, avps);
                            break;
                        case 400: // bad request
                            document.getElementById("errormessage").textContent = message;
                            break;
                        case 401: // unauthorized
                            document.getElementById("errormessage").textContent = message;
                            window.location.href = "LandingPage.html";
                            break;
                        case 500: // server error
                            document.getElementById("errormessage").textContent = message;
                            break;
                    }
                }
            }
        );

    })

    document.getElementById("anchor_logout").addEventListener("click", () => {
        makeCall("GET", "Logout", null,
            function (req) {
                if(req.readyState === XMLHttpRequest.DONE) {
                    // let message = req.responseText;
                    if(req.status === 200) {
                        document.getElementById("anchor_logout").hidden = true;
                        window.location.href = "LandingPage.html";
                    }
                }
            }
        );
    });

})();

function buildPage(sptb, aops, avps){
    document.getElementById("totalCost").innerHTML = "0";
    document.getElementById("packageName").innerHTML = "You chose the package: " + sptb.name;
    sptb.servicesDescriptions.forEach(service => showService(service));
    let chosenOptionalProducts = []
    aops.forEach(op => showOptionalProduct(op, chosenOptionalProducts));
    let vpName = "validityPeriods";
    let chosenValidityPeriod = [];
    avps.forEach(vp => showValidityPeriod(vp, vpName, chosenValidityPeriod));

    document.getElementById("confirmBtn").addEventListener("click",
        (event) => confirmRedirect(event, sptb, chosenOptionalProducts, chosenValidityPeriod));

}


function showService(service){
    let serviceP = document.createElement("p");
    serviceP.innerHTML = service;
    document.getElementById("servicesDiv").appendChild(serviceP);
}


function showOptionalProduct(optionalProduct, chosenOptionalProducts){
    let singleProductDiv = document.createElement("div");
    singleProductDiv.class = "checkbox";
    let opCheckbox = document.createElement("input");
    opCheckbox.type = "checkbox";
    opCheckbox.addEventListener("change",
        (event) => updateProductChoices(event, opCheckbox.checked, optionalProduct, chosenOptionalProducts));
    let opLabel = document.createElement("label");
    opLabel.innerHTML = optionalProduct.name + ": " + optionalProduct.monthlyFee_euro + "€/month";
    opLabel.appendChild(opCheckbox);
    singleProductDiv.appendChild(opLabel);
    document.getElementById("optionalProductsDiv").appendChild(singleProductDiv);
}

function updateProductChoices(event, checked, optionalProduct, chosenOptionalProducts){
    event.preventDefault();
    if(checked){
        chosenOptionalProducts[optionalProduct.optionalProductId] = optionalProduct;
    } else {
        chosenOptionalProducts[optionalProduct.optionalProductId] = null;
    }
    updateTotalCost(checked, optionalProduct.monthlyFee_euro);
}

function showValidityPeriod(validityPeriod, vpName, chosenValidityPeriod){
    let singleProductDiv = document.createElement("div");
    singleProductDiv.class = "checkbox";
    let vpRadioBtn = document.createElement("input");
    vpRadioBtn.type = "radio";
    vpRadioBtn.name = vpName;
    vpRadioBtn.addEventListener("change",
        (event) => updatePeriodChoice(event, validityPeriod, chosenValidityPeriod));
    let vpLabel = document.createElement("label");
    vpLabel.innerHTML = validityPeriod.monthsOfValidity + " months at " + validityPeriod.monthlyFee_euro + "€/month";
    vpLabel.appendChild(vpRadioBtn);
    singleProductDiv.appendChild(vpLabel);
    document.getElementById("validityPeriodsDiv").appendChild(singleProductDiv);
}

function updatePeriodChoice(event, validityPeriod, chosenValidityPeriod){
    event.preventDefault();
    document.getElementById("errormessage").innerHTML = "";
    document.getElementById("confirmBtn").disabled = false;
    let oldFee
    if(chosenValidityPeriod[0] != null) {
        oldFee = chosenValidityPeriod[0].monthlyFee_euro;
    } else {
        oldFee = 0;
    }
    chosenValidityPeriod[0] = validityPeriod;
    updateTotalCost(true, validityPeriod.monthlyFee_euro - oldFee);
}

function updateTotalCost(adding, quantity){
    let totCost = document.getElementById("totalCost");
    if(adding){
        totCost.innerHTML = parseFloat(totCost.innerHTML) + quantity;
    } else {
        totCost.innerHTML = parseFloat(totCost.innerHTML) - quantity;
    }
}

function confirmRedirect(event, servicePackageToBuy, chosenOptionalProducts, chosenValidityPeriod){
    event.preventDefault();
    if(chosenValidityPeriod[0] != null){
        let pendingOrder = {};
        pendingOrder.orderId = null;
        pendingOrder.totalCost = parseFloat(document.getElementById("totalCost").innerHTML);
        pendingOrder.startDate = null;
        pendingOrder.valid = null;
        if(sessionStorage.getItem("loggedUser") != null) {
            pendingOrder.userId = JSON.parse(sessionStorage.getItem("loggedUser")).userId;
        } else {
            pendingOrder.userId = -1;
        }
        pendingOrder.servicePackageId = servicePackageToBuy.servicePackageId;
        pendingOrder.servicePackageName = servicePackageToBuy.name;
        pendingOrder.servicesDescriptions = servicePackageToBuy.servicesDescriptions;
        pendingOrder.chosenValidityPeriod = chosenValidityPeriod[0];
        pendingOrder.chosenOptionalProducts = chosenOptionalProducts;
        sessionStorage.setItem("pendingOrder",JSON.stringify(pendingOrder));
        sessionStorage.removeItem("servicePackageIdToBuy");
        window.location.href = "ConfirmationPage.html";
    } else {
        document.getElementById("errormessage").innerHTML = "You have to choose validity period";
    }
}

