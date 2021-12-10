const loadAllDesksForAdmin = async () => {
    //load the daily bookings as JSON from /user/loadDailyBookings in BookingRestController

    console.log("LOADDAILYBOOKINGS - FOR ADMIN")
    console.log(document.getElementById("officeLocation").value);
    const params = {
        officeId : document.getElementById("officeLocation").value
    }

    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    }

    let response = await fetch('/admin/viewDesksByOffice', options);
    response = await response.json();

    console.log(response);

    displayAllDesksForAdmin(response);
}

const displayAllDesksForAdmin = (jsonResponse) => {
    let mainDiv = document.getElementById("mainDiv");;

    //remove mainDivDisplayed and take new copy of mainDiv
    if (document.body.contains(document.getElementById("mainDivDisplayed"))) {
        document.getElementById("mainDivDisplayed").remove()
    }
    let mainDivDisplayed = mainDiv.cloneNode(true);
    mainDivDisplayed.setAttribute("id", "mainDivDisplayed");
    mainDivDisplayed.className = "card col d-flex justify-content-center container-fluid mt-100 deskListBox";
    mainDivDisplayed.style.display = "block";

    //insert new copy of mainDiv after mainDiv
    mainDiv.parentNode.insertBefore(mainDivDisplayed, mainDiv.nextSibling);

    // displayDate.innerText = "Showing all desks for " + document.getElementById("date").value
    //     + " at the " + jsonResponse[0].officeLocation + " office:";

    for(let i = 0; i < jsonResponse.length; i++){

        const bookingsContainerToRepeat = mainDivDisplayed.firstElementChild.nextElementSibling;
        const deskCardContainer = bookingsContainerToRepeat.cloneNode(true);

        if(i === 0){
            //on first loop, remove template card
            bookingsContainerToRepeat.remove();
        }

        // if(jsonResponse.length===0) {
        //     mainDivDisplayed.lastChild.remove();
        // }

        mainDivDisplayed.appendChild(deskCardContainer);

        const deskContainerChild = deskCardContainer.firstElementChild;
        const bookedOrUnbooked = deskContainerChild.firstElementChild;
        console.log(bookedOrUnbooked.className);
        console.log(deskCardContainer.firstElementChild.firstElementChild);
        bookedOrUnbooked.setAttribute("id", "display-booked-or-unbooked-" + jsonResponse[i].deskId);

        if (!jsonResponse[i].booked) {
            bookedOrUnbooked.className = "card deskCard";
        }
        if (jsonResponse[i].booked) {
            bookedOrUnbooked.className = "card deskCardBooked";
        }

        const cardText = bookedOrUnbooked.firstElementChild.firstElementChild;
        cardText.innerText = "Desk " + jsonResponse[i].deskId;

        let bookButton = cardText.nextElementSibling;
        bookButton.setAttribute("id", "book-button-" + jsonResponse[i].deskId);

        bookButton.className = "bookDeskButton btn btn-warning";
        bookButton.innerText = "Cancel";

        let deskId = jsonResponse[i].deskId;
        bookButton.setAttribute("onclick", "deleteDesk(" + deskId + ")");

        const deskCardExpand = bookButton.nextElementSibling;

        const collapseButton = deskCardExpand.firstElementChild;

        collapseButton.setAttribute("data-target", "#deskImg" + jsonResponse[i].deskId);
        //create picture dropdown

        const imgDiv = bookedOrUnbooked.firstElementChild.nextElementSibling;

        imgDiv.setAttribute("id", "deskImg" + jsonResponse[i].deskId);


        const img = imgDiv.firstElementChild.firstElementChild;
        img.setAttribute("src", "/images/" + jsonResponse[i].deskImageName);

        const pillTagDiv = bookedOrUnbooked.lastElementChild;

        const firstPillTag = pillTagDiv.firstElementChild;

        firstPillTag.innerHTML = jsonResponse[i].deskType;
        firstPillTag.nextElementSibling.innerHTML = jsonResponse[i].deskPosition;
        firstPillTag.nextElementSibling.nextElementSibling.innerHTML = "Monitors: " + jsonResponse[i].monitorOption;

    }


    //loop through all objects in the json response and create nodes for each
    //then append these nodes to the outer div

    // if (jsonResponse.length === 0) {
    //     displayEmpty();
    //     return;
    // }

    //json response needs to contain: date, desk id, booked = true/false


        // const div3 = document.createElement("div");
        // div3.setAttribute('class', "desk-container col-12 col-md-6 col-lg-4 col-xl-2");
        //
        // const div4 = document.createElement("div");
        // div4.setAttribute("id", "display-booked-or-unbooked-" + jsonResponse[i].deskId);

        // if (!jsonResponse[i].booked) {
        //     div4.className = "card deskCard";
        // }
        // if (jsonResponse[i].booked) {
        //     div4.className = "card deskCardBooked";
        // }
        //
        // if (jsonResponse[i].cancelButton) {
        //     div4.className = "card deskCardCancel"
        // }

        // const div5 = document.createElement("div");
        // div5.setAttribute('class', "card-header");
        //
        // //create heading
        // const heading = document.createElement("h5");
        // heading.setAttribute('class', "card-text");
        // const deskTextNode = document.createTextNode("Desk " + jsonResponse[i].deskId);
        // heading.append(deskTextNode);

        // const bookButton = document.createElement("button");
        // bookButton.setAttribute("id", "book-button-" + jsonResponse[i].deskId);

        //create book button
        // if (!jsonResponse[i].booked) {
        //     bookButton.setAttribute("class", "bookDeskButton btn btn-success");
        //     bookButton.setAttribute("onclick", "bookDesk(" + jsonResponse[i].deskId + ")");
        //     const bookButtonText = document.createTextNode("Book");
        //     bookButton.append(bookButtonText);
        //     if (jsonResponse[i].disableButton) {
        //         bookButton.className = "bookDeskButton btn btn-success disabled"
        //         bookButton.removeAttribute("onclick");
        //     }
        // } else {
        //     if (jsonResponse[i].cancelButton) {
        //         bookButton.className = "bookDeskButton btn btn-warning";
        //         const bookButtonText = document.createTextNode("Cancel");
        //         let bookingId = jsonResponse[i].bookingId;
        //         let deskId = jsonResponse[i].deskId;
        //         bookButton.setAttribute("onclick", "cancelBookingFromDash(" + bookingId + "," + deskId + ")");
        //         bookButton.append(bookButtonText);
        //     } else {
        //         bookButton.className = "bookDeskButton btn btn-outline-danger disabled";
        //         const bookButtonText = document.createTextNode("Booked");
        //         bookButton.append(bookButtonText);
        //     }
        // }

        // const cardText = document.createElement("div");
        // cardText.className = "card-text row bookingCardDate";
        //
        // if (!jsonResponse[i].booked) {
        //     const deskType = document.createElement("span");
        //     const deskPos = document.createElement("span");
        //     const monitors = document.createElement("span");
        //
        //     deskType.className = "deskTags col-3";
        //     deskPos.className = "deskTags col-3";
        //     monitors.className = "deskTags col-3";
        //
        //     cardText.append(deskType);
        //     cardText.append(deskPos);
        //     cardText.append(monitors);
        //
        //     // Add values from desk table in DB
        //     deskType.innerHTML = jsonResponse[i].deskType;
        //     deskPos.innerHTML = jsonResponse[i].deskPosition;
        //     monitors.innerHTML = "Monitors: " + jsonResponse[i].monitorOption;
        //
        // } else {
        //     const userName = document.createElement("span");
        //     userName.className = "deskTagsOne col-12";
        //     cardText.append(userName);
        //     userName.innerText = jsonResponse[i].userBooked;
        // }
    //
    //
    //     const deskCardExpand = document.createElement("div");
    //     deskCardExpand.className = "card-title deskCardExpand";
    //
    //
    //     //create picture dropdown
    //     const displayPictureButton = document.createElement("button");
    //     displayPictureButton.setAttribute("class", "btn btn-link");
    //     displayPictureButton.setAttribute("data-toggle", "collapse");
    //     displayPictureButton.setAttribute("data-target", "#deskImg" + jsonResponse[i].deskId);
    //     displayPictureButton.setAttribute("aria-expanded", "true");
    //     displayPictureButton.setAttribute("aria-controls", "deskImg");
    //
    //     const svgDeskPic = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    //     svgDeskPic.setAttribute("width", "16");
    //     svgDeskPic.setAttribute("height", "16");
    //     svgDeskPic.setAttribute("fill", "currentColor");
    //     svgDeskPic.setAttribute("class", "bi bi-caret-down-fill");
    //     svgDeskPic.setAttribute("viewBox", "0 0 16 16");
    //
    //     const svgPath = document.createElementNS("http://www.w3.org/2000/svg", "path");
    //     svgPath.setAttribute("d", "M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z");
    //
    //
    //     const imgDiv = document.createElement("div");
    //     imgDiv.setAttribute("id", "deskImg" + jsonResponse[i].deskId);
    //     imgDiv.setAttribute("class", "collapse");
    //     imgDiv.setAttribute("aria-labelledby", "deskCardExpand");
    //     imgDiv.setAttribute("data-parent", ".deskListBox");
    //
    //     const imgDiv2 = document.createElement("div");
    //     imgDiv2.setAttribute("class", "card-body deskImg");
    //
    //     const img = document.createElement("img");
    //     img.setAttribute("src", "/images/" + jsonResponse[i].deskImageName);
    //     img.setAttribute("class", "card-img-top");
    //     img.setAttribute("alt", "");
    //
    //     imgDiv2.append(img);
    //     imgDiv.append(imgDiv2);
    //
    //     svgDeskPic.append(svgPath);
    //     displayPictureButton.append(svgDeskPic);
    //
    //     deskCardExpand.append(displayPictureButton);
    //
    //
    //     div5.append(heading);
    //     div5.append(bookButton);
    //     div5.append(deskCardExpand);
    //     div5.append(cardText);
    //
    //     div4.append(div5);
    //     div4.append(imgDiv);
    //
    //     div3.append(div4);
    //     div2.append(div3)
    //
    //
    // }
}