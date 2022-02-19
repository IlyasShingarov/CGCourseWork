interf_btn = document.getElementById("get_interf");
interf_btn.addEventListener('click', getInterf)

let rotate_btn = document.getElementById("rotate")
rotate_btn.addEventListener('click', getModel)

let vp = document.getElementById("vp")
let interf = document.getElementById("interferometer")

let rx = document.getElementById('x_angle')
let ry = document.getElementById('y_angle')
let rz = document.getElementById('z_angle')

let lambda = document.getElementById("wavelength");
let induction = document.getElementById("induction");
let polaroid = document.getElementById("polaroid");

function getModel() {

    vp.innerHTML = `<img src="/static/loading.gif" width="800" height="800"/>`
    fetch(`http://localhost:8080/render?x_angle=${rx.value}&y_angle=${ry.value}&z_angle=${rz.value}`)
        .then(res => res.json()).then(data => {
            vp.innerHTML = `<img src="/img/model_${data}.png" width="800" height="800"/>`
    })
}

function getInterf() {

    fetch(`http://localhost:8080/interferometer?lambda=${lambda.value}&induction=${induction.value}&polaroid=${polaroid.value}`)
        .then(res => res.json()).then(data => {
        interf.innerHTML = `<img src="/img/interf_${data}.png" width="600" height="600"/>`
    })
}