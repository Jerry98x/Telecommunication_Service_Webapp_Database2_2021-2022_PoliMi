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
                            //No need of JSON.parse because just null check
                            if(sessionStorage.getItem('logged_user') != null){
                                //show user info
                                //No need of JSON.parse because just null check
                                if(sessionStorage.getItem('rejectedOrders') != null){
                                    //show rejected orders
                                }
                            }
                            let sps = JSON.parse(message);

                            let anchor = document.createDocumentFragment();
                            let title = document.createElement("div");
                            title.innerHTML = "Available Service Packages";
                            anchor.appendChild(title);
                            sps.forEach(sp => showServicePackage(sp, anchor));

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



function servicePackageRedirect(spId){
    //sessionStorage.setItem('servicePackageIdToBuy', servicePackage.id);
        let form = document.createElement("form");
        form.name = "spToBuyForm";
        let input = document.createElement("input");
        input.name = "spIdToBuy"
        input.value = spId;
        form.appendChild(input);
        makeCall("POST", "BuyPageLoading", form,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            let sptb = JSON.parse(message);
                            sessionStorage.setItem('servicePackageToBuy',JSON.stringify(sptb));
                            console.log(JSON.parse(sessionStorage.getItem('servicePackageToBuy')));
                            sessionStorage.setItem('availableOptionalProducts', JSON.stringify(sptb.availableOptionalProducts));
                            console.log(JSON.parse(sessionStorage.getItem('availableOptionalProducts')));

                            /*
                            var anchor = document.createDocumentFragment();
                            let title = document.createElement("div");
                            title.innerHTML = sptb.name;
                            anchor.appendChild(title);
                            sptb.services.forEach(service => showService(service, anchor));
                            sptb.products.forEach(product => showOptionalProduct(product, anchor));

                            document.getElementById("main").appendChild(anchor);
                            */

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
    let servicePackageDiv = document.createElement("div");
    //servicePackageDiv.id = servicePackage.id;
    let packageName = document.createElement("a");
    packageName.id = servicePackage.servicePackageId;
    packageName.innerHTML = servicePackage.name;
    packageName.href = "BuyPage.html";
    packageName.addEventListener('click', (event) => {servicePackageRedirect(packageName.id);});
    let servicesDiv = document.createElement("div");
    servicePackage.servicesDescriptions.forEach(sd => showServiceDescription(sd, servicesDiv));

    let availableOptionalProductsDiv = document.createElement("div");
    servicePackage.availableOptionalProducts.forEach(aop => showOptionalProductDescription(aop, availableOptionalProductsDiv));


    servicePackageDiv.appendChild(packageName);
    servicePackageDiv.appendChild(servicesDiv);
    servicePackageDiv.appendChild(availableOptionalProductsDiv);

    anchor.appendChild(servicePackageDiv);
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