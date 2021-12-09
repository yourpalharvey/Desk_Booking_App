const loadUserBookings = async () => {

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
    await fetch('/public/cancelMyBooking', {
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