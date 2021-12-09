function autocomplete(inp, arr) {
    /*the autocomplete function takes two arguments,
    the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", function(e) {
        var a, b, i, val = this.value;
        /*close any already open lists of autocompleted values*/
        closeAllLists();
        if (!val) { return false;}
        currentFocus = -1;
        /*create a DIV element that will contain the items (values):*/
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        /*append the DIV element as a child of the autocomplete container:*/
        this.parentNode.appendChild(a);
        /*for each item in the array...*/
        for (i = 0; i < arr.length; i++) {
            /*check if the item starts with the same letters as the text field value:*/
            if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                /*create a DIV element for each matching element:*/
                b = document.createElement("DIV");
                /*make the matching letters bold:*/
                b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
                /*insert a input field that will hold the current array item's value:*/
                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                /*execute a function when someone clicks on the item value (DIV element):*/
                b.addEventListener("click", function(e) {
                    /*insert the value for the autocomplete text field:*/
                    inp.value = this.getElementsByTagName("input")[0].value;
                    /*close the list of autocompleted values,
                    (or any other open lists of autocompleted values:*/
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });
    /*execute a function presses a key on the keyboard:*/
    inp.addEventListener("keydown", function(e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            /*If the arrow DOWN key is pressed,
            increase the currentFocus variable:*/
            currentFocus++;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 38) { //up
            /*If the arrow UP key is pressed,
            decrease the currentFocus variable:*/
            currentFocus--;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 13) {
            /*If the ENTER key is pressed, prevent the form from being submitted,*/
            e.preventDefault();
            if (currentFocus > -1) {
                /*and simulate a click on the "active" item:*/
                if (x) x[currentFocus].click();
            }
        }
    });
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}

var countries = ["Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua &amp; Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia &amp; Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central Arfrican Republic","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cuba","Curacao","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kiribati","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauro","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","North Korea","Norway","Oman","Pakistan","Palau","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Pierre &amp; Miquelon","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Korea","South Sudan","Spain","Sri Lanka","St Kitts &amp; Nevis","St Lucia","St Vincent","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Trinidad &amp; Tobago","Tunisia","Turkey","Turkmenistan","Turks &amp; Caicos","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States of America","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"];

const loadUserAdminBookings = async () => {

    //load the user bookings as JSON from /user/getMyBookings
    let response = await fetch('/user/getMyBookings');
    response = await response.json();

    displayUserBookings(response);
}

const displayEmpty = () => {
    const cardTitleDiv = document.getElementById("card-title-div");

    const emptyDisplay = document.createElement("h5");
    emptyDisplay.className = "card-title";
    emptyDisplay.innerText = "You have no bookings";
    cardTitleDiv.append(emptyDisplay)
}

const displayUserBookings = (jsonResponse) => {

    if(document.body.contains(document.getElementById("mainDiv"))){
        document.getElementById("mainDiv").remove();
    }

    const div1 = document.createElement("div");
    div1.setAttribute('class', "card col d-flex justify-content-center container-fluid mt-100 deskListBox");
    div1.setAttribute("id", "mainDiv");

    const div2 = document.createElement("div");
    div2.setAttribute('class', "card-body row");
    div2.id = "card-title-div";

    //loop through all objects in the json response and create nodes for each
    //then append these nodes to the outer div

    div1.append(div2);

    document.body.append(div1);

    if(jsonResponse.length === 0){
        displayEmpty();
        return;
    }

    for(let i = 0; i < jsonResponse.length; i++) {

        const div3 = document.createElement("div");
        div3.setAttribute('class', "desk-container col-12 col-md-6 col-lg-4 col-xl-2");
        div3.setAttribute("id", "bookings-container");

        const div4 = document.createElement("div");
        div4.setAttribute('class', "card deskCardCancel");
        div4.setAttribute("id", "desk-card-"+jsonResponse[i].bookingId);

        const div5 = document.createElement("div");
        div5.setAttribute('class', "card-header deskCardCancelHeader");
        div5.setAttribute('id', "cancel-card-"+jsonResponse[i].bookingId);

        const heading = document.createElement("h5");
        heading.setAttribute('class', "card-text");
        const deskTextNode = document.createTextNode("Desk " + jsonResponse[i].deskId);
        heading.append(deskTextNode);

        const cancelButton = document.createElement("button");
        cancelButton.setAttribute("class", "bookDeskButton btn btn-warning");
        cancelButton.setAttribute("id", "cancelBookingId-" + jsonResponse[i].bookingId);
        cancelButton.type = "submit";

        let bookingDate = "\"" + jsonResponse[i].date + "\"";
        let bookingId = jsonResponse[i].bookingId;
        let deskId = jsonResponse[i].deskId;

        cancelButton.setAttribute("onclick", "cancelBooking(" + bookingId + ", " + deskId + ", " + bookingDate + ")");
        console.log(jsonResponse[i].date);
        const cancelButtonText = document.createTextNode("Cancel");
        cancelButton.append(cancelButtonText);

        const datePara = document.createElement("div");
        datePara.className = "card-text row bookingCardDate";

        const dateTag = document.createElement("span");
        const officeTag = document.createElement("span");

        dateTag.className = "deskTagsTwo col-6"
        officeTag.className = "deskTagsTwo col-6"

        datePara.append(dateTag);
        datePara.append(officeTag);

        // Todo Add values from DB
        dateTag.innerHTML = jsonResponse[i].date;
        officeTag.innerHTML = jsonResponse[i].officeLocation;

        // const dateTextNode = document.createTextNode(jsonResponse[i].date);
        // datePara.append(dateTextNode);

        const deskCardExpand = document.createElement("div");
        deskCardExpand.className = "card-title deskCardExpand";

        //create picture dropdown
        const displayPictureButton = document.createElement("button");
        displayPictureButton.setAttribute("class", "btn btn-link");
        displayPictureButton.setAttribute("data-toggle", "collapse");
        displayPictureButton.setAttribute("data-target", "#deskImg" + jsonResponse[i].deskId);
        displayPictureButton.setAttribute("aria-expanded", "true");
        displayPictureButton.setAttribute("aria-controls", "deskImg");

        const svgDeskPic = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        svgDeskPic.setAttribute("width", "16");
        svgDeskPic.setAttribute("height", "16");
        svgDeskPic.setAttribute("fill", "currentColor");
        svgDeskPic.setAttribute("class", "bi bi-caret-down-fill");
        svgDeskPic.setAttribute("viewBox", "0 0 16 16");

        const svgPath = document.createElementNS("http://www.w3.org/2000/svg", "path");
        svgPath.setAttribute("d", "M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z");


        const imgDiv = document.createElement("div");
        imgDiv.setAttribute("id", "deskImg"+ jsonResponse[i].deskId);
        imgDiv.setAttribute("class", "collapse");
        imgDiv.setAttribute("aria-labelledby", "deskCardExpand");
        imgDiv.setAttribute("data-parent", ".deskListBox");

        const imgDiv2 = document.createElement("div");
        imgDiv2.setAttribute("class", "card-body deskImg");

        const img = document.createElement("img");
        img.setAttribute("src", "/images/" + jsonResponse[i].deskImageName);
        img.setAttribute("class", "card-img-top");
        img.setAttribute("alt", "");

        imgDiv2.append(img);
        imgDiv.append(imgDiv2);

        svgDeskPic.append(svgPath);
        displayPictureButton.append(svgDeskPic);
        deskCardExpand.append(displayPictureButton);

        div5.append(heading);
        div5.append(datePara);
        div5.append(cancelButton)
        div5.append(deskCardExpand);


        div4.append(div5);
        div4.append(imgDiv);

        div3.append(div4);
        div2.append(div3);

    }


    //                  template below

// <div className="card col d-flex justify-content-center container-fluid mt-100 deskListBox">
//     <div className="card-body">
//         <div className="row row-cols-1 row-cols-sm-2 row-cols-md-4">
//             <div className="col">
//                 <div className="card deskCardCancel">
//                     <div className="card-header deskCardExpand">
//                         <h5 className="card-text">Desk 7</h5>
//                         <p>08/12/2021</p>
//                         <button className="bookDeskButton btn btn-warning my-2 my-sm-0" type="submit">Cancel</button>
//                         <button className="btn btn-link" data-toggle="collapse" data-target="#deskFourImg"
//                                 aria-expanded="true" aria-controls="deskImg">
//                             <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
//                                  className="bi bi-caret-down-fill" viewBox="0 0 16 16">
//                                 <path
//                                     d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
//                             </svg>
//                         </button>
//                     </div>
//                     <div id="deskFourImg" className="collapse" aria-labelledby="deskCardExpand"
//                          data-parent=".deskListBox">
//                         <div className="card-body">
//                             <img th:src="@{/images/standing.jpg}" className="card-img-top" alt="">
//                         </div>
//                     </div>
//                 </div>
//             </div>
//         </div>
//     </div>
// </div>


}

const cancelBooking = async (bookingId, deskId, dateString) => {

    // Call /user/cancelMyBooking to cancel a booking,
    // and delete the relevant div from the DOM
    await fetch('/user/cancelMyBooking', {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({bookingId: bookingId})
    });

    document.getElementById("desk-card-" + bookingId).remove();
    showCancelNotification(deskId, dateString);
    await loadUserBookings();
}

function showCancelNotification(deskId, dateString) {

    //replace div with clone of itself, to restart the css animation
    const cancelNot = document.getElementById("cancelNotification");
    const cancelNot2 = cancelNot.cloneNode(true);

    cancelNot.parentNode.replaceChild(cancelNot2,cancelNot);
    cancelNot2.style.display = "block";

    //display booking details on notification
    document.getElementById("deskIdCancelNot").innerText = deskId;
    document.getElementById("dateCancelNot").innerText = dateString;
}