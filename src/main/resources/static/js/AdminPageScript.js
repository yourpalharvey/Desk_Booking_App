const loadDailyAdminBookings = async () => {
    //load the daily bookings as JSON from /user/loadDailyBookings in BookingRestController

    console.log("LOADDAILYBOOKINGS")
    const params = {
        date: document.getElementById('date').value,
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

    console.log(response);

    displayDailyAdminBookings(response);
}

const cancelBookingFromAdminDash = async (bookingId,deskId,userBooked,officeLocation) => {

    console.log("cancelBookingFromDash")
    await fetch('/public/cancelMyBooking', {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({bookingId: bookingId})
    });

    cancelAdminNotification(deskId, userBooked, officeLocation);
    await loadDailyAdminBookings();
}

//if no desks in database, display the below
const displayAdminEmpty = () => {
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

const displayDailyAdminBookings = (jsonResponse) => {

    console.log(jsonResponse);

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

    //json response needs to contain: date, desk id, booked = true/false
    for(let i = 0; i < jsonResponse.length; i++) {

        const div3 = document.createElement("div");
        div3.setAttribute('class', "desk-container col-12 col-md-6 col-lg-4 col-xl-2");

        const div4 = document.createElement("div");
        div4.setAttribute("id", "display-booked-or-unbooked-" + jsonResponse[i].deskId);

        if (!jsonResponse[i].booked) {
            div4.className = "card deskCard";
        }
        // if booked card displays yellow
        if (jsonResponse[i].booked) {
            div4.className = "card deskCardCancel";
        }

        // if (jsonResponse[i].cancelButton) {
        //     div4.className = "card deskCardCancel"
        // }

        const div5 = document.createElement("div");
        div5.setAttribute('class', "card-header");

        //create heading
        const heading = document.createElement("h5");
        heading.setAttribute('class', "card-text");
        const deskTextNode = document.createTextNode("Desk " + jsonResponse[i].deskId);
        heading.append(deskTextNode);

        const bookButton = document.createElement("button");
        bookButton.setAttribute("id", "book-button-" + jsonResponse[i].deskId);

        //create book button
        if (!jsonResponse[i].booked) {
            bookButton.setAttribute("class", "bookDeskButton btn btn-success disabled");
            const bookButtonText = document.createTextNode("Available");
            bookButton.append(bookButtonText);
        } else {
                bookButton.className = "bookDeskButton btn btn-warning";
                const bookButtonText = document.createTextNode("Cancel");
                let bookingId = jsonResponse[i].bookingId;
                let deskId = jsonResponse[i].deskId;
                let officeLocation = "\"" + jsonResponse[i].officeLocation + "\"";
                let userBooked = "\"" + jsonResponse[i].userBooked + "\"";
                bookButton.setAttribute("onclick", "cancelBookingFromAdminDash(" + bookingId
                    + "," + deskId
                    + "," + userBooked
                    +"," + officeLocation + ")" );
                bookButton.append(bookButtonText);
        }

        const cardText = document.createElement("div");
        cardText.className = "card-text row bookingCardDate";

        if (!jsonResponse[i].booked) {
            const deskType = document.createElement("span");
            const deskPos = document.createElement("span");
            const monitors = document.createElement("span");

            deskType.className = "deskTags col-3";
            deskPos.className = "deskTags col-3";
            monitors.className = "deskTags col-3";

            cardText.append(deskType);
            cardText.append(deskPos);
            cardText.append(monitors);

            // Add values from desk table in DB
            deskType.innerHTML = jsonResponse[i].deskType;
            deskPos.innerHTML = jsonResponse[i].deskPosition;
            monitors.innerHTML = "Monitors: " + jsonResponse[i].monitorOption;

        } else {
            const userName = document.createElement("span");
            userName.className = "deskTagsOne col-12";
            cardText.append(userName);
            userName.innerText = jsonResponse[i].userBooked;
        }


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
        imgDiv.setAttribute("id", "deskImg" + jsonResponse[i].deskId);
        imgDiv.setAttribute("class", "collapse");
        imgDiv.setAttribute("aria-labelledby", "deskCardExpand");
        imgDiv.setAttribute("data-parent", ".deskListBox");

        const imgDiv2 = document.createElement("div");
        imgDiv2.setAttribute("class", "card-body deskImg");

        const img = document.createElement("img");
        img.setAttribute("src","/desk/" + jsonResponse[i].deskId + "/" + jsonResponse[i].deskImageName);
        img.setAttribute("class", "card-img-top");
        img.setAttribute("alt", "");

        imgDiv2.append(img);
        imgDiv.append(imgDiv2);

        svgDeskPic.append(svgPath);
        displayPictureButton.append(svgDeskPic);

        deskCardExpand.append(displayPictureButton);


        div5.append(heading);
        div5.append(bookButton);
        div5.append(deskCardExpand);
        div5.append(cardText);

        div4.append(div5);
        div4.append(imgDiv);

        div3.append(div4);
        div2.append(div3)
    }

}

function cancelAdminNotification(deskId, userBooked, officeLocation) {
    const cancelAdminNot = document.getElementById("cancelFromAdminDashNotification");

    const cancelAdminNot2 = cancelAdminNot.cloneNode(true);
    cancelAdminNot.parentNode.replaceChild(cancelAdminNot2,cancelAdminNot);

    cancelAdminNot2.style.display = "block";
    document.getElementById("deskIdCancelAdminNot").innerText = deskId;
    document.getElementById("userBookedCancelAdminNot").innerText = userBooked;
    document.getElementById("officeLocationCancelAdminNot").innerText = officeLocation;
}
