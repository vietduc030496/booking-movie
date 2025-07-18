function changeLanguage(lang) {
    const url = new URL(window.location);
    url.searchParams.set('lang', lang);
    window.location.href = url.toString();
}

document.querySelectorAll('.play-button').forEach(el => {
    el.addEventListener('click', function() {
        const title = this.getAttribute('data-title');
        const trailerUrl = this.getAttribute('data-trailer');
        viewTrailer(title, trailerUrl);
    });
});

function viewTrailer(movieTitle, videoUrl) {
    document.getElementById('trailerModalLabel').textContent ='TRAILER - ' + movieTitle;
    const iframe = document.getElementById('trailerIframe');
    iframe.src = videoUrl + "?autoplay=1";
    const modal = new bootstrap.Modal(document.getElementById('trailerModal'));
    modal.show();
}

function stopTrailer() {
    const iframe = document.getElementById('trailerIframe');
    iframe.src = ""; // dừng video khi đóng modal
    iframe.addEventListener('hidden.bs.modal', function () {
        document.getElementById('trailerFrame').src = '';
    });
}
