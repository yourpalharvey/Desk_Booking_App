const setDate = () => {
    let today = new Date().toISOString().slice(0, 10);
    let maxDate = new Date();
    maxDate.setDate(maxDate.getDate() + 21);
    maxDate = maxDate.toISOString().slice(0, 10);
    document.getElementById("date").setAttribute('min', today);
    document.getElementById("date").setAttribute('max', maxDate);
}

const loadOffices = async () => {

    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }

    let response = await fetch('/public/getAllOffices', options);
    response = await response.json();

    loadOfficeSelections(response);
}

const loadOfficeSelections = (response) => {
    for (let i = 0; i<response.length; i++) {
        const newOption = document.createElement("option");
        newOption.value = response[i].officeId;
        newOption.innerText = response[i].officeName;
        document.getElementById("officeLocation").append(newOption);
    }
}

const loadDailyBookings = async () => {
    //load the daily bookings as JSON from /user/loadDailyBookings in BookingRestController

    const params = {
        date : document.getElementById('date').value,
        officeId: document.getElementById("officeLocation").value
    }
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    }

    let response = await fetch('/public/loadDailyBookings', options);
    response = await response.json();

    displayDailyBookings(response);
}

const cancelBookingFromDash = async (bookingId,deskId) => {

    console.log("cancelBookingFromDash")
    await fetch('/user/cancelMyBooking', {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({bookingId: bookingId})
    });

    cancelNotification(deskId);
    await loadDailyBookings();
}

//if no desks in database, display the below
const displayEmpty = () => {
    const div3 = document.createElement("div");
    div3.setAttribute('class', "col desk-container");

    const div4 = document.createElement("div");
    div4.setAttribute('class', "card deskCard");

    document.getElementById("bookings-container").append(div3);
    div3.append(div4)

    const p = document.createElement("p");
    const noBookings = document.createTextNode("NO Desks!!");
    div4.append(p);
    p.append(noBookings);
}

const displayDailyBookings = (jsonResponse) => {

    if(document.body.contains(document.getElementById("mainDiv"))){
        document.getElementById("mainDiv").remove();
    }

    const div1 = document.createElement("div");
    div1.setAttribute('class', "card col d-flex justify-content-center container-fluid mt-100 deskListBox");
    div1.setAttribute("id", "mainDiv");

    const displayDate = document.createElement("h5");
    displayDate.setAttribute("class", "card-title");
    displayDate.innerText = "Showing all desks for " + ukDateHelper(document.getElementById("date").value)
            + " at the " + jsonResponse[0].officeLocation + " office:";
    div1.append(displayDate);

    const div2 = document.createElement("div");
    div2.setAttribute('class', "card-body row");
    div2.setAttribute("id", "bookings-container");

    //loop through all objects in the json response and create nodes for each
    //then append these nodes to the outer div

    div1.append(div2);
    document.body.append(div1);

    if(jsonResponse.length === 0){
        displayEmpty();
        return;
    }

    let deskCard = "";

    //json response needs to contain: date, desk id, booked = true/false
    for(let i = 0; i < jsonResponse.length; i++) {

        //get response information
        const deskId = jsonResponse[i].deskId;
        const deskType = jsonResponse[i].deskType;
        const deskPosition = jsonResponse[i].deskPosition;
        const monitorOption = jsonResponse[i].monitorOption;
        const userBooked = jsonResponse[i].userBooked;
        const deskImageName = jsonResponse[i].deskImageName;
        const bookingId = jsonResponse[i].bookingId;

        //set classname for desk card based on whether desk is booked
        let bookedOrUnbookedDiv = "";

        if(!jsonResponse[i].booked){
            bookedOrUnbookedDiv = "card deskCard";
        }
        if(jsonResponse[i].booked){
            bookedOrUnbookedDiv = "card deskCardBooked";
        }
        if(jsonResponse[i].cancelButton){
            bookedOrUnbookedDiv = "card deskCardCancel"
        }

        //set details for the book/cancel/disabled buttons based on details from server response
        let bookButtonClassName = "";
        let bookButtonText = "";
        let bookButtonOnClick = "";

        if(!jsonResponse[i].booked) {
            bookButtonClassName = "bookDeskButton btn btn-success";
            bookButtonOnClick =  "bookDesk(" + deskId + ")";
            bookButtonText = "Book";
            if(jsonResponse[i].disableButton){
                bookButtonClassName = "bookDeskButton btn btn-success disabled";
                bookButtonOnClick = "";
            }
        } else {
            if(jsonResponse[i].cancelButton){
                bookButtonClassName = "bookDeskButton btn btn-warning";
                bookButtonText = "Cancel";
                bookButtonOnClick= "cancelBookingFromDash("+bookingId+","+deskId+")";
            } else {
                bookButtonClassName ="bookDeskButton btn btn-outline-danger disabled";
                bookButtonText ="Booked";
            }
        }

        let bookingCardDate = "";

        //set details to display (either user booked, or desk information if desk is unbooked)
        if(userBooked === ""){
            bookingCardDate = "<span class=\"deskTags col-3\">"+deskType+"</span><span\n" +
            "                        class=\"deskTags col-3\">"+ deskPosition+"</span><span class=\"deskTags col-3\">Monitors: "+monitorOption+"</span>\n";
        } else {
            bookingCardDate = "<span class=\"deskTagsOne col-12\">"+userBooked+"</span>";
        }
        //create and append HTML
        deskCard += "        <div class=\"desk-container col-12 col-md-6 col-lg-4 col-xl-2\">\n" +
            "            <div id=\"display-booked-or-unbooked-"+ deskId+ "\" class=\""+bookedOrUnbookedDiv+"\">\n" +
            "                <div class=\"card-header\"><h5 class=\"card-text\">Desk "+ deskId +"</h5>\n" +
            "                    <button id=\"book-button-"+ deskId + "\" class=\""+bookButtonClassName+"\" onclick=\""+bookButtonOnClick+"\">"+bookButtonText+"</button>\n" +
            "                    <div class=\"card-title deskCardExpand\">\n" +
            "                        <button class=\"btn btn-link\" data-toggle=\"collapse\" data-target=\"#deskImg"+deskId+"\"\n" +
            "                                aria-expanded=\"true\" aria-controls=\"deskImg\">\n" +
            "                            <svg width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-caret-down-fill\"\n" +
            "                                 viewBox=\"0 0 16 16\">\n" +
            "                                <path\n" +
            "                                    d=\"M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z\"></path>\n" +
            "                            </svg>\n" +
            "                        </button>\n" +
            "                    </div>\n" +
            "                    <div class=\"card-text row bookingCardDate\">"+ bookingCardDate + "" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div id=\"deskImg"+deskId+"\" class=\"collapse\" aria-labelledby=\"deskCardExpand\" data-parent=\".deskListBox\">\n" +
            "                    <div class=\"card-body deskImg\"><img src=\"/desk/"+deskId+"/"+deskImageName+"\" class=\"card-img-top\" alt=\"\">\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n";

    }
    div2.innerHTML=deskCard;

}

//when a 'Book' button is clicked - create POST request to BookingRestController to book a desk
const bookDesk = async (deskId) => {

    console.log("THE DESK ID IS: " + deskId);   //make new booking
    const params = {
        date : document.getElementById('date').value,
        deskId : deskId.toString()
    }
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    }

    await fetch('/user/makeBooking', options);

    showBookNotification(deskId);
    //Disable the booking button once a booking has been made
    disableDesk(deskId);

}

//disable the booking of a desk once booking is made
const disableDesk = (deskId) => {
    document.getElementById("display-booked-or-unbooked-"+deskId)
        .setAttribute('class', "card deskCardBooked");

    //remove onclick handler from button
    const bookButton = document.getElementById("book-button-"+deskId);
    bookButton.removeAttribute("onclick");

    //set button style to disabled
    bookButton.innerHTML = "Booked";
    bookButton.setAttribute("class", "bookDeskButton btn btn-outline-danger my-2 my-sm-0 disabled");

    loadDailyBookings();
}

// Todo - div defaults to not display and displays on button click, however as page is currently refreshing on click therefore div goes back to default
function showBookNotification(deskId) {
    document.getElementById("cancelFromDashNotification").style.display = "none";
    const bookNot = document.getElementById("bookNotification");

    const bookNot2 = bookNot.cloneNode(true);
    bookNot.parentNode.replaceChild(bookNot2,bookNot);

    bookNot2.style.display = "block";
    document.getElementById("deskIdNot").innerText = deskId;
}

function cancelNotification(deskId) {
    document.getElementById("bookNotification").style.display = "none";
    const cancelNot = document.getElementById("cancelFromDashNotification");

    const cancelNot2 = cancelNot.cloneNode(true);
    cancelNot.parentNode.replaceChild(cancelNot2,cancelNot);

    cancelNot2.style.display = "block";
    document.getElementById("deskIdCancelNot").innerText = deskId;
}





