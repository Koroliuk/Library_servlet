function chooseType() {
    const orderType = document.getElementById("orderType").value;
    document.getElementById('startDate').valueAsDate = new Date();
    document.getElementById('endDate').valueAsDate = new Date();
    if (orderType === 'readingHole') {
        document.getElementById('startDate').disabled = true;
        document.getElementById('endDate').disabled = true;
    } else {
        document.getElementById('startDate').disabled = false;
        document.getElementById('endDate').disabled = false;
    }
}
