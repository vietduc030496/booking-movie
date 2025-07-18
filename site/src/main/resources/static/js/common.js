function changeLanguage(lang) {
    const url = new URL(window.location);
    url.searchParams.set('lang', lang);
    window.location.href = url.toString();
}

// Play movie's trailer
document.querySelectorAll('.play-button').forEach(el => {
    el.addEventListener('click', function() {
        const title = this.getAttribute('data-title');
        const trailerUrl = this.getAttribute('data-trailer');
        viewTrailer(title, trailerUrl);
    });
});

function viewTrailer(movieTitle, videoUrl) {
    if (!videoUrl || videoUrl.trim() === "") {
        let msgError = document.getElementById('trailer-notfound').innerText;
        alert(msgError);
        return;
    }

    document.getElementById('trailerModalLabel').textContent ='TRAILER - ' + movieTitle;
    const iframe = document.getElementById('trailerIframe');
    iframe.src = videoUrl + "?autoplay=1";
    const modal = new bootstrap.Modal(document.getElementById('trailerModal'));
    modal.show();
}

// Pause video trailer before close modal
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('trailerModal');
    const iframe = document.getElementById('trailerIframe');

    modal.addEventListener('hidden.bs.modal', function () {
        iframe.src = '';
    });
});