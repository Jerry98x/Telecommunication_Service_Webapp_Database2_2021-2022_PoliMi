/**
 *
 */
function notLoggedRedirect(event, id){
    event.preventDefault();
    sessionStorage.setItem('pendingOrder', id);
    window.location.href = "LandingPage.html";
}

function paymentRedirect(event, isSuccessful){
    event.preventDefault();
    if(sessionStorage.getItem('loggedUser') != null) {
        if (isSuccessful) {

        } else {

        }
        sessionStorage.setItem('pendingOrder', null);
        //TODO reset sptb,aops,avps relative sessionStorage vars
    } else {
        document.getElementById('errormessage').textContent = "You need to login before making a purchase";
        return;
    }
}

(function () {
    window.addEventListener("load", () => {
        var anchor = document.createDocumentFragment();

        let sptb = JSON.parse(sessionStorage.getItem('servicePackageToBuy'));
        let aops = JSON.parse(sessionStorage.getItem('availableOptionalProducts'));
        let avps = JSON.parse(sessionStorage.getItem('availableValidityPeriods'));


        if(sessionStorage.getItem('loggedUser') == null){
            let landingDiv = document.createElement("div");

            let loginBtn = document.createElement("button");
            loginBtn.innerHTML = "Login";
            loginBtn.addEventListener('click',(event) => notLoggedRedirect(event, sptb.servicePackageId));

            let signUpBtn = document.createElement("button");
            signUpBtn.innerHTML = "Sign up";
            signUpBtn.addEventListener('click', (event) => notLoggedRedirect(event, sptb.servicePackageId));

            landingDiv.appendChild(loginBtn);
            landingDiv.appendChild(signUpBtn);

            anchor.appendChild(landingDiv);
        }

        let recapDiv = document.createElement("div");

        let paymentDiv = document.createElement("div");

        let successBuyBtn = document.createElement("button");
        successBuyBtn.innerHTML = "Success Buy";
        //TODO create valid order and redirect to homePage
        successBuyBtn.addEventListener('click', (event) => paymentRedirect(event, true));
        let failBuyBtn = document.createElement("button");
        failBuyBtn.innerHTML = "Fail Buy";
        //TODO create invalid order and redirect to homePage
        failBuyBtn.addEventListener('click', (event) => paymentRedirect(event, false));


        paymentDiv.appendChild(successBuyBtn);
        paymentDiv.appendChild(failBuyBtn);

        anchor.appendChild(paymentDiv);

        document.getElementById("main").appendChild(anchor);

    });

})();