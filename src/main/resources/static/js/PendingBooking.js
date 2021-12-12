const loadPendingBookings = async () => {

    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }

    let response = await fetch('/admin/getAllPending', options);
    response = await response.json();
    console.log(response);

    displayPendingBookings(response);
}

const cancelPendingBooking = async (bookingId) => {
    const params = {
        bookingId : bookingId
    }
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    }

    let response = await fetch('/admin/cancelPending', options);
    response = await response.json();

    displayPendingNotification(response, "cancel");
    await loadPendingBookings();
}

const approvePendingBooking = async (bookingId) => {
    const params = {
        bookingId : bookingId
    }
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params)
    }

    let response = await fetch('/admin/approvePending', options);
    response = await response.json();

    console.log(response);

    displayPendingNotification(response, "approve");
    await loadPendingBookings();
}

const displayPendingBookings = (jsonResponse) => {

    let pendingDiv = document.getElementById("pendingDiv")

    if(jsonResponse.length === 0){
        pendingDiv.innerHTML = "<h5>No current bookings require approval</h5>";
        return;
    }

    let htmlToAppend = "";
    for(let i = 0; i < jsonResponse.length; i++){
        const bookingId = jsonResponse[i].bookingId;
        const date = jsonResponse[i].date;
        const deskId = jsonResponse[i].deskId;
        const deskImageName = jsonResponse[i].deskImageName;
        const officeLocation = jsonResponse[i].officeLocation;
        const userBooked = jsonResponse[i].userBooked;

        htmlToAppend +=
            "        <div class=\"desk-container col-12 col-md-12 col-lg-6 col-xl-4\" id=\"bookings-container\">\n" +
            "            <div class=\"card deskCardCancel\" id=\"desk-card-"+ bookingId +"\">\n" +
            "                <div class=\"card-header deskCardCancelHeader\" id=\"cancel-card-"+ bookingId +"\">\n" +
            "                    <h5 class=\"card-text\">Desk "+ deskId +"</h5>\n" +
            "                    <div class=\"card-text row bookingCardDate\">\n" +
            "                        <span class=\"deskTagsTwo col-6\">"+ userBooked +"</span>\n" +
            "                        <span class=\"deskTagsTwo col-6\">"+ ukDateHelper(date) +"</span>\n" +
            "                        <span class=\"deskTagsTwo col-6\">"+ officeLocation +"</span>\n" +
            "                    </div>\n" +
            "                    <button class=\"bookDeskButton btn btn-warning\" id=\"cancelBookingId-"+bookingId+"\" type=\"submit\" onclick=\"cancelPendingBooking(" + bookingId + ")\">Cancel</button>\n" +
            "                    <button class=\"bookDeskButton btn btn-success\" id=\"approveBookingId-"+bookingId+"\" type=\"submit\" onclick=\"approvePendingBooking(" + bookingId + ")\">Approve</button>\n" +
            "                    <div class=\"card-title deskCardExpand\">\n" +
            "                        <button class=\"btn btn-link\" data-toggle=\"collapse\" data-target=\"#deskImg"+deskId+"\" aria-expanded=\"true\" aria-controls=\"deskImg\">\n" +
            "                            <svg width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-caret-down-fill\" viewBox=\"0 0 16 16\">\n" +
            "                                <path d=\"M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z\"></path>\n" +
            "                            </svg>\n" +
            "                        </button>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div id=\"deskImg" + deskId + "\" class=\"collapse\" aria-labelledby=\"deskCardExpand\" data-parent=\".deskListBox\">\n" +
            "                    <div class=\"card-body deskImg\">\n" +
            "                        <img src=\"/desk/" +deskId +"/"+ deskImageName + "\" class=\"card-img-top\" alt=\"\">\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>"

    }
    pendingDiv.innerHTML = htmlToAppend;

}

const displayPendingNotification = (response, cancelOrApprove) => {
    const notifDiv = document.getElementById("pendingNotification");
    if(document.body.contains(notifDiv)){
        const clonedNode = notifDiv.cloneNode(true);
        notifDiv.parentNode.replaceChild(clonedNode,notifDiv);
    }

    document.getElementById("pendingUsername").innerHTML = response.userBooked;
    document.getElementById("pendingDeskId").innerHTML = response.deskId;
    document.getElementById("pendingLocation").innerHTML = response.officeLocation;
    document.getElementById("pendingDate").innerHTML = ukDateHelper(response.date);
    document.getElementById("pendingNotification").style.display = "block";
    if(cancelOrApprove === "cancel"){
        document.getElementById("pendingCancelOrApprove").innerHTML = "cancelled.";
    }
    if(cancelOrApprove === "approve"){
        document.getElementById("pendingCancelOrApprove").innerHTML = "approved.";
    }

}