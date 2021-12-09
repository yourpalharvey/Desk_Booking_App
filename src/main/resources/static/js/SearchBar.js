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

const getAllUserNames = async () => {

    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }

    let response = await fetch('/public/getAllUsers', options);
    response = await response.json();
    console.log(response);
    
    setUsersArray(response);
    
}

let users = [];

const setUsersArray = (userList) => {

    for(let i = 0; i < userList.length; i++){
        users.push(userList[i].username);
    }
}

const loadUserAdminBookings = async () => {
    //load the daily bookings as JSON from /user/loadDailyBookings in BookingRestController

    console.log(document.getElementById("userName").value)
    console.log("LOADDAILYBOOKINGS")
    const params = {
        username: document.getElementById("userName").value
    }
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    }

    let response = await fetch('/public/getUserBookingsByAdmin', options);
    response = await response.json();

    console.log(response);
    displayUserAdminBookings(response);

}

const displayAdminSearchEmpty = () => {
    const cardTitleDiv = document.getElementById("card-title-div");

    const emptyDisplay = document.createElement("h5");
    emptyDisplay.className = "card-title";
    emptyDisplay.innerText = "You have no bookings";
    cardTitleDiv.append(emptyDisplay)
}

const displayUserAdminBookings = (jsonResponse) => {

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
        displayAdminSearchEmpty();
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
        let searchUsername = "\"" + jsonResponse[i].userBooked + "\"";
        let officeLocation = "\"" + jsonResponse[i].officeLocation + "\"";

        console.log("IN DISPLAY FUNCTION: " + searchUsername);

        cancelButton.setAttribute("onclick", "adminCancelBooking(" + bookingId + ", " + deskId + ", " + bookingDate + "," + searchUsername + "," + officeLocation + ")");
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


}

const adminCancelBooking = async (bookingId, deskId, dateString, username, officeLocation) => {

    // Call /user/cancelMyBooking to cancel a booking,
    // and delete the relevant div from the DOM
    await fetch('/public/cancelMyBooking', {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({bookingId: bookingId})
    });
    console.log("IN ADMIN CANCEL BOOKING" + username);
    document.getElementById("desk-card-" + bookingId).remove();
    showSearchCancelNotification(deskId, dateString, username, officeLocation);
    await loadUserAdminBookings();
}

function showSearchCancelNotification(deskId, dateString, username, officeLocation) {

    console.log("IN SHOW SEARCH ADMIN CANCEL BOOKING" + username);

    //replace div with clone of itself, to restart the css animation
    const cancelNot = document.getElementById("cancelFromAdminDashNotification");
    const cancelNot2 = cancelNot.cloneNode(true);

    cancelNot.parentNode.replaceChild(cancelNot2,cancelNot);
    cancelNot2.style.display = "block";

    //display booking details on notification
    document.getElementById("deskIdCancelAdminNot").innerText = deskId;
    document.getElementById("adminDateToCancel").innerText = `on ${dateString}`;
    document.getElementById("userBookedCancelAdminNot").innerText = username;
    document.getElementById("officeLocationCancelAdminNot").innerText = officeLocation;
}