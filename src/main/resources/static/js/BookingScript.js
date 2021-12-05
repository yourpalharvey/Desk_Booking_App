const displayDeskBooked = () => {
    console.log("hello");
    alert("Desk booked")
}

const displayDeskCancelled = () => {
    alert("Desk has been cancelled");
}

// Todo - div defaults to not display and displays on button click, however as page is currently refreshing on click therefore div goes back to default
function showBookNotification() {
    const bookNot = document.getElementById("bookNotification");
    bookNot.style.display = "block";
}


function showCancelNotification() {
    const cancelNot = document.getElementById("cancelNotification");
    cancelNot.style.display = "block";
}

