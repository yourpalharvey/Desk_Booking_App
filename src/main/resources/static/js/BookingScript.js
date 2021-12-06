const displayDeskBooked = () => {
    console.log("hello");
    alert("Desk booked")
}

const displayDeskCancelled = () => {
    alert("Desk has been cancelled");
}

// Todo - div defaults to not display and displays on button click, however as page is currently refreshing on click therefore div goes back to default
function showBookNotification(deskId) {
    const bookNot = document.getElementById("bookNotification");

    const bookNot2 = bookNot.cloneNode(true);
    bookNot.parentNode.replaceChild(bookNot2,bookNot);

    bookNot2.style.display = "block";
    document.getElementById("deskIdNot").innerText = deskId;

}


function showCancelNotification() {
    const cancelNot = document.getElementById("cancelNotification");
    cancelNot.style.display = "block";
}

