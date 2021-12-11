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
    resetNotification();
    const officeSpanInTemplate = document.getElementById("officeSpanInTemplate");
    officeSpanInTemplate.innerText = jsonResponse[0].officeLocation;

    let mainDiv = document.getElementById("mainDiv");

    //remove mainDivDisplayed and take new copy of mainDiv
    if (document.body.contains(document.getElementById("mainDivDisplayed"))) {
        document.getElementById("mainDivDisplayed").remove()
    }
    let mainDivDisplayed = mainDiv.cloneNode(true);
    mainDivDisplayed.setAttribute("id", "mainDivDisplayed");
    mainDivDisplayed.className = "card col d-flex justify-content-center container-fluid mt-100 deskListBox";
    mainDivDisplayed.style.display = "block";

    let bookingsContainer = mainDivDisplayed.firstElementChild.nextElementSibling

    //insert new copy of mainDiv after mainDiv
    mainDiv.parentNode.insertBefore(mainDivDisplayed, mainDiv.nextSibling);

    for(let i = 0; i < jsonResponse.length; i++){
        console.log("loop  " + i);
        const deskContainerToRepeat = mainDivDisplayed.firstElementChild.nextElementSibling.firstElementChild;
        //clone element to be repeated

        const deskCardContainer = deskContainerToRepeat.cloneNode(true);

        if(i === 0){
            //on first loop, remove template card
            deskContainerToRepeat.remove();
        }

        const bookedOrUnbooked = deskCardContainer.firstElementChild;

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

        bookButton.className = "bookDeskButton btn btn-danger";
        bookButton.innerText = "Delete Desk";

        //let deskId = jsonResponse[i].deskId;
        bookButton.setAttribute("onclick", "areYouReallySure(" + jsonResponse[i].deskId + ")");

        const deskCardExpand = bookButton.nextElementSibling;

        const collapseButton = deskCardExpand.firstElementChild;

        collapseButton.setAttribute("data-target", "#deskImg" + jsonResponse[i].deskId);
        collapseButton.setAttribute("aria-expanded", "true");
        //create picture dropdown

        const imgDiv = bookedOrUnbooked.firstElementChild.nextElementSibling;

        imgDiv.setAttribute("id", "deskImg" + jsonResponse[i].deskId);


        const img = imgDiv.firstElementChild.firstElementChild;
        img.setAttribute("src","/desk/" + jsonResponse[i].deskId + "/" + jsonResponse[i].deskImageName);

        console.log(jsonResponse[i].deskImageName);

        const pillTagDiv = bookedOrUnbooked.lastElementChild;

        const firstPillTag = pillTagDiv.firstElementChild;

        firstPillTag.innerHTML = jsonResponse[i].deskType;
        firstPillTag.nextElementSibling.innerHTML = jsonResponse[i].deskPosition;
        firstPillTag.nextElementSibling.nextElementSibling.innerHTML = "Monitors: " + jsonResponse[i].monitorOption;

        bookingsContainer.appendChild(deskCardContainer);
        console.log(deskCardContainer);
    }
}

const areYouReallySure = (deskId) => {
    document.getElementById("deleteDeskNotification").style.zIndex = "-1";
    resetNotification();
    const deleteNotification = document.getElementById("deleteNotification");
    deleteNotification.style.display = "block";

    //this function makes the buttons available after 1s - because they had z-index of -1
    setTimeout(function(){
        document.getElementById("deleteDeskNotification").style.zIndex = "0";
    }, 1100)
    console.log("areYouReallySure");

    document.getElementById("deleteDeskButton").setAttribute("onclick", "sendDeleteRequest("+ deskId +")");
    document.getElementById("goBackButton").setAttribute("onclick", "resetNotification()");


}

const resetNotification = () => {

    const deleteNotification = document.getElementById("deleteNotification");
    deleteNotification.style.display="none";
    const deleteNotificationCloned = deleteNotification.cloneNode(true);

    deleteNotification.parentNode.replaceChild(deleteNotificationCloned,deleteNotification);
}

const sendDeleteRequest = async (deskId) => {

    const params = {
        deskId : deskId
    }

    const options = {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    }

    let response = await fetch('/admin/deleteDesk', options);
    response = await response.json();

    console.log(response);
    displayAllBookingsCancelled(response);
    resetNotification();


}

displayAllBookingsCancelled = (response) => {
    document.getElementById("userDeskBookingCancelled")

    let htmlToDisplay = "";

    for (let i = 0; i < response.length; i++){
        htmlToDisplay += "<div class=\"card deskCard\">\n" +
            "                    <div class=\"card-header\">\n" +
            "                        <h5 class=\"userDeskBookingCancelled\">"+response[i].userBooked+"'s booking for desk"+response[i].deskId +" has been cancelled\n" +
            "                            for "+ukDateHelper(response[i].date)+"</h5>\n" +
            "                    </div>\n" +
            "                </div>"
    }

    document.getElementById("userDeskBookingCancelled").innerHTML = htmlToDisplay;
    document.getElementById("deletedBookingTemplate").style.display = "block";
    loadAllDesksForAdmin();
}









