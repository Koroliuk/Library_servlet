function showDescription(orderId) {
    const hidden = document.getElementById("additionalInfo"+orderId).hidden;
    if (!hidden) {
        document.getElementById("additionalInfo"+orderId).hidden = true;
        document.getElementById("showLessMoreButton"+orderId).textContent = "Show more";
    } else {

        document.getElementById("additionalInfo"+orderId).hidden = false;
        document.getElementById("showLessMoreButton"+orderId).textContent = "Show less";
    }
}
