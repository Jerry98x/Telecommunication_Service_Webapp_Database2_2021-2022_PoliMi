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
    let optionalProductDiv = document.createElement("div");
    let opCheckbox = document.createElement("input");
    opCheckbox.type = "checkbox";
    let optionalProductName = document.createElement("div");
    optionalProductName.innerHTML = optionalProduct.name;
    let optionalProductMonthlyFee = document.createElement("div");
    optionalProductMonthlyFee.innerHTML = "It costs " + optionalProduct.monthlyFee_euro + "€/month";
    optionalProductDiv.appendChild(opCheckbox);
    optionalProductDiv.appendChild(optionalProductName);
    optionalProductDiv.appendChild(optionalProductMonthlyFee);
    anchor.appendChild(optionalProductDiv);
}

(function () {
    window.addEventListener("load", () => {

        let sptb = JSON.parse(sessionStorage.getItem('servicePackageToBuy'));
        let aops = JSON.parse(sessionStorage.getItem('availableOptionalProducts'));

        var anchor = document.createDocumentFragment();
        let title = document.createElement("div");
        title.innerHTML = sptb.name;
        anchor.appendChild(title);
        sptb.servicesDescriptions.forEach(service => showService(service, anchor));
        aops.forEach(product => showOptionalProduct(product, anchor));
        //TODO show validity periods

        document.getElementById("main").appendChild(anchor);

    });

})();

