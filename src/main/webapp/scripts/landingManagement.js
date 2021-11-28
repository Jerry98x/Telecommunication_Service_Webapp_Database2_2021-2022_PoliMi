/**
 * Landing page management
 */

(function() {
    let lpb = document.getElementById("loginPageButton");
    if(lpb) {
        lpb.addEventListener('click', (e) => {
            window.location.assign("WEB-INF/LoginPage.html");
        });
    }

    let rpb = document.getElementById("registerPageButton");
    if(rpb) {
        rpb.addEventListener('click', (e) => {
            window.location.href = "WEB-INF/RegistrationPage.html";
        });
    }
})();

// function redirectLogin() {
//     let lpb = document.getElementById("loginPageButton");
//     if(lpb) {
//         lpb.addEventListener('click', (e) => {
//             window.location.assign("WEB-INF/LoginPage.html");
//         });
//     }
// }