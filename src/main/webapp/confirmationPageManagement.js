/**
 *
 */
function notLoggedRedirect(event, id){
    event.preventDefault();
    sessionStorage.setItem('pendingOrder', id);
    window.location.href = "LandingPage.html";
}

function paymentRedirect(event, isSuccessful) {
    event.preventDefault();
    //TODO post call passing an order
    //needed: orderId=null, dates(TBD), totalCost, valid=isSuccessful, userId, sptb.servicePackageId, cops(forEach new ClientOptProd), cvp.validityPeriodId
    sessionStorage.removeItem('pendingOrder');
    //TODO reset sptb,aops,avps,cops,cvp,totalCost relative sessionStorage vars
    //window.location.href = "LandingPage.html";
}

(function () {
    window.addEventListener("load", () => {
        let sptb = JSON.parse(sessionStorage.getItem('servicePackageToBuy'));
        let cops = JSON.parse(sessionStorage.getItem('chosenOptionalProducts'));
        let cvp = JSON.parse(sessionStorage.getItem('chosenValidityPeriod'))[0];
        let tot = sessionStorage.getItem("totalCost");

        if(sessionStorage.getItem('loggedUser') == null){
            document.getElementById("errormessage").innerHTML = "You need to be logged in to complete a payment";
            //disable payment buttons
            document.getElementById("successfulPaymentBtn").disabled = true;
            document.getElementById("failingPaymentBtn").disabled = true;
            //add event listener to login/signup buttons
            document.getElementById("loginBtn").addEventListener('click',(event) => notLoggedRedirect(event, sptb.servicePackageId));
            document.getElementById("signUpBtn").addEventListener('click', (event) => notLoggedRedirect(event, sptb.servicePackageId));
            //show login/signup buttons
            document.getElementById("loginBtn").hidden = false;
            document.getElementById("signUpBtn").hidden = false;
        }

        document.getElementById("packageName").innerHTML = sptb.name;
        sptb.servicesDescriptions.forEach(sd => showServiceDescription(sd));
        if(cops != null && cops.length > 0){
            document.getElementById("optionalProductsDiv").hidden = false;
            cops.forEach(cop => showOptionalProduct(cop));
        }
        document.getElementById("validityPeriodDiv").innerHTML = cvp.monthsOfValidity + " months at " + cvp.monthlyFee_euro + "€/month";
        document.getElementById("totalCost").innerHTML = tot;



        //TODO create valid order and redirect to homePage
        document.getElementById("successfulPaymentBtn").addEventListener('click', (event) => paymentRedirect(event, true));
        //TODO create invalid order and redirect to homePage
        document.getElementById("failingPaymentBtn").addEventListener('click', (event) => paymentRedirect(event, false));
    });

})();

function showServiceDescription(serviceDescription){
    let service = document.createElement("p");
    service.innerHTML = serviceDescription;
    document.getElementById("servicesDiv").appendChild(service);
}

function showOptionalProduct(optionalProduct){
    if(optionalProduct != null) {
        let singleProductDiv = document.createElement("div");
        let opLabel = document.createElement("label");
        opLabel.innerHTML = optionalProduct.name + ": " + optionalProduct.monthlyFee_euro + "€/month";
        singleProductDiv.appendChild(opLabel);
        document.getElementById("optionalProductsDiv").appendChild(singleProductDiv);
    }
}