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
        document.getElementById("totalCost").innerHTML = "0";
        let sptb = JSON.parse(sessionStorage.getItem('servicePackageToBuy'));
        let aops = JSON.parse(sessionStorage.getItem('availableOptionalProducts'));
        let avps = JSON.parse(sessionStorage.getItem('availableValidityPeriods'));

        document.getElementById("packageName").innerHTML = "You chose the package: " + sptb.name;
        sptb.servicesDescriptions.forEach(service => showService(service));
        let chosenOptionalProducts = []
        aops.forEach(op => showOptionalProduct(op, chosenOptionalProducts));
        let vpName = "validityPeriods";
        let chosenValidityPeriod = [];
        avps.forEach(vp => showValidityPeriod(vp, vpName, chosenValidityPeriod));

        document.getElementById("confirmBtn").addEventListener('click',
            (event) => confirmRedirect(event, chosenOptionalProducts, chosenValidityPeriod));

    });

})();


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
    opCheckbox.addEventListener('change',
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
    vpRadioBtn.addEventListener('change',
        (event) => updatePeriodChoice(event, validityPeriod, chosenValidityPeriod));
    let vpLabel = document.createElement("label");
    vpLabel.innerHTML = validityPeriod.monthsOfValidity + " months at " + validityPeriod.monthlyFee_euro + "€/month";
    vpLabel.appendChild(vpRadioBtn);
    singleProductDiv.appendChild(vpLabel);
    document.getElementById("validityPeriodsDiv").appendChild(singleProductDiv);
}

function updatePeriodChoice(event, validityPeriod, chosenValidityPeriod){
    event.preventDefault();
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

function confirmRedirect(event, chosenOptionalProducts, chosenValidityPeriod){
    event.preventDefault();
    sessionStorage.setItem('chosenOptionalProducts', JSON.stringify(chosenOptionalProducts));
    sessionStorage.setItem('chosenValidityPeriod', JSON.stringify(chosenValidityPeriod));
    sessionStorage.setItem('totalCost', document.getElementById("totalCost").innerHTML);
    window.location.href = "ConfirmationPage.html";
}

