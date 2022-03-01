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

/*
//TODO move
function notLoggedRedirect(event, id){
    event.preventDefault();
    sessionStorage.setItem('pendingOrder',id);
    window.location.href = "LandingPage.html";
}

 */

function confirmRedirect(event){
    event.preventDefault();
    //window.location.href = "ConfirmationPage.html";
}

(function () {
    window.addEventListener("load", () => {
        var anchor = document.createDocumentFragment();
        let sptb = JSON.parse(sessionStorage.getItem('servicePackageToBuy'));
        let aops = JSON.parse(sessionStorage.getItem('availableOptionalProducts'));
        let avps = JSON.parse(sessionStorage.getItem('availableValidityPeriods'));


        /*
        //TODO move
        if(sessionStorage.getItem('logged_user') == null){
            let loginBtn = document.createElement("button");
            loginBtn.innerHTML = "Login";
            loginBtn.addEventListener('click',(event) => notLoggedRedirect(event, sptb.servicePackageId));
            anchor.appendChild(loginBtn);
            let signUpBtn = document.createElement("button");
            signUpBtn.innerHTML = "Sign up";
            signUpBtn.addEventListener('click', (event) => notLoggedRedirect(event, sptb.servicePackageId));
            anchor.appendChild(signUpBtn);
        }

         */

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

        /*
        //TODO move
        let successBuyBtn = document.createElement("button");
        successBuyBtn.innerHTML = "Success Buy";
        //TODO create valid order and redirect to homePage
        let failBuyBtn = document.createElement("button");
        failBuyBtn.innerHTML = "Fail Buy";
        //TODO create invalid order and redirect to homePage

        //TODO check logged
        //TODO reset sptb,aops,avps relative sessionStorage vars

        anchor.appendChild(successBuyBtn);
        anchor.appendChild(failBuyBtn);


         */


        document.getElementById("main").appendChild(anchor);

    });

})();

