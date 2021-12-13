const makeQuickBooking = async () => {

    const params = {
        date : document.getElementById('date').value,
        officeId : document.getElementById('officeLocation').value
    }
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)

    }
    // console.log(params);

    let response = await fetch('/user/createQuickBooking', options);
    response = await response.json();

    console.log(response);

    displayBookingDetails(response);

}

const displayBookingDetails = (response) => {
    console.log(response);

    const quickBookingCompleteNotification = document.getElementById("quickBookingComplete");
    const deskFullNotification = document.getElementById("quickBookingFull");
    const quickBookingExistsNotification = document.getElementById("quickBookingExists");

    quickBookingCompleteNotification.style.display="none";
    deskFullNotification.style.display="none";
    quickBookingExistsNotification.style.display="none";

    //this is to re-trigger the animation each time
    const quickBookingCompleteNotificationClone = quickBookingCompleteNotification.cloneNode(true);
    const deskFullNotificationClone = deskFullNotification.cloneNode(true);
    const quickBookingExistsNotificationClone = quickBookingExistsNotification.cloneNode(true);

    quickBookingCompleteNotification.parentNode.replaceChild(quickBookingCompleteNotificationClone, quickBookingCompleteNotification);
    deskFullNotification.parentNode.replaceChild(deskFullNotificationClone, deskFullNotification);
    quickBookingExistsNotification.parentNode.replaceChild(quickBookingExistsNotificationClone, quickBookingExistsNotification);

    if(response.length === 0){
        //if desks full:
        deskFullNotificationClone.style.display = "block"
        console.log("ALL DESKS FULL");
    } else if (response.booked === true) {
        console.log("RESPONSE BOOKED = TRUE")
        //if desk has already been booked for this day, display:
        const deskBookedExistsSpan = document.getElementById("deskBookedExists");
        deskBookedExistsSpan.innerHTML = response.deskId;

        const deskBookedDateSpan = document.getElementById("deskBookedDate");
        deskBookedDateSpan.innerHTML = ukDateHelper(response.date);

        const deskBookedLocationSpan = document.getElementById("deskBookedLocation");
        deskBookedLocationSpan.innerHTML = response.officeLocation;

        quickBookingExistsNotificationClone.style.display = "block";
    } else {
        //if desk successfully booked, display:
        const deskIdSpan = document.getElementById("idOfDeskBooked");
        deskIdSpan.innerHTML = response.deskId;

        const dateSpan = document.getElementById("dateOfDeskBooked");
        dateSpan.innerHTML = ukDateHelper(response.date);

        const locationSpan = document.getElementById("locationOfDeskBooked");
        locationSpan.innerHTML = response.officeLocation;

        quickBookingCompleteNotificationClone.style.display = "block";
    }




    // <div id="quickBookingFull" className="card d-flex justify-content-center container-fluid mt-100 notificationBox"
    //      style="display:none">
    //     <div className="card-body row">
    //         <!--TODO Insert correct date the user has selected to the notification message-->
    //         <!--Sorry, all desks for (Insert date selected) (this is wrong<span th:text="${startDate}"></span>) are booked-->
    //         <h5 className="card-title col-12">Sorry, all desks for the day selected are booked</h5>
    //     </div>
    // </div>
    //
    // <div id="quickBookingComplete" className="card d-flex justify-content-center container-fluid mt-100 notificationBox"
    //      style="display:none">
    //     <div className="card-body row">
    //         <!--TODO Insert correct date the user has selected to the notification message-->
    //         <!--Desk <span th:text="${deskBooked}"></span> booked for (Insert date selected)<span th:text="${startDate}"></span>-->
    //         <div className="notificationCol col-12 col-md-6">
    //             <h5 className="card-title">Desk <span id="idOfDeskBooked"></span> has been booked</h5>
    //         </div>
    //         <div className="notificationCol col-12 col-md-6">
    //             <a href="/user/mybookings">
    //                 <button className="myBookingButton btn btn-warning my-2 my-sm-0" type="submit">My Bookings</button>
    //             </a>
    //         </div>
    //     </div>
    // </div>








    // console.log(response);
    //
    // if(document.body.contains(document.getElementById("dateBox-1"))){
    //     document.getElementById("dateBox-1").remove();
    // }
    //
    // const div1 = document.createElement("div");
    // div1.setAttribute("class", "container-fluid mt-100");
    // div1.setAttribute("id", "dateBox-1");
    //
    // const div2 = document.createElement("div");
    // div2.setAttribute("class", "card col d-flex justify-content-center");
    //
    //
    // const div3 = document.createElement("div");
    // div3.setAttribute("class", "card-body");
    //
    // const h5 = document.createElement("h5");
    // h5.setAttribute("class", "card-title");
    //
    // if(response.length === 0){
    //     h5.innerHTML = "All desks full sir";
    // } else {
    //     h5.innerHTML = "Desk " + response[0].deskId + " booked";
    //
    // }
    //
    // div3.append(h5);
    // div2.append(div3);
    // div1.append(div2);
    // document.body.append(div1);


    // <div th:if="${fulldesk != null}" className="container-fluid mt-100" id="dateBox">
    //     <div className="card col d-flex justify-content-center">
    //         <div className="card-body">
    //             <h5 className="card-title">All desks full sir</h5>
    //         </div>
    //     </div>
    // </div>
    //
    // <div th:if="${deskBooked != null}" className="container-fluid mt-100" id="dateBox">
    //     <div className="card col d-flex justify-content-center">
    //         <div className="card-body">
    //             <h5 className="card-title">Desk <span th:text="${deskBooked}"></span> booked :)</h5>
    //         </div>
    //     </div>
    // </div>
}