const loadDailyBookings = async () => {
    //load the daily bookings as JSON from /user/loadDailyBookings in BookingRestController
    const params = {
        date : document.getElementById('date').value
    }
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    }

    let response = await fetch('/user/loadDailyBookings', options);
    response = await response.json();

    displayDailyBookings(response);
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

    const div2 = document.createElement("div");
    div2.setAttribute('class', "row row-cols-1 row-cols-sm-2 row-cols-md-4");
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
        div3.setAttribute('class', "col desk-container");

        const div4 = document.createElement("div");
        div4.setAttribute("id", "display-booked-or-unbooked-"+jsonResponse[i].deskId);

        if(!jsonResponse[i].booked){
            div4.setAttribute('class', "card deskCard");
        }
        if(jsonResponse[i].booked){
            div4.setAttribute('class', "card deskCardBooked");
        }

        const div5 = document.createElement("div");
        div5.setAttribute('class', "card-header deskCardExpand");

        //create heading
        const heading = document.createElement("h5");
        heading.setAttribute('class', "card-text");
        const deskTextNode = document.createTextNode("Desk " + jsonResponse[i].deskId);
        heading.append(deskTextNode);

        const bookButton = document.createElement("button");
        bookButton.setAttribute("id", "book-button-" + jsonResponse[i].deskId);
        //create book button
        if(!jsonResponse[i].booked) {
            bookButton.setAttribute("class", "bookDeskButton btn btn-success my-2 my-sm-0");
            bookButton.setAttribute("onclick", "bookDesk(" + jsonResponse[i].deskId + ")");
            const bookButtonText = document.createTextNode("Book");
            bookButton.append(bookButtonText);
        }

        if(jsonResponse[i].booked) {
            bookButton.setAttribute("class", "bookDeskButton btn btn-outline-danger my-2 my-sm-0 disabled");
            const bookButtonText = document.createTextNode("Booked");
            bookButton.append(bookButtonText);
        }

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
        imgDiv.setAttribute("id", "deskOneImg");
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

        div5.append(heading);
        div5.append(bookButton)
        div5.append(displayPictureButton);

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
}

