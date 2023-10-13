let hover = null
let hTags = [...document.querySelectorAll('h2, h3, h4')].map((value, index) => {
    return {top: value.offsetTop, hash: `#${value.id}`}
})
let classList = ['text-emerald-600','border-l','border-emerald-600', '-ml-[1px]']

const hoverLink = (scroll) => {
    let hash = null
    hTags.forEach(value => {
        if(value.top < scroll) {
            hash = value.hash
        }
    })
    if (hash) {
        const link = document.body.querySelector(`a[href="${hash}"]`);
        link.classList.add(...classList)
        if(hover && hover.href !== link.href) {
            hover.classList.remove(...classList)
        }
        hover = link
    }
}
window.onscroll = (e) => {
    let scroll = this.scrollY + 150
    hoverLink(scroll)
}