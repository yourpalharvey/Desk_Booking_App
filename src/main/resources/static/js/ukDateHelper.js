const ukDateHelper = (dateString) => {
    let dateArr = dateString.split("-");
    let dateToReturn = dateArr[2] + "/" + dateArr[1] + "/" + dateArr[0];
    return dateToReturn;
}