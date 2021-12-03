const displayDeskBooked = () => {
    console.log("hello");
    alert("Desk booked")
}

const displayDeskCancelled = () => {
    alert("Desk has been cancelled");
}


function showBookNotification() {
    document.getElementById("bookNotification").style.display = "block";
}

// function showBookNotification() {
//     document.getElementById("bookNotification").innerHTML = "<div class=\"container-fluid mt-100 notificationBox\">\n" +
//         "        <div class=\"card col d-flex justify-content-center\">\n" +
//         "            <div class=\"card-body\">\n" +
//         "                <!--TODO Insert correct date the user has selected to the notification message-->\n" +
//         "                <!--Desk <span th:text=\"${deskBooked}\"></span> booked for (Insert date selected)<span th:text=\"${startDate}\"></span>-->\n" +
//         "                <h5 class=\"card-title\">Desk <span th:text=\"${deskBooked}\"></span> has been booked</h5>\n" +
//         "                <a href=\"/user/mybookings\">\n" +
//         "                    <button class=\"myBookingButton btn btn-warning my-2 my-sm-0\" type=\"submit\">My Bookings</button>\n" +
//         "                </a>\n" +
//         "            </div>\n" +
//         "        </div>\n" +
//         "    </div>";
// }

function showCancelNotification() {
    document.getElementById("cancelNotification").style.display = "block";
}
