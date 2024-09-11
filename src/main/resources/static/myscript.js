function getCheckElm() {
    const queary = "input[name='compare']:checked"
    const selectedElm = document.querySelectorAll(queary);

    let result = '';
    selectedElm.forEach((one) => {
        console.log(one);

    })
}
