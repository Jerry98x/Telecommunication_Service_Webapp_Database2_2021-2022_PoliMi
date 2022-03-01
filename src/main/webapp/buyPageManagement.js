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
    let sDiv = document.createElement("div");
    sDiv.innerHTML = service;
    anchor.appendChild(sDiv);
}

function showOptionalProduct(optionalProduct, anchor){
    let opDiv = document.createElement("div");
    let opCheckbox = document.createElement("input");
    opCheckbox.id = optionalProduct.optionalProductId;
    opCheckbox.type = "checkbox";
    let opName = document.createElement("div");
    opName.innerHTML = optionalProduct.name;
    let opMonthlyFee = document.createElement("div");
    opMonthlyFee.innerHTML = "It costs " + optionalProduct.monthlyFee_euro + "€/month";
    opDiv.appendChild(opCheckbox);
    opDiv.appendChild(opName);
    opDiv.appendChild(opMonthlyFee);
    anchor.appendChild(opDiv);
}

function showValidityPeriod(validityPeriod, anchor){
    let vpDiv = document.createElement("div");
    let vpCheckbox = document.createElement("input");
    vpCheckbox.id = validityPeriod.validityPeriodId;
    vpCheckbox.type = "checkbox";
    let vpMonthsOfValidity = document.createElement("div");
    vpMonthsOfValidity.innerHTML = "Months of validity: " + validityPeriod.monthsOfValidity;
    let vpMonthlyFee = document.createElement("div");
    vpMonthlyFee.innerHTML = "It costs " + validityPeriod.monthlyFee_euro + "€/month";
    vpDiv.appendChild(vpCheckbox);
    vpDiv.appendChild(vpMonthsOfValidity);
    vpDiv.appendChild(vpMonthlyFee);
    anchor.appendChild(vpDiv);
}



function confirmRedirect(event){
    event.preventDefault();
    window.location.href = "ConfirmationPage.html";
}

(function () {
    window.addEventListener("load", () => {
        var anchor = document.createDocumentFragment();
        let sptb = JSON.parse(sessionStorage.getItem('servicePackageToBuy'));
        let aops = JSON.parse(sessionStorage.getItem('availableOptionalProducts'));
        let avps = JSON.parse(sessionStorage.getItem('availableValidityPeriods'));

        let title = document.createElement("div");
        title.innerHTML = "You chose the package: " + sptb.name;
        anchor.appendChild(title);
        sptb.servicesDescriptions.forEach(service => showService(service, anchor));
        aops.forEach(product => showOptionalProduct(product, anchor));
        avps.forEach(vp => showValidityPeriod(vp, anchor));

        let confirmBtn = document.createElement("button");
        confirmBtn.addEventListener('click', (event) => confirmRedirect(event));
        confirmBtn.innerHTML = "Confirm";
        anchor.appendChild(confirmBtn);

        document.getElementById("main").appendChild(anchor);

    });

})();

