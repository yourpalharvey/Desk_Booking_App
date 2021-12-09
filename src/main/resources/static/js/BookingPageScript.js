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
    console.log(response);

    loadOfficeSelections(response);
}

const loadOfficeSelections = (response) => {
    for (let i = 0; i<response.length; i++) {
        const newOption = document.createElement("option");
        newOption.value = response[i].officeId;
        newOption.innerText = response[i].officeName;
        document.getElementById("officeLocation").append(newOption);
    }
    console.log(response);
}

const loadDailyBookings = async () => {
    //load the daily bookings as JSON from /user/loadDailyBookings in BookingRestController

    console.log("LOADDAILYBOOKINGS")
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

    console.log(response);

    displayDailyBookings(response);
}

const cancelBookingFromDash = async (bookingId,deskId) => {

    console.log("cancelBookingFromDash")
    await fetch('/public/cancelMyBooking', {
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

    console.log(jsonResponse);

    if(document.body.contains(document.getElementById("mainDiv"))){
        document.getElementById("mainDiv").remove();
    }

    const div1 = document.createElement("div");
    div1.setAttribute('class', "card col d-flex justify-content-center container-fluid mt-100 deskListBox");
    div1.setAttribute("id", "mainDiv");

    const displayDate = document.createElement("h5");
    displayDate.setAttribute("class", "card-title");
    displayDate.innerText = "Showing all desks for " + document.getElementById("date").value
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
        div4.setAttribute("id", "display-booked-or-unbooked-"+jsonResponse[i].deskId);

        if(!jsonResponse[i].booked){
            div4.className = "card deskCard";
        }
        if(jsonResponse[i].booked){
            div4.className = "card deskCardBooked";
        }

        if(jsonResponse[i].cancelButton){
            div4.className = "card deskCardCancel"
        }

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
        if(!jsonResponse[i].booked) {
            bookButton.setAttribute("class", "bookDeskButton btn btn-success");
            bookButton.setAttribute("onclick", "bookDesk(" + jsonResponse[i].deskId + ")");
            const bookButtonText = document.createTextNode("Book");
            bookButton.append(bookButtonText);
            if(jsonResponse[i].disableButton){
                bookButton.className = "bookDeskButton btn btn-success disabled"
                bookButton.removeAttribute("onclick");
            }
        } else {
            if(jsonResponse[i].cancelButton){
                bookButton.className = "bookDeskButton btn btn-warning";
                const bookButtonText = document.createTextNode("Cancel");
                let bookingId = jsonResponse[i].bookingId;
                let deskId = jsonResponse[i].deskId;
                bookButton.setAttribute("onclick", "cancelBookingFromDash("+bookingId+","+deskId+")");
                bookButton.append(bookButtonText);
            } else {
                bookButton.className ="bookDeskButton btn btn-outline-danger disabled";
                const bookButtonText = document.createTextNode("Booked");
                bookButton.append(bookButtonText);
            }
        }

        const cardText = document.createElement("div");
        cardText.className = "card-text row bookingCardDate";

        if(!jsonResponse[i].booked){
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
        div5.append(bookButton);
        div5.append(deskCardExpand);
        div5.append(cardText);

        div4.append(div5);
        div4.append(imgDiv);

        div3.append(div4);
        div2.append(div3)


    }


    //                  template below

    // <div
    //     className="card col d-flex justify-content-center container-fluid mt-100 deskListBox"> <!-- removed 'col' as class -->
    //     <div className="row row-cols-1 row-cols-sm-2 row-cols-md-4">
    //
    //         <!-- Loop through the keySet and display Book or Booked item based on the boolean key value-->
    //         <!-- if boolean value is true, desk is booked. -->
    //         <div th:each="key, status : ${allDeskMap.keySet()}" className="col desk-container">
    //
    //             <!-- Display this if desk is not booked (ie. boolean hashmap value = false) -->
    //             <div th:if="${not allDeskMap.get(key)}" className="card deskCard">
    //                 <div className="card-header deskCardExpand">
    //                     <h5 className="card-text">Desk <span th:text="${status.index + 1}"></span></h5>
    //
    //                     <!-- form for book button -->
    //                     <form className="booking-button-form" action="/user/bookDesk" method="POST">
    //                         <label htmlFor="date"><input type="hidden" id="booking-date" name="date"
    //                                                      th:value="${viewingDate}"></label>
    //                         <input type="hidden" id="desk-id" name="deskId" th:value="${status.index + 1}"></label>
    //                         <button className="bookDeskButton btn btn-success my-2 my-sm-0"
    //                                 th:id="'book-button'+${status.index+1}" type="submit">Book
    //                         </button>
    //                     </form>
    //
    //                     <!--<button class="bookDeskButton btn btn-success my-2 my-sm-0" th:id="'book-button'+${status.index+1}" type="submit">Book</button>-->
    //                     <button className="btn btn-link" data-toggle="collapse" data-target="#deskOneImg"
    //                             aria-expanded="true" aria-controls="deskImg">
    //                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
    //                              className="bi bi-caret-down-fill" viewBox="0 0 16 16">
    //                             <path
    //                                 d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
    //                         </svg>
    //                     </button>
    //                 </div>
    //                 <!-- increment the ids here to display each picture -->
    //                 <div id="deskOneImg" className="collapse" aria-labelledby="deskCardExpand"
    //                      data-parent=".deskListBox">
    //                     <div className="card-body">
    //                         <img th:src="@{/images/standing.jpg}" className="card-img-top" alt="">
    //                     </div>
    //                 </div>
    //             </div>
    //
    //             <!-- Display this if desk is booked (ie. boolean hashmap value = true) -->
    //             <div th:if="${allDeskMap.get(key)}" className="card deskCardBooked">
    //                 <div className="card-header deskCardExpand">
    //                     <h5 className="card-text">Desk <span th:text="${status.index + 1}"></span></h5>
    //                     <button className="bookDeskButton btn btn-outline-danger my-2 my-sm-0 disabled"
    //                             type="submit">Booked
    //                     </button>
    //                     <button className="btn btn-link" data-toggle="collapse" data-target="#deskFourImg"
    //                             aria-expanded="true" aria-controls="deskImg">
    //                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
    //                              className="bi bi-caret-down-fill" viewBox="0 0 16 16">
    //                             <path
    //                                 d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
    //                         </svg>
    //                     </button>
    //                 </div>
    //                 <div className="deskFourImg collapse" aria-labelledby="deskCardExpand" data-parent=".deskListBox">
    //                     <div className="card-body">
    //                         <img th:src="@{/images/standing.jpg}" className="card-img-top" alt="">
    //                     </div>
    //                 </div>
    //             </div>
    //         </div>
    //
    //     </div>
    // </div>

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





