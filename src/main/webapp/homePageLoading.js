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
        makeCall("GET", "GuestHomePageLoading", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            var hpc = JSON.parse(message);

                            // hpc.servicePackages.forEach(showSP(servicePakages))

                            hpc.servicePackages.forEach(showSP)
                            //showSP(hpc.servicePackages);
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

function showSP(servicePackage){

    if('content' in document.createElement('template')) {

        //Instantiating page elements for service package
        var body = document.querySelector("body");

        var container = document.querySelector('#container');
        var outerTemplate = document.querySelector('#spRow');
        //var spName = document.querySelector('#sp_name');
        //var innerTemplate = document.querySelector('#type_list');

        //Cloning the new service packages
        //var cloneContainer = container.content.cloneNode(true);
        var cloneOutTemp = outerTemplate.content.cloneNode(true);
        //var cloneSpName = spName.content.cloneNode(true);
        //var cloneInTemp = innerTemplate.content.cloneNode(true);

        var h5Name = cloneOutTemp.querySelector("#sp_name");
        h5Name.textContent = servicePackage.name;

        var divDescription = cloneOutTemp.querySelector("#descriptionInner");
        divDescription.textContent = servicePackage.servicesDescriptions;

        body.appendChild(cloneOutTemp);



        // var li = cloneInTemp.querySelectorAll("li");
        // servicePackage.servicesDescriptions.forEach(e => {
        //      li.textContent = e;
        // });

        //TODO: generare dinamicamente ID HTML

    }


    // var elem, i, row, destcell, datecell, linkcell, anchor;
    // var list, listobj;
    // this.getElementById("id_spBody").innerHTML = ""; // empty the table body
    // // build updated list
    // var self = this;
    // servicePackages.forEach(function(sp) { // self visible here, not this
    //     row = document.createElement("tr");
    //     destcell = document.createElement("td");
    //     destcell.textContent = mission.destination;
    //     row.appendChild(destcell);
    //     datecell = document.createElement("td");
    //     datecell.textContent = mission.startDate;
    //     row.appendChild(datecell);
    //     linkcell = document.createElement("td");
    //     anchor = document.createElement("a");
    //     linkcell.appendChild(anchor);
    //     linkText = document.createTextNode("Show");
    //     anchor.appendChild(linkText);
    //     //anchor.missionid = mission.id; // make list item clickable
    //     anchor.setAttribute('missionid', mission.id); // set a custom HTML attribute
    //     anchor.addEventListener("click", (e) => {
    //         // dependency via module parameter
    //         missionDetails.show(e.target.getAttribute("missionid")); // the list must know the details container
    //     }, false);
    //     anchor.href = "#";
    //     row.appendChild(linkcell);
    //     self.listcontainerbody.appendChild(row);
    // });
    this.listcontainer.style.visibility = "visible";
}