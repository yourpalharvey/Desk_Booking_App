const displayDeskBooked = () => {
    console.log("hello");
    alert("Desk booked")
}

const displayDeskCancelled = () => {
    alert("Desk has been cancelled");
}

const loadUserBookings = async () => {

    let response = await fetch('/user/getMyBookings');
    response = await response.json();

    console.log(response);
    displayUserBookings(response);

}

const displayUserBookings = (jsonResponse) => {

    console.log("HLSADKHASLKDSA")
    console.log(jsonResponse);

    const div1 = document.createElement("div");
    div1.setAttribute('class', "card col d-flex justify-content-center container-fluid mt-100 deskListBox");

    const div2 = document.createElement("div");
    div2.setAttribute('class', "card-body");

    const div3 = document.createElement("div");
    div3.setAttribute('class', "row row-cols-1 row-cols-sm-2 row-cols-md-4");

    for(let i = 0; i < jsonResponse.length; i++) {


        console.log(jsonResponse[i].deskId);

        const div4 = document.createElement("div");
        div4.setAttribute('class', "col");

        const div5 = document.createElement("div");
        div5.setAttribute('class', "card deskCardCancel");

        const div6 = document.createElement("div");
        div6.setAttribute('class', "card-header deskCardExpand");

        const heading = document.createElement("h5");
        heading.setAttribute('class', "card-text");
        const deskTestNode = document.createTextNode("Desk " + jsonResponse[i].deskId);
        heading.append(deskTestNode);

        const datePara = document.createElement("p");
        const dateTextNode = document.createTextNode(jsonResponse[i].date);
        datePara.append(dateTextNode);

        const cancelButton = document.createElement("button");
        cancelButton.setAttribute("class", "bookDeskButton btn btn-warning my-2 my-sm-0");
        cancelButton.type = "submit";
        const cancelButtonText = document.createTextNode("Cancel");
        cancelButton.append(cancelButtonText);

        //create picture dropdown
        const displayPictureButton = document.createElement("button");
        displayPictureButton.setAttribute("class", "btn btn-link");
        displayPictureButton.setAttribute("data-toggle", "collapse");
        displayPictureButton.setAttribute("data-target", "#deskFourImg");
        displayPictureButton.setAttribute("aria-expanded", "true");
        displayPictureButton.setAttribute("aria-controls", "deskImg");

        const svgDeskPic = document.createElement("svg");
        svgDeskPic.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        svgDeskPic.setAttribute("width", "16");
        svgDeskPic.setAttribute("height", "16");
        svgDeskPic.setAttribute("fill", "currentColor");
        svgDeskPic.setAttribute("class", "bi bi-caret-down-fill");
        svgDeskPic.setAttribute("viewBox", "0 0 16 16");

        const svgPath = document.createElement("path");
        svgPath.setAttribute("d", "M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z");


        const imgDiv = document.createElement("div");
        imgDiv.setAttribute("id", "deskFourImg");
        imgDiv.setAttribute("class", "collapse");
        imgDiv.setAttribute("aria-labelledby", "deskCardExpand");
        imgDiv.setAttribute("data-parent", ".deskListBox");

        const imgDiv2 = document.createElement("div");
        imgDiv2.setAttribute("class", "card-body");

        const img = document.createElement("img");
        img.setAttribute("th:src", "@{/images/standing.jpg}");
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
    div2.append(div3);
    div1.append(div2);

    document.body.append(div1);


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
