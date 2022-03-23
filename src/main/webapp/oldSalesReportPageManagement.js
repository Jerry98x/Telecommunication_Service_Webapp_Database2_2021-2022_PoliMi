// /**
//  *
//  */
//
// (function () {
//     const array0 = [];
//     const array1 = [];
//     const array2 = [];
//     const array3 = [];
//     const array4 = [];
//     const array5 = [];
//     const array6 = [];
//     const array7 = [];
//     const array8 = [];
//
//     window.addEventListener("load", () => {
//         if(sessionStorage.getItem("loggedEmployee") != null) {
//             let employee = JSON.parse(sessionStorage.getItem("loggedEmployee"));
//             let employeeInfo = document.createElement("h6");
//             employeeInfo.innerHTML = "Logged in as <b>" + employee.employeeId + "</b>";
//             document.getElementById("employee_login").appendChild(employeeInfo);
//
//             document.getElementById("anchor_logout").hidden = false;
//         }
//
//     });
//
//     window.addEventListener("load", () => {
//         makeCall("GET", "GetAggregatedData", null,
//             function (req) {
//                 if (req.readyState === XMLHttpRequest.DONE) {
//                     let message = req.responseText;
//                     switch (req.status) {
//                         case 200:
//                             let json = JSON.parse(message);
//                             let salesData = json[0];
//                             let UOAData = json[1];
//
//                             let pur_sp_div = document.getElementById("pur_package_list");
//                             let pur_sp_vp_div = document.getElementById("pur_package_valper_list");
//                             let sal_sp_div = document.getElementById("sal_package_list");
//                             let sal_sp_op_div = document.getElementById("sal_package_optprod_list");
//                             let avg_op_sp_div = document.getElementById("avg_optprod_package_list");
//                             let best_seller_div = document.getElementById("best_seller_optprod_list");
//
//                             let ins_users_div = document.getElementById("ins_users_list");
//                             let susp_ord_div = document.getElementById("susp_orders_list");
//                             let alerts_div = document.getElementById("alerts_list");
//
//
//                             salesData[0].forEach(x => {
//                                 array0[0] = x.servicePackageId;
//                                 array0[1] = x.totalPurchases;
//                             });
//                             salesData[1].forEach(x => {
//                                 array1[0] = x.servicePackageId;
//                                 array1[1] = x.validityPeriodId;
//                                 array1[2] = x.totalPurchases;
//                             });
//                             salesData[2].forEach(x => {
//                                 array2[0] = x.servicePackageId;
//                                 array2[1] = x.totalValue_euro;
//                             });
//                             salesData[3].forEach(x => {
//                                 array3[0] = x.servicePackageId;
//                                 array3[1] = x.totalValue_euro;
//                             });
//                             salesData[4].forEach(x => {
//                                 array4[0] = x.servicePackageId;
//                                 array4[1] = x.avgAmountOptionalProducts;
//                             });
//                             salesData[5].forEach(x => {
//                                 array5[0] = x.optionalProductId;
//                                 array5[1] = x.totalPurchases;
//                             });
//
//                             UOAData[0].forEach(x => {
//                                 array6[0] = x.userId;
//                                 array6[1] = x.username;
//                                 array6[2] = x.insolvent;
//                             });
//                             UOAData[1].forEach(x => {
//                                 array7[0] = x.orderId;
//                                 array7[1] = x.creationDate;
//                                 array7[2] = x.creationTime;
//                                 array7[3] = x.totalCost_euro;
//                                 array7[4] = x.valid;
//                                 array7[5] = x.userId;
//                                 array7[6] = x.servicePackageId;
//                                 array7[7] = x.servicePackageName;
//                             });
//                             UOAData[2].forEach(x => {
//                                 array8[0] = x.alertId;
//                                 array8[1] = x.userId;
//                                 array8[2] = x.username;
//                                 array8[3] = x.totalAmount_euro;
//                                 array8[4] = x.rejectionDate;
//                                 array8[5] = x.rejectionHour;
//                             });
//
//
//                             salesData[0].forEach(x => buildList(pur_sp_div, x, array0));
//                             salesData[1].forEach(x => buildList(pur_sp_vp_div, x, array1));
//                             salesData[2].forEach(x => buildList(sal_sp_div, x, array2));
//                             salesData[3].forEach(x => buildList(sal_sp_op_div, x, array3));
//                             salesData[4].forEach(x => buildList(avg_op_sp_div, x, array4));
//                             salesData[5].forEach(x => buildList(best_seller_div, x, array5));
//
//                             UOAData[0].forEach(x => buildList(ins_users_div, x, array6));
//                             UOAData[1].forEach(x => buildList(susp_ord_div, x, array7));
//                             UOAData[2].forEach(x => buildList(alerts_div, x, array8));
//
//                             break;
//                         case 401:
//                             window.location.href = "LandingPage.html";
//                             break;
//                         default:
//                             document.getElementById("errormessage").textContent += message;
//                             break;
//                     }
//                 }
//             }
//         );
//     });
//
//     document.getElementById("anchor_logout").addEventListener("click", () => {
//         makeCall("GET", "Logout", null,
//             function (req) {
//                 if(req.readyState === XMLHttpRequest.DONE) {
//                     // let message = req.responseText;
//                     if(req.status === 200) {
//                         document.getElementById("anchor_logout").hidden = true;
//                         window.location.href = "LandingPage.html";
//                     }
//                 }
//             }
//         );
//     });
//
// })();
//
//
// function buildList(div, x, array) {
//     let list_container = document.createElement("ul")
//     list_container.classList.add("list-group");
//     list_container.classList.add("list-group-horizontal");
//
//     let i = 0;
//
//
//     for (let prop in x) {
//         if (Object.prototype.hasOwnProperty.call(x, prop)) {
//             let li = document.createElement("li");
//             li.classList.add("list-group-item");
//             li.innerHTML = String(prop) + ": ";
//             li.innerHTML += array[i];
//             i++;
//             list_container.appendChild(li);
//         }
//     }
//
//     div.appendChild(list_container);
// }
