const displayDeskBooked = () => {
    console.log("hello");
    alert("Desk booked")
}

const displayDeskCancelled = () => {
    alert("Desk has been cancelled");
}

const loadUserBookings = async () => {

    //load the user bookings as JSON from /user/getMyBookings
    let response = await fetch('/user/getMyBookings');
    response = await response.json();

    displayUserBookings(response);
}

const displayUserBookings = (jsonResponse) => {

    const div1 = document.createElement("div");
    div1.setAttribute('class', "card col d-flex justify-content-center container-fluid mt-100 deskListBox");
    div1.setAttribute("id", "mainDiv");

    const div2 = document.createElement("div");
    div2.setAttribute('class', "card-body");

    const div3 = document.createElement("div");
    div3.setAttribute('class', "row row-cols-1 row-cols-sm-2 row-cols-md-4");
    div3.setAttribute("id", "bookings-container");

    //loop through all objects in the json response and create nodes for each
    //then append these nodes to the outer div

    div2.append(div3);
    div1.append(div2);

    document.body.append(div1);

    if(jsonResponse.length === 0){
        displayEmpty();
        return;
    }

    for(let i = 0; i < jsonResponse.length; i++) {

        const div4 = document.createElement("div");
        div4.setAttribute('class', "col");
        div4.setAttribute("id", "desk-card-"+jsonResponse[i].bookingId);

        const div5 = document.createElement("div");
        div5.setAttribute('class', "card deskCardCancel");
        div5.setAttribute('id', "cancel-card-"+jsonResponse[i].bookingId);

        const div6 = document.createElement("div");
        div6.setAttribute('class', "card-header deskCardExpand");

        const heading = document.createElement("h5");
        heading.setAttribute('class', "card-text");
        const deskTextNode = document.createTextNode("Desk " + jsonResponse[i].deskId);
        heading.append(deskTextNode);

        const datePara = document.createElement("p");
        const dateTextNode = document.createTextNode(jsonResponse[i].date);
        datePara.append(dateTextNode);

        const cancelButton = document.createElement("button");
        cancelButton.setAttribute("class", "bookDeskButton btn btn-warning my-2 my-sm-0");
        cancelButton.setAttribute("id", "cancelBookingId-" + jsonResponse[i].bookingId);
        cancelButton.type = "submit";
        cancelButton.setAttribute("onclick", "cancelBooking(" + jsonResponse[i].bookingId +")");
        const cancelButtonText = document.createTextNode("Cancel");
        cancelButton.append(cancelButtonText);

        //create picture dropdown
        const displayPictureButton = document.createElement("button");
        displayPictureButton.setAttribute("class", "btn btn-link");
        displayPictureButton.setAttribute("data-toggle", "collapse");
        displayPictureButton.setAttribute("data-target", "#deskFourImg");
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
        imgDiv.setAttribute("id", "deskFourImg");
        imgDiv.setAttribute("class", "collapse");
        imgDiv.setAttribute("aria-labelledby", "deskCardExpand");
        imgDiv.setAttribute("data-parent", ".deskListBox");

        const imgDiv2 = document.createElement("div");
        imgDiv2.setAttribute("class", "card-body");

        const img = document.createElement("img");
        img.setAttribute("src", "/images/standing.jpg");
        img.setAttribute("class", "card-img-top");
        img.setAttribute("alt", "");

        imgDiv2.append(img);
        imgDiv.append(imgDiv2);

        svgDeskPic.append(svgPath);
        displayPictureButton.append(svgDeskPic);

        div6.append(heading);
        div6.append(datePara);
        div6.append(cancelButton)
        div6.append(displayPictureButton);

        div5.append(div6);
        div5.append(imgDiv);

        div4.append(div5);
        div3.append(div4);

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

const cancelBooking = async (bookingIdToCancel) => {

    console.log(bookingIdToCancel);
    // Call /user/cancelMyBooking to cancel a booking,
    // and delete the relevant div from the DOM
    let response = await fetch('/user/cancelMyBooking', {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({bookingId: bookingIdToCancel})
    });

    document.getElementById("desk-card-"+bookingIdToCancel).remove();
    if(!document.getElementById("bookings-container").hasChildNodes()){
        displayEmpty();
    }
}

const displayEmpty = () => {
    const div4 = document.createElement("div");
    div4.setAttribute('class', "col");

    const div5 = document.createElement("div");
    div5.setAttribute('class', "card deskCardCancel");

    document.getElementById("bookings-container").append(div4);
    div4.append(div5)

    const p = document.createElement("p");
    const noBookings = document.createTextNode("NO BOOKINGS!");
    div5.append(p);
    p.append(noBookings);
}
