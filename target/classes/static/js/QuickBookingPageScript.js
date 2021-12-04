const makeQuickBooking = async () => {

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

    let response = await fetch('/user/createQuickBooking', options);
    response = await response.json();

    displayBookingDetails(response);

}

const displayBookingDetails = (response) => {
    console.log(response);

    if(document.body.contains(document.getElementById("dateBox-1"))){
        document.getElementById("dateBox-1").remove();
    }

    const div1 = document.createElement("div");
    div1.setAttribute("class", "container-fluid mt-100");
    div1.setAttribute("id", "dateBox-1");

    const div2 = document.createElement("div");
    div2.setAttribute("class", "card col d-flex justify-content-center");


    const div3 = document.createElement("div");
    div3.setAttribute("class", "card-body");

    const h5 = document.createElement("h5");
    h5.setAttribute("class", "card-title");

    if(response.length === 0){
        h5.innerHTML = "All desks full sir";
    } else {
        h5.innerHTML = "Desk " + response[0].deskId + " booked";

    }

    div3.append(h5);
    div2.append(div3);
    div1.append(div2);
    document.body.append(div1);


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