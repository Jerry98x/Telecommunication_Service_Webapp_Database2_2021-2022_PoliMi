/**
 *
 */
(function () {
    window.addEventListener("load", () => {
        makeCall("GET", "GetServicePackages", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            var hpc = JSON.parse(message);
                            showSP(hpc.servicePackages);
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


    function showSP(servicePackages){
        var elem, i, row, destcell, datecell, linkcell, anchor;
        var list, listobj;
        this.getElementById("id_spBody").innerHTML = ""; // empty the table body
        // build updated list
        var self = this;
        servicePackages.forEach(function(sp) { // self visible here, not this
            row = document.createElement("tr");
            destcell = document.createElement("td");
            destcell.textContent = mission.destination;
            row.appendChild(destcell);
            datecell = document.createElement("td");
            datecell.textContent = mission.startDate;
            row.appendChild(datecell);
            linkcell = document.createElement("td");
            anchor = document.createElement("a");
            linkcell.appendChild(anchor);
            linkText = document.createTextNode("Show");
            anchor.appendChild(linkText);
            //anchor.missionid = mission.id; // make list item clickable
            anchor.setAttribute('missionid', mission.id); // set a custom HTML attribute
            anchor.addEventListener("click", (e) => {
                // dependency via module parameter
                missionDetails.show(e.target.getAttribute("missionid")); // the list must know the details container
            }, false);
            anchor.href = "#";
            row.appendChild(linkcell);
            self.listcontainerbody.appendChild(row);
        });
        this.listcontainer.style.visibility = "visible";
    }
})
