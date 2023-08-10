const $ = document;
let leftTimeSecond = 600
const leftTime = $.querySelector("#left-time")
const cardNumberInputs = $.querySelectorAll(".card-number");

cardNumberInputs.forEach((cardNumberInput, index) => {
    cardNumberInput.addEventListener("focus", () => {
        if(index > 0 && cardNumberInputs[index - 1].value.length < 4) cardNumberInputs[index - 1].focus()
    })

    cardNumberInput.addEventListener("input", () => {
        if(cardNumberInput.value.length === 4 && index < 3) cardNumberInputs[index + 1].focus()
        else if(cardNumberInput.value.length > 4) cardNumberInput.value = cardNumberInput.value.substring(0, 4);
    })
})

const cancelBtn = $.querySelector(".cancel-btn");
cancelBtn.addEventListener("click", (e) => {
    e.preventDefault();
    window.location.href = "/cancelpp"
})

const checkLeftTime = () => {
    if (leftTimeSecond === 0) {
        window.location.href = "/some-err-page"
    } else {
        leftTime.innerHTML = secToMin(leftTimeSecond)
        leftTimeSecond--;
    }
}

setInterval(checkLeftTime, 1000)


const secToMin = (second) => {
    let min = parseInt(second / 60);
    let leftSecond = second % 60;
    if (min < 10) min = "0" + min;
    if (leftSecond < 10) leftSecond = "0" + leftSecond
    return `${min}:${leftSecond}`
}