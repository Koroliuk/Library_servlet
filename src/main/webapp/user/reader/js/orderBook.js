function chooseType() {
    const orderType = document.getElementById("orderType").value;
    document.getElementById('startDate').valueAsDate = new Date();
    document.getElementById('endDate').valueAsDate = new Date();
    document.getElementById('endDate').disabled = orderType === 'readingHole';
}
